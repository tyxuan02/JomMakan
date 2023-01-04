package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    private EditText email_edit_text, password_edit_text;
    private TextView forgotPassword_text_view, register_text_view;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        forgotPassword_text_view = findViewById(R.id.forgotPassword_text_view);
        register_text_view = findViewById(R.id.register_text_view);
        login_button = findViewById(R.id.login_button);


        // Login (need to check the validity of email and password)
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email_edit_text.getText().toString();
                String Password = password_edit_text.getText().toString();
//                if(isValidEmail(Email) && isValidPassword(Password)){
//
//                }else{
//
//                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        //Redirect to change password page
        forgotPassword_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Redirect to account registration page
        register_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegistrationPage.class));
            }
        });




    }
}