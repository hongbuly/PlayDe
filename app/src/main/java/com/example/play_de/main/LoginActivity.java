package com.example.play_de.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.play_de.R;
import com.example.play_de.chat.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout login_layout, register_layout;

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseAuth firebaseAuth;

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

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

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
            if (mail_id.getText().toString() == null || password.getText().toString() == null || name.getText().toString() == null)
                Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            else if(password.getText().toString().length() < 6) {
                Toast.makeText(getApplicationContext(), "비밀번호 길이는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            } else{
                //name, mail_id, password 를 Firebase 로
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(mail_id.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            String uid = task.getResult().getUser().getUid();
                            Uri uri = Uri.parse("android.resource://com.example.play_de/drawable/cafe01");
                            FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(uri).addOnCompleteListener(task1 -> {
                                String imageUrl = task1.getResult().getUploadSessionUri().toString();

                                UserModel userModel = new UserModel();
                                userModel.name = name.getText().toString();
                                userModel.image = imageUrl;
                                userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                userModel.level = "보드게임러버";
                                userModel.pushToken = "보드게임 같이 하실분?";

                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);
                            });
                        });

                login_layout.setVisibility(View.VISIBLE);
                register_layout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginEvent() {
        if (id_edit.getText().toString() == null || password_edit.getText().toString() == null) {
            Toast.makeText(this, "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(id_edit.getText().toString(), password_edit.getText().toString())
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //로그인 실패
                        Toast.makeText(this, "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("name", id_edit.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
