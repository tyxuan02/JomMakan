package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

public class EditProfileActivity extends AppCompatActivity {

    TextView inputName, inputEmail, inputPhoneNum;
    EditText inputPassword;
    Button save_button;
    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        // showing the back button in action bar
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
            @Override
            public void onClick(View v) {
                if (isValidPassword(String.valueOf(inputPassword.getText()))) {
                    if (!String.valueOf(inputPassword.getText()).equals(UserInstance.getPassword())) {
                        Toast.makeText(EditProfileActivity.this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
                        UserInstance.setPassword(String.valueOf(inputPassword.getText()));
                        userDatabase.userDAO().changePassword(UserInstance.getUser_email_address(), UserInstance.getPassword());
                        onBackPressed();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Please use a different password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // this event will enable the back function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidPassword(String Pass){
        //Check the validity of password
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