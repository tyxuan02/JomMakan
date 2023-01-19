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
import androidx.room.Room;

/**
 * A dialog fragment that is responsible for displaying and managing forgot-password process
 * It act as a pop up window
 * It allows the users to reset their account password by entering new account password
 */
public class ForgotPasswordDialogFragment3 extends DialogFragment {

    /**
     * An edit text view that allows users to enter new password
     */
    EditText new_password_text_view;

    /**
     * An edit text view that allows users to enter confirm password
     */
    EditText confirm_password_text_view;

    /**
     * A button that allows users to close the pop up window
     */
    Button cancel_button;

    /**
     * A button that allows user to submit their new account password
     */
    Button confirm_button;

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
        View view = inflater.inflate(R.layout.fragment_forgot_password_dialog3, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new_password_text_view = view.findViewById(R.id.new_password_text_view);
        confirm_password_text_view = view.findViewById(R.id.confirm_password_text_view);
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
                dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Save new account password to database after clicking on it if it satisfy all conditions in the method
             * @param v view
             */
            @Override
            public void onClick(View v) {
                if (isValidPassword(new_password_text_view.getText().toString())) {
                    if (isMatchedPassword(new_password_text_view.getText().toString(), confirm_password_text_view.getText().toString())) {

                        try {
                            String email_address = getArguments().getString("email_address");
                            userDatabase.userDAO().changePassword(email_address, new_password_text_view.getText().toString());
                            Toast.makeText(getActivity(), "Your password was successfully changed.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } catch (SQLiteException e) {
                            // Handle errors
                            e.printStackTrace();
                        } finally {
                            // Close the database connection
                            userDatabase.close();
                        }
                    }
                }
            }
        });
    }

    /**
     * //Check the validity of password
     * @param Pass new password
     * @return boolean
     */
    private boolean isValidPassword(String Pass){
        if (Pass.isEmpty()) {
            Toast.makeText(getActivity(), "Invalid password. Password cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Pass.matches(".*[^a-zA-Z0-9].*") || !Pass.matches(".*[a-z].*") || !Pass.matches(".*[A-Z].*") || !Pass.matches(".*[0-9].*") || Pass.length() < 8){
            Toast.makeText(getActivity(), "Password must be at least 8 characters long and include a combination of numbers, uppercase letters, and special characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * //Check whether the password and confirm password are matched
     * @param Pass new password
     * @param ConfirmPass confirm password
     * @return boolean
     */
    private boolean isMatchedPassword(String Pass, String ConfirmPass){
        if(!Pass.equals(ConfirmPass)){
            Toast.makeText(getActivity(), "The confirm password field does not match the password field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}