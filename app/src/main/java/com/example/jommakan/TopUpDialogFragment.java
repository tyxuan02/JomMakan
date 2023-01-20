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

/**
 * A dialog fragment that is responsible for displaying and managing wallet-top-up process
 * It act as a pop up window
 * It allows users to top up wallet balance
 */
public class TopUpDialogFragment extends DialogFragment {

    /**
     * An edit text view that allows users to enter amount to top up
     */
    EditText input_top_up_amount;

    /**
     * An edit text view that allows users to enter account password
     */
    EditText input_verified_password;

    /**
     * A button that allows users to top up their account wallets after clicking on it
     */
    Button confirm_button;

    /**
     * A button that allows users to close the top up pop up window after clicking on it
     */
    Button cancel_button;

    /**
     * An instance of UserDatabase
     */
    UserDatabase userDatabase;

    /**
     * A double that is used to store new wallet balance after top up
     */
    double sumOfTopUpAmount;

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
        View view = inflater.inflate(R.layout.fragment_top_up_dialog, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        input_top_up_amount = view.findViewById(R.id.top_up_amount);
        input_verified_password = view.findViewById(R.id.verified_topup_password);
        confirm_button = view.findViewById(R.id.confirm_button);
        cancel_button = view.findViewById(R.id.cancel_button);

        // Database connection
        userDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        cancel_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Close the top up pop up window after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Allows users to top up their account wallets after clicking on it if it satisfies all the if-else condition in this method
             * @param v view
             */
            @Override
            public void onClick(View v) {
                if(String.valueOf(input_top_up_amount.getText()).isEmpty() || String.valueOf(input_verified_password.getText()).isEmpty()){
                    // If users enter nothing
                    Toast.makeText(getActivity(), "Please fill out the empty field!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (String.valueOf(input_verified_password.getText()).equals(UserInstance.getPassword())) {
                        // If users enter correct account password, top up the account wallet
                        Toast.makeText(getActivity(), "Top up successfully.", Toast.LENGTH_SHORT).show();
                        sumOfTopUpAmount = UserInstance.getWallet_balance();
                        sumOfTopUpAmount = sumOfTopUpAmount + Double.parseDouble(String.valueOf(input_top_up_amount.getText()));
                        UserInstance.setWallet_balance(sumOfTopUpAmount);

                        try {
                            userDatabase.userDAO().updateWalletBalance(UserInstance.getWallet_balance(), UserInstance.getUser_email_address());
                        } catch (SQLiteException e) {
                            // Handle errors
                            e.printStackTrace();
                        } finally {
                            // Close the database connection
                            userDatabase.close();
                        }

                        // Close the top up pop up window
                        dismiss();

                        // Direct users back to My Wallet Page
                        Intent intent = new Intent(getActivity(), MyWalletActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        // If users enter incorrect password
                        Toast.makeText(getActivity(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
