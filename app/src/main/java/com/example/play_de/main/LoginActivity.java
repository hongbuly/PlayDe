package com.example.play_de.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout login_layout, register_layout;

    private Button mail, kakao, naver;
    private TextView register;
    private EditText id_edit, password_edit;
    private Button login;

    private EditText name, mail_id, password;
    private Button finish_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialSetUp();
        eventListener();
    }

    private void initialSetUp() {
        login_layout = findViewById(R.id.login_layout);
        register_layout = findViewById(R.id.register_layout);

        mail = findViewById(R.id.mail);
        kakao = findViewById(R.id.kakao);
        naver = findViewById(R.id.naver);
        register = findViewById(R.id.register);

        id_edit = findViewById(R.id.id_edit);
        password_edit = findViewById(R.id.password_edit);
        login = findViewById(R.id.login);

        name = findViewById(R.id.name);
        mail_id = findViewById(R.id.mail_id);
        password = findViewById(R.id.password);
        finish_register = findViewById(R.id.finish_register);
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
            loginEvent();
        });

        register.setOnClickListener(v -> {
            login_layout.setVisibility(View.GONE);
            register_layout.setVisibility(View.VISIBLE);
        });

        finish_register.setOnClickListener(v -> {
            login_layout.setVisibility(View.VISIBLE);
            register_layout.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
        });
    }

    private void loginEvent() {
        //로그인 버튼
    }
}
