package com.example.play_de.fundingChild;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.play_de.R;

public class ChildDoingFragment extends Fragment {
    private Context context;
    private ImageView board_image;

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

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
