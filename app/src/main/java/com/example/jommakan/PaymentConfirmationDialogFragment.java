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

public class PaymentConfirmationDialogFragment extends DialogFragment {

    TextView pick_up_time;
    TextView total_price_text_view;
    TextView wallet_balance;
    Button cancel_button;
    Button confirm_button;
    OrderDatabase orderDatabase;
    CartItemDatabase cartItemDatabase;
    UserDatabase userDatabase;
    CartItem cartItem;
    String location;
    String stall;
    Double total_price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_confirmation_dialog, container, false);
        return view;
    }

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

        pick_up_time.setText(getEstimatedTime());
        total_price_text_view.setText("RM "  + String.format("%.2f", total_price));
        wallet_balance.setText("RM "  + String.format("%.2f", UserHolder.getWallet_balance()));

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserHolder.getWallet_balance() >= total_price) {
                    // If user has sufficient wallet balance
                    removeCartItem();
                    addToOrderHistory();
                    updateWalletBalance(total_price);

                    NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_container);
                    navController.popBackStack(R.id.DestCart, true);
                    navController.navigate(R.id.DestCart);
                    Toast.makeText(getActivity(), "Your purchase was successful. Please be sure to pick up your order at the estimated time.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Your balance is insufficient. Please top up your wallet to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Remove cart item from cart
    private void removeCartItem() {
        try {
            cartItemDatabase.cartItemDAO().deleteCartItem(UserHolder.getUser_email_address(), location, stall);

        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            cartItemDatabase.close();
        }
    }

    // Add a new order to order history
    private void addToOrderHistory() {
        Random random = new Random();
        int min = 100000000;
        int max = 999999999;
        int randomNumber = random.nextInt((max - min) + 1) + min;

        try {
            orderDatabase.orderDAO().insert(new Order(UserHolder.getUser_email_address(), randomNumber, location, stall, cartItem.getCart_food_list(), getCurrentDate(), getCurrentTime()));
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            orderDatabase.close();
        }
    }

    // Update user wallet balance
    private void updateWalletBalance(double total_price) {
        double wallet_balance = UserHolder.getWallet_balance() - total_price;
        UserHolder.setWallet_balance(wallet_balance);

        try {
            userDatabase.userDAO().updateWalletBalance(wallet_balance, UserHolder.getUser_email_address());
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            userDatabase.close();
        }
    }

    // Get current date
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        return dateFormat.format(currentDate);
    }

    // Get current time
    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm a");
        return timeFormat.format(currentTime);
    }

    // Get estimated pickup time
    private String getEstimatedTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        Date estimatedTime = calendar.getTime();

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm a");
        return timeFormat.format(estimatedTime);
    }
}