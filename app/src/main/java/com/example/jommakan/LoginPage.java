package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

//import android.content.Context;
import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;

public class LoginPage extends AppCompatActivity {

    EditText email_edit_text, password_edit_text;
    TextView forgotPassword_text_view, register_text_view;
    Button login_button;

    UserDatabase userDatabase;

//    private static final String USER_FILE_NAME = "user_file";

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

//        // check if the file exists
//        Context context = getApplicationContext();
//        // create file handler
//        File userFile = new File(context.getFilesDir(), USER_FILE_NAME);

        //Login (need to check the validity of email and password)
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Context context = v.getContext();
                String Email = email_edit_text.getText().toString();
                String Password = password_edit_text.getText().toString();
                User user = userDatabase.userDAO().getUser(Email, Password);
                if(user != null){
                    Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginPage.this, "The email address or password you entered is incorrect. Please try again.", Toast.LENGTH_SHORT).show();
                }
//
//                // write user_email and user_password in storage file
//                String filename = "user_file";
//                String user_email = email_edit_text.getText().toString();
//                String user_password = password_edit_text.getText().toString();
//                try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
//                    fos.write(user_email.getBytes());
//                    fos.write(user_password.getBytes());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
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

//    // get data from SharedPreference
//    @Override
//    protected void onResume(){
//        super.onResume();
//
//        SharedPreferences sh = getSharedPreferences("UserSharedPref", MODE_PRIVATE);
//
//        String email = sh.getString("email", "");
//        String password = sh.getString("password", "");
//
//        email_edit_text.setText(email);
//        password_edit_text.setText(password);
//    }
//
//    // save data to SharedPreference
//    @Override
//    protected void onPause(){
//        super.onPause();
//        SharedPreferences sharedPreferences = getSharedPreferences("UserSharedPref", MODE_PRIVATE);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//
//        edit.putString("email", email_edit_text.getText().toString());
//        edit.putString("password", password_edit_text.getText().toString());
//        edit.apply();
//    }
//
}
