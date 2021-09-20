package com.example.play_de.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;
import com.example.play_de.profile.ProfileActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment implements OnBackPressedListener, GoUP {
    private MainActivity main;
    private Context context;
    private View view;
    private ImageButton back, userBtn;

    private TextView start_chatting;
    private ChatHistoryAdapter chat_adapter;
    RecyclerView chat_historyView;

    private String name, destUid, image;

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
        chat_historyView = view.findViewById(R.id.chat_history);
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
            setData(chat_adapter.setData(position));
            new Handler().postDelayed(() -> {
                intent.putExtra("destinationName", name);
                intent.putExtra("destinationUid", destUid);
                intent.putExtra("destinationImage", image);
                startActivity(intent);
            }, 700);
        });
    }

    private void setData(String destinationUid) {
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("user/profile");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        name = jsonObject.getString("nickname");
                        destUid = Integer.toString(jsonObject.getInt("id"));
                        image = jsonObject.getString("profile");
                    } catch (Exception e) {
                        Log.e("ChatHistory", "예외 발생");
                    }
                },
                error -> {
                    Log.e("ChatHistory", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", destinationUid);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
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
        chat_adapter.setChatModels();
        chat_adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
