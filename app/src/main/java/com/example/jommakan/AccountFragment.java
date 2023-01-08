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
 * An fragment that is used to control fragment_account.xml
 */
public class AccountFragment extends Fragment {

    /**
     * Log out button link
     */
    TextView logOutBtn;

    /**
     * Username text view
     */
    TextView username_text_view;

    /**
     * Email address text view
     */
    TextView email_address_text_view;

    /**
     * Edit profile button
     */
    Button editProfileBtn;

    /**
     * Order history button
     */
    Button order_history_btn;

    /**
     * My wallet button
     */
    Button myWalletBtn;

    /**
     * Provides access to various global information about an app
     */
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /**
         * Set up toolbar
         */
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_secondary);
            toolbar_title.setText("ACCOUNT");
        }


        /**
         * Link the views with their corresponding IDs
         */
        username_text_view = view.findViewById(R.id.username_text_view);
        email_address_text_view = view.findViewById(R.id.email_address_text_view);
        username_text_view.setText(UserHolder.getUsername());
        email_address_text_view.setText(UserHolder.getUser_email_address());
        logOutBtn = view.findViewById(R.id.log_out_btn);
        editProfileBtn = (Button) view.findViewById(R.id.edit_profile_btn);

        // get file dir
        context = view.getContext();
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteFile("user_file");
                Intent intent = new Intent(getActivity(),LoginPage.class);
                startActivity(intent);
            }
        });

        /**
         * Add options menu to toolbar, when in the account fragment
         */
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_options, menu);
            }

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


        /**
         * Direct user to EditProfileActivity when the button is click
         */
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(in);
            }
        });

        /**
         * Direct user to OrderHistoryPage when the button is click
         */
        order_history_btn = view.findViewById(R.id.order_history_btn);
        order_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderHistoryPage.class);
                startActivity(intent);
            }
        });

        /**
         * Direct user to MyWalletActivity when the button is click
         */
        myWalletBtn = (Button) view.findViewById(R.id.my_wallet_btn);
        myWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), MyWalletActivity.class);
                startActivity(in);
            }
        });
    }
}
