package com.example.play_de.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;

public class SearchidActivity extends AppCompatActivity {
    private Button btn_searchid_next;
    private ImageView iv_searchid_back;
    private CheckBox check_1;
    private CheckBox check_2;
    private LinearLayout linear_1;
    private LinearLayout linear_2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchid);
        btn_searchid_next = findViewById(R.id.btn_searchid_next);
        iv_searchid_back = findViewById(R.id.iv_searchid_back);
        btn_searchid_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.play_de.main.login.SearchidActivity.this , Searchid2Activity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
        check_1 = findViewById(R.id.checkbox_searchid_1);
        check_2 = findViewById(R.id.checkbox_searchid_2);
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
