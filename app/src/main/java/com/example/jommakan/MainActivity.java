package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    TextView toolbar_title;

    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        // toolbar_title.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = host.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.DestHome, R.id.DestMenu, R.id.DestCart, R.id.DestAccount)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        setupBottomNavMenu(navController);
    }

    // Bottom Navigation Bar
    private void setupBottomNavMenu (NavController navController) {
        BottomNavigationView bottom_navigation_bar = findViewById(R.id.bottom_navigation_bar);
        NavigationUI.setupWithNavController(bottom_navigation_bar, navController, false);
    }

    public void setTitleBBB() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_secondary);
        }
    }
}