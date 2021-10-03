package com.example.play_de.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play_de.R;
import com.example.play_de.community.CommunityProfileFavorite;
import com.example.play_de.community.CommunityProfileFavoriteAdapter;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.github.mmin18.widget.RealtimeBlurView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private String destToken;

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
        setFavGame(destUid);

        store_adapter = new CommunityProfileFavoriteAdapter();
        RecyclerView store_recyclerView = findViewById(R.id.store_recycler);
        RecyclerView.LayoutManager storeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        store_recyclerView.setLayoutManager(storeLayoutManager);
        store_recyclerView.setAdapter(store_adapter);
        setFavCafe(destUid);

        chat_view = findViewById(R.id.chat_recycler);
        layoutManager = new LinearLayoutManager(this);
        checkChatRoom();

        setDestToken();
    }

    void eventListener() {
        backBtn.setOnClickListener(v -> finish());

        pictureBtn.setOnClickListener(v -> {
            //사진, 동영상 등등.
        });

        msg_edit.setOnClickListener(v -> {
            new Handler().postDelayed(() -> chat_view.smoothScrollToPosition(chatAdapter.getItemCount() - 1), 200);
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
                new Handler().postDelayed(() -> chat_view.smoothScrollToPosition(chatAdapter.getItemCount() - 1), 200);
            }
        });

        overlap.setOnClickListener(v -> goToUp());
        back_layout.setOnClickListener(v -> goToDown());
    }

    void setDestToken() {
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
                            destToken = tokenModel.pushToken;
                            break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    void sendMsg() {
        ChatModel.CommentModel comments = new ChatModel.CommentModel();
        comments.myUid = myUid;
        comments.message = msg_edit.getText().toString();
        comments.time = getTime();
        comments.read = false;

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("chatRooms")
                .child(chatRoomUid)
                .child("comments")
                .push()
                .setValue(comments)
                .addOnSuccessListener(unused -> {
                    sendGcm();
                    msg_edit.setText("");
                });
    }

    void sendGcm() {
        Gson gson = new Gson();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = destToken;
        notificationModel.notification.title = MainActivity.name;
        notificationModel.notification.body = msg_edit.getText().toString();
        notificationModel.data.title = MainActivity.name;
        notificationModel.data.body = msg_edit.getText().toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notificationModel));
        Log.e("json", gson.toJson(notificationModel));
        Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=AAAANipQibc:APA91bEK0mWBtESqbthZXkIF-Bv2tkJao2fOouScTbRuk015-jcJe5LR5wFy5ssoBct6xxpPjS_g8hYitkbayD1nn-K3t65DxpbocaMLGi75u88JkPtkvrYnEEENbMp73OeLkkjUZOei")
                .url("https://fcm.googleapis.com/fcm/send")
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

    @SuppressLint("SimpleDateFormat")
    String getTime() {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm");
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
                                new Handler().postDelayed(() -> chat_view.smoothScrollToPosition(chatAdapter.getItemCount() - 1), 700);
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

    private void setFavCafe(String uid) {
        //찜 카페 가져오기
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("cafe/fav");
        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String cafe = jsonObject.getString("cafe");
                        JSONArray jsonArray = new JSONArray(cafe);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject subJsonObject = jsonArray.getJSONObject(i);
                            int id = subJsonObject.getInt("id");
                            String name = subJsonObject.getString("name");
                            String profile = subJsonObject.getString("profile");
                            addStoreRecyclerView(id, name, profile);
                        }
                    } catch (Exception e) {
                        Log.e("setFavCafe", "예외 발생");
                    }
                },
                error -> {
                    Toast.makeText(this, "서버와의 연결에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("setFavCafe", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", uid);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    private void setFavGame(String uid) {
        //보드게임 위시리스트 가져오기
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("game/wish");
        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String games = jsonObject.getString("games");
                        JSONArray jsonArray = new JSONArray(games);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject subJsonObject = jsonArray.getJSONObject(i);
                            int id = subJsonObject.getInt("id");
                            String name = subJsonObject.getString("kor_name");
                            String profile = subJsonObject.getString("profile_img");
                            addHeartRecyclerView(id, name, profile);
                        }
                    } catch (Exception e) {
                        Log.e("setFavGame", "예외 발생");
                    }
                },
                error -> {
                    Toast.makeText(this, "서버와의 연결에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("setFavGame", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", uid);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    private void addHeartRecyclerView(int id, String name, String image) {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.id = id;
        item.image = image;
        item.name = name;
        heart_adapter.addItem(item);
        heart_adapter.notifyDataSetChanged();
    }

    private void addStoreRecyclerView(int id, String name, String image) {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.id = id;
        item.image = image;
        item.name = name;
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
