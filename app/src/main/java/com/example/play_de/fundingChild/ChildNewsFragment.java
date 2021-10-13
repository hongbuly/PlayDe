package com.example.play_de.fundingChild;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;
import com.example.play_de.community.CommunityItem;
import com.example.play_de.community.CommunityRecyclerAdapter;

public class ChildNewsFragment extends Fragment {
    private View view;
    private Context context;
    private Button last, past;

    private boolean isLast;
    private int whiteColor, greyColor;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private RecyclerView news_recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //홈화면에 대한 자바 코드 작성을 여기에.
        view = inflater.inflate(R.layout.child_fragment_news, container, false);
        initialization();
        eventListener();
        return view;
    }

    private void initialization() {
        last = view.findViewById(R.id.last);
        past = view.findViewById(R.id.past);

        newsRecyclerAdapter = new NewsRecyclerAdapter();
        news_recyclerView = view.findViewById(R.id.news_recycler);
        RecyclerView.LayoutManager newsLayoutManager = new LinearLayoutManager(getActivity());
        news_recyclerView.setLayoutManager(newsLayoutManager);
        news_recyclerView.setAdapter(newsRecyclerAdapter);

        isLast = true;
        whiteColor = ContextCompat.getColor(context, R.color.White);
        greyColor = ContextCompat.getColor(context, R.color.LineGrey);

        newsRecyclerAdapter.initialSetUp();
        addNewsRecyclerView("글리치 보드게임 캐릭터 공개!", "GLITCH", "어제");
        addNewsRecyclerView("글리치 보드게임 이벤트!", "GLITCH", "2021.09.06");
        addNewsRecyclerView("글리치 보드게임 펀딩 시작!", "GLITCH", "2021.09.06");
    }

    private void eventListener() {
        last.setOnClickListener(v -> {
            //최신순
            last.setBackgroundResource(R.drawable.round_red20);
            last.setTextColor(whiteColor);
            past.setBackgroundResource(R.drawable.round_corner_line20);
            past.setTextColor(greyColor);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);

            news_recyclerView.setLayoutManager(mLayoutManager);
        });

        past.setOnClickListener(v -> {
            //과거순
            last.setBackgroundResource(R.drawable.round_corner_line20);
            last.setTextColor(greyColor);
            past.setBackgroundResource(R.drawable.round_red20);
            past.setTextColor(whiteColor);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);

            news_recyclerView.setLayoutManager(mLayoutManager);
        });

        newsRecyclerAdapter.setOnItemClickListener(position -> {
            //소식 화면으로 전환.

        });
    }

    private void addNewsRecyclerView(String title, String name, String time) {
        NewsRecyclerAdapter.newsItem item = new NewsRecyclerAdapter.newsItem();
        item.title = title;
        item.name = name;
        item.time = time;
        newsRecyclerAdapter.addItem(item);
        newsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
