package com.example.jommakan;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

/**
 * A dialog fragment that is responsible for displaying and managing forgot-password process
 * It act as a pop up window
 * It allows the users to enter their user email addresses for reset password purpose
 */
public class ForgotPasswordDialogFragment1 extends DialogFragment {

    /**
     * A button that allows users to close the pop up window
     */
    Button cancel_button;

    /**
     * A button that allows users to send code to the user gmail
     */
    Button send_code_button;

    /**
     * An edit text view that allows users to enter their email addresses
     */
    EditText email_address_edit_text_view;

    /**
     * An instance of the class userDatabase
     */
    UserDatabase userDatabase;

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
       View view = inflater.inflate(R.layout.fragment_forgot_password_dialog, container, false);
       return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        email_address_edit_text_view = view.findViewById(R.id.email_address_edit_text_view);
        cancel_button = view.findViewById(R.id.cancel_button);
        send_code_button = view.findViewById(R.id.send_code_button);
        
        // Database connection
        userDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "UserDB").allowMainThreadQueries().build();
        
        cancel_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Close the pop up window after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
        send_code_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Send code to user gmail and direct user to next forgot password pop up window after clicking on it if it satisfy all the if-else conditions in the method
             * @param v view
             */
            @Override
            public void onClick(View v) {
                try {
                    if (email_address_edit_text_view.getText().toString().isEmpty()) {
                        // If users didn't enter any email address
                        Toast.makeText(getActivity(), "Please enter your email address.", Toast.LENGTH_SHORT).show();
                    } else if (userDatabase.userDAO().checkIfUserExist(String.valueOf(email_address_edit_text_view.getText())) == null) {
                        // If the email address entered by the user is not registered yet
                        Toast.makeText(getActivity(), "The email address you provided is not associated with any account in our application.", Toast.LENGTH_SHORT).show();
                    } else {
                        // If users enter a correct email address
                        // Close current dialog fragment
                        dismiss();

                        Bundle bundle = new Bundle();
                        bundle.putString("email_address", email_address_edit_text_view.getText().toString());
                        ForgotPasswordDialogFragment2 forgotPasswordDialogFragment2 = new ForgotPasswordDialogFragment2();
                        forgotPasswordDialogFragment2.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        forgotPasswordDialogFragment2.show(transaction, "Forgot Password Fragment 2");
                        Toast.makeText(getActivity(), "A verification code has been sent to your Gmail account. Please use it to reset your account password.", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLiteException e) {
                    // Handle errors
                    e.printStackTrace();
                } finally {
                    // Close the database connection
                    userDatabase.close();
                }
            }
        });
    }
}