package com.example.play_de.chat;

import androidx.annotation.NonNull;
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
import com.example.play_de.main.MainActivity;
import com.github.mmin18.widget.RealtimeBlurView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
    private String chatRoomUid; //채팅방 id
    private String myUid;       //나의 id
    private String destUid;     //상대방 uid
    private String destImage;

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

    private String destName;
    private RecyclerView chat_view;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initialSetUp();
        eventListener();
    }

    void initialSetUp() {


        destName = getIntent().getStringExtra("destinationName");
        destUid = getIntent().getStringExtra("destinationUid"); //채팅 상대
        destImage = getIntent().getStringExtra("destinationImage");
        myUid = MainActivity.userId;

        nameText = findViewById(R.id.nameText);
        nameText.setText(destName);
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
            ChatModel chatModel = new ChatModel();
            chatModel.users.put(myUid, true);
            chatModel.users.put(destUid, true);

            if (chatRoomUid == null) {
                sendBtn.setEnabled(false);
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("chatRooms")
                        .push()
                        .setValue(chatModel)
                        .addOnSuccessListener(unused -> checkChatRoom());
            } else if (!msg_edit.getText().toString().equals("")) {
                sendMsg();
                chat_view.setAdapter(new ChatAdapter(chatRoomUid, destImage));
            }
        });

        overlap.setOnClickListener(v -> goToUp());
        back_layout.setOnClickListener(v -> goToDown());
    }

    void sendGcm() {
        Gson gson = new Gson();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = getDestToken();
        notificationModel.notification.title = destName;
        notificationModel.notification.text = msg_edit.getText().toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notificationModel));
        Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=AAAANipQibc:APA91bEK0mWBtESqbthZXkIF-Bv2tkJao2fOouScTbRuk015-jcJe5LR5wFy5ssoBct6xxpPjS_g8hYitkbayD1nn-K3t65DxpbocaMLGi75u88JkPtkvrYnEEENbMp73OeLkkjUZOei")
                .url("https://gcm-http.googleapis.com/gcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }

    String getDestToken() {
        final String[] token = new String[1];
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("userTokens")
                .orderByChild("uid")
                .equalTo(destUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            TokenModel tokenModel = item.getValue(TokenModel.class);
                            token[0] = tokenModel.pushToken;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return token[0];
    }

    void sendMsg() {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("chatRooms")
                .child(chatRoomUid)
                .child("comments")
                .push()
                .setValue(myUid + ":" + msg_edit.getText().toString() + ":" + getTime())
                .addOnSuccessListener(unused -> {
                    sendGcm();
                    msg_edit.setText("");
                });
    }

    String getTime() {
        SimpleDateFormat mFormat = new SimpleDateFormat("hh:mm");
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    void checkChatRoom() {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("chatRooms")
                .orderByChild("users/" + myUid)
                .equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            ChatModel chatModel = item.getValue(ChatModel.class);
                            if (chatModel.users.containsKey(destUid)) {
                                chatRoomUid = item.getKey();
                                sendBtn.setEnabled(true);
                                chat_view.setLayoutManager(layoutManager);
                                chatAdapter = new ChatAdapter(chatRoomUid, destImage);
                                chat_view.setAdapter(chatAdapter);
                                chat_view.smoothScrollToPosition(chatAdapter.getItemCount());
                                if (!msg_edit.getText().toString().equals(""))
                                    sendMsg();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
