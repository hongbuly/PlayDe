package com.example.play_de.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout login_layout, register_layout;

    private Button mail, kakao, naver;
    private EditText id_edit, password_edit;
    private Button login;
    private TextView register;

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
        id_edit = findViewById(R.id.id_edit);
        password_edit = findViewById(R.id.password_edit);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

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
            //main 띄우기
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("name", id_edit.getText().toString());
            startActivity(intent);
            finish();
        });

        register.setOnClickListener(v -> {
            login_layout.setVisibility(View.GONE);
            register_layout.setVisibility(View.VISIBLE);
        });

        finish_register.setOnClickListener(v -> {
            if (mail_id.getText().toString() == null || password.getText().toString() == null || name.getText().toString() == null)
                return;

            //name, mail_id, password 를 Firebase 로
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(mail_id.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        String uid = task.getResult().getUser().getUid();
                    });

            login_layout.setVisibility(View.VISIBLE);
            register_layout.setVisibility(View.GONE);
        });
    }
}
