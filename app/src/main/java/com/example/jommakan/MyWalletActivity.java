package com.example.jommakan;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * An activity that is responsible for displaying and managing wallet balance information in My Wallet Page
 */
public class MyWalletActivity extends AppCompatActivity {

    /**
     * A dialog that acts as a pop up window
     * It allows users to top up their account wallet balance after clicking on it
     */
    Dialog topUpDialog;

    /**
     * A text view that is used to display account wallet balance
     */
    TextView wallet_balance;

    /**
     * A text view that is used to display username
     */
    TextView name;

    /**
     * This method is used to set up the initial state of the activity, such as initializing variables and setting the layout for the activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        wallet_balance = findViewById(R.id.wallet_balance);
        name = findViewById(R.id.name);

        // Toolbar
        Toolbar toolbarActivity = findViewById(R.id.toolbarActivity);
        setSupportActionBar(toolbarActivity);

        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView toolbar_title = (TextView) toolbarActivity.findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_quaternary);
            toolbar_title.setText("My Wallet");
        }

        // Show the back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        topUpDialog = new Dialog(this);

        // Wallet balance
        name.setText(UserInstance.getUsername());
        wallet_balance.setText(String.format("%.2f", UserInstance.getWallet_balance()));
    }

    /**
     * This event will display a pop up window that allows users to top up their account wallet balance after clicking on it
     * @param v view
     */
    public void show_TopUpDialog(View v){
        TopUpDialogFragment topUpDialogFragment = new TopUpDialogFragment();
        topUpDialogFragment.show(getSupportFragmentManager(),"top up dialog");
    }

    /**
     * This event will enable the back function to the button on press
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
}