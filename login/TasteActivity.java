package com.example.play_de.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;
import com.example.play_de.main.MainActivity;


public class TasteActivity extends AppCompatActivity {
    private Button btn_taste_next;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste);
        btn_taste_next = findViewById(R.id.btn_taste_next);
        btn_taste_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TasteActivity.this , MainActivity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
    }
}
