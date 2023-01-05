package com.example.jommakan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.mail.MessagingException;

public class ReportPage extends AppCompatActivity {

    EditText email_edit_text, description_edit_text;
    ImageButton screenshot_image_button,remove_screenshot_button;
    Button submit_button;
    ImageView screenshot_image_view;
    ActivityResultLauncher<String>activityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        email_edit_text = findViewById(R.id.email_edit_text);
        description_edit_text = findViewById(R.id.description_edit_text);
        screenshot_image_button = findViewById(R.id.screenshot_image_button);
        remove_screenshot_button = findViewById(R.id.remove_screenshot_button);
        submit_button = findViewById(R.id.submit_button);
        screenshot_image_view = findViewById(R.id.screenshot_image_view);

        Toolbar toolbarActivity = findViewById(R.id.toolbarActivity);
        setSupportActionBar(toolbarActivity);

        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView toolbar_title = (TextView) toolbarActivity.findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_quaternary);
            toolbar_title.setText("Report");
        }

        toolbarActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null){
                    try{
                        // Open an InputStream to read the image data
                        InputStream inputStream = getContentResolver().openInputStream(result);
                        // Load the image into a Bitmap
                        Bitmap image = BitmapFactory.decodeStream(inputStream);

                        // Scale the image if it is larger than the maximum allowed size
                        final int MAX_IMAGE_SIZE = 800; // Maximum image size, in pixels
                        if (image.getWidth() > MAX_IMAGE_SIZE || image.getHeight() > MAX_IMAGE_SIZE) {
                            float scaleFactor = Math.min((float) MAX_IMAGE_SIZE / image.getWidth(),
                                    (float) MAX_IMAGE_SIZE / image.getHeight());
                            int newWidth = (int) (image.getWidth() * scaleFactor);
                            int newHeight = (int) (image.getHeight() * scaleFactor);
                            image = Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
                        }

                        // Display the scaled Bitmap in the ImageView
                        screenshot_image_view.setImageBitmap(image);
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    screenshot_image_button.setVisibility(View.VISIBLE);
                    remove_screenshot_button.setVisibility(View.GONE);
                    return;
                }



            }
        });

        screenshot_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch("image/*");
                screenshot_image_button.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        screenshot_image_button.setVisibility(View.GONE);
                    }
                }, 1000);

                remove_screenshot_button.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        remove_screenshot_button.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });

        remove_screenshot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the displayed image
                screenshot_image_view.setImageDrawable(null);
                remove_screenshot_button.setVisibility(View.GONE);
                screenshot_image_button.setVisibility(View.VISIBLE);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail(email_edit_text.getText().toString())) {
                    if (!description_edit_text.getText().toString().isEmpty()) {
                        Toast.makeText(ReportPage.this, "We received your email and will get back to you with a human response as soon as possible.", Toast.LENGTH_SHORT).show();
                        new SendEmailTask().execute();
                        onBackPressed();
                    } else {
                        Toast.makeText(ReportPage.this, "Please enter something in the description text field.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidEmail(String Email){
        //Check the validity of email
        if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Invalid email address. Please check the format and try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Send verification code to user Gmail
    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Email email = new Email();
            try {
                if (screenshot_image_view.getDrawable() != null) {
                    email.sendReportedIssue(email_edit_text.getText().toString(), description_edit_text.getText().toString(), ((BitmapDrawable) screenshot_image_view.getDrawable()).getBitmap(), getApplicationContext());
                } else {
                    email.sendReportedIssue(email_edit_text.getText().toString(), description_edit_text.getText().toString(), null, getApplicationContext());
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}