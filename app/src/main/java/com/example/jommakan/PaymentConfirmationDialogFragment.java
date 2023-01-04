package com.example.jommakan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PaymentConfirmationDialogFragment extends DialogFragment {

    TextView pick_up_time;
    TextView wallet_balance;
    Button cancel_button;
    Button confirm_button;
    OrderDatabase orderDatabase;
    CartItemDatabase cartItemDatabase;
    String location;
    String stall;
    CartItem cartItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_confirmation_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pick_up_time = view.findViewById(R.id.pick_up_time);
        wallet_balance = view.findViewById(R.id.wallet_balance);
        cancel_button = view.findViewById(R.id.cancel_button);
        confirm_button = view.findViewById(R.id.confirm_button);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(getActivity(), CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
        orderDatabase = Room.databaseBuilder(getActivity(), OrderDatabase.class, "OrderDB").allowMainThreadQueries().build();

        cartItem = (CartItem) getArguments().getSerializable("cart_item");
        location = cartItem.getLocation();
        stall = cartItem.getStall();

        // ToDo
        pick_up_time.setText("");
        wallet_balance.setText("RM 100");

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ToDo: check user wallet balance before make payment
                removeCartItem();
                addToOrderHistory();

                NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_container);
                navController.popBackStack(R.id.DestCart, true);
                navController.navigate(R.id.DestCart);

                // Toast.makeText(getActivity(), "You don't have enough balance", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeCartItem() {
        cartItemDatabase.cartItemDAO().deleteCartItem("user@gmail.com", location, stall);
    }

    private void addToOrderHistory() {
        Random random = new Random();
        int min = 100000000;
        int max = 999999999;
        int randomNumber = random.nextInt((max - min) + 1) + min;

        orderDatabase.orderDAO().insert(new Order("user@gmail.com", randomNumber, location, stall, cartItem.getCart_food_list(), getCurrentDate(), getCurrentTime()));
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        return dateFormat.format(currentDate);
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm a");
        return timeFormat.format(currentTime);
    }
}