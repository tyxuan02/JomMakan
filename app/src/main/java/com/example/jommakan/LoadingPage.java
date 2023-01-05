package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
//      // Context context = v.getContext();
        if (userFile.exists()) {
            String userPassword = readUserPassword();
            String userEmail = readUserEmail();
            User user = userDatabase.userDAO().getUser(userEmail, userPassword);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userLogged", user);
            startActivity(intent);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoadingPage.this,LoginPage.class));
                    finish();
                }
            },1000);
        }
    }

    protected String readUserPassword() {
        String userPassword;
        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput(USER_FILE_NAME);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            userPassword = stringBuilder.toString();
        }
        return userPassword;
    }

    protected String readUserEmail() {
        String userEmail;
        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput(USER_FILE_NAME);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            userEmail = stringBuilder.toString();
        }
        return userEmail;
    }
}