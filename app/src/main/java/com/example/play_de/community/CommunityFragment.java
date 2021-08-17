package com.example.play_de.community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;
import com.hedgehog.ratingbar.RatingBar;

public class CommunityFragment extends Fragment implements OnBackPressedListener {
    private MainActivity main;
    private View view;
    private ImageButton back;
    private EditText filterEdit;

    private LinearLayout community_view01;
    private RecyclerView tag_recyclerView;
    private CommunityTagAdapter communityTagAdapter;
    private RecyclerView.LayoutManager tagLayoutManager;

    private RecyclerView community_recyclerView;
    private CommunityRecyclerAdapter communityRecyclerAdapter;
    private RecyclerView.LayoutManager communityLayoutManager;

    private RelativeLayout community_view02;
    private ImageView image;
    private TextView name, level, content, answer;

    private RecyclerView comment_recyclerView;
    private CommunityCommentAdapter comment_adapter;
    private RecyclerView.LayoutManager commentLayoutManager;

    private LinearLayout profile_view;
    private ImageView profile_image;
    private TextView profile_name, profile_level, send_message;
    private RatingBar heart_rating;
    private ImageButton blockBtn;

    private RecyclerView heart_recyclerView;
    private CommunityProfileFavoriteAdapter heart_adapter;
    private RecyclerView.LayoutManager heartLayoutManager;

    private RecyclerView store_recyclerView;
    private CommunityProfileFavoriteAdapter store_adapter;
    private RecyclerView.LayoutManager storeLayoutManager;


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
        community_view01 = view.findViewById(R.id.community_view01);
        community_view02 = view.findViewById(R.id.community_view02);
        profile_view = view.findViewById(R.id.profile_view);

        back = view.findViewById(R.id.backBtn);
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        filterEdit = view.findViewById(R.id.filterEdit);

        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        level = view.findViewById(R.id.level);
        content = view.findViewById(R.id.content);
        answer = view.findViewById(R.id.answer);

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
        communityRecyclerAdapter.setOnItemClickListener(position -> {
            community_view01.setVisibility(View.GONE);
            community_view02.setVisibility(View.VISIBLE);
            Glide.with(requireContext())
                    .load(communityRecyclerAdapter.getData(position).image)
                    .apply(new RequestOptions().circleCrop())
                    .into(image);
            name.setText(communityRecyclerAdapter.getData(position).name);
            level.setText(communityRecyclerAdapter.getData(position).level);
            content.setText(communityRecyclerAdapter.getData(position).pushToken);
        });

        profile_image = view.findViewById(R.id.profile_image);
        profile_name = view.findViewById(R.id.profile_name);
        profile_level = view.findViewById(R.id.profile_level);
        send_message = view.findViewById(R.id.send_message);
        send_message.setOnClickListener(v -> {
            //메시지 보내기.
        });

        blockBtn = view.findViewById(R.id.blockBtn);
        blockBtn.setOnClickListener(v -> {
            //차단하기.
        });

        heart_rating = view.findViewById(R.id.heart_rating);
        //User Model 에 heart 갯수 추가하기.
        heart_rating.setStar(3);

        comment_adapter = new CommunityCommentAdapter();
        comment_recyclerView = view.findViewById(R.id.comment_recycler);
        commentLayoutManager = new LinearLayoutManager(getActivity());
        comment_recyclerView.setLayoutManager(commentLayoutManager);
        comment_recyclerView.setAdapter(comment_adapter);
        addCommentRecyclerView();

        comment_adapter.setOnItemClickListener(position -> {
            community_view02.setVisibility(View.GONE);
            profile_view.setVisibility(View.VISIBLE);
            profile_image.setImageResource(comment_adapter.getData(position).image);
            profile_name.setText(comment_adapter.getData(position).name);
            profile_level.setText(comment_adapter.getData(position).level);
        });

        heart_adapter = new CommunityProfileFavoriteAdapter();
        heart_recyclerView = view.findViewById(R.id.heart_recycler);
        heartLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        heart_recyclerView.setLayoutManager(heartLayoutManager);
        heart_recyclerView.setAdapter(heart_adapter);
        addHeartRecyclerView();

        store_adapter = new CommunityProfileFavoriteAdapter();
        store_recyclerView = view.findViewById(R.id.store_recycler);
        storeLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        store_recyclerView.setLayoutManager(storeLayoutManager);
        store_recyclerView.setAdapter(store_adapter);
        addStoreRecyclerView();
    }

    private void addCommentRecyclerView() {
        CommunityComment item = new CommunityComment();
        item.image = R.drawable.cafe01;
        item.name = "박혜원";
        item.level = "보드게임러버";
        item.comment = "저 주말에 시간되는데 같이하실래요??";
        comment_adapter.addItem(item);
        comment_adapter.notifyDataSetChanged();
    }

    private void addHeartRecyclerView() {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.image = R.drawable.rumicube;
        item.name = "루미큐브";
        heart_adapter.addItem(item);

        item.image = R.drawable.cluedo;
        item.name = "클루";
        heart_adapter.addItem(item);

        item.image = R.drawable.ticket_to_ride;
        item.name = "티켓투라이드";
        heart_adapter.addItem(item);

        item.image = R.drawable.uno;
        item.name = "우노";
        heart_adapter.addItem(item);

        item.image = R.drawable.diamond;
        item.name = "다이아몬드";
        heart_adapter.addItem(item);

        heart_adapter.notifyDataSetChanged();
    }

    private void addStoreRecyclerView() {
        CommunityProfileFavorite item = new CommunityProfileFavorite();
        item.image = R.drawable.rumicube;
        item.name = "루미큐브";
        store_adapter.addItem(item);

        item.image = R.drawable.cluedo;
        item.name = "클루";
        store_adapter.addItem(item);

        item.image = R.drawable.ticket_to_ride;
        item.name = "티켓투라이드";
        store_adapter.addItem(item);

        item.image = R.drawable.uno;
        item.name = "우노";
        store_adapter.addItem(item);

        item.image = R.drawable.diamond;
        item.name = "다이아몬드";
        store_adapter.addItem(item);

        store_adapter.notifyDataSetChanged();
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
        if (profile_view.getVisibility() == View.VISIBLE) {
            profile_view.setVisibility(View.GONE);
            community_view02.setVisibility(View.VISIBLE);
        } else if (community_view02.getVisibility() == View.VISIBLE) {
            community_view02.setVisibility(View.GONE);
            community_view01.setVisibility(View.VISIBLE);
        } else {
            main.onBackTime();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 2);
    }
}
