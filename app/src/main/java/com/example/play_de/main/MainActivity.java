package com.example.play_de.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play_de.R;
import com.example.play_de.chat.TokenModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private StringBuilder urlStr;
    static ViewPager vp;
    public TabLayout mTab;
    private OnBackPressedListener[] listener = new OnBackPressedListener[5];
    private OnClickReportListener reportListener;
    private OnClickRemoveListener removeListener;
    private OnClickUrlListener urlListener;
    private long backKeyPressedTime = 0;
    public static String userId;
    public static String name;
    public static String profile;
    public static int score;
    public static String mainUrl = "https://playde-server-pzovl.run.goorm.io/";

    private View blur;
    private LinearLayout finish_reserve;
    private TextView reportBtn, urlBtn, removeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        sendToken(token);
                    }
                });

        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");

        vp = findViewById(R.id.MainVP);
        MainVPAdapter adapter = new MainVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        //탭과 뷰 연동
        mTab = findViewById(R.id.MainTab);
        mTab.setupWithViewPager(vp);

        //탭 이미지 추가
        int[] images = {R.drawable.game, R.drawable.cafe, R.drawable.community, R.drawable.funding, R.drawable.chat};

        for (int i = 0; i < 5; i++)
            Objects.requireNonNull(mTab.getTabAt(i)).setIcon(images[i]);

        blur = findViewById(R.id.blur);
        blur.setOnClickListener(v -> {
            showBlur_report(false);
            showBlur_remove(false);
            showBlur_url(false);
        });

        finish_reserve = findViewById(R.id.finish_reserve);
        reportBtn = findViewById(R.id.reportBtn);
        reportBtn.setOnClickListener(v -> {
            //커뮤니티 글 신고
            if (reportBtn.getVisibility() == View.VISIBLE) {
                showBlur_report(false);
                showBlur_url(false);
                showBlur_remove(false);
                reportListener.onClickReport();
            }
        });

        urlBtn = findViewById(R.id.urlBtn);
        urlBtn.setOnClickListener(v -> {
            //커뮤니티 글 URL
            if (urlBtn.getVisibility() == View.VISIBLE) {
                showBlur_report(false);
                showBlur_url(false);
                showBlur_remove(false);
                urlListener.onClickUrl();
            }
        });

        removeBtn = findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(v -> {
            //커뮤니티 글 삭제
            if (removeBtn.getVisibility() == View.VISIBLE) {
                showBlur_report(false);
                showBlur_url(false);
                showBlur_remove(false);
                removeListener.onClickRemove();
            }
        });

        setName();
    }

    private void sendToken(String token) {
        //token 저장하기
        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("user/push_token/set");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean act = jsonObject.getBoolean("act");
                        if (act) {
                            Log.e("Token", "성공");
                        } else
                            Log.e("Token", "실패");
                    } catch (Exception e) {
                        Log.e("Token", "예외 발생");
                    }
                },
                error -> {
                    Log.e("Token", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                body.put("token", token);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("userTokens")
                .orderByChild("uid")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isSame = false;
                        for (DataSnapshot item : snapshot.getChildren()) {
                            TokenModel tokenModel = item.getValue(TokenModel.class);
                            if (tokenModel.uid.equals(userId)) {
                                isSame = true;
                                break;
                            }
                        }

                        if (!isSame) {
                            TokenModel firebaseToken = new TokenModel();
                            firebaseToken.pushToken = token;
                            firebaseToken.uid = userId;
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference()
                                    .child("userTokens")
                                    .push()
                                    .setValue(firebaseToken)
                                    .addOnSuccessListener(unused -> Log.e("FirebaseToken", "Access"));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void setOnBackPressedListener(OnBackPressedListener listener, int num) {
        this.listener[num] = listener;
    }

    public void setOnClickReportListener(OnClickReportListener listener) {
        this.reportListener = listener;
    }

    public void setOnClickRemoveListener(OnClickRemoveListener listener) {
        this.removeListener = listener;
    }

    public void setOnClickUrlListener(OnClickUrlListener listener) {
        this.urlListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (listener != null)
            listener[vp.getCurrentItem()].onBackPressed();
        else
            onBackTime();
    }

    public void onBackTime() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
        }
    }

    public static void setCurrentItem(int num) {
        vp.setCurrentItem(num);
    }

    private void setName() {
        //사용자 프로필 가져오기
        urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/user/profile");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                this::nameJSONParse,
                error -> {
                    Toast.makeText(this, "서버와의 연결에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("setName", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", MainActivity.userId);
                return body;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(request);
    }

    private void nameJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            name = jsonObject.getString("nickname");
            profile = jsonObject.getString("profile");
            score = jsonObject.getInt("score");
        } catch (Exception e) {
            Log.e("commentJSONParse", "예외 발생");
        }
    }

    public void showBlur(boolean show) {
        //카페 결제
        if (show) {
            blur.setVisibility(View.VISIBLE);
            finish_reserve.setVisibility(View.VISIBLE);
        } else {
            blur.setVisibility(View.GONE);
            finish_reserve.setVisibility(View.GONE);
        }
    }

    public void showBlur_report(boolean show) {
        //커뮤니티 신고하기 버튼
        if (show) {
            Animation down_up = AnimationUtils.loadAnimation(this, R.anim.down_up);
            blur.setVisibility(View.VISIBLE);
            reportBtn.setVisibility(View.VISIBLE);
            reportBtn.startAnimation(down_up);
        } else {
            Animation up_down = AnimationUtils.loadAnimation(this, R.anim.up_down);
            reportBtn.startAnimation(up_down);
            up_down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    blur.setVisibility(View.GONE);
                    reportBtn.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public void showBlur_remove(boolean show) {
        //커뮤니티 삭제하기 버튼
        if (show) {
            Animation down_up = AnimationUtils.loadAnimation(this, R.anim.down_up300);
            removeBtn.setVisibility(View.VISIBLE);
            removeBtn.startAnimation(down_up);
        } else {
            Animation up_down = AnimationUtils.loadAnimation(this, R.anim.up_down300);
            //애니메이션의 fillAfter 를 사용하면, gone 이 적용되지 않음.
            removeBtn.startAnimation(up_down);
            up_down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    removeBtn.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public void showBlur_url(boolean show) {
        //커뮤니티 Url 버튼
        if (show) {
            Animation down_up = AnimationUtils.loadAnimation(this, R.anim.down_up200);
            urlBtn.setVisibility(View.VISIBLE);
            urlBtn.startAnimation(down_up);
        } else {
            Animation up_down = AnimationUtils.loadAnimation(this, R.anim.up_down200);
            //애니메이션의 fillAfter 를 사용하면, gone 이 적용되지 않음.
            urlBtn.startAnimation(up_down);
            up_down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    urlBtn.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}
