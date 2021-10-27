package com.example.play_de.fundingParent;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play_de.fundingChild.ChildNewsFragment;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.example.play_de.R;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.profile.ProfileActivity;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FundingFragment extends Fragment implements OnBackPressedListener {
    private Context context;
    private MainActivity main;
    private View view;
    private ImageButton backBtn, userBtn;
    private LinearLayout funding_view01;
    private LinearLayout funding_view02;

    private TextView plusBtn;
    private RelativeLayout[] game;
    private ImageView[] image;
    private TextView[] name;
    private TextView[] percent;
    private TextView[] tag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_funding, container, false);
        initialSetup();
        eventListener();
        return view;
    }

    private void initialSetup() {
        main = (MainActivity) getActivity();
        backBtn = view.findViewById(R.id.backBtn);

        userBtn = view.findViewById(R.id.userBtn);

        funding_view01 = view.findViewById(R.id.funding_view01);
        funding_view02 = view.findViewById(R.id.funding_view02);

        ViewPager vp02 = view.findViewById(R.id.funding_vp02);
        FundingVPAdapter adapter02 = new FundingVPAdapter(getChildFragmentManager());
        vp02.setAdapter(adapter02);

        //탭과 뷰 연동
        TabLayout tab02 = view.findViewById(R.id.funding_tab02);
        tab02.setupWithViewPager(vp02);

        plusBtn = view.findViewById(R.id.plusBtn);

        game = new RelativeLayout[4];
        image = new ImageView[4];
        name = new TextView[4];
        percent = new TextView[4];
        tag = new TextView[4];

        game[0] = view.findViewById(R.id.game01);
        game[1] = view.findViewById(R.id.game02);
        game[2] = view.findViewById(R.id.game03);
        game[3] = view.findViewById(R.id.game04);

        image[0] = view.findViewById(R.id.image01);
        image[1] = view.findViewById(R.id.image02);
        image[2] = view.findViewById(R.id.image03);
        image[3] = view.findViewById(R.id.image04);

        name[0] = view.findViewById(R.id.name01);
        name[1] = view.findViewById(R.id.name02);
        name[2] = view.findViewById(R.id.name03);
        name[3] = view.findViewById(R.id.name04);

        percent[3] = view.findViewById(R.id.percent01);
        percent[3] = view.findViewById(R.id.percent02);
        percent[3] = view.findViewById(R.id.percent03);
        percent[3] = view.findViewById(R.id.percent04);

        tag[3] = view.findViewById(R.id.tag01);
        tag[3] = view.findViewById(R.id.tag02);
        tag[3] = view.findViewById(R.id.tag03);
        tag[3] = view.findViewById(R.id.tag04);
    }

    void eventListener() {
        backBtn.setOnClickListener(v -> onChangeFunding(false));

        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileActivity.class);
            startActivity(intent);
        });

        plusBtn.setOnClickListener(v -> {
            //더보기 버튼
        });
        game[0].setOnClickListener(v -> {
            //펀딩 게임 상세 화면으로 전환.
            onChangeFunding(true);
        });
    }

    public void onChangeFunding(boolean detail) {
        if (detail) {
            funding_view01.setVisibility(View.GONE);
            funding_view02.setVisibility(View.VISIBLE);
        } else {
            funding_view01.setVisibility(View.VISIBLE);
            funding_view02.setVisibility(View.GONE);
        }
    }

    public boolean getVisibility() {
        if (funding_view01.getVisibility() == View.VISIBLE)
            return true;
        else
            return false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 3);
    }

    @Override
    public void onBackPressed() {
        if (ChildNewsFragment.backView()) {
            //no backView
        } else if (getVisibility())
            main.onBackTime();
        else
            onChangeFunding(false);
    }
}
