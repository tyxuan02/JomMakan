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

public class ForgotPasswordDialogFragment3 extends DialogFragment {

    EditText new_password_text_view;
    EditText confirm_password_text_view;
    Button cancel_button;
    Button confirm_button;
    UserDatabase userDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password_dialog3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new_password_text_view = view.findViewById(R.id.new_password_text_view);
        confirm_password_text_view = view.findViewById(R.id.confirm_password_text_view);
        cancel_button = view.findViewById(R.id.cancel_button);
        confirm_button = view.findViewById(R.id.confirm_button);

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
                if (isValidPassword(new_password_text_view.getText().toString())) {
                    if (isMatchedPassword(new_password_text_view.getText().toString(), confirm_password_text_view.getText().toString())) {
                        String email_address = getArguments().getString("email_address");

                        try {
                            userDatabase.userDAO().changePassword(email_address, new_password_text_view.getText().toString());
                        } catch (SQLiteException e) {
                            // Handle errors
                            e.printStackTrace();
                        } finally {
                            // Close the database connection
                            userDatabase.close();
                        }

                        Toast.makeText(getActivity(), "Your password was successfully changed.", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }
        });
    }

    private boolean isValidPassword(String Pass){
        //Check the validity of password
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

    private boolean isMatchedPassword(String Pass, String ConfirmPass){
        //Check whether the password and confirm password are matched
        if(!Pass.equals(ConfirmPass)){
            Toast.makeText(getActivity(), "The confirm password field does not match the password field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}