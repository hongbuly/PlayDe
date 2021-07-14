package com.example.play_de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class GameSelectFirstFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static GameSelectFirstFragment newInstance(String param1, String param2) {
        GameSelectFirstFragment fragment = new GameSelectFirstFragment();
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
        View view = inflater.inflate(R.layout.fragment_first_game_select, container, false);
        final Button btn1 = view.findViewById(R.id.first_select_btn01);
        final Button btn2 = view.findViewById(R.id.first_select_btn02);
        final Button btn3 = view.findViewById(R.id.first_select_btn03);
        final Button btn4 = view.findViewById(R.id.first_select_btn04);

        ImageView game1 = view.findViewById(R.id.first_select01);
        ImageView game2 = view.findViewById(R.id.first_select02);
        ImageView game3 = view.findViewById(R.id.first_select03);
        ImageView game4 = view.findViewById(R.id.first_select04);

        final boolean[] isChecked = new boolean[4];
        for (int i = 0; i < 4; i++) {
            isChecked[i] = false;
        }
        //어떤 게임이 선택되었는지 이미지를 바꿔주고 정보를 저장해야함.
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[0]) {
                    btn1.setBackgroundResource(R.drawable.unchecked_game);
                    isChecked[0] = false;
                } else {
                    btn1.setBackgroundResource(R.drawable.checked_game01);
                    isChecked[0] = true;
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[1]) {
                    btn2.setBackgroundResource(R.drawable.unchecked_game);
                    isChecked[1] = false;
                } else {
                    btn2.setBackgroundResource(R.drawable.checked_game02);
                    isChecked[1] = true;
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[2]) {
                    btn3.setBackgroundResource(R.drawable.unchecked_game);
                    isChecked[2] = false;
                } else {
                    btn3.setBackgroundResource(R.drawable.checked_game03);
                    isChecked[2] = true;
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[3]) {
                    btn4.setBackgroundResource(R.drawable.unchecked_game);
                    isChecked[3] = false;
                } else {
                    btn4.setBackgroundResource(R.drawable.checked_game04);
                    isChecked[3] = true;
                }
            }
        });
        return view;
    }
}
