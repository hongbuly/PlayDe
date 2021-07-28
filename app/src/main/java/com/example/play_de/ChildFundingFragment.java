package com.example.play_de;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ChildFundingFragment extends Fragment {
    private TextView plusBtn;
    private RelativeLayout[] game;
    private ImageView[] image;
    private TextView[] name;
    private TextView[] percent;
    private TextView[] tag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //홈화면에 대한 자바 코드 작성을 여기에.
        View view = inflater.inflate(R.layout.child_fragment_funding, container, false);

        plusBtn = view.findViewById(R.id.plusBtn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //더보기 버튼
            }
        });

        game = new RelativeLayout[4];
        image = new ImageView[4];
        name = new TextView[4];
        percent = new TextView[4];
        tag = new TextView[4];

        game[0] = view.findViewById(R.id.game01);
        game[1] = view.findViewById(R.id.game02);
        game[2] = view.findViewById(R.id.game03);
        game[3] = view.findViewById(R.id.game04);

        image[0] = view.findViewById(R.id.image01);
        image[1] = view.findViewById(R.id.image02);
        image[2] = view.findViewById(R.id.image03);
        image[3] = view.findViewById(R.id.image04);

        name[0] = view.findViewById(R.id.name01);
        name[1] = view.findViewById(R.id.name02);
        name[2] = view.findViewById(R.id.name03);
        name[3] = view.findViewById(R.id.name04);

        percent[3] = view.findViewById(R.id.percent01);
        percent[3] = view.findViewById(R.id.percent02);
        percent[3] = view.findViewById(R.id.percent03);
        percent[3] = view.findViewById(R.id.percent04);

        tag[3] = view.findViewById(R.id.tag01);
        tag[3] = view.findViewById(R.id.tag02);
        tag[3] = view.findViewById(R.id.tag03);
        tag[3] = view.findViewById(R.id.tag04);

        game[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //펀딩 게임 상세 화면으로 전환.
                FundingFragment.change_view(true);
            }
        });

        return view;
    }
}