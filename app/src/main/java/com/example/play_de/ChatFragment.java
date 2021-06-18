package com.example.play_de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChatFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView chat_listView;
    private ChatListViewAdapter chat_adapter;
    private ImageView chat_profile;
    private TextView chat_name;
    private TextView chat_dong;

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
}
