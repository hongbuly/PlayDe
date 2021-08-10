package com.example.play_de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class GameFragment extends Fragment implements OnBackPressedListener {
    private MainActivity main;
    private EditText edit;
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        main = (MainActivity) getActivity();

        edit = view.findViewById(R.id.edit);
        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            //chat nick 설정.
            Bundle result = new Bundle();
            result.putString("bundleKey", "result");

            getParentFragmentManager().setFragmentResult("requestKey", result);
        });
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
