package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterPage extends AppCompatActivity {

    private Button signUp_button;
    private TextView signIn_text_view;
    private EditText username_edit_text, email_edit_text, phoneNumber_edit_text, newPassword_edit_text, confirmPassword_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        signUp_button = findViewById(R.id.signUp_button);
        signIn_text_view = findViewById(R.id.signIn_text_view);
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

                if(!isValidUsername(Username)){
                    Toast.makeText(RegisterPage.this,"The username is invalid. Please try again.",Toast.LENGTH_SHORT).show();
                }
                if(!isValidEmail(Email)){
                    Toast.makeText(RegisterPage.this,"This email is invalid. Please try again.",Toast.LENGTH_SHORT).show();
                }
                if(!isValidPhoneNumber(PhoneNumber)){
                    Toast.makeText(RegisterPage.this,"The phone number is invalid. Please try again.",Toast.LENGTH_SHORT).show();
                }
                if(!isValidPassword(NewPassword)){
                    Toast.makeText(RegisterPage.this,"The password is invalid. Please try again.",Toast.LENGTH_SHORT).show();
                }
                if(!isMatchedPassword(NewPassword,ConfirmPassword)){
                    Toast.makeText(RegisterPage.this,"The passwords do not match. Please try again.",Toast.LENGTH_SHORT).show();
                }
                if(isValidUsername(Username) && isValidEmail(Email) && isValidPhoneNumber(PhoneNumber) && isValidPassword(NewPassword) && isMatchedPassword(NewPassword,ConfirmPassword)){
                    //proceed to Main Activity
                    startActivity(new Intent(RegisterPage.this,MainActivity.class));
                    finish();
                    //save info to database ...
                }
            }
        });

        signIn_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPage.this,LoginPage.class));
                finish();
            }
        });
    }

    private boolean isValidUsername(String Username){
        //Check the validity of username
        if(TextUtils.isEmpty(Username)){
            //The username is empty
            return false;
        }else if(Username.length()<3 || Username.length()>20){
            //The username is too short or too long
            return false;
        }else if(!Username.matches("[a-zA-Z0-9._-]+")){
            //The username contains invalid characters
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String Email){
        //Check the validity of email
        return Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    private boolean isValidPhoneNumber(String PhoneNo){
        //Check the validity of phone number
        Pattern phoneNumberPattern = Pattern.compile("\\d{3}-\\d{7}");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(PhoneNo);
        return phoneNumberMatcher.matches();
    }

    private boolean isValidPassword(String Pass){
        //Check the validity of password
        if(Pass.length()<8){
            return false;
        }
        if(!Pass.matches(".*[a-z].*")){
            return false;
        }
        if(!Pass.matches(".*[A-Z].*")){
            return false;
        }
        if(!Pass.matches(".*[0-9].*")){
            return false;
        }
        if(!Pass.matches(".*[^a-zA-Z0-9].*")){
            return false;
        }
        return true;
    }

    private boolean isMatchedPassword(String Pass, String ConfirmPass){
        //Check whether the password and confirm password are matched
        if(!Pass.equals(ConfirmPass)){
            return false;
        }
        return true;
    }
}