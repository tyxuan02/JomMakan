package com.example.jommakan;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

/**
 * A fragment that is responsible for display and managing user's account information
 * It allows the user to navigate to edit profile, top up wallet, show order history, rate our application and report an issue pages
 */
public class AccountFragment extends Fragment {

    /**
     * A button that is used to logout
     */
    TextView logOutBtn;

    /**
     * A text view that is used to display username
     */
    TextView username_text_view;

    /**
     * A text view that is used to display user email address
     */
    TextView email_address_text_view;

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_secondary);
            toolbar_title.setText("ACCOUNT");
        }

        username_text_view = view.findViewById(R.id.username_text_view);
        email_address_text_view = view.findViewById(R.id.email_address_text_view);
        username_text_view.setText(UserInstance.getUsername());
        email_address_text_view.setText(UserInstance.getUser_email_address());

        // Logout button
        logOutBtn = view.findViewById(R.id.log_out_btn);
        context = view.getContext();
        logOutBtn.setOnClickListener(new View.OnClickListener() {


            // Direct user to Login Page after clicking on it
            @Override
            public void onClick(View v) {
                context.deleteFile("user_file");
                Intent intent = new Intent(getActivity(),LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // Add options menu to toolbar
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_options, menu);
            }

            // Display Rate Our App popup window or direct user to Report Page depending on the option that user selects in the option menu
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();

                if(id == R.id.rateOurApp){
                    RatingDialogFragment ratingDialogFragment = new RatingDialogFragment();
                    ratingDialogFragment.show(getActivity().getSupportFragmentManager(), "My Fragment");
                }

                if(id == R.id.reportAnIssue){
                    startActivity(new Intent(getActivity(), ReportPage.class));
                }
                return true;

            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        // Edit profile button
        Button editProfileBtn = (Button) view.findViewById(R.id.edit_profile_btn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {

            // Direct user to Edit Profile Page after clicking on it
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(in);
            }
        });

        // Order history button
        Button order_history_btn = view.findViewById(R.id.order_history_btn);
        order_history_btn.setOnClickListener(new View.OnClickListener() {

            // Direct user to Order History Page after clicking on it
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderHistoryPage.class);
                startActivity(intent);
            }
        });

        // My wallet button
        Button myWalletBtn = (Button) view.findViewById(R.id.my_wallet_btn);
        myWalletBtn.setOnClickListener(new View.OnClickListener() {

            // Direct user to My Wallet Page after clicking on it
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), MyWalletActivity.class);
                startActivity(in);
            }
        });
    }
}
