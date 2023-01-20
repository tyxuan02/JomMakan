package com.example.jommakan;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
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

import javax.mail.MessagingException;

/**
 * A dialog fragment that is responsible for displaying and managing get-verification-code process
 * It act as a pop up window
 * It will send a verification code to user gmail for account registration process
 */
public class VerificationCodeForRegistrationDialogFragment extends DialogFragment {

    /**
     * An edit text view that allows users to enter verification code
     */
    EditText verification_code_edit_text_view;

    /**
     * A button that is used to close the pop up window after clicking on it
     */
    Button cancel_button;

    /**
     * A button that is used to create an account for users after clicking on it
     */
    Button confirm_button;

    /**
     * An instance of UserDatabase
     */
    UserDatabase userDatabase;

    /**
     * A string that is used to store email address
     */
    String email_address;

    /**
     * A string that is used to store verification code
     */
    String verification_code;

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
        View view = inflater.inflate(R.layout.fragment_verificantion_code_for_registration_dialog, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        verification_code_edit_text_view = view.findViewById(R.id.verification_code_edit_text_view);
        cancel_button = view.findViewById(R.id.cancel_button);
        confirm_button = view.findViewById(R.id.confirm_button);

        // Database connection
        userDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        cancel_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Close the pop up window after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Account creation failed.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        email_address = getArguments().getString("email_address");
        String username = getArguments().getString("username");
        String password = getArguments().getString("password");
        String phone_number = getArguments().getString("phone_number");
        new SendEmailTask().execute();

        confirm_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Create an account for users after clicking on it if it satisfies all the if-else conditions in this method
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (!verification_code_edit_text_view.getText().toString().isEmpty()) {
                    // If users enter a verification code
                    if (verification_code_edit_text_view.getText().toString().equals(verification_code)) {
                        // If the verification code entered by users match the verification code generated
                        createNewAccount(email_address, username, password, phone_number);
                        Toast.makeText(getActivity(), "Your account has been successfully created", Toast.LENGTH_SHORT).show();
                        dismiss();
                        Intent intent = new Intent(getActivity(), LoginPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Incorrect verification code.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter the verification code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Create a new account for users
     * @param email_address email address
     * @param username username
     * @param password password
     * @param phone_number phone number
     */
    private void createNewAccount(String email_address, String username, String password, String phone_number) {
        User user = new User(email_address, username, password, phone_number);

        try {
            userDatabase.userDAO().insertUser(user);
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            userDatabase.close();
        }
    }

    /**
     * Send verification code to user gmail
     */
    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Email email = new Email();
            try {
                email.sendVerificationEmail(email_address, "user");
                verification_code = email.getVerificationCode();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}