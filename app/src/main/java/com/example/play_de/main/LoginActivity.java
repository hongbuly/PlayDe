package com.example.play_de.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play_de.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private StringBuilder urlStr;
    private String userId;

    private LinearLayout login_layout, register_layout;

    private Button mail, kakao, naver;
    private TextView register;
    private EditText id_edit, password_edit;
    private Button login;

    private EditText name, mail_id, password;
    private Button finish_register;

    private TextView blur;

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

        blur = findViewById(R.id.blur);
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
            registerEvent();
        });
    }

    private void registerEvent() {
        urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/user/join?platform=0&email=");
        urlStr.append(mail_id.getText().toString());
        urlStr.append("&password=");
        urlStr.append(password.getText().toString());
        urlStr.append("&name=");
        urlStr.append(name.getText().toString());
        urlStr.append("&nickname=");
        urlStr.append(name.getText().toString());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                this::registerJSONParse,
                error -> {
                    Toast.makeText(getApplicationContext(), "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("Register", "Error");
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    private void loginEvent() {
        //로그인 버튼
        urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/user/login?platform=0&email=");
        urlStr.append(id_edit.getText().toString());
        urlStr.append("&password=");
        urlStr.append(password_edit.getText().toString());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                this::loginJSONParse,
                error -> {
                    Toast.makeText(getApplicationContext(), "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("Login", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    private void registerJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean("access")) {
                login_layout.setVisibility(View.VISIBLE);
                register_layout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                if (jsonObject.getString("message").equals("ALREADY USER")) {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                } else if (jsonObject.getString("message").equals("ALREADY NICKNAME")) {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("registerJSONParse", "예외 발생");
        }
    }

    private void loginJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean("access")) {
                userId = Integer.toString(jsonObject.getInt("id"));
                blur.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("loginJSONParse", "예외 발생");
        }
    }

    @Override
    public void onBackPressed() {
        if (register_layout.getVisibility() == View.VISIBLE) {
            register_layout.setVisibility(View.GONE);
            login_layout.setVisibility(View.VISIBLE);
        } else if (login.getVisibility() == View.VISIBLE) {
            mail.setVisibility(View.VISIBLE);
            kakao.setVisibility(View.VISIBLE);
            naver.setVisibility(View.VISIBLE);

            id_edit.setVisibility(View.GONE);
            password_edit.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
        } else
            finish();
    }
}
