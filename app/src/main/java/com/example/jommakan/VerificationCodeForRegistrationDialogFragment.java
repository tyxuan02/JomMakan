package com.example.jommakan;

import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.room.Room;

import javax.mail.MessagingException;

public class VerificationCodeForRegistrationDialogFragment extends DialogFragment {

    TextView verification_code_text_view;
    Button cancel_button;
    Button confirm_button;
    UserDatabase userDatabase;
    String email_address;
    String verification_code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verificantion_code_for_registration_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        verification_code_text_view = view.findViewById(R.id.verification_code_text_view);
        cancel_button = view.findViewById(R.id.cancel_button);
        confirm_button = view.findViewById(R.id.confirm_button);

        // Database connection
        userDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        cancel_button.setOnClickListener(new View.OnClickListener() {
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
            @Override
            public void onClick(View v) {
                if (!verification_code_text_view.getText().toString().isEmpty()) {
                    if (verification_code_text_view.getText().toString().equals(verification_code)) {
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

    // Create a new account
    private void createNewAccount(String email_address, String username, String password, String phone_number) {
        User user = new User(email_address, username, password, phone_number);
        userDatabase.userDAO().insertUser(user);
    }

    // Send verification code to user Gmail
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