package com.example.jommakan;

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
import androidx.fragment.app.FragmentTransaction;

import javax.mail.MessagingException;

/**
 * A dialog fragment that is responsible for displaying and managing forgot-password process
 * It act as a pop up window
 * It allows the users to enter the verification code that sent to user gmail for reset password purpose
 */
public class ForgotPasswordDialogFragment2 extends DialogFragment {

    /**
     * A button that allows users to close the pop up window
     */
    Button cancel_button;

    /**
     * A button that allows users to submit the verification code
     */
    Button confirm_button;

    /**
     * An edit text view that allows users to enter verification code
     */
    EditText verification_code_edit_text_view;

    /**
     * A string that is used to store user email address
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
        View view = inflater.inflate(R.layout.fragment_forgot_password_dialog2, container, false);
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
        confirm_button = view.findViewById(R.id.confirm_button);
        cancel_button = view.findViewById(R.id.cancel_button);

        // Get data passed from previous dialog fragment
        email_address = getArguments().getString("email_address");

        // Send verification code
        new SendEmailTask().execute();

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
             * Direct users to next pop up window after clicking on it if it satisfy all if-else conditions in the method
             * @param v view
             */
            @Override
            public void onClick(View v) {
                if (verification_code_edit_text_view.getText().toString().equals(verification_code)) {
                    dismiss();

                    // Pass user email address to the next dialog fragment using bundle
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

    /**
     * Send verification code to user Gmail
     */
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