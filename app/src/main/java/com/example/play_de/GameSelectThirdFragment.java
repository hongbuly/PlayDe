package com.example.play_de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
        View view = inflater.inflate(R.layout.fragment_game_select, container, false);
        btn[0] = view.findViewById(R.id.select_btn01);
        btn[1] = view.findViewById(R.id.select_btn02);
        btn[2] = view.findViewById(R.id.select_btn03);
        btn[3] = view.findViewById(R.id.select_btn04);

        //서버로부터 게임 이미지와 이름을 받아와 바꿀것.
        game[0] = view.findViewById(R.id.select01);
        game[0].setImageResource(R.drawable.diamond);
        gameName[0] = "다이아몬드";
        game[1] = view.findViewById(R.id.select02);
        game[1].setImageResource(R.drawable.cluedo);
        gameName[1] = "CLUEDO";
        game[2] = view.findViewById(R.id.select03);
        game[2].setImageResource(R.drawable.ticket_to_ride);
        gameName[2] = "TicketToRide";
        game[3] = view.findViewById(R.id.select04);
        game[3].setImageResource(R.drawable.uno);
        gameName[3] = "UNO";

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
                        if (CafeFragment.checkedGame == 2) {
                            changeChecked();
                            changeCheckedAnother();
                        }
                        CafeFragment.checkedGame--;
                    } else {
                        if (CafeFragment.checkedGame == 0) {
                            btn[finalInt].setBackgroundResource(R.drawable.checked_game01);
                            isChecked[finalInt] = true;
                            CafeFragment.checkedGame++;
                        } else if (CafeFragment.checkedGame == 1) {
                            btn[finalInt].setBackgroundResource(R.drawable.checked_game02);
                            isChecked[finalInt] = true;
                            CafeFragment.checkedGame++;
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
                if (CafeFragment.gameList.isEmpty())
                    CafeFragment.gameList = gameName[j];
                else {
                    CafeFragment.gameList += ", " + gameName[j];
                }
            }
        }
    }
}
