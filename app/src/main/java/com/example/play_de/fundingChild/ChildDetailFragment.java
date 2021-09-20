package com.example.play_de.fundingChild;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.play_de.R;

public class ChildDetailFragment extends Fragment {
    private Context context;
    private ImageView board_image;
    private TextView explain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //홈화면에 대한 자바 코드 작성을 여기에.
        View view = inflater.inflate(R.layout.child_fragment_detail, container, false);
        board_image = view.findViewById(R.id.board_image);
        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.image_round10);
        board_image.setBackground(drawable);
        board_image.setClipToOutline(true);

        explain = view.findViewById(R.id.explain);
        explain.setText("<게임 설명>\n플레이어 2~4인/재료카드를 모아 6명의 캐릭터들을 먼저 탈출시키자! 모든 플레이어는 재료 카드 10장과 캐릭터카드 3장을 받고 시작, 순서대로 카드 더미에서 한 장씩 뽑고 한 장씩 버린다. 각 캐릭터에 맞는 재료카드를 4장씩 모아서 캐릭터를 탈출시키면 캐릭터 피규어를 얻는다.");
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
