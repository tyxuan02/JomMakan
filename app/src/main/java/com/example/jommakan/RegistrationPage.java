package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationPage extends AppCompatActivity {

    private Button signUp_button;
    private TextView signIn_text_view;
    private EditText username_edit_text, email_edit_text, phoneNumber_edit_text, newPassword_edit_text, confirmPassword_edit_text;
    UserDatabase userDatabase;

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
            @Override
            public void onClick(View v) {
                String Username = username_edit_text.getText().toString();
                String Email = email_edit_text.getText().toString();
                String PhoneNumber = phoneNumber_edit_text.getText().toString();
                String NewPassword = newPassword_edit_text.getText().toString();
                String ConfirmPassword = confirmPassword_edit_text.getText().toString();

                if (isValidUsername(Username)) {
                    if (isValidEmail(Email)) {
                        if (isValidPhoneNumber(PhoneNumber)) {
                            if (isValidPassword(NewPassword)) {
                                if (isMatchedPassword(NewPassword, ConfirmPassword)) {
                                    if (isEmailInUse(Email)) {
                                        createNewAccount(Email, Username, NewPassword, PhoneNumber);
                                        Toast.makeText(RegistrationPage.this, "Your account has been successfully created.", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    } else {
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
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationPage.this,LoginPage.class));
            }
        });
    }

    private boolean isValidUsername(String Username){
        //Check the validity of username
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

    private boolean isValidEmail(String Email){
        //Check the validity of email
        if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Invalid email address. Please check the format and try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isValidPhoneNumber(String PhoneNo){
        //Check the validity of phone number
        Pattern phoneNumberPattern = Pattern.compile("\\d{3}-\\d{7}");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(PhoneNo);
        if (phoneNumberMatcher.matches()) {
            return true;
        } else {
            Toast.makeText(this, "Invalid phone number. Please use the correct format for Malaysia, including the (-).", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isValidPassword(String Pass){
        //Check the validity of password
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

    private boolean isMatchedPassword(String Pass, String ConfirmPass){
        //Check whether the password and confirm password are matched
        if(!Pass.equals(ConfirmPass)){
            Toast.makeText(this, "The confirm password field does not match the password field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Check if the email address entered by the user is registered
    private boolean isEmailInUse(String email_address) {
        return userDatabase.userDAO().checkIfUserExist(email_address) == null;
    }

    // Create a new account
    private void createNewAccount(String email_address, String username, String password, String phone_number) {
        User user = new User(email_address, username, password, phone_number);
        userDatabase.userDAO().insertUser(user);
    }
}