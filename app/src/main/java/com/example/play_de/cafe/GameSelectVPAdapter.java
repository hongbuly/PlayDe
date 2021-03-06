package com.example.play_de.cafe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class GameSelectVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;

    public GameSelectVPAdapter(FragmentManager fm) {
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
