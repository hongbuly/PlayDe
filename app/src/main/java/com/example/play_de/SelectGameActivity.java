package com.example.play_de;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SelectGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        ViewPager vp = findViewById(R.id.SelectGameVP);
        SelectGameVPAdapter adapter = new SelectGameVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.SelectGameTab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.tab_circle_yellow);
        images.add(R.drawable.tab_circle_grey);

        tab.getTabAt(0).setIcon(images.get(0));
        tab.getTabAt(1).setIcon(images.get(1));
        tab.getTabAt(2).setIcon(images.get(1));

        Button next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택한 게임 정보를 인자로 다음 화면에 넘겨주기.
            }
        });
    }
}
