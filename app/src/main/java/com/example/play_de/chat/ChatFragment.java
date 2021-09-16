package com.example.play_de.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;
import com.example.play_de.profile.ProfileActivity;

public class ChatFragment extends Fragment implements OnBackPressedListener, GoUP {
    private MainActivity main;
    private View view;
    private ImageButton back, userBtn;

    private TextView start_chatting;
    private ChatHistoryAdapter chat_adapter;

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
        back = view.findViewById(R.id.backBtn);
        userBtn = view.findViewById(R.id.userBtn);

        chat_adapter = new ChatHistoryAdapter();
        RecyclerView chat_historyView = view.findViewById(R.id.chat_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        chat_historyView.setLayoutManager(layoutManager);
        chat_historyView.setAdapter(chat_adapter);

        start_chatting = view.findViewById(R.id.start_chatting);
        if (chat_adapter.getItemCount() != 0) {
            start_chatting.setVisibility(View.GONE);
            chat_historyView.setVisibility(View.VISIBLE);
        }
    }

    private void eventListener() {
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileActivity.class);
            startActivity(intent);
        });

        chat_adapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("destinationName", chat_adapter.getName());
            intent.putExtra("destinationUid", chat_adapter.getDestUid());
            intent.putExtra("destinationImage", chat_adapter.getImage());
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        main.onBackTime();
    }

    @Override
    public void goToUp() {
        //chat Activity 를 intent 해서, 하트를 띄어주거나 해야함.
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 4);
    }
}
