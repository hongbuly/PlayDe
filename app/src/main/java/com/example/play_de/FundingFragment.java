package com.example.play_de;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class FundingFragment extends Fragment {
    ImageButton backBtn;

    static LinearLayout funding_view01;
    static LinearLayout funding_view02;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //홈화면에 대한 자바 코드 작성을 여기에.
        View view = inflater.inflate(R.layout.fragment_funding, container, false);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_view(false);
            }
        });

        funding_view01 = view.findViewById(R.id.funding_view01);
        funding_view02 = view.findViewById(R.id.funding_view02);

        ViewPager vp01 = view.findViewById(R.id.funding_vp01);
        FundingVPAdapter adapter01 = new FundingVPAdapter(getChildFragmentManager(), true);
        vp01.setAdapter(adapter01);

        //탭과 뷰 연동
        TabLayout tab01 = view.findViewById(R.id.funding_tab01);
        tab01.setupWithViewPager(vp01);

        ViewPager vp02 = view.findViewById(R.id.funding_vp02);
        FundingVPAdapter adapter02 = new FundingVPAdapter(getChildFragmentManager(), false);
        vp02.setAdapter(adapter02);

        //탭과 뷰 연동
        TabLayout tab02 = view.findViewById(R.id.funding_tab02);
        tab02.setupWithViewPager(vp02);
        return view;
    }

    static void change_view(boolean detail) {
        if (detail){
            funding_view01.setVisibility(View.GONE);
            funding_view02.setVisibility(View.VISIBLE);
        } else {
            funding_view01.setVisibility(View.VISIBLE);
            funding_view02.setVisibility(View.GONE);
        }
    }
}
