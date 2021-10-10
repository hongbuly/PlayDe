package com.example.play_de.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.play_de.R;
import com.example.play_de.community.CommunityProfileFavoriteAdapter;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.profile.ProfileActivity;

public class GameFragment extends Fragment implements OnBackPressedListener {
    private Context context;
    private View view;
    private MainActivity main;
    private ImageButton userBtn;
    private EditText filterEdit;

    private Button cafe, game, community;
    private GameRecyclerAdapter recommendAdapter, wishAdapter, recentlyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);
        initialSetUp();
        eventListener();
        return view;
    }

    void initialSetUp() {
        main = (MainActivity) getActivity();
        userBtn = view.findViewById(R.id.userBtn);
        filterEdit = view.findViewById(R.id.filterEdit);

        cafe = view.findViewById(R.id.cafe);
        game = view.findViewById(R.id.game);
        community = view.findViewById(R.id.community);

        recommendAdapter = new GameRecyclerAdapter();
        RecyclerView recommend_recyclerView = view.findViewById(R.id.recommend);
        RecyclerView.LayoutManager recommendLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recommend_recyclerView.setLayoutManager(recommendLayoutManager);
        recommend_recyclerView.setAdapter(recommendAdapter);

        wishAdapter = new GameRecyclerAdapter();
        RecyclerView wish_recyclerView = view.findViewById(R.id.wish);
        RecyclerView.LayoutManager wishLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        wish_recyclerView.setLayoutManager(wishLayoutManager);
        wish_recyclerView.setAdapter(wishAdapter);

        recentlyAdapter = new GameRecyclerAdapter();
        RecyclerView recently_recyclerView = view.findViewById(R.id.recently);
        RecyclerView.LayoutManager recentlyLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recently_recyclerView.setLayoutManager(recentlyLayoutManager);
        recently_recyclerView.setAdapter(recentlyAdapter);
    }

    void eventListener() {
        //오른쪽 상단 프로필 버튼
        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileActivity.class);
            startActivity(intent);
        });

        cafe.setOnClickListener(v -> MainActivity.setCurrentItem(1));
        community.setOnClickListener(v -> MainActivity.setCurrentItem(2));
    }

    @Override
    public void onBackPressed() {
        main.onBackTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 0);
    }
}
