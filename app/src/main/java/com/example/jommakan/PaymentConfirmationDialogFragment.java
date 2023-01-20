package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * A dialog fragment that is responsible for displaying and managing make-payment process
 * It act as a pop up window
 * It allows users to make payment for an order
 */
public class PaymentConfirmationDialogFragment extends DialogFragment {

    /**
     * A text view that is used to display order pickup time
     */
    TextView pick_up_time;

    /**
     * A text view that is used to display total price of an order
     */
    TextView total_price_text_view;

    /**
     * A text view that is used to display account wallet balance
     */
    TextView wallet_balance;

    /**
     * A button that is used to close the pop up window
     */
    Button cancel_button;

    /**
     * A button that is used to confirm the payment
     */
    Button confirm_button;

    /**
     * An instance of OrderDatabase
     */
    OrderDatabase orderDatabase;

    /**
     * An instance of CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * An instance of UserDatabase
     */
    UserDatabase userDatabase;

    /**
     * A cart item object that is used to store information about cart item
     */
    CartItem cartItem;

    /**
     * A string that is used to store location name of the order
     */
    String location;

    /**
     * A string that is used to store stall name of the order
     */
    String stall;

    /**
     * A double that is used to store the total price of an order
     */
    Double total_price;

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_confirmation_dialog, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pick_up_time = view.findViewById(R.id.pick_up_time);
        total_price_text_view = view.findViewById(R.id.total_price_text_view);
        wallet_balance = view.findViewById(R.id.wallet_balance);
        cancel_button = view.findViewById(R.id.cancel_button);
        confirm_button = view.findViewById(R.id.confirm_button);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(getActivity(), CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
        orderDatabase = Room.databaseBuilder(getActivity(), OrderDatabase.class, "OrderDB").allowMainThreadQueries().build();
        userDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        cartItem = (CartItem) getArguments().getSerializable("cart_item");
        total_price = getArguments().getDouble("total_price");

        location = cartItem.getLocation();
        stall = cartItem.getStall();

        // Display pickup time, total price of the order and account wallet balance
        pick_up_time.setText(getEstimatedTime());
        total_price_text_view.setText("RM "  + String.format("%.2f", total_price));
        wallet_balance.setText("RM "  + String.format("%.2f", UserInstance.getWallet_balance()));

        cancel_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Close the payment confirmation pop up window after clicking on it
             * @param v
             */
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Confirm the payment and close the pop up window after clicking on it if it satisfies all the if-else conditions in the mehtod
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (UserInstance.getWallet_balance() >= total_price) {
                    // If the wallet balance is sufficient
                    removeCartItem();
                    addToOrderHistory();
                    updateWalletBalance(total_price);

                    NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_container);
                    navController.popBackStack(R.id.DestCart, true);
                    navController.navigate(R.id.DestCart);
                    Toast.makeText(getActivity(), "Your purchase was successful. Please be sure to pick up your order at the estimated time.", Toast.LENGTH_SHORT).show();
                } else {
                    // If the wallet balance is not sufficient
                    Toast.makeText(getActivity(), "Your balance is insufficient. Please top up your wallet to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Remove cart item from Cart Page after users have made payment
     */
    private void removeCartItem() {
        try {
            cartItemDatabase.cartItemDAO().deleteCartItem(UserInstance.getUser_email_address(), location, stall);

        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            cartItemDatabase.close();
        }
    }

    /**
     * Add a new order to Order History page after users have made payment
     */
    private void addToOrderHistory() {

        // Randomly generate a number for an order as an order number
        Random random = new Random();
        int min = 100000000;
        int max = 999999999;
        int randomNumber = random.nextInt((max - min) + 1) + min;

        try {
            orderDatabase.orderDAO().insert(new Order(UserInstance.getUser_email_address(), randomNumber, location, stall, cartItem.getCart_food_list(), getCurrentDate(), getCurrentTime()));
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            orderDatabase.close();
        }
    }

    /**
     * Update account wallet balance after users have made payment
     * Account wallet balance will be deducted
     * @param total_price double
     */
    private void updateWalletBalance(double total_price) {
        double wallet_balance = UserInstance.getWallet_balance() - total_price;
        UserInstance.setWallet_balance(wallet_balance);

        try {
            userDatabase.userDAO().updateWalletBalance(wallet_balance, UserInstance.getUser_email_address());
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            userDatabase.close();
        }
    }

    /**
     * Get the current date as it will be used as the order date for an order
     * @return String
     */
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        return dateFormat.format(currentDate);
    }

    /**
     * Get the current time as it will be used as the order time for an order
     * @return String
     */
    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm a");
        return timeFormat.format(currentTime);
    }

    /**
     * Get estimated pickup time for an order
     * @return String
     */
    private String getEstimatedTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        Date estimatedTime = calendar.getTime();

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm a");
        return timeFormat.format(estimatedTime);
    }
}