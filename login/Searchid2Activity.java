package com.example.play_de.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;
import com.example.play_de.main.LoginActivity;

public class Searchid2Activity extends AppCompatActivity {
    private Button btn_searchid2_login;
    private Button btn_searchid2_searchpass;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchid2);
        btn_searchid2_login = findViewById(R.id.btn_searchid2_login);
        btn_searchid2_searchpass = findViewById(R.id.btn_searchid2_searchpass);
        btn_searchid2_searchpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.play_de.main.login.Searchid2Activity.this , SearchidpassActivity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
        btn_searchid2_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.play_de.main.login.Searchid2Activity.this , LoginActivity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
    }
}
