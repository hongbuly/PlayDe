package com.example.play_de.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.play_de.R;
import com.example.play_de.community.CommunityProfileFavorite;
import com.example.play_de.community.CommunityProfileFavoriteAdapter;
import com.github.mmin18.widget.RealtimeBlurView;

public class ChatActivity extends AppCompatActivity {
    private String chatRoomUid; //채팅방 id
    private String myUid;       //나의 id
    private String destUid;     //상대방 uid

    private TextView nameText;
    private ImageButton backBtn;

    private ImageView pictureBtn;
    private EditText msg_edit;
    private ImageView sendBtn;

    private View back_layout;
    private LinearLayout overlap;
    private LinearLayout overlap2;
    private RealtimeBlurView blurView;

    private CommunityProfileFavoriteAdapter heart_adapter;
    private CommunityProfileFavoriteAdapter store_adapter;

    private String name;
    private RecyclerView chat_view;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initialSetUp();
        eventListener();
    }

    void initialSetUp() {
        name = getIntent().getStringExtra("destinationName");
        destUid = getIntent().getStringExtra("destinationUid"); //채팅 상대

        nameText = findViewById(R.id.nameText);
        nameText.setText(name);
        backBtn = findViewById(R.id.backBtn);

        pictureBtn = findViewById(R.id.pictureBtn);
        msg_edit = findViewById(R.id.msg_edit);
        sendBtn = findViewById(R.id.sendBtn);

        back_layout = findViewById(R.id.backLayout);
        blurView = findViewById(R.id.blurView);
        overlap2 = findViewById(R.id.overlap2);
        overlap = findViewById(R.id.overlap);

        heart_adapter = new CommunityProfileFavoriteAdapter();
        RecyclerView heart_recyclerView = findViewById(R.id.heart_recycler);
        RecyclerView.LayoutManager heartLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        heart_recyclerView.setLayoutManager(heartLayoutManager);
        heart_recyclerView.setAdapter(heart_adapter);
        addHeartRecyclerView();

        store_adapter = new CommunityProfileFavoriteAdapter();
        RecyclerView store_recyclerView = findViewById(R.id.store_recycler);
        RecyclerView.LayoutManager storeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        store_recyclerView.setLayoutManager(storeLayoutManager);
        store_recyclerView.setAdapter(store_adapter);
        addStoreRecyclerView();

        chat_view = findViewById(R.id.chat_recycler);
        layoutManager = new LinearLayoutManager(this);
        checkChatRoom();
    }

    void eventListener() {
        backBtn.setOnClickListener(v -> finish());

        pictureBtn.setOnClickListener(v -> {
            //사진, 동영상 등등.
        });

        sendBtn.setOnClickListener(v -> {

        });

        overlap.setOnClickListener(v -> goToUp());
        back_layout.setOnClickListener(v -> goToDown());
    }

    private void checkChatRoom() {

    }

    private void addHeartRecyclerView() {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.image = R.drawable.rumicube;
        item.name = "루미큐브";
        heart_adapter.addItem(item);

        item.image = R.drawable.cluedo;
        item.name = "클루";
        heart_adapter.addItem(item);

        item.image = R.drawable.ticket_to_ride;
        item.name = "티켓투라이드";
        heart_adapter.addItem(item);

        item.image = R.drawable.uno;
        item.name = "우노";
        heart_adapter.addItem(item);

        item.image = R.drawable.diamond;
        item.name = "다이아몬드";
        heart_adapter.addItem(item);

        heart_adapter.notifyDataSetChanged();
    }

    private void addStoreRecyclerView() {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.image = R.drawable.rumicube;
        item.name = "루미큐브";
        store_adapter.addItem(item);

        item.image = R.drawable.cluedo;
        item.name = "클루";
        store_adapter.addItem(item);

        item.image = R.drawable.ticket_to_ride;
        item.name = "티켓투라이드";
        store_adapter.addItem(item);

        item.image = R.drawable.uno;
        item.name = "우노";
        store_adapter.addItem(item);

        item.image = R.drawable.diamond;
        item.name = "다이아몬드";
        store_adapter.addItem(item);

        store_adapter.notifyDataSetChanged();
    }

    private void goToDown() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_down);
        overlap2.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                blurView.setVisibility(View.GONE);
                overlap2.setVisibility(View.GONE);
                back_layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void goToUp() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.down_up);
        blurView.setVisibility(View.VISIBLE);
        overlap2.setVisibility(View.VISIBLE);
        back_layout.setVisibility(View.VISIBLE);
        overlap2.startAnimation(animation);
    }
}
