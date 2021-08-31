package com.example.play_de.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.R;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TabLayout tab;
    private TextView positionText;
    private ImageButton backBtn;

    private RelativeLayout main_profile;
    private ImageView main_image;
    private TextView main_name, main_level;
    private ImageView user_profile, user_friend, user_block, user_reserve, user_game, user_cafe, user_comment, user_write, user_etc;

    private RelativeLayout favorite_game;
    private TextView favorite_game_count, favorite_game_delete;
    private RecyclerView game_recyclerView;
    private ProfileUserAdapter game_adapter;
    private RecyclerView.LayoutManager gameLayoutManager;

    private RelativeLayout favorite_store;
    private TextView favorite_store_count, favorite_store_delete;
    private RecyclerView store_recyclerView;
    private ProfileUserAdapter store_adapter;
    private RecyclerView.LayoutManager storeLayoutManager;

    private LinearLayout review_profile;
    private LinearLayout review_background;
    private LinearLayout recent_play01, recent_play02, recent_play03, recent_play04;
    private ImageView recent_play_image01, recent_play_image02, recent_play_image03, recent_play_image04;
    private TextView recent_play_name01, recent_play_name02, recent_play_name03, recent_play_name04;
    private Button block_review_btn;
    private EditText review_editText;
    private Button register_review_btn;

    private LinearLayout block_layout01;
    private TextView[] _block_reason = new TextView[6];

    private RelativeLayout block_layout02;
    private boolean is_no_see = false;
    private TextView block_reason, no_see;
    private Button block_btn01;

    private RelativeLayout block_layout03;
    private EditText block_edit;
    private Button block_btn02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialSetUp();
        eventListener();
    }

    private void initialSetUp() {
        positionText = findViewById(R.id.positionText);
        backBtn = findViewById(R.id.backBtn);
        tab = findViewById(R.id.MainTab);

        //add tab images
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.game);
        images.add(R.drawable.cafe);
        images.add(R.drawable.community);
        images.add(R.drawable.funding);
        images.add(R.drawable.chat);

        ArrayList<String> names = new ArrayList<>();
        names.add("게임");
        names.add("카페");
        names.add("커뮤니티");
        names.add("펀딩");
        names.add("채팅");

        for (int i = 0; i < 5; i++) {
            tab.getTabAt(i).setIcon(images.get(i));
            tab.getTabAt(i).setText(names.get(i));
        }

        //layout
        main_profile = findViewById(R.id.main_profile);
        review_profile = findViewById(R.id.review_profile);
        review_background = findViewById(R.id.review_background);
        favorite_game = findViewById(R.id.favorite_game);
        favorite_store = findViewById(R.id.favorite_store);
        block_layout01 = findViewById(R.id.block_layout01);
        block_layout02 = findViewById(R.id.block_layout02);
        block_layout03 = findViewById(R.id.block_layout03);

        //main profile
        main_image = findViewById(R.id.main_image);
        main_name = findViewById(R.id.main_name);
        main_name.setText(MainActivity.name);
        main_level = findViewById(R.id.main_level);

        //main profile btn
        user_profile = findViewById(R.id.user_profile);
        user_friend = findViewById(R.id.user_friend);
        user_block = findViewById(R.id.user_block);
        user_reserve = findViewById(R.id.user_reserve);
        user_game = findViewById(R.id.user_game);
        user_cafe = findViewById(R.id.user_cafe);
        user_comment = findViewById(R.id.user_comment);
        user_write = findViewById(R.id.user_write);
        user_etc = findViewById(R.id.user_etc);

        //favorite game
        favorite_game_count = findViewById(R.id.favorite_game_count);
        favorite_game_delete = findViewById(R.id.favorite_game_delete);

        game_adapter = new ProfileUserAdapter();
        game_recyclerView = findViewById(R.id.favorite_game_list);
        gameLayoutManager = new LinearLayoutManager(this);
        game_recyclerView.setLayoutManager(gameLayoutManager);
        game_recyclerView.setAdapter(game_adapter);
        addGameRecyclerView();

        //favorite store
        favorite_store_count = findViewById(R.id.favorite_store_count);
        favorite_store_delete = findViewById(R.id.favorite_store_delete);

        store_adapter = new ProfileUserAdapter();
        store_recyclerView = findViewById(R.id.favorite_store_list);
        storeLayoutManager = new LinearLayoutManager(this);
        store_recyclerView.setLayoutManager(storeLayoutManager);
        store_recyclerView.setAdapter(store_adapter);
        addStoreRecyclerView();

        //review
        recent_play01 = findViewById(R.id.recent_play01);
        recent_play02 = findViewById(R.id.recent_play02);
        recent_play03 = findViewById(R.id.recent_play03);
        recent_play04 = findViewById(R.id.recent_play04);
        block_review_btn = findViewById(R.id.block_review_btn);
        register_review_btn = findViewById(R.id.register_review_btn);

        //block reason
        _block_reason[0] = findViewById(R.id.block_reason01);
        _block_reason[1] = findViewById(R.id.block_reason02);
        _block_reason[2] = findViewById(R.id.block_reason03);
        _block_reason[3] = findViewById(R.id.block_reason04);
        _block_reason[4] = findViewById(R.id.block_reason05);
        _block_reason[5] = findViewById(R.id.block_reason06);

        //no see
        block_reason = findViewById(R.id.block_reason);
        no_see = findViewById(R.id.no_see);
        block_btn01 = findViewById(R.id.block_btn01);

        //block reason etc
        block_edit = findViewById(R.id.block_edit);
        block_btn02 = findViewById(R.id.block_btn02);
    }

    private void eventListener() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 0) {
                    //game
                    MainActivity.setCurrentItem(0);
                } else if (pos == 1) {
                    //cafe
                    MainActivity.setCurrentItem(1);
                } else if (pos == 2) {
                    //community
                    MainActivity.setCurrentItem(2);
                } else if (pos == 3) {
                    //funding
                    MainActivity.setCurrentItem(3);
                } else if (pos == 4) {
                    //chat
                    MainActivity.setCurrentItem(4);
                }
                finish();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        backBtn.setOnClickListener(v -> back_view());

        //main
        main_image.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 0);
        });

        user_friend.setOnClickListener(v -> {
            main_profile.setVisibility(View.GONE);
            review_profile.setVisibility(View.VISIBLE);
        });

        user_game.setOnClickListener(v -> {
            main_profile.setVisibility(View.GONE);
            favorite_game.setVisibility(View.VISIBLE);
        });

        user_cafe.setOnClickListener(v -> {
            main_profile.setVisibility(View.GONE);
            favorite_store.setVisibility(View.VISIBLE);
        });

        //review
        recent_play01.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon01));
        recent_play02.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon02));
        recent_play03.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon03));
        recent_play04.setOnClickListener(v -> review_background.setBackgroundResource(R.drawable.word_balloon04));

        block_review_btn.setOnClickListener(v -> {
            review_profile.setVisibility(View.GONE);
            block_layout01.setVisibility(View.VISIBLE);
        });

        register_review_btn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
            back_view();
        });

        //block reason
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            _block_reason[i].setOnClickListener(v -> {
                block_reason.setText(_block_reason[finalI].getText().toString());
                block_layout01.setVisibility(View.GONE);
                block_layout02.setVisibility(View.VISIBLE);
            });
        }

        //block no see
        no_see.setOnClickListener(v -> {
            if (is_no_see) {
                no_see.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_corner_grey, 0, 0, 0);
                is_no_see = false;
            } else {
                no_see.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_gradient, 0, 0, 0);
                is_no_see = true;
            }
        });

        block_btn01.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "차단되었습니다.", Toast.LENGTH_SHORT).show();
            back_view();
        });

        //block reason etc
        _block_reason[5].setOnClickListener(v -> {
            block_layout01.setVisibility(View.GONE);
            block_layout03.setVisibility(View.VISIBLE);
        });

        block_btn02.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "차단되었습니다.", Toast.LENGTH_SHORT).show();
            back_view();
        });
    }

    private void addGameRecyclerView() {
        ProfileUser item = new ProfileUser();
        item.image = R.drawable.cluedo;
        item.name = "CLUEDO";
        item.switchOn = "삭제";
        item.switchOff = "저장";
        game_adapter.addItem(item);
        game_adapter.notifyDataSetChanged();
    }

    private void addStoreRecyclerView() {
        ProfileUser item = new ProfileUser();
        item.image = R.drawable.cafe01;
        item.name = "정릉 플레이";
        item.switchOn = "삭제";
        item.switchOff = "저장";
        store_adapter.addItem(item);
        store_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            Glide.with(this)
                    .load(selectedImageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(main_image);
            replacePicture(selectedImageUri);
        }
    }

    private void replacePicture(Uri uri) {
        //사용자 프로필사진 변경
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("/user/profile/image/set?user_id=");
        urlStr.append(MainActivity.userId);
        urlStr.append("&image_url=");
        urlStr.append(uri.toString());
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr.toString(),
                this::pictureJSONParse,
                error -> {
                    Toast.makeText(getApplicationContext(), "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("Picture", urlStr.toString());
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    private void pictureJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean("act")) {
                Toast.makeText(getApplicationContext(), "업로드 성공", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("pictureJSONParse", "예외 발생");
        }
    }

    private void back_view() {
        if (main_profile.getVisibility() == View.VISIBLE)
            finish();
        main_profile.setVisibility(View.VISIBLE);
        review_profile.setVisibility(View.GONE);
        favorite_game.setVisibility(View.GONE);
        favorite_store.setVisibility(View.GONE);
        block_layout01.setVisibility(View.GONE);
        block_layout02.setVisibility(View.GONE);
        block_layout03.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        back_view();
    }


}
