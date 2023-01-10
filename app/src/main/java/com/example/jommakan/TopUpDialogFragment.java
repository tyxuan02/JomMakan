package com.example.jommakan;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;


public class TopUpDialogFragment extends DialogFragment {
    EditText input_top_up_amount, input_verified_password;
    Button confirm_button, cancel_button;
    UserDatabase userDatabase;
    double sumOfTopUpAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_up_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        input_top_up_amount = view.findViewById(R.id.top_up_amount);
        input_verified_password = view.findViewById(R.id.verified_topup_password);
        confirm_button = view.findViewById(R.id.confirm_button);
        cancel_button = view.findViewById(R.id.cancel_button);

        // Database connection
        userDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(input_top_up_amount.getText()).isEmpty() || String.valueOf(input_verified_password.getText()).isEmpty()){
                    Toast.makeText(getActivity(), "Please fill out the empty field!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (String.valueOf(input_verified_password.getText()).equals(UserHolder.getPassword())) {
                        Toast.makeText(getActivity(), "Top up successfully.", Toast.LENGTH_SHORT).show();
                        sumOfTopUpAmount = UserHolder.getWallet_balance();
                        sumOfTopUpAmount = sumOfTopUpAmount + Double.parseDouble(String.valueOf(input_top_up_amount.getText()));
                        UserHolder.setWallet_balance(sumOfTopUpAmount);

                        try {
                            userDatabase.userDAO().updateWalletBalance(UserHolder.getWallet_balance(), UserHolder.getUser_email_address());
                        } catch (SQLiteException e) {
                            // Handle errors
                            e.printStackTrace();
                        } finally {
                            // Close the database connection
                            userDatabase.close();
                        }

                        // Close the top up window
                        dismiss();

                        // Navigate back to My Wallet Page
                        Intent intent = new Intent(getActivity(), MyWalletActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getActivity(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
