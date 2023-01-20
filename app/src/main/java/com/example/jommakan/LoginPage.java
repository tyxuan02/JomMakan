package com.example.jommakan;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * An activity that is responsible for displaying login page
 */
public class LoginPage extends AppCompatActivity {

    /**
     * An edit text view that allows users to enter account email address
     */
    EditText email_edit_text;

    /**
     * An edit text view that allows users to enter account password
     */
    EditText password_edit_text;

    /**
     * A text view that allows users to click and it will display forgot password pop up window
     */
    TextView forgotPassword_text_view;

    /**
     * A text view that allows users to click and it will direct users to registration page
     */
    TextView register_text_view;

    /**
     * A button that will direct users to homepage after clicking on it
     */
    Button login_button;

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
        setContentView(R.layout.activity_login_page);

        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        forgotPassword_text_view = findViewById(R.id.forgotPassword_text_view);
        register_text_view = findViewById(R.id.register_text_view);
        login_button = findViewById(R.id.login_button);

        // Database connection
        userDatabase = Room.databaseBuilder(this, UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        // Login (need to check the validity of email and password)
        login_button.setOnClickListener(new View.OnClickListener() {

            /**
             * It will direct users to homepage after clicking on it if it satisfies all the if-else conditions in this method
             * @param v
             */
            @Override
            public void onClick(View v) {
                // If user has already log in before, check if local storage has 'user_file', which stores the user email and password
                // If no user_file exist, ask the user to log in abd jump to log in page
                Context context = v.getContext();

                // First time login - check if the user data exists in database
                String filename = "user_file";
                String Email = email_edit_text.getText().toString();
                String Password = password_edit_text.getText().toString();

                User user = null;

                // Close connection and handle errors in room database
                try {
                    user = userDatabase.userDAO().getUser(Email, Password);
                } catch (SQLiteException e) {
                    // Handle errors
                    e.printStackTrace();
                } finally {
                    // Close the database connection
                    userDatabase.close();
                }

                // If user data exist in the database, create a file named 'user_file' and store user data in it
                // Direct users to login page
                if(user != null){
                    try(FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
                        fos.write(Email.getBytes());
                        fos.write(10);
                        fos.write(Password.getBytes());
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
                    // If user data doesn't exist in the database (Wrong user credentials)
                    Toast.makeText(LoginPage.this, "The email address or password you entered is incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPassword_text_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Display forgot password pop up window after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                ForgotPasswordDialogFragment1 forgotPasswordDialogFragment = new ForgotPasswordDialogFragment1();
                forgotPasswordDialogFragment.show(getSupportFragmentManager(), "Forgot Password Fragment 1");
            }
        });

        register_text_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Direct users to login page after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegistrationPage.class));
            }
        });
    }
}
