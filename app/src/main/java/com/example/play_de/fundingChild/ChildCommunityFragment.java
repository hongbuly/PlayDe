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

public class ChildCommunityFragment extends Fragment {
    private View view;
    private Context context;
    private TextView write_btn;
    private Button cheering, question;
    private RecyclerView cheering_recycler, question_recycler;
    private EditText write_editText;
    private Button add_write;

    private boolean isCheering;
    private int whiteColor, greyColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //홈화면에 대한 자바 코드 작성을 여기에.
        view = inflater.inflate(R.layout.child_fragment_community, container, false);
        initialization();
        eventListener();
        return view;
    }

    private void initialization() {
        write_btn = view.findViewById(R.id.write_btn);
        cheering = view.findViewById(R.id.cheering);
        question = view.findViewById(R.id.question);

        cheering_recycler = view.findViewById(R.id.cheering_recycler);
        question_recycler = view.findViewById(R.id.question_recycler);
        write_editText = view.findViewById(R.id.write_editText);
        add_write = view.findViewById(R.id.add_write);

        isCheering = true;
        whiteColor = ContextCompat.getColor(context, R.color.White);
        greyColor = ContextCompat.getColor(context, R.color.LineGrey);
    }

    private void eventListener() {
        write_btn.setOnClickListener(v -> {
            if (isCheering) {
                cheering_recycler.setVisibility(View.GONE);
            } else {
                question_recycler.setVisibility(View.GONE);
            }

            write_editText.setVisibility(View.VISIBLE);
            add_write.setVisibility(View.VISIBLE);
        });

        cheering.setOnClickListener(v -> {
            cheering.setBackgroundResource(R.drawable.round_red20);
            cheering.setTextColor(whiteColor);
            question.setBackgroundResource(R.drawable.round_corner_line20);
            question.setTextColor(greyColor);
            cheering_recycler.setVisibility(View.VISIBLE);
            question_recycler.setVisibility(View.GONE);
        });

        question.setOnClickListener(v -> {
            cheering.setBackgroundResource(R.drawable.round_corner_line20);
            cheering.setTextColor(greyColor);
            question.setBackgroundResource(R.drawable.round_red20);
            question.setTextColor(whiteColor);
            cheering_recycler.setVisibility(View.GONE);
            question_recycler.setVisibility(View.VISIBLE);
        });

        add_write.setOnClickListener(v -> {
            write_community();

            write_editText.setVisibility(View.GONE);
            add_write.setVisibility(View.GONE);
        });
    }

    private void write_community() {
        //커뮤니티 글 올리기
        if (isCheering) {
            //응원 글이라면...
        } else {
            //질문 글이라면...
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
