package com.example.play_de;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class ChatActivity extends AppCompatActivity {
    private String chatRoomUid; //채팅방 하나 id
    private String myUid;       //나의 id
    private String destUid;     //상대방 uid

    private FirebaseDatabase firebaseDatabase;
    private User destUser;

    private TextView nameText;
    private ImageButton backBtn;

    private ImageView plusBtn;
    private EditText msg_edit;
    private ImageView sendBtn;

    private View back_layout;
    private LinearLayout overlap;
    private LinearLayout overlap2;
    private RealtimeBlurView blurView;

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
        Intent intent = getIntent();
        if (intent.getExtras().getString("name") != null)
            name = intent.getExtras().getString("name");

        firebaseDatabase = FirebaseDatabase.getInstance();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        destUid = getIntent().getStringExtra("destUid"); //채팅 상대

        nameText = findViewById(R.id.nameText);
        nameText.setText(name);
        backBtn = findViewById(R.id.backBtn);

        plusBtn = findViewById(R.id.plusBtn);
        msg_edit = findViewById(R.id.msg_edit);
        sendBtn = findViewById(R.id.sendBtn);

        back_layout = findViewById(R.id.backLayout);
        blurView = findViewById(R.id.blurView);
        overlap2 = findViewById(R.id.overlap2);
        overlap = findViewById(R.id.overlap);

        chat_view = findViewById(R.id.chat_recycler);
        layoutManager = new LinearLayoutManager(this);
        //addChatRecyclerView();
        checkChatRoom();
    }

//    private void addChatRecyclerView() {
//        //서버로부터 데이터 가져와서 추가하기.
//        ChatRecyclerItem item = new ChatRecyclerItem();
//        int image = R.drawable.cafe01;
//        String name = "윤홍현";
//        String text = "안녕하세요";
//
//        item.setData(image, name, text);
//        chat_adapter.addItem(item);
//        chat_adapter.notifyDataSetChanged();
//    }

    void eventListener() {
        backBtn.setOnClickListener(v -> finish());

        plusBtn.setOnClickListener(v -> {
            //사진, 동영상 등등.
        });

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        sendBtn.setOnClickListener(v -> {
            ChatModel chatModel = new ChatModel();
            chatModel.users.put(myUid, true);
            chatModel.users.put(destUid, true);

            //push() 데이터가 쌓이기 위해 채팅방 key가 생성
            if (chatRoomUid == null) {
                Toast.makeText(this, "채팅방 생성", Toast.LENGTH_SHORT).show();
                sendBtn.setEnabled(false);
                firebaseDatabase.getReference().child("chatRooms").push().setValue(chatModel).addOnSuccessListener(aVoid -> checkChatRoom());
            } else {
                sendMsgToDataBase();
            }
        });

        overlap.setOnClickListener(v -> goToUp());
        back_layout.setOnClickListener(v -> goToDown());
    }

    private void checkChatRoom() {
        //자신 key == true 일때 chatModel 가져온다.
        /* chatModel
        public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저
        public Map<String, ChatModel.Comment> comments = new HashMap<>(); //채팅 메시지
        */
        firebaseDatabase.getReference().child("chatRooms").orderByChild("users/" + myUid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) //나, 상대방 id 가져온다.
                {
                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                    if (chatModel.users.containsKey(destUid)) { //상대방 id 포함돼 있을때 채팅방 key 가져옴
                        chatRoomUid = dataSnapshot.getKey();
                        sendBtn.setEnabled(true);

                        //동기화
                        chat_view.setLayoutManager(layoutManager);
                        chat_view.setAdapter(new ChatAdapter(firebaseDatabase, destUid, destUser, chatRoomUid, chat_view, myUid));

                        //메시지 보내기
                        sendMsgToDataBase();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void sendMsgToDataBase() {
        if (!msg_edit.getText().toString().equals("")) {
            ChatModel.Comment comment = new ChatModel.Comment();
            comment.uid = myUid;
            comment.message = msg_edit.getText().toString();
            comment.timestamp = ServerValue.TIMESTAMP;
            firebaseDatabase.getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnSuccessListener((OnSuccessListener<Void>) aVoid -> msg_edit.setText(""));
        }
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
