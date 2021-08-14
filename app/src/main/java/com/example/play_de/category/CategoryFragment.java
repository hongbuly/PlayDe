package com.example.play_de.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.play_de.R;

public class CategoryFragment extends Fragment {
    private ListView category_listView;
    private CategoryListViewAdapter category_adapter;
    private TextView countText;
    private EditText filterEdit;
    private Button popularBtn;
    private Button themeBtn;
    private Button levelBtn;
    private Button peopleBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        category_adapter = new CategoryListViewAdapter();
        category_listView = view.findViewById(R.id.listView);
        category_listView.setAdapter(category_adapter);
        addCategoryListView();

        countText = view.findViewById(R.id.countText);
        filterEdit = view.findViewById(R.id.filterEdit);
        popularBtn = view.findViewById(R.id.popularBtn);
        themeBtn = view.findViewById(R.id.themeBtn);
        levelBtn = view.findViewById(R.id.levelBtn);
        peopleBtn = view.findViewById(R.id.peopleBtn);

        //여기서 검색, 버튼 기능들을 구현할 것.

        return view;
    }

    void addCategoryListView() {
        //서버로부터 정보를 받아서 리스트 추가할 것.
        int image = R.drawable.monopoly;
        String name = "모노폴리";
        String theme = "협상";
        String people = "3~4인";
        String level = "중하";

        category_adapter.addItem(image, name, theme, people, level);

        image = R.drawable.round_corner_white;
        name = "게임 이름";
        theme = "게임 테마";
        people = "인원수";
        level = "난이도";

        for (int i = 0; i < 10; i++) {
            category_adapter.addItem(image, name, theme, people, level);
        }
        category_adapter.notifyDataSetChanged();
    }
}
