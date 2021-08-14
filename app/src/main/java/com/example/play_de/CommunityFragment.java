package com.example.play_de;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class CommunityFragment extends Fragment implements OnBackPressedListener {
    private MainActivity main;
    private View view;
    private EditText filterEdit;

    private RecyclerView tag_recyclerView;
    private CommunityTagAdapter communityTagAdapter;
    private RecyclerView.LayoutManager tagLayoutManager;

    private RecyclerView community_recyclerView;
    private CommunityRecyclerAdapter communityRecyclerAdapter;
    private RecyclerView.LayoutManager communityLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_community, container, false);

        initialSetUp();

        return view;
    }

    private void initialSetUp() {
        filterEdit = view.findViewById(R.id.filterEdit);

        communityTagAdapter = new CommunityTagAdapter();
        tag_recyclerView = view.findViewById(R.id.tag_recycler);
        tagLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        tag_recyclerView.setLayoutManager(tagLayoutManager);
        tag_recyclerView.setAdapter(communityTagAdapter);
        addTagRecyclerView();

        communityRecyclerAdapter = new CommunityRecyclerAdapter();
        community_recyclerView = view.findViewById(R.id.community_recycler);
        communityLayoutManager = new LinearLayoutManager(getActivity());
        community_recyclerView.setLayoutManager(communityLayoutManager);
        community_recyclerView.setAdapter(communityRecyclerAdapter);
        addCommunityRecyclerView();
    }

    private void addTagRecyclerView() {
        String item = "#추천해요";
        communityTagAdapter.addItem(item);
        item = "#만나요";
        communityTagAdapter.addItem(item);
        item = "#질문있어요";
        communityTagAdapter.addItem(item);
        item = "#보드게임 소식";
        communityTagAdapter.addItem(item);
        item = "#일상";
        communityTagAdapter.addItem(item);
        item = "#수다";
        communityTagAdapter.addItem(item);
        communityTagAdapter.notifyDataSetChanged();
    }

    private void addCommunityRecyclerView() {
        //서버로부터 데이터 가져와서 추가하기.
        CommunityRecycler item = new CommunityRecycler();
        int image = R.drawable.diamond;
        String name = "방한슬";
        String level = "보드게임러버";
        String content = "클루 보드게임 최근 들어서 인기가 많은데, 요번주 주말에 만나서 같이 하실 분들 없으신가요?";

        item.setData(image, name, level, content);
        communityRecyclerAdapter.addItem(item);

        item = new CommunityRecycler();
        name = "박혜원";
        content = "루미큐브 앱으로도 잘 되어 있던데, 혹시 비대면으로 지금 4인으로 보드 게임하실 분들 계신가요?";
        item.setData(image, name, level, content);
        communityRecyclerAdapter.addItem(item);

        item = new CommunityRecycler();
        name = "이지상";
        content = "Ticket To Ride 모바일 앱이 있던데, 같이 하실 분 있나요??";
        item.setData(image, name, level, content);
        communityRecyclerAdapter.addItem(item);
        communityRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        main.onBackTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 2);
    }
}
