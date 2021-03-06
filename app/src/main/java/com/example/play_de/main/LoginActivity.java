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
        blur.setVisibility(View.VISIBLE);

        urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("user/join");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                this::registerJSONParse,
                error -> {
                    Toast.makeText(getApplicationContext(), "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("Register", urlStr.toString());
                    blur.setVisibility(View.GONE);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("platform", "0");
                body.put("email", mail_id.getText().toString());
                body.put("password", password.getText().toString());
                body.put("name", name.getText().toString());
                body.put("nickname", name.getText().toString());
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    private void loginEvent() {
        //????????? ??????
        blur.setVisibility(View.VISIBLE);
        urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("user/login");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                this::loginJSONParse,
                error -> {
                    Toast.makeText(getApplicationContext(), "???????????? ???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    Log.e("Login", urlStr.toString());
                    blur.setVisibility(View.GONE);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("platform", "0");
                body.put("email", id_edit.getText().toString());
                body.put("password", password_edit.getText().toString());
                Log.e("Login", body.toString());
                return body;
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
                Toast.makeText(getApplicationContext(), "???????????????????????????.", Toast.LENGTH_SHORT).show();
            } else {
                if (jsonObject.getString("message").equals("ALREADY USER")) {
                    Toast.makeText(getApplicationContext(), "?????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                } else if (jsonObject.getString("message").equals("ALREADY NICKNAME")) {
                    Toast.makeText(getApplicationContext(), "?????? ???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("registerJSONParse", "?????? ??????");
        }

        blur.setVisibility(View.GONE);
    }

    private void loginJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean("access")) {
                userId = Integer.toString(jsonObject.getInt("id"));
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "????????? ?????? ??????????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
            blur.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e("loginJSONParse", response);
            blur.setVisibility(View.GONE);
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
