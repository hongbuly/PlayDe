package com.example.play_de.main.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;
import com.example.play_de.main.LoginActivity;

public class SearchidpassActivity extends AppCompatActivity {
    private Button btn_searchidpass_next;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchidpass);
        btn_searchidpass_next = findViewById(R.id.btn_searchidpass_next);
        btn_searchidpass_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.play_de.main.login.SearchidpassActivity.this , LoginActivity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
    }
}