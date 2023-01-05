package com.example.jommakan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordDialogFragment extends DialogFragment {
    
    Button cancel_button;
    Button send_code_button;
    TextView email_address_text_view;
    UserDatabase userDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_forgot_password_dialog, container, false);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        email_address_text_view = view.findViewById(R.id.email_address_text_view);
        cancel_button = view.findViewById(R.id.cancel_button);
        send_code_button = view.findViewById(R.id.send_code_button);
        
        // Database connection
        userDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "UserDB").allowMainThreadQueries().build();
        
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
        send_code_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_address_text_view.getText().toString().isEmpty()) {
                    // If users didn't enter any email address
                    Toast.makeText(getActivity(), "Please enter your email address.", Toast.LENGTH_SHORT).show();
                } else if (userDatabase.userDAO().checkIfUserExist(String.valueOf(email_address_text_view.getText())) == null) {
                    // If the email address entered by the user is not registered yet
                    Toast.makeText(getActivity(), "The email address you provided is not associated with any account in our application.", Toast.LENGTH_SHORT).show();
                } else {
                    // If users enter a correct email address
                    Toast.makeText(getActivity(), "A verification code has been sent to your Gmail account. Please use it to reset your password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}