package com.example.play_de.fundingChild;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;

public class ChildNewsFragment extends Fragment {
    private View view;
    private Context context;
    private Button last, past;
    private RecyclerView news_recycler;

    private boolean isLast;
    private int whiteColor, greyColor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //홈화면에 대한 자바 코드 작성을 여기에.
        view = inflater.inflate(R.layout.child_fragment_news, container, false);
        initialization();
        eventListener();
        return view;
    }

    private void initialization() {
        last = view.findViewById(R.id.last);
        past = view.findViewById(R.id.past);
        news_recycler = view.findViewById(R.id.news_recycler);

        isLast = true;
        whiteColor = ContextCompat.getColor(context, R.color.White);
        greyColor = ContextCompat.getColor(context, R.color.LineGrey);
    }

    private void eventListener() {
        last.setOnClickListener(v -> {
            last.setBackgroundResource(R.drawable.round_red20);
            last.setTextColor(whiteColor);
            past.setBackgroundResource(R.drawable.round_corner_line20);
            past.setTextColor(greyColor);
        });

        past.setOnClickListener(v -> {
            last.setBackgroundResource(R.drawable.round_corner_line20);
            last.setTextColor(greyColor);
            past.setBackgroundResource(R.drawable.round_red20);
            past.setTextColor(whiteColor);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
