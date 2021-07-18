package com.example.play_de;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class GameSelectThirdFragment extends Fragment {
    static ImageButton[] btn = new ImageButton[4];
    private ImageView[] game = new ImageView[4];
    static boolean[] isChecked = new boolean[4];
    static String[] gameName = new String[4];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_game_select, container, false);
        btn[0] = view.findViewById(R.id.third_select_btn01);
        btn[1] = view.findViewById(R.id.third_select_btn02);
        btn[2] = view.findViewById(R.id.third_select_btn03);
        btn[3] = view.findViewById(R.id.third_select_btn04);

        //서버로부터 게임 이미지와 이름을 받아와 바꿀것.
        game[0] = view.findViewById(R.id.third_select01);
        game[0].setImageResource(R.drawable.game01);
        gameName[0] = "다빈치 캐슬";
        game[1] = view.findViewById(R.id.third_select02);
        game[1].setImageResource(R.drawable.game02);
        gameName[1] = "카탄";
        game[2] = view.findViewById(R.id.third_select03);
        game[2].setImageResource(R.drawable.game03);
        gameName[2] = "FUGITIVE";
        game[3] = view.findViewById(R.id.third_select04);
        game[3].setImageResource(R.drawable.game04);
        gameName[3] = "카르카손";

        //어떤 게임이 선택되었는지 이미지를 바꿔주고 정보를 저장해야함.
        for (int i = 0; i < 4; i++) {
            isChecked[i] = false;
            final int finalInt = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isChecked[finalInt]) {
                        btn[finalInt].setBackgroundResource(R.drawable.unchecked_game);
                        isChecked[finalInt] = false;
                        if (HomeFragment.checkedGame == 2) {
                            changeChecked();
                            changeCheckedAnother();
                        }
                        HomeFragment.checkedGame--;
                    } else {
                        if (HomeFragment.checkedGame == 0) {
                            btn[finalInt].setBackgroundResource(R.drawable.checked_game01);
                            isChecked[finalInt] = true;
                            HomeFragment.checkedGame++;
                        } else if (HomeFragment.checkedGame == 1) {
                            btn[finalInt].setBackgroundResource(R.drawable.checked_game02);
                            isChecked[finalInt] = true;
                            HomeFragment.checkedGame++;
                        } else
                            Toast.makeText(getActivity(), "게임은 2개까지만 선택가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }

    public static void changeChecked() {
        for (int j = 0; j < 4; j++) {
            if (isChecked[j]) {
                btn[j].setBackgroundResource(R.drawable.checked_game01);
            }
        }
    }

    private void changeCheckedAnother() {
        GameSelectFirstFragment.changeChecked();
        GameSelectSecondFragment.changeChecked();
    }

    public static void setGameName() {
        for (int j = 0; j < 4; j++) {
            if (isChecked[j]) {
                if (HomeFragment.gameList.isEmpty())
                    HomeFragment.gameList = gameName[j];
                else {
                    HomeFragment.gameList += ", " + gameName[j];
                }
            }
        }
    }
}
