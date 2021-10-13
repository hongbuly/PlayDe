package com.example.play_de.fundingChild;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.play_de.R;

public class ChildDoingFragment extends Fragment {
    private Context context;
    private ImageView board_image;
    private ImageButton heart;
    private boolean isClicked = false;
    private Button go_funding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //홈화면에 대한 자바 코드 작성을 여기에.
        View view = inflater.inflate(R.layout.child_fragment_doing, container, false);

        board_image = view.findViewById(R.id.board_image);
        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.image_round10);
        board_image.setBackground(drawable);
        board_image.setClipToOutline(true);

        heart = view.findViewById(R.id.heart);
        heart.setOnClickListener(v -> {
            if (isClicked) {
                heart.setImageResource(R.drawable.grey_heart);
            } else {
                heart.setImageResource(R.drawable.red_heart);
            }

            isClicked = !isClicked;
        });

        go_funding = view.findViewById(R.id.go_funding);
        go_funding.setOnClickListener(v -> {
            Toast.makeText(context, "준비중인 기능입니다.", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
