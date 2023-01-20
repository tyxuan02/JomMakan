package com.example.jommakan;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An activity that is responsible for displaying Registration Page
 */
public class RegistrationPage extends AppCompatActivity {

    /**
     * A button that is used to create an account for users after clicking on it
     */
    Button signUp_button;

    /**
     * A text view that allows users to click and it wil direct users to login page
     */
    TextView signIn_text_view;

    /**
     * An edit text view that allows users to enter username
     */
    EditText username_edit_text;

    /**
     * An edit text view that allows users to enter email address
     */
    EditText email_edit_text;

    /**
     * An edit text view that allows users to enter phone number
     */
    EditText phoneNumber_edit_text;

    /**
     * An edit text view that allows users to enter new password
     */
    EditText newPassword_edit_text;

    /**
     * An edit text view that allows users to enter confirm password
     */
    EditText confirmPassword_edit_text;

    /**
     * An instance of UserDatabase
     */
    UserDatabase userDatabase;

    /**
     * This method is used to set up the initial state of the activity, such as initializing variables and setting the layout for the activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        // Database connection
        userDatabase = Room.databaseBuilder(this, UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        signUp_button = findViewById(R.id.signUp_button);
        signIn_text_view = findViewById(R.id.sign_in_text_view);
        username_edit_text = findViewById(R.id.username_edit_text);
        email_edit_text = findViewById(R.id.email_edit_text);
        phoneNumber_edit_text = findViewById(R.id.phoneNumber_edit_text);
        newPassword_edit_text = findViewById(R.id.newPassword_edit_text);
        confirmPassword_edit_text = findViewById(R.id.confirmPassword_edit_text);

        signUp_button.setOnClickListener(new View.OnClickListener() {

            /**
             * It will display a pop up window that allows users to enter a verification code that has been sent to user gmail  after clicking on it if it satisfies all the if-else conditions in this method
             * @param v view
             */
            @Override
            public void onClick(View v) {
                String Username = username_edit_text.getText().toString();
                String Email = email_edit_text.getText().toString();
                String PhoneNumber = phoneNumber_edit_text.getText().toString();
                String NewPassword = newPassword_edit_text.getText().toString();
                String ConfirmPassword = confirmPassword_edit_text.getText().toString();

                if (isValidUsername(Username)) {
                    // If the username entered is valid
                    if (isValidEmail(Email)) {
                        // If the email address entered is valid
                        if (isValidPhoneNumber(PhoneNumber)) {
                            // If the phone number entered is valid
                            if (isValidPassword(NewPassword)) {
                                // If the new password entered is valid
                                if (isMatchedPassword(NewPassword, ConfirmPassword)) {
                                    // If the new password entered matches the confirm password entered
                                    if (!isEmailInUse(Email)) {
                                        // If the email address entered is not registered in this app yet

                                        // Pass email address, username, password and phone number to VerificationCodeForRegistrationDialogFragment
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email_address", Email);
                                        bundle.putString("username", Username);
                                        bundle.putString("password", NewPassword);
                                        bundle.putString("phone_number", PhoneNumber);

                                        VerificationCodeForRegistrationDialogFragment verificationCodeForRegistrationDialogFragment = new VerificationCodeForRegistrationDialogFragment();
                                        verificationCodeForRegistrationDialogFragment.setArguments(bundle);
                                        verificationCodeForRegistrationDialogFragment.show(getSupportFragmentManager(), "Verification Code for Registration Fragment");
                                        Toast.makeText(RegistrationPage.this, "A verification code has been sent to your account. Please enter this code to complete the account registration.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If the email address entered is already registered in this app
                                        Toast.makeText(RegistrationPage.this, "The email address you entered is already registered. Please use a different email address", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        signIn_text_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Direct users to login page after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationPage.this,LoginPage.class));
            }
        });
    }

    /**
     * Check the validity of username
     * @param Username username entered
     * @return boolean
     */
    private boolean isValidUsername(String Username){
        if(TextUtils.isEmpty(Username)){
            //The username is empty
            Toast.makeText(this, "Invalid username. The username can't be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(Username.length() < 3 || Username.length() > 20){
            //The username is too short or too long
            Toast.makeText(this, "Invalid username. Please use a username that is between 3 and 20 characters long.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!Username.matches("[a-zA-Z0-9._-]+")){
            //The username contains invalid characters
            Toast.makeText(this, "Invalid username. Username cannot contain invalid characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Check the validity of email address
     * @param Email email address entered
     * @return boolean
     */
    private boolean isValidEmail(String Email){
        if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Invalid email address. Please check the format and try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Check the validity of phone number
     * @param PhoneNo phone number entered
     * @return boolean
     */
    private boolean isValidPhoneNumber(String PhoneNo){
        Pattern phoneNumberPattern = Pattern.compile("\\d{3}-\\d{7}");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(PhoneNo);
        if (phoneNumberMatcher.matches()) {
            return true;
        } else {
            Toast.makeText(this, "Invalid phone number. Please use the correct format for Malaysia, including the (-).", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Check the validity of password
     * @param Pass password entered
     * @return boolean
     */
    private boolean isValidPassword(String Pass){
        if (Pass.isEmpty()) {
            Toast.makeText(this, "Invalid password. Password cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Pass.matches(".*[^a-zA-Z0-9].*") || !Pass.matches(".*[a-z].*") || !Pass.matches(".*[A-Z].*") || !Pass.matches(".*[0-9].*") || Pass.length() < 8){
            Toast.makeText(this, "Password must be at least 8 characters long and include a combination of numbers, uppercase letters, and special characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Check whether the password and confirm password are matched
     * @param Pass password entered
     * @param ConfirmPass confirm password entered
     * @return boolean
     */
    private boolean isMatchedPassword(String Pass, String ConfirmPass){
        if(!Pass.equals(ConfirmPass)){
            Toast.makeText(this, "The confirm password field does not match the password field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Check if the email address entered by the user is registered
     * @param email_address email address entered
     * @return boolean
     */
    private boolean isEmailInUse(String email_address) {
        try {
            return userDatabase.userDAO().checkIfUserExist(email_address) != null;
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            userDatabase.close();
        }
        return false;
    }
}