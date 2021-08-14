package com.example.play_de.community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;

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
