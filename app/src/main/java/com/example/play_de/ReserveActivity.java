package com.example.play_de;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReserveActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView positionText;
    private ImageButton backBtn;

    private LinearLayout reserve_view01;
    private GoogleMap map;
    private ImageButton map_callBtn;
    private String tel = "tel:01012341234";
    private Button map_seat;
    private Button map_noSeat;

    private LinearLayout reserve_view02;
    private ViewPager vp;
    private TabLayout tab;
    private ArrayList<Integer> images;
    private Button next_btn01;

    private LinearLayout reserve_view03;
    private Button reserve_day_btn;
    private int year, month, day;
    private Button reserve_time_btn[];
    private boolean isChecked[];
    private Button next_btn02;

    private LinearLayout reserve_view04;
    private TextView reserve_day;
    private TextView reserve_time;
    private TextView reserve_people;
    private TextView reserve_name;
    private TextView reserve_number;
    private TextView reserve_game;
    private TextView reserve_bill;
    private Button pay_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        reserve_view01 = findViewById(R.id.reserve_view01);
        reserve_view02 = findViewById(R.id.reserve_view02);
        reserve_view03 = findViewById(R.id.reserve_view03);
        reserve_view04 = findViewById(R.id.reserve_view04);

        positionText = findViewById(R.id.positionText);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //뒤로가기 버튼.
                goBack();
            }
        });

        map_seat = findViewById(R.id.map_seat);
        map_seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve_view01.setVisibility(View.GONE);
                reserve_view02.setVisibility(View.VISIBLE);
                positionText.setText("카페 이름");
            }
        });

        map_callBtn = findViewById(R.id.map_callBtn);
        map_callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });

        vp = findViewById(R.id.SelectGameVP);
        GameSelectVPAdapter adapter = new GameSelectVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        tab = findViewById(R.id.SelectGameTab);
        tab.setupWithViewPager(vp);

        images = new ArrayList<>();
        images.add(R.drawable.tab_circle_orange);
        images.add(R.drawable.tab_circle_grey);

        tab.getTabAt(0).setIcon(images.get(0));
        tab.getTabAt(1).setIcon(images.get(1));
        tab.getTabAt(2).setIcon(images.get(1));

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tab.getTabAt(0).setIcon(images.get(0));
                    tab.getTabAt(1).setIcon(images.get(1));
                    tab.getTabAt(2).setIcon(images.get(1));
                } else if (position == 1) {
                    tab.getTabAt(0).setIcon(images.get(1));
                    tab.getTabAt(1).setIcon(images.get(0));
                    tab.getTabAt(2).setIcon(images.get(1));
                } else {
                    tab.getTabAt(0).setIcon(images.get(1));
                    tab.getTabAt(1).setIcon(images.get(1));
                    tab.getTabAt(2).setIcon(images.get(0));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next_btn01 = findViewById(R.id.next_btn01);
        next_btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 게임 저장하기.
                reserve_view02.setVisibility(View.GONE);
                reserve_view03.setVisibility(View.VISIBLE);
            }
        });

        reserve_day_btn = findViewById(R.id.reserve_day_btn);
        reserve_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        reserve_time_btn = new Button[14];
        reserve_time_btn[0] = findViewById(R.id.reserve_9btn);
        reserve_time_btn[1] = findViewById(R.id.reserve_10btn);
        reserve_time_btn[2] = findViewById(R.id.reserve_11btn);
        reserve_time_btn[3] = findViewById(R.id.reserve_12btn);
        reserve_time_btn[4] = findViewById(R.id.reserve_13btn);
        reserve_time_btn[5] = findViewById(R.id.reserve_14btn);
        reserve_time_btn[6] = findViewById(R.id.reserve_15btn);
        reserve_time_btn[7] = findViewById(R.id.reserve_16btn);
        reserve_time_btn[8] = findViewById(R.id.reserve_17btn);
        reserve_time_btn[9] = findViewById(R.id.reserve_18btn);
        reserve_time_btn[10] = findViewById(R.id.reserve_19btn);
        reserve_time_btn[11] = findViewById(R.id.reserve_20btn);
        reserve_time_btn[12] = findViewById(R.id.reserve_21btn);
        reserve_time_btn[13] = findViewById(R.id.reserve_22btn);

        isChecked = new boolean[14];
        for (int i = 0; i < 14; i++) {
            isChecked[i] = false;
        }

        for (int i = 0; i < 14; i++) {
            final int finalI = i;
            reserve_time_btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isChecked[finalI]) {
                        reserve_time_btn[finalI].setBackgroundResource(R.drawable.circle_corner_grey);
                        isChecked[finalI] = false;
                    } else {
                        reserve_time_btn[finalI].setBackgroundResource(R.drawable.circle_corner_red);
                        isChecked[finalI] = true;
                    }
                }
            });
        }

        //이전 화면에서 정보를 넘겨받아 text 바꾸기.
        reserve_day = findViewById(R.id.reserve_day);
        reserve_time = findViewById(R.id.reserve_time);
        reserve_people = findViewById(R.id.reserve_people);
        reserve_name = findViewById(R.id.reserve_name);
        reserve_number = findViewById(R.id.reserve_number);
        reserve_game = findViewById(R.id.reserve_game);
        reserve_bill = findViewById(R.id.reserve_bill);
        pay_btn = findViewById(R.id.pay_btn);

        next_btn02 = findViewById(R.id.next_btn02);
        next_btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = new String();
                int howLong = 0;
                for (int i = 9; i < 23; i++) {
                    if (isChecked[i - 9]) {
                        howLong++;
                        if (time.isEmpty())
                            time = Integer.toString(i);
                        else {
                            time += ", " + i;
                        }
                    }
                }
                reserve_time.setText(time);

                if (howLong == 0) {
                    Toast.makeText(ReserveActivity.this, "시간을 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    reserve_view03.setVisibility(View.GONE);
                    reserve_view04.setVisibility(View.VISIBLE);
                }
            }
        });

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제하기.
                positionText.setText("지도");
                Toast.makeText(ReserveActivity.this, "결제되었습니다.", Toast.LENGTH_SHORT).show();
                reserve_view01.setVisibility(View.VISIBLE);
                reserve_view04.setVisibility(View.GONE);
                finish();
            }
        });

        setDate();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);
    }

    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                reserve_day_btn.setText(year + "년\t\t\t\t\t" + (month + 1) + "월\t\t\t\t\t" + dayOfMonth + "일");
                reserve_day.setText(year + "년\t\t\t\t\t" + (month + 1) + "월\t\t\t\t\t" + dayOfMonth + "일");
            }
        }, year, month - 1, day);
        datePickerDialog.show();
    }

    void setDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat simpleMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
        year = Integer.parseInt(simpleYear.format(date));
        month = Integer.parseInt(simpleMonth.format(date));
        day = Integer.parseInt(simpleDate.format(date));

        reserve_day_btn.setText(year + "년\t\t\t\t\t" + month + "월\t\t\t\t\t" + day + "일");
        reserve_day.setText(year + "년\t\t\t\t\t" + month + "월\t\t\t\t\t" + day + "일");
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack() {
        if (reserve_view01.getVisibility() == View.VISIBLE) {
            finish();
        } else if (reserve_view02.getVisibility() == View.VISIBLE) {
            reserve_view02.setVisibility(View.GONE);
            reserve_view01.setVisibility(View.VISIBLE);
            positionText.setText("지도");
        } else if (reserve_view03.getVisibility() == View.VISIBLE) {
            reserve_view03.setVisibility(View.GONE);
            reserve_view02.setVisibility(View.VISIBLE);
        } else {
            reserve_view04.setVisibility(View.GONE);
            reserve_view03.setVisibility(View.VISIBLE);
        }
    }
}
