package com.example.play_de.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.play_de.R;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;

public class GameFragment extends Fragment implements OnBackPressedListener {
    private MainActivity main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        main = (MainActivity) getActivity();
        return view;
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
