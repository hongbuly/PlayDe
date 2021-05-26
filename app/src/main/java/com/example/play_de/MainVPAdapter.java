package com.example.play_de;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext;

    public MainVPAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
        items.add(new HomeFragment());
        items.add(new CategoryFragment());
        items.add(new CommunityFragment());
        items.add(new ChatFragment());
        items.add(new ProfileFragment());

        itext = new ArrayList<>();
        itext.add("Home");
        itext.add("Category");
        itext.add("Person");
        itext.add("Chat");
        itext.add("Profile");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itext.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
