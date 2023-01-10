package com.example.jommakan;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoadingPage extends AppCompatActivity {

    private static final String USER_FILE_NAME = "user_file";
    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        Context context = getApplicationContext();
        File userFile = new File(context.getFilesDir(), USER_FILE_NAME);

        // database connection
        userDatabase = Room.databaseBuilder(this, UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        // user has already log in - check if local storage has 'user_file'
        // which stores the user email and password
        // if no user_file exist, ask the user to log in
        // jump to log in page
        // Context context = v.getContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userFile.exists()) {
                    String [] user_credentials = readUserCredential();
                    String userEmail = user_credentials[0];
                    String userPassword = user_credentials[1];

                    User user = null;
                    try {
                        user = userDatabase.userDAO().getUser(userEmail, userPassword);
                    } catch (SQLiteException e) {
                        // Handle errors
                        e.printStackTrace();
                    } finally {
                        // Close the database connection
                        userDatabase.close();
                    }

                    Intent intent = new Intent(LoadingPage.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(LoadingPage.this,LoginPage.class));
                }
                finish();
            }
            },1000);
    }

    // Read user credentials
    protected String[] readUserCredential() {
        String [] user_credentials = new String[2];
        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput(USER_FILE_NAME);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        try(BufferedReader reader = new BufferedReader(inputStreamReader)) {
            user_credentials[0] = reader.readLine();
            user_credentials[1] = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user_credentials;
    }
}