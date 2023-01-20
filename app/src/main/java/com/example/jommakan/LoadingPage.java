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

/**
 * An activity that is responsible for displaying landing page when users open the app
 */
public class LoadingPage extends AppCompatActivity {

    /**
     * A string that is used to store the name of the file that is used to store user account email address and password
     */
    private static final String USER_FILE_NAME = "user_file";

    /**
     * An instance of userDatabase
     */
    UserDatabase userDatabase;

    /**
     * This method is used to set up the initial state of the activity, such as initializing variables and setting the layout for the activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        Context context = getApplicationContext();
        File userFile = new File(context.getFilesDir(), USER_FILE_NAME);

        // Database connection
        userDatabase = Room.databaseBuilder(this, UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        // If user has already log in, it will check if local storage has 'user_file', which stores the user email and password
        // If no user_file exist, ask the user to log in and jump to log in page
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

                    // If 'user_file' exists, direct user to homepage
                    Intent intent = new Intent(LoadingPage.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    // If 'user_file doesn't exist, direct user to login page
                    startActivity(new Intent(LoadingPage.this,LoginPage.class));
                }
                finish();
            }
            },1000);
    }

    /**
     * Read user credentials from 'user_file'
     * @return A string that contains user account email address and password
     */
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