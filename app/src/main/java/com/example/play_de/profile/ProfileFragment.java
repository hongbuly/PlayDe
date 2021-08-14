package com.example.play_de.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.play_de.R;

public class ProfileFragment extends Fragment {
    public static View view;
    private Context context;

    private ImageButton backBtn;

    public static LinearLayout main_profile;

    private ImageView profile_image;
    private TextView profile_name;
    private ImageButton heart_btn;
    private ImageButton store_btn;

    public static LinearLayout favorite_game;
    private ListView favorite_game_list;
    public static LinearLayout favorite_store;
    private ListView favorite_store_list;

    private FavoriteListViewAdapter favorite_game_adapter;
    private FavoriteListViewAdapter favorite_store_adapter;

    private Button recent_play_btnM;
    private LinearLayout recent_play01M;
    private ImageView recent_play_image01M;
    private TextView recent_play_name01M;
    private LinearLayout recent_play02M;
    private ImageView recent_play_image02M;
    private TextView recent_play_name02M;
    private LinearLayout recent_play03M;
    private ImageView recent_play_image03M;
    private TextView recent_play_name03M;
    private LinearLayout recent_play04M;
    private ImageView recent_play_image04M;
    private TextView recent_play_name04M;

    private Button friend_btn;
    private LinearLayout friend01;
    private ImageView friend_image01;
    private TextView friend_name01;
    private LinearLayout friend02;
    private ImageView friend_image02;
    private TextView friend_name02;
    private LinearLayout friend03;
    private ImageView friend_image03;
    private TextView friend_name03;
    private LinearLayout friend04;
    private ImageView friend_image04;
    private TextView friend_name04;


    public static LinearLayout review_profile;
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


    public static LinearLayout block_reason;
    private boolean[] isChecked;
    private Button block_reason_btn01;
    private Button block_reason_btn02;
    private Button block_reason_btn03;
    private Button block_reason_btn04;
    private EditText block_review_editText;
    private Button block_review_btn2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //프로필 화면 자바 코드 작성을 여기에.
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = container.getContext();

        main_profile = view.findViewById(R.id.main_profile);
        review_profile = view.findViewById(R.id.review_profile);
        review_background = view.findViewById(R.id.review_background);
        block_reason = view.findViewById(R.id.block_reason);
        favorite_game = view.findViewById(R.id.favorite_game);
        favorite_store = view.findViewById(R.id.favorite_store);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_view();
            }
        });

        //main profile
        profile_image = view.findViewById(R.id.profile_image);
        profile_name = view.findViewById(R.id.profile_name);

        heart_btn = view.findViewById(R.id.heart_btn);
        heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                favorite_game.setVisibility(View.VISIBLE);
            }
        });

        store_btn = view.findViewById(R.id.store_btn);
        store_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                favorite_store.setVisibility(View.VISIBLE);
            }
        });

        favorite_game_adapter = new FavoriteListViewAdapter();
        favorite_game_list = view.findViewById(R.id.favorite_game_list);
        favorite_game_list.setAdapter(favorite_game_adapter);
        addGameListView();

        favorite_store_adapter = new FavoriteListViewAdapter();
        favorite_store_list = view.findViewById(R.id.favorite_store_list);
        favorite_store_list.setAdapter(favorite_store_adapter);
        addStoreListView();

        recent_play01M = view.findViewById(R.id.recent_play01M);
        recent_play01M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon01);
            }
        });

        recent_play02M = view.findViewById(R.id.recent_play02M);
        recent_play02M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon02);
            }
        });

        recent_play03M = view.findViewById(R.id.recent_play03M);
        recent_play03M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon03);
            }
        });

        recent_play04M = view.findViewById(R.id.recent_play04M);
        recent_play04M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_profile.setVisibility(View.GONE);
                review_profile.setVisibility(View.VISIBLE);
                review_background.setBackgroundResource(R.drawable.word_balloon04);
            }
        });

        //review
        recent_play01 = view.findViewById(R.id.recent_play01);
        recent_play01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_background.setBackgroundResource(R.drawable.word_balloon01);
            }
        });

        recent_play02 = view.findViewById(R.id.recent_play02);
        recent_play02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_background.setBackgroundResource(R.drawable.word_balloon02);
            }
        });

        recent_play03 = view.findViewById(R.id.recent_play03);
        recent_play03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_background.setBackgroundResource(R.drawable.word_balloon03);
            }
        });

        recent_play04 = view.findViewById(R.id.recent_play04);
        recent_play04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_background.setBackgroundResource(R.drawable.word_balloon04);
            }
        });

        block_review_btn = view.findViewById(R.id.block_review_btn);
        block_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_profile.setVisibility(View.GONE);
                block_reason.setVisibility(View.VISIBLE);
            }
        });

        register_review_btn = view.findViewById(R.id.register_review_btn);
        register_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                back_view();
            }
        });

        //block reason
        isChecked = new boolean[4];
        for (int i = 0; i < 4; i++) {
            isChecked[i] = false;
        }

        block_reason_btn01 = view.findViewById(R.id.block_reason_btn01);
        block_reason_btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[0]) {
                    block_reason_btn01.setBackgroundResource(R.drawable.circle_corner_grey);
                    isChecked[0] = false;
                } else {
                    block_reason_btn01.setBackgroundResource(R.drawable.circle_corner_red);
                    isChecked[0] = true;
                }
            }
        });

        block_reason_btn02 = view.findViewById(R.id.block_reason_btn02);
        block_reason_btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[1]) {
                    block_reason_btn02.setBackgroundResource(R.drawable.circle_corner_grey);
                    isChecked[1] = false;
                } else {
                    block_reason_btn02.setBackgroundResource(R.drawable.circle_corner_red);
                    isChecked[1] = true;
                }
            }
        });

        block_reason_btn03 = view.findViewById(R.id.block_reason_btn03);
        block_reason_btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[2]) {
                    block_reason_btn03.setBackgroundResource(R.drawable.circle_corner_grey);
                    isChecked[2] = false;
                } else {
                    block_reason_btn03.setBackgroundResource(R.drawable.circle_corner_red);
                    isChecked[2] = true;
                }
            }
        });

        block_reason_btn04 = view.findViewById(R.id.block_reason_btn04);
        block_reason_btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked[3]) {
                    block_reason_btn04.setBackgroundResource(R.drawable.circle_corner_grey);
                    isChecked[3] = false;
                } else {
                    block_reason_btn04.setBackgroundResource(R.drawable.circle_corner_red);
                    isChecked[3] = true;
                }
            }
        });

        block_review_editText = view.findViewById(R.id.block_review_editText);

        block_review_btn2 = view.findViewById(R.id.block_review_btn2);
        block_review_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "차단되었습니다.", Toast.LENGTH_SHORT).show();
                back_view();
            }
        });

        return view;
    }

    public static void back_view() {
        main_profile.setVisibility(View.VISIBLE);
        review_profile.setVisibility(View.GONE);
        block_reason.setVisibility(View.GONE);
        favorite_game.setVisibility(View.GONE);
        favorite_store.setVisibility(View.GONE);
    }

    public static boolean is_review_view() {
        try {
            if (main_profile.getVisibility() == View.GONE) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    void addGameListView() {
        int[] favorite_game = new int[4];
        favorite_game[0] = R.drawable.rumicube;
        favorite_game[1] = R.drawable.catan;
        favorite_game[2] = R.drawable.monopoly;
        favorite_game[3] = R.drawable.qwixx;

        String[] name = new String[4];
        name[0] = "루미큐브";
        name[1] = "카탄";
        name[2] = "모노폴리";
        name[3] = "Qwixx";
        favorite_game_adapter.addItem(favorite_game, name);
    }

    void addStoreListView() {
        int[] favorite_store = new int[4];
        String[] name = new String[4];
        for (int i = 0; i < 4; i++) {
            favorite_store[i] = R.drawable.round_grey;
            name[i] = "이름";
        }

        for (int i = 0; i < 9; i++)
            favorite_store_adapter.addItem(favorite_store, name);
    }
}
