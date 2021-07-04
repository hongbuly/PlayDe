package com.example.play_de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static LinearLayout main_profile;

    private ImageView profile_image;
    private TextView profile_name;
    private ImageButton heart_btn;
    private ImageButton store_btn;

    private Button recent_play_btn;
    private LinearLayout recent_play01M;
    private ImageView recent_play_image01M;
    private TextView recent_play_name01M;
    private LinearLayout recent_play02M;
    private ImageView recent_play_image02M;
    private TextView recent_play_name02M;
    private LinearLayout recent_play03M;
    private ImageView recent_play_image03M;
    private TextView recent_play_name03M;
    private LinearLayout recent_play04M;
    private ImageView recent_play_image04M;
    private TextView recent_play_name04M;

    private Button friend_btn;
    private LinearLayout friend01;
    private ImageView friend_image01;
    private TextView friend_name01;
    private LinearLayout friend02;
    private ImageView friend_image02;
    private TextView friend_name02;
    private LinearLayout friend03;
    private ImageView friend_image03;
    private TextView friend_name03;
    private LinearLayout friend04;
    private ImageView friend_image04;
    private TextView friend_name04;


    public static LinearLayout review_profile;
    private LinearLayout review_background;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //프로필 화면 자바 코드 작성을 여기에.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        main_profile = view.findViewById(R.id.main_profile);
        review_profile = view.findViewById(R.id.review_profile);
        review_background = view.findViewById(R.id.review_background);

        recent_play01M = view.findViewById(R.id.recent_play01M);
        recent_play01M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon01);
            }
        });

        recent_play02M = view.findViewById(R.id.recent_play02M);
        recent_play02M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon02);
            }
        });

        recent_play03M = view.findViewById(R.id.recent_play03M);
        recent_play03M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon03);
            }
        });

        recent_play04M = view.findViewById(R.id.recent_play04M);
        recent_play04M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon04);
            }
        });

        return view;
    }

    public static void back_view() {
        main_profile.setVisibility(View.VISIBLE);
        review_profile.setVisibility(View.GONE);
    }

    public static boolean is_review_view() {
        if (main_profile.getVisibility() == View.GONE) {
            return true;
        } else {
            return false;
        }
    }
}
