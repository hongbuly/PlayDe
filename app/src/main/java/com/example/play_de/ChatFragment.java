package com.example.play_de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mmin18.widget.RealtimeBlurView;

public class ChatFragment extends Fragment {
    private ListView chat_listView;
    private ChatListViewAdapter chat_adapter;
    private ImageView chat_profile;
    private TextView chat_name;
    private TextView chat_dong;
    private View back_layout;
    private LinearLayout overlap;
    private LinearLayout overlap2;
    private RealtimeBlurView blurView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chat_adapter = new ChatListViewAdapter();
        chat_listView = view.findViewById(R.id.chat_list);
        chat_listView.setAdapter(chat_adapter);
        addChatListView();

        chat_profile = view.findViewById(R.id.chat_profile);
        chat_name = view.findViewById(R.id.chat_name);
        chat_dong = view.findViewById(R.id.chat_dong);

        back_layout = view.findViewById(R.id.backLayout);
        blurView = view.findViewById(R.id.blurView);
        overlap2 = view.findViewById(R.id.overlap2);
        overlap = view.findViewById(R.id.overlap);
        overlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUp();
            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDown();
            }
        });

        //여기서 프로필 이미지, 이름, 동을 설정할 것.

        return view;
    }

    void addChatListView() {
        //여기서 메시지 내용과 누가 보낸 것인지 설정하면 됨.
        String text = "안녕하세요~ 보드게임 아직 하시나요?? 저랑 보드게임하지 않으실래요?? 언제 시간되시나요??";
        boolean isISend = true;

        for (int i = 0; i < 10; i++) {
            chat_adapter.addItem(text, isISend);
            isISend = !isISend;
        }

        chat_adapter.notifyDataSetChanged();
    }

    void goToDown() {
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

    void goToUp() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_up);
        blurView.setVisibility(View.VISIBLE);
        overlap2.setVisibility(View.VISIBLE);
        back_layout.setVisibility(View.VISIBLE);
        overlap2.startAnimation(animation);
    }
}
