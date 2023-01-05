package com.example.jommakan;

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
import androidx.fragment.app.FragmentTransaction;

import javax.mail.MessagingException;

public class ForgotPasswordDialogFragment2 extends DialogFragment {

    Button cancel_button;
    Button confirm_button;
    TextView verification_code_text_view;
    String email_address;
    String verification_code;
    Email email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password_dialog2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        verification_code_text_view = view.findViewById(R.id.verification_code_text_view);
        confirm_button = view.findViewById(R.id.confirm_button);
        cancel_button = view.findViewById(R.id.cancel_button);

        // Get data passed from previous dialog fragment
        email_address = getArguments().getString("email_address");

        // Send verification code
        new SendEmailTask().execute();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verification_code_text_view.getText().toString().equals(verification_code)) {
                    dismiss();

                    Bundle bundle = new Bundle();
                    bundle.putString("email_address", email_address);
                    ForgotPasswordDialogFragment3 forgotPasswordDialogFragment3 = new ForgotPasswordDialogFragment3();
                    forgotPasswordDialogFragment3.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    forgotPasswordDialogFragment3.show(transaction, "Forgot Password Fragment 3");
                } else {
                    Toast.makeText(getActivity(), "Please enter correct verification code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Send verification code to user Gmail
    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Email email = new Email();
            try {
                email.sendVerificationEmail(email_address, "forgetPassword");
                verification_code = email.getVerificationCode();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}