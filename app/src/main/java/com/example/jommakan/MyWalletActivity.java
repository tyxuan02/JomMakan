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

public class MyWalletActivity extends AppCompatActivity {

    Dialog topUpDialog;
    TextView wallet_balance;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        wallet_balance = findViewById(R.id.wallet_balance);
        name = findViewById(R.id.name);

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

        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        topUpDialog = new Dialog(this);

        // Wallet balance
        name.setText(UserInstance.getUsername());
        wallet_balance.setText(String.format("%.2f", UserInstance.getWallet_balance()));
    }

    public void show_TopUpDialog(View v){
        TopUpDialogFragment topUpDialogFragment = new TopUpDialogFragment();
        topUpDialogFragment.show(getSupportFragmentManager(),"top up dialog");
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
}