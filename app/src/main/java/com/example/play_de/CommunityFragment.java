package com.example.play_de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.material.imageview.ShapeableImageView;

public class CommunityFragment extends Fragment implements OnBackPressedListener {
    private MainActivity main;
    private View view;
    private LinearLayout backLayout;
    private EditText filterEdit;
    private Button filterBtn01;
    private RelativeLayout filterBtn02;
    private Button filterBtn02_1;
    private Button filterBtn02_2;
    private Button filterBtn02_3;

    private HorizontalScrollView[] heart_list;
    private ImageView[] profile;
    private TextView[] name;
    private TextView[] dong;
    private TextView[] heart;
    private ShapeableImageView[][] image;
    private RealtimeBlurView blurView;
    int clickNum = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_community, container, false);

        initialSetup();
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterBtn02.getVisibility() == View.VISIBLE) {
                    filterBtn01.setVisibility(View.VISIBLE);
                    setFilterBtn02_Gone();
                }
            }
        });

        //버튼 누르면, 밑으로 늘어나서 필터링을 결정하는 것을 구현할 것.
        filterBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDown();
            }
        });

        filterBtn02_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUp();
                filterBtn01.setText("위시리스트 순");
            }
        });

        filterBtn02_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUp();
                filterBtn01.setText("거리 순");
            }
        });

        filterBtn02_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUp();
                filterBtn01.setText("카페 좋아요 순");
            }
        });
        return view;
    }

    private void setFilterBtn02_Gone() {
        filterBtn02.setVisibility(View.GONE);
        filterBtn02_1.setVisibility(View.GONE);
        filterBtn02_2.setVisibility(View.GONE);
        filterBtn02_3.setVisibility(View.GONE);
        filterBtn01.setVisibility(View.VISIBLE);
    }

    private void goToDown() {
        filterBtn01.setVisibility(View.GONE);
        filterBtn02.setVisibility(View.VISIBLE);
        filterBtn02_1.setVisibility(View.VISIBLE);
        filterBtn02_2.setVisibility(View.VISIBLE);
        filterBtn02_3.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_down100);
        filterBtn02_2.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_down200);
        filterBtn02_3.startAnimation(animation);
    }

    private void goToUp() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_up100);
        filterBtn02_2.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_up200);
        filterBtn02_3.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setFilterBtn02_Gone();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (filterBtn02.getVisibility() == View.VISIBLE) {
            filterBtn01.setVisibility(View.VISIBLE);
            setFilterBtn02_Gone();
        } else {
            main.onBackTime();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 2);
    }

    private void initialSetup() {
        filterEdit = view.findViewById(R.id.filterEdit);
        filterBtn01 = view.findViewById(R.id.filterBtn01);
        filterBtn02 = view.findViewById(R.id.filterBtn02);
        filterBtn02_1 = view.findViewById(R.id.filterBtn02_1);
        filterBtn02_2 = view.findViewById(R.id.filterBtn02_2);
        filterBtn02_3 = view.findViewById(R.id.filterBtn02_3);

        backLayout = view.findViewById(R.id.backLayout);

        heart_list = new HorizontalScrollView[7];
        profile = new ImageView[7];
        name = new TextView[7];
        dong = new TextView[7];
        heart = new TextView[7];
        image = new ShapeableImageView[7][10];
        blurView = view.findViewById(R.id.blurView);

        Integer[] Rid_heartList = {R.id.heart_list01, R.id.heart_list02, R.id.heart_list03, R.id.heart_list04,
                R.id.heart_list05, R.id.heart_list06, R.id.heart_list07};
        Integer[] Rid_profile = {R.id.profile01, R.id.profile02, R.id.profile03, R.id.profile04,
                R.id.profile05, R.id.profile06, R.id.profile07};
        Integer[] Rid_name = {R.id.name01, R.id.name02, R.id.name03, R.id.name04,
                R.id.name05, R.id.name06, R.id.name07};
        Integer[] Rid_dong = {R.id.dong01, R.id.dong02, R.id.dong03, R.id.dong04,
                R.id.dong05, R.id.dong06, R.id.dong07};
        Integer[] Rid_heart = {R.id.heart01, R.id.heart02, R.id.heart03, R.id.heart04,
                R.id.heart05, R.id.heart06, R.id.heart07};
        Integer[][] Rid_image = {{R.id.image01_1, R.id.image01_2, R.id.image01_3, R.id.image01_4,
                R.id.image01_5, R.id.image01_6, R.id.image01_7, R.id.image01_8, R.id.image01_9, R.id.image01_10},
                {R.id.image02_1, R.id.image02_2, R.id.image02_3, R.id.image02_4,
                        R.id.image02_5, R.id.image02_6, R.id.image02_7, R.id.image02_8, R.id.image02_9, R.id.image02_10},
                {R.id.image03_1, R.id.image03_2, R.id.image03_3, R.id.image03_4,
                        R.id.image03_5, R.id.image03_6, R.id.image03_7, R.id.image03_8, R.id.image03_9, R.id.image03_10},
                {R.id.image04_1, R.id.image04_2, R.id.image04_3, R.id.image04_4,
                        R.id.image04_5, R.id.image04_6, R.id.image04_7, R.id.image04_8, R.id.image04_9, R.id.image04_10},
                {R.id.image05_1, R.id.image05_2, R.id.image05_3, R.id.image05_4,
                        R.id.image05_5, R.id.image05_6, R.id.image05_7, R.id.image05_8, R.id.image05_9, R.id.image05_10},
                {R.id.image06_1, R.id.image06_2, R.id.image06_3, R.id.image06_4,
                        R.id.image06_5, R.id.image06_6, R.id.image06_7, R.id.image06_8, R.id.image06_9, R.id.image06_10},
                {R.id.image07_1, R.id.image07_2, R.id.image07_3, R.id.image07_4,
                        R.id.image07_5, R.id.image07_6, R.id.image07_7, R.id.image07_8, R.id.image07_9, R.id.image07_10}};

        for (int i = 0; i < 7; i++) {
            final int finalI1 = i;
            heart_list[i] = view.findViewById(Rid_heartList[i]);
            profile[i] = view.findViewById(Rid_profile[i]);
            name[i] = view.findViewById(Rid_name[i]);
            dong[i] = view.findViewById(Rid_dong[i]);
            heart[i] = view.findViewById(Rid_heart[i]);
            heart[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickNum == -1) {
                        clickNum = finalI1;
                        heart_list[finalI1].setVisibility(View.VISIBLE);
                        blurView.setVisibility(View.VISIBLE);
                        filterBtn01.setVisibility(View.GONE);
                    } else {
                        heart_list[clickNum].setVisibility(View.GONE);
                        clickNum = finalI1;
                        heart_list[finalI1].setVisibility(View.VISIBLE);
                    }
                }
            });

            for (int j = 0; j < 10; j++) {
                image[i][j] = view.findViewById(Rid_image[i][j]);
            }
        }

        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart_list[clickNum].setVisibility(View.GONE);
                blurView.setVisibility(View.GONE);
                filterBtn01.setVisibility(View.VISIBLE);
                clickNum = -1;
            }
        });
    }
}
