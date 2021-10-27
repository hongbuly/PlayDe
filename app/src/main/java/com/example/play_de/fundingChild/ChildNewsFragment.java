package com.example.play_de.fundingChild;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;
import com.example.play_de.community.CommunityItem;
import com.example.play_de.community.CommunityRecyclerAdapter;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;

public class ChildNewsFragment extends Fragment {
    private View view;
    private Context context;

    private static LinearLayout news01;
    private Button last, past;

    private boolean isLast;
    private int whiteColor, greyColor;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private RecyclerView news_recyclerView;

    private static ScrollView news02;
    private TextView explain;

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
        news01 = view.findViewById(R.id.news01);
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

        news02 = view.findViewById(R.id.news02);
        explain = view.findViewById(R.id.explain);
        explain.setText("<게임 설명>\n플레이어 2~4인/재료카드를 모아 6명의 캐릭터들을 먼저 탈출시키자! 모든 플레이어는 재료 카드 10장과 캐릭터카드 3장을 받고 시작, 순서대로 카드 더미에서 한 장씩 뽑고 한 장씩 버린다. 각 캐릭터에 맞는 재료카드를 4장씩 모아서 캐릭터를 탈출시키면 캐릭터 피규어를 얻는다.");
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
            news01.setVisibility(View.GONE);
            news02.setVisibility(View.VISIBLE);
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

    public static boolean backView() {
        if (news02.getVisibility() == View.VISIBLE) {
            news01.setVisibility(View.VISIBLE);
            news02.setVisibility(View.GONE);
            return true;
        }

        return false;
    }
}
