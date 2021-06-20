package com.example.play_de;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SelectGameVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;

    public SelectGameVPAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
        items.add(new GameSelectFirstFragment());
        items.add(new GameSelectSecondFragment());
        items.add(new GameSelectThirdFragment());
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
