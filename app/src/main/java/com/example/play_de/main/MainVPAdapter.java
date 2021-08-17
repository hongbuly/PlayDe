package com.example.play_de.main;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.play_de.game.GameFragment;
import com.example.play_de.cafe.CafeFragment;
import com.example.play_de.chat.ChatFragment;
import com.example.play_de.community.CommunityFragment;
import com.example.play_de.fundingParent.FundingFragment;

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
        text.add("게임");
        text.add("카페");
        text.add("커뮤니티");
        text.add("펀딩");
        text.add("채팅");
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
