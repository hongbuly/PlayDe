package com.example.play_de.cafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.play_de.R;

public class GameSelectFirstFragment extends Fragment {
    static ImageButton[] btn = new ImageButton[4];
    private ImageView[] game = new ImageView[4];
    static String[] gameName = new String[4];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_select, container, false);
        btn[0] = view.findViewById(R.id.select_btn01);
        btn[1] = view.findViewById(R.id.select_btn02);
        btn[2] = view.findViewById(R.id.select_btn03);
        btn[3] = view.findViewById(R.id.select_btn04);

        //서버로부터 게임 이미지와 이름을 받아와 바꿀것.
        game[0] = view.findViewById(R.id.select01);
        game[0].setImageResource(R.drawable.rumicube);
        gameName[0] = "루미큐브";
        game[1] = view.findViewById(R.id.select02);
        game[1].setImageResource(R.drawable.catan);
        gameName[1] = "카탄";
        game[2] = view.findViewById(R.id.select03);
        game[2].setImageResource(R.drawable.monopoly);
        gameName[2] = "모노폴리";
        game[3] = view.findViewById(R.id.select04);
        game[3].setImageResource(R.drawable.qwixx);
        gameName[3] = "Qwixx";

        //어떤 게임이 선택되었는지 이미지를 바꿔주고 정보를 저장해야함.
        for (int i = 0; i < 4; i++) {
            final int finalInt = i;
            if (CafeFragment.checkedGame[finalInt]) {
                if (CafeFragment.countChecked == 1) {
                    btn[finalInt].setBackgroundResource(R.drawable.checked_game01);
                } else if (CafeFragment.countChecked == 2) {
                    if (CafeFragment.firstChecked == finalInt)
                        btn[finalInt].setBackgroundResource(R.drawable.checked_game01);
                    else
                        btn[finalInt].setBackgroundResource(R.drawable.checked_game02);
                }
            }

            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CafeFragment.checkedGame[finalInt]) {
                        btn[finalInt].setBackgroundResource(R.drawable.unchecked_game);
                        CafeFragment.checkedGame[finalInt] = false;
                        if (CafeFragment.countChecked == 2) {
                            changeChecked();
                            changeCheckedAnother();
                        }
                        CafeFragment.countChecked--;
                    } else {
                        if (CafeFragment.countChecked == 0) {
                            btn[finalInt].setBackgroundResource(R.drawable.checked_game01);
                            CafeFragment.checkedGame[finalInt] = true;
                            CafeFragment.countChecked++;
                            CafeFragment.firstChecked = finalInt;
                        } else if (CafeFragment.countChecked == 1) {
                            btn[finalInt].setBackgroundResource(R.drawable.checked_game02);
                            CafeFragment.checkedGame[finalInt] = true;
                            CafeFragment.countChecked++;
                        } else
                            Toast.makeText(getActivity(), "게임은 2개까지만 선택가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }

    static void changeChecked() {
        for (int j = 0; j < 4; j++) {
            if (CafeFragment.checkedGame[j]) {
                btn[j].setBackgroundResource(R.drawable.checked_game01);
                CafeFragment.firstChecked = j;
            }
        }
    }

    private void changeCheckedAnother() {
        GameSelectSecondFragment.changeChecked();
        GameSelectThirdFragment.changeChecked();
    }

    public static void setGameName() {
        for (int j = 0; j < 4; j++) {
            if (CafeFragment.checkedGame[j]) {
                if (CafeFragment.gameList.isEmpty())
                    CafeFragment.gameList = gameName[j];
                else {
                    CafeFragment.gameList += ", " + gameName[j];
                }
            }
        }
    }

    public static void initialization() {
        for (int i = 0; i < 4; i++)
            btn[i].setBackgroundResource(R.drawable.unchecked_game);
    }
}
