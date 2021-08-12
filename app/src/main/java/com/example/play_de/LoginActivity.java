package com.example.play_de;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button mail, kakao, naver;
    private EditText id_edit, password_edit;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialSetUp();
        eventListener();
    }

    private void initialSetUp() {
        mail = findViewById(R.id.mail);
        kakao = findViewById(R.id.kakao);
        naver = findViewById(R.id.naver);
        id_edit = findViewById(R.id.id_edit);
        password_edit = findViewById(R.id.password_edit);
        login = findViewById(R.id.login);
    }

    private void eventListener() {
        mail.setOnClickListener(v -> {
            mail.setVisibility(View.GONE);
            kakao.setVisibility(View.GONE);
            naver.setVisibility(View.GONE);

            id_edit.setVisibility(View.VISIBLE);
            password_edit.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        });

        login.setOnClickListener(v -> {
            //main 띄우기
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
