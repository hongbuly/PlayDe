package com.example.play_de.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;

public class ChatFragment extends Fragment implements OnBackPressedListener, GoUP {
    private MainActivity main;
    private View view;

    private RecyclerView chat_historyView;
    private ChatHistoryAdapter chat_adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        initialSetUp();
        eventListener();
        return view;
    }

    private void initialSetUp() {
        main = (MainActivity) getActivity();
        if (main.getName() != null)
            name = main.getName();

        chat_adapter = new ChatHistoryAdapter();
        chat_historyView = view.findViewById(R.id.chat_history);
        layoutManager = new LinearLayoutManager(getActivity());
        chat_historyView.setLayoutManager(layoutManager);
        chat_historyView.setAdapter(chat_adapter);
        addChatHistoryView();
    }

    private void eventListener() {
        chat_adapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("name", chat_adapter.getName(position));
            startActivity(intent);
        });
    }

    private void addChatHistoryView() {
        //서버로부터 데이터 가져와서 추가하기.
        ChatRecyclerItem item = new ChatRecyclerItem();
        int image = R.drawable.cafe01;

        item.setData(image, name, "안녕하세요");
        chat_adapter.addItem(item);

        item = new ChatRecyclerItem();
        item.setData(image, "신채이", "누구세요");
        chat_adapter.addItem(item);

        chat_adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        main.onBackTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 4);
        main.setGoUP(this);
    }

    @Override
    public void goToUp() {
        //chat Activity 를 intent 해서, 하트를 띄어주거나 해야함.
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        startActivity(intent);
    }
}
