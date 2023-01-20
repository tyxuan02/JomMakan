package com.example.jommakan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * A dialog fragment that is responsible for displaying and managing rating process
 * It act as a pop up window
 * It allows the users to rate the app
 */
public class RatingDialogFragment extends DialogFragment {

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View popUp = getLayoutInflater().inflate(R.layout.rating_window,null);

        Button submitBtn = (Button) popUp.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            /**
             * Display a message that shows users have successfully rate the app and close the rating pop up window after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Thanks for your rating!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return popUp;
    }
}
