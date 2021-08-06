package com.example.play_de;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CommunityFragment extends Fragment implements OnBackPressedListener {
    private MainActivity main;
    private View view;
    private LinearLayout backLayout;
    private ListView community_listView;
    private CommunityListViewAdapter community_adapter;
    private EditText filterEdit;
    private Button filterBtn01;
    private RelativeLayout filterBtn02;
    private Button filterBtn02_1;
    private Button filterBtn02_2;
    private Button filterBtn02_3;
    private Button meetBtn;
    private Button recommendBtn;
    private View bar01, bar02;
    private int blackColor, greyColor;
    private RelativeLayout meetLayout, recommendLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_community, container, false);

        community_adapter = new CommunityListViewAdapter(this);
        community_listView = view.findViewById(R.id.listView);
        community_listView.setAdapter(community_adapter);
        addCommunityListView();

        filterEdit = view.findViewById(R.id.filterEdit);
        filterBtn01 = view.findViewById(R.id.filterBtn01);
        filterBtn02 = view.findViewById(R.id.filterBtn02);
        filterBtn02_1 = view.findViewById(R.id.filterBtn02_1);
        filterBtn02_2 = view.findViewById(R.id.filterBtn02_2);
        filterBtn02_3 = view.findViewById(R.id.filterBtn02_3);
        meetBtn = view.findViewById(R.id.meetBtn);
        recommendBtn = view.findViewById(R.id.recommendBtn);
        bar01 = view.findViewById(R.id.bar01);
        bar02 = view.findViewById(R.id.bar02);
        meetLayout = view.findViewById(R.id.meetLayout);
        recommendLayout = view.findViewById(R.id.recommendLayout);

        backLayout = view.findViewById(R.id.backLayout);
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

        blackColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.Black);
        greyColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.Grey);
        meetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meetBtn.setTextColor(blackColor);
                recommendBtn.setTextColor(greyColor);
                bar01.setVisibility(View.VISIBLE);
                bar02.setVisibility(View.INVISIBLE);
                recommendLayout.setVisibility(View.GONE);
                meetLayout.setVisibility(View.VISIBLE);
            }
        });

        recommendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meetBtn.setTextColor(greyColor);
                recommendBtn.setTextColor(blackColor);
                bar01.setVisibility(View.INVISIBLE);
                bar02.setVisibility(View.VISIBLE);
                recommendLayout.setVisibility(View.VISIBLE);
                meetLayout.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private void addCommunityListView() {
        //프로필 이미지 타입 결정하고 설정할 것.
        int profile = R.drawable.circle_grey;
        String name = "이름";
        String dong = "보드게임러버";
        String heart = "5개가 겹칩니다.";
        String place = "2개가 겹칩니다.";

        for (int i = 1; i < 8; i++) {
            community_adapter.addItem(profile, name, dong, heart, place);
        }
        community_adapter.notifyDataSetChanged();
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

    public void onListClick(int position) {
        main.setCurrentItem(community_adapter.getListViewItemsList().get(position).getIsHeart(), 4);
    }
}
