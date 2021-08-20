package com.example.play_de.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.play_de.R;

public class ProfileFragment extends Fragment {
    private Context context;
    private ImageButton backBtn;

    private RelativeLayout main_profile;
    private ImageView main_image;
    private TextView main_name, main_level;

    private RelativeLayout favorite_game;
    private RelativeLayout favorite_store;

    private LinearLayout review_profile;
    private LinearLayout review_background;
    private Button recent_play_btn;
    private LinearLayout recent_play01;
    private ImageView recent_play_image01;
    private TextView recent_play_name01;
    private LinearLayout recent_play02;
    private ImageView recent_play_image02;
    private TextView recent_play_name02;
    private LinearLayout recent_play03;
    private ImageView recent_play_image03;
    private TextView recent_play_name03;
    private LinearLayout recent_play04;
    private ImageView recent_play_image04;
    private TextView recent_play_name04;
    private Button block_review_btn;
    private EditText review_editText;
    private Button register_review_btn;

    private LinearLayout block_layout01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //프로필 화면 자바 코드 작성을 여기에.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = container.getContext();

        main_profile = view.findViewById(R.id.main_profile);
        review_profile = view.findViewById(R.id.review_profile);
        review_background = view.findViewById(R.id.review_background);
        block_layout01 = view.findViewById(R.id.block_layout01);
        favorite_game = view.findViewById(R.id.favorite_game);
        favorite_store = view.findViewById(R.id.favorite_store);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> back_view());

        //main profile
        main_image = view.findViewById(R.id.main_image);
        main_name = view.findViewById(R.id.main_name);
        main_level = view.findViewById(R.id.main_level);

        //review
        recent_play01 = view.findViewById(R.id.recent_play01);
        recent_play01.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon01));

        recent_play02 = view.findViewById(R.id.recent_play02);
        recent_play02.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon02));

        recent_play03 = view.findViewById(R.id.recent_play03);
        recent_play03.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon03));

        recent_play04 = view.findViewById(R.id.recent_play04);
        recent_play04.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon04));

        block_review_btn = view.findViewById(R.id.block_review_btn);
        block_review_btn.setOnClickListener(v -> {
            review_profile.setVisibility(View.GONE);
            block_layout01.setVisibility(View.VISIBLE);
        });

        register_review_btn = view.findViewById(R.id.register_review_btn);
        register_review_btn.setOnClickListener(v -> {
            Toast.makeText(context, "등록되었습니다.", Toast.LENGTH_SHORT).show();
            back_view();
        });

        return view;
    }

    private void back_view() {
        main_profile.setVisibility(View.VISIBLE);
        review_profile.setVisibility(View.GONE);
        block_layout01.setVisibility(View.GONE);
        favorite_game.setVisibility(View.GONE);
        favorite_store.setVisibility(View.GONE);
    }
}
