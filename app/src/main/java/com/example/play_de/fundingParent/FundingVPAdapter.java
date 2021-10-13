package com.example.play_de.fundingParent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.play_de.fundingChild.ChildCommunityFragment;
import com.example.play_de.fundingChild.ChildDetailFragment;
import com.example.play_de.fundingChild.ChildDoingFragment;
import com.example.play_de.fundingChild.ChildNewsFragment;
import com.example.play_de.fundingChild.ChildSupporterFragment;

import java.util.ArrayList;

public class FundingVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> text;

    public FundingVPAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
        items.add(new ChildDetailFragment());
        items.add(new ChildDoingFragment());
        items.add(new ChildNewsFragment());
        items.add(new ChildCommunityFragment());
        items.add(new ChildSupporterFragment());

        text = new ArrayList<>();
        text.add("상세");
        text.add("펀딩하기");
        text.add("소식");
        text.add("커뮤니티");
        text.add("서포터");
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
