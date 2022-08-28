package com.example.reachme.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.reachme.Fragment.Notification2Fragment;
import com.example.reachme.Fragment.RequestFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Notification2Fragment();
            case 1: return new RequestFragment();
            default: return new Notification2Fragment();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if (position == 0){
            title = "NOTIFICATION";
        }else if (position == 1){
            title = "REQUEST";
        }

        return title;
    }
}
