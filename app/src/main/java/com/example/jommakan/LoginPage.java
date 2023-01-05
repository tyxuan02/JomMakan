package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginPage extends AppCompatActivity {

    EditText email_edit_text, password_edit_text;
    TextView forgotPassword_text_view, register_text_view;
    Button login_button;

    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        forgotPassword_text_view = findViewById(R.id.forgotPassword_text_view);
        register_text_view = findViewById(R.id.register_text_view);
        login_button = findViewById(R.id.login_button);

        // Database connection
        userDatabase = Room.databaseBuilder(this, UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        //Login (need to check the validity of email and password)
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // user has already log in -> check if local storage has 'user_file'
                // which stores the user email and password
                // if no user_file exist, ask the user to log in
                // jump to log in page
                Context context = v.getContext();

                // first time login - check if the user data exists in database
                String filename = "user_file";
                String Email = email_edit_text.getText().toString();
                String Password = password_edit_text.getText().toString();
                User user = userDatabase.userDAO().getUser(Email, Password);
                if(user != null){
                    try(FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
                        fos.write(Email.getBytes());
                        fos.write(10);
                        fos.write(Password.getBytes());
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginPage.this, "The email address or password you entered is incorrect. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Redirect to change password page
        forgotPassword_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordDialogFragment forgotPasswordDialogFragment = new ForgotPasswordDialogFragment();
                forgotPasswordDialogFragment.show(getSupportFragmentManager(), "Forgot Password Fragment");
            }
        });

        // Redirect to account registration page
        register_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegistrationPage.class));
            }
        });
    }
}
