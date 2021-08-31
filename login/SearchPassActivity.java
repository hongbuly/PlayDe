package com.example.play_de.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;

public class SearchPassActivity extends AppCompatActivity {
    private Button btn_searchpass_next;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpass);
        btn_searchpass_next = findViewById(R.id.btn_searchpass_next);
        btn_searchpass_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.play_de.main.login.SearchPassActivity.this , SearchPass2Activity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
    }
}
