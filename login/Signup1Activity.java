package com.example.play_de.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;

public class Signup1Activity extends AppCompatActivity {
    private Button btn_signup_1_next;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_1);
        btn_signup_1_next = findViewById(R.id.btn_signup_1_next);
        btn_signup_1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.play_de.main.login.Signup1Activity.this , com.example.play_de.main.login.Signup2Activity.class);
                startActivity(intent); // 액티비티 이동
            }
        });
    }
}
