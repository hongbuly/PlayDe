package com.example.play_de;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ChatFragment extends Fragment implements OnBackPressedListener, GoUP {
    private MainActivity main;
    private Context mContext;

    private ListView chat_listView;
    private ChatListViewAdapter chat_adapter;
    private ImageView chat_profile;
    private TextView chat_name;
    private TextView chat_dong;
    private EditText sending_message;
    private Button sending_btn;
    private View back_layout;
    private LinearLayout overlap;
    private LinearLayout overlap2;
    private RealtimeBlurView blurView;
    private String nick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, (key, bundle) -> nick = bundle.getString("bundleKey"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chat_adapter = new ChatListViewAdapter(nick);
        chat_listView = view.findViewById(R.id.chat_list);
        chat_listView.setAdapter(chat_adapter);

        chat_profile = view.findViewById(R.id.chat_profile);
        chat_name = view.findViewById(R.id.chat_name);
        chat_dong = view.findViewById(R.id.chat_dong);

        sending_message = view.findViewById(R.id.sending_message);
        sending_btn = view.findViewById(R.id.sending_btn);

        back_layout = view.findViewById(R.id.backLayout);
        blurView = view.findViewById(R.id.blurView);
        overlap2 = view.findViewById(R.id.overlap2);
        overlap = view.findViewById(R.id.overlap);
        overlap.setOnClickListener(v -> goToUp());

        back_layout.setOnClickListener(v -> goToDown());

        //여기서 프로필 이미지, 이름, 동을 설정할 것.

        FirebaseApp.initializeApp(mContext);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        sending_btn.setOnClickListener(v -> {
            if (sending_message.getText() != null) {
                ChatListViewItem chat = new ChatListViewItem();
                chat.setNickName(nick);
                chat.setText(String.valueOf(sending_message.getText()));
                myRef.push().setValue(chat);
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatListViewItem chat = snapshot.getValue(ChatListViewItem.class);
                chat_adapter.addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void goToDown() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_down);
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
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_up);
        blurView.setVisibility(View.VISIBLE);
        overlap2.setVisibility(View.VISIBLE);
        back_layout.setVisibility(View.VISIBLE);
        overlap2.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        if (back_layout.getVisibility() == View.VISIBLE)
            goToDown();
        else
            main.onBackTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 4);
        main.setGoUP(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
