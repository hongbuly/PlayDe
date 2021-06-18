package com.example.play_de;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CommunityFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView community_listView;
    private CommunityListViewAdapter community_adapter;
    private EditText filterEdit;
    private Button filterBtn;

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        community_adapter = new CommunityListViewAdapter();
        community_listView = view.findViewById(R.id.listView);
        community_listView.setAdapter(community_adapter);
        addCommunityListView();

        filterEdit = view.findViewById(R.id.filterEdit);
        filterBtn = view.findViewById(R.id.filterBtn);

        //버튼 누르면, 밑으로 늘어나서 필터링을 결정하는 것을 구현할 것.
        return view;
    }

    void addCommunityListView() {
        //프로필 이미지 타입 결정하고 설정할 것.
        Drawable profile = null;
        String name = "이름";
        String dong = "OO동";
        String heart = "5개가 겹칩니다.";
        String place = "2개가 겹칩니다.";

        for (int i = 1; i < 8; i++) {
            community_adapter.addItem(profile, name, dong, heart, place);
        }
        community_adapter.notifyDataSetChanged();
    }
}
