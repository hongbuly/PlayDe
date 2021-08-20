package com.example.play_de.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.play_de.R;
import com.example.play_de.chat.GoUP;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private MainVPAdapter adapter;
    private TabLayout tab;
    private ArrayList<Integer> images;
    private OnBackPressedListener[] listener = new OnBackPressedListener[5];
    private GoUP goUp;
    private long backKeyPressedTime = 0;
    private String name;

    private View blur;
    private LinearLayout finish_reserve;
    private TextView reportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

        //키보드가 레이아웃에 영향을 안주게
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Intent intent = getIntent();
        if (Objects.requireNonNull(intent.getExtras()).getString("userId") != null)
            name = intent.getExtras().getString("userId");

        vp = findViewById(R.id.MainVP);
        adapter = new MainVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        //탭과 뷰 연동
        tab = findViewById(R.id.MainTab);
        tab.setupWithViewPager(vp);

        //탭 이미지 추가
        images = new ArrayList<>();
        images.add(R.drawable.game);
        images.add(R.drawable.cafe);
        images.add(R.drawable.community);
        images.add(R.drawable.funding);
        images.add(R.drawable.chat);

        for (int i = 0; i < 5; i++)
            tab.getTabAt(i).setIcon(images.get(i));

        blur = findViewById(R.id.blur);
        blur.setOnClickListener(v -> {
            if (reportBtn.getVisibility() == View.VISIBLE)
                showBlur_report(false);
        });

        finish_reserve = findViewById(R.id.finish_reserve);
        reportBtn = findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(v -> {
            //댓글 신고
        });
    }

    public void setOnBackPressedListener(OnBackPressedListener listener, int num) {
        this.listener[num] = listener;
    }

    public void setGoUP(GoUP goUp) {
        this.goUp = goUp;
    }

    @Override
    public void onBackPressed() {
        if (listener != null)
            listener[vp.getCurrentItem()].onBackPressed();
        else
            onBackTime();
    }

    public void onBackTime() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
        }
    }

    public void setCurrentItem(boolean isHeart, int num) {
        vp.setCurrentItem(num);
        //chat
        if (isHeart)
            goUp.goToUp();
    }

    public String getName() {
        return name;
    }

    public void showBlur(boolean show) {
        //카페 결제
        if (show) {
            blur.setVisibility(View.VISIBLE);
            finish_reserve.setVisibility(View.VISIBLE);
        } else {
            blur.setVisibility(View.GONE);
            finish_reserve.setVisibility(View.GONE);
        }
    }

    public void showBlur_report(boolean show) {
        //커뮤니티 신고하기 버튼
        if (show) {
            Animation down_up = AnimationUtils.loadAnimation(this, R.anim.down_up);
            blur.setVisibility(View.VISIBLE);
            reportBtn.setVisibility(View.VISIBLE);
            reportBtn.startAnimation(down_up);
        } else {
            Animation up_down = AnimationUtils.loadAnimation(this, R.anim.up_down);
            reportBtn.startAnimation(up_down);
            up_down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    blur.setVisibility(View.GONE);
                    reportBtn.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}
