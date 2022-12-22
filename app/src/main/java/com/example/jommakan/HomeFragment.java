package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    // Top 5
    int [] top_5_images = {R.drawable.top_5_image, R.drawable.top_5_image, R.drawable.top_5_image, R.drawable.top_5_image, R.drawable.top_5_image};
    SliderAdapter sliderAdapter;
    SliderView top_5_image_slider;

    // Home Menu
    RecyclerView home_menu_recycle_view;
    ArrayList<Food> food_list;
    HomeMenuItemAdapter homeMenuItemAdapter;
    ImageButton menu_view_all_button;

    // Home Location
    RecyclerView home_location_recycle_view;
    ArrayList<Location> location_list;
    HomeLocationItemAdapter homeLocationItemAdapter;
    ImageButton location_view_all_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    // Declare and assign views here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Toolbar
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_main);
            toolbar_title.setText("JomMakan");
        }


        // Top 5 Slider (Needs to fetch data from database, will implement soon)
        sliderAdapter = new SliderAdapter(top_5_images);
        top_5_image_slider = view.findViewById(R.id.top_5_image_slider);

        top_5_image_slider.setSliderAdapter(sliderAdapter);
        top_5_image_slider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        top_5_image_slider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        top_5_image_slider.startAutoCycle();


        // Home Menu (Needs to fetch data from database, will implement soon)
        SimpleDateFormat format = new SimpleDateFormat("hh.mm a");
        Date open = new Date();
        Date close = new Date();
        try {
            open = format.parse("10.00 AM");
            close = format.parse("10.00 PM");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        food_list = new ArrayList<>();
        food_list.add(new Food("Nasi Goreng", "Kolej Kediaman Kinabalu", "Restoran Famidah", 6.00, new String[]{"Local delight", "Spicy", "Contains prawn"}, R.drawable.nasi_goreng_image, new Date[]{open, close}));
        food_list.add(new Food("Nasi Goreng", "Kolej Kediaman Kinabalu", "Restoran Famidah", 6.00, new String[]{"Local delight", "Spicy", "Contains prawn"}, R.drawable.nasi_goreng_image, new Date[]{open, close}));
        food_list.add(new Food("Nasi Goreng", "Kolej Kediaman Kinabalu", "Restoran Famidah", 6.00, new String[]{"Local delight", "Spicy", "Contains prawn"}, R.drawable.nasi_goreng_image, new Date[]{open, close}));

        homeMenuItemAdapter = new HomeMenuItemAdapter(getActivity(), food_list);

        home_menu_recycle_view = view.findViewById(R.id.home_menu_recycle_view);
        home_menu_recycle_view.setAdapter(homeMenuItemAdapter);
        home_menu_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        home_menu_recycle_view.setHasFixedSize(true);
        home_menu_recycle_view.setNestedScrollingEnabled(false);

        menu_view_all_button = view.findViewById(R.id.menu_view_all_button);
        menu_view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMenu);
            }
        });


        // Home Location (Needs to fetch data from database, will implement soon)
        location_list = new ArrayList<>();
        location_list.add(new Location("Faculty of Computer Science and Information Technology", R.drawable.fsktm_image, new String[]{"Restoran Famidah", "Restoran ABC"}));
        location_list.add(new Location("Kolej Kediaman Kinabalu", R.drawable.fsktm_image, new String[]{"Restoran Famidah", "Restoran ABC"}));
        location_list.add(new Location("Faculty of Computer Science and Information Technology", R.drawable.fsktm_image, new String[]{"Restoran Famidah", "Restoran ABC"}));

        homeLocationItemAdapter = new HomeLocationItemAdapter(getActivity(), location_list);

        home_location_recycle_view = view.findViewById(R.id.home_location_recycle_view);
        home_location_recycle_view.setAdapter(homeLocationItemAdapter);
        home_location_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        home_location_recycle_view.setHasFixedSize(true);
        home_location_recycle_view.setNestedScrollingEnabled(false);

        location_view_all_button = view.findViewById(R.id.location_view_all_button);
        location_view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMenu);
            }
        });
    }
}