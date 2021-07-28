package com.example.play_de;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> text;

    public MainVPAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
        items.add(new GameFragment());
        items.add(new CafeFragment());
        items.add(new CommunityFragment());
        items.add(new FundingFragment());
        items.add(new ChatFragment());

        text = new ArrayList<>();
        text.add("Game");
        text.add("Cafe");
        text.add("Community");
        text.add("Funding");
        text.add("Chat");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return text.get(position);
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
