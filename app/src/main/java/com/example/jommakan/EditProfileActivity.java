package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

/**
 * An activity that is responsible for display and managing user's account information
 * It allows the users to navigate to edit their account credentials
 */
public class EditProfileActivity extends AppCompatActivity {

    /**
     * A text view that is used to display username
     */
    TextView inputName;

    /**
     * A text view that is used to display user email address
     */
    TextView inputEmail;

    /**
     * A text view that is used to display user phone number
     */
    TextView inputPhoneNum;

    /**
     * A edit text that is used to let users edit their account passwords
     */
    EditText inputPassword;

    /**
     * A button that is used to let users save their account credentials after editing
     */
    Button save_button;

    /**
     * An instance of the class userDatabase
     */
    UserDatabase userDatabase;

    /**
     * This method is used to set up the initial state of the activity, such as initializing variables and setting the layout for the activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Toolbar
        Toolbar toolbarActivity = findViewById(R.id.toolbarActivity);
        setSupportActionBar(toolbarActivity);

        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView toolbar_title = (TextView) toolbarActivity.findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_quaternary);
            toolbar_title.setText("Edit profile");
        }

        // Shows the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Database connection
        userDatabase = Room.databaseBuilder(this, UserDatabase.class, "UserDB").allowMainThreadQueries().build();

        // Fill in user credentials
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhoneNum = findViewById(R.id.inputPhoneNum);
        inputPassword = findViewById(R.id.inputPassword);

        inputName.setText(UserInstance.getUsername());
        inputEmail.setText(UserInstance.getUser_email_address());
        inputPhoneNum.setText(UserInstance.getPhone_number());
        inputPassword.setText(UserInstance.getPassword());

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Save user account credentials after clicking on it if the password entered by the user is valid
             * @param v view
             */
            @Override
            public void onClick(View v) {
                if (isValidPassword(String.valueOf(inputPassword.getText()))) {
                    if (!String.valueOf(inputPassword.getText()).equals(UserInstance.getPassword())) {
                        Toast.makeText(EditProfileActivity.this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
                        UserInstance.setPassword(String.valueOf(inputPassword.getText()));

                        // Close connection and handle errors in room database
                        try {
                            userDatabase.userDAO().changePassword(UserInstance.getUser_email_address(), UserInstance.getPassword());
                        } catch (SQLiteException e) {
                            // Handle errors
                            e.printStackTrace();
                        } finally {
                            // Close the database connection
                            userDatabase.close();
                        }

                        onBackPressed();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Please use a different password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Enable the back function to the button on press
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Check the validity of password
     * @param Pass password
     * @return boolean
     */
    private boolean isValidPassword(String Pass){
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
}