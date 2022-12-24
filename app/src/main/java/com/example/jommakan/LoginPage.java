package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    private EditText ETEmail, ETPassword;
    private TextView TVForgotPassword, TVRegister;
    private Button BTLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ETEmail = findViewById(R.id.Email);
        ETPassword = findViewById(R.id.Password);
        TVForgotPassword = findViewById(R.id.ForgotPassword);
        TVRegister = findViewById(R.id.Register);
        BTLogin = findViewById(R.id.Login);


        //Login (need to check the validity of email and password)
        BTLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = ETEmail.getText().toString();
                String Password = ETPassword.getText().toString();
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
        TVForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Redirect to register a new acc
        TVRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,RegisterPage.class));
                finish();
            }
        });




    }
}