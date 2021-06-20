package com.example.play_de;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //키보드가 레이아웃에 영향을 안주게
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        ViewPager vp = findViewById(R.id.MainVP);
        MainVPAdapter adapter = new MainVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        //탭과 뷰 연동
        TabLayout tab = findViewById(R.id.MainTab);
        tab.setupWithViewPager(vp);

        //탭 이미지 추가
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.home);
        images.add(R.drawable.category);
        images.add(R.drawable.plus);
        images.add(R.drawable.chat);
        images.add(R.drawable.user);

        for(int i=0; i<5; i++)
            tab.getTabAt(i).setIcon(images.get(i));
    }
}
