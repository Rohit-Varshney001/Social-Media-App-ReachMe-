package com.example.reachme.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.reachme.Adapter.ViewPagerAdapter;
import com.example.reachme.R;
import com.google.android.material.tabs.TabLayout;

public class NotificationFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
