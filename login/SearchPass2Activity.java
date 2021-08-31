package com.example.play_de.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;

public class SearchPass2Activity extends AppCompatActivity {
    private Button btn_searchpass2_next;
    private CheckBox check_1;
    private CheckBox check_2;
    private LinearLayout linear_1;
    private LinearLayout linear_2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpass2);
        btn_searchpass2_next = findViewById(R.id.btn_searchpass2_next);
        btn_searchpass2_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.play_de.main.login.SearchPass2Activity.this , com.example.play_de.main.login.SearchidpassActivity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
        check_1 = findViewById(R.id.checkbox_searchpass2_1);
        check_2 = findViewById(R.id.checkbox_searchpass2_2);
        linear_1 = findViewById(R.id.linear_check1);
        linear_2 = findViewById(R.id.linear_check2);
        linear_1.setVisibility(View.VISIBLE);
        check_1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_1.setVisibility(View.VISIBLE);
                linear_2.setVisibility(View.GONE);
                check_2.setChecked(false);
            }
        });
        check_2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_1.setVisibility(View.GONE);
                linear_2.setVisibility(View.VISIBLE);
                check_1.setChecked(false);
            }
        });
        check_1.setChecked(true);
        check_2.setChecked(false);
    }
}
