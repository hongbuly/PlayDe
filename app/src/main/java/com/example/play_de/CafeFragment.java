package com.example.play_de;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CafeFragment extends Fragment implements OnMapReadyCallback {
    public static TextView positionText;
    private ImageButton backBtn;

    public static LinearLayout reserve_view01;
    private GoogleMap map;
    private ImageButton map_callBtn;
    private String tel = "tel:01012341234";
    private Button map_seat;
    private Button map_noSeat;

    public static LinearLayout reserve_view02;
    private ViewPager vp;
    private ImageView tab01, tab02, tab03;
    private ArrayList<Integer> images;
    public static int checkedGame = 0;
    public static String gameList = "";
    private Button next_btn01;

    public static LinearLayout reserve_view03;
    private Button reserve_day_btn;
    private int year, month, day;
    private Button reserve_time_btn[];
    private boolean isChecked[];
    private int blackColor, whiteColor;
    private Button next_btn02;

    public static LinearLayout reserve_view04;
    private TextView reserve_day;
    private TextView reserve_time;
    private TextView reserve_people;
    private TextView reserve_name;
    private TextView reserve_number;
    private TextView reserve_game;
    private TextView reserve_bill;
    private Button pay_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafe, container, false);

        reserve_view01 = view.findViewById(R.id.reserve_view01);
        reserve_view02 = view.findViewById(R.id.reserve_view02);
        reserve_view03 = view.findViewById(R.id.reserve_view03);
        reserve_view04 = view.findViewById(R.id.reserve_view04);

        positionText = view.findViewById(R.id.positionText);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        map_seat = view.findViewById(R.id.map_seat);
        map_seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve_view01.setVisibility(View.GONE);
                reserve_view02.setVisibility(View.VISIBLE);
                positionText.setText("카페 이름");
            }
        });

        //자리가 없을 시 버튼 색깔을 바꿔주어야 함.
        map_noSeat = view.findViewById(R.id.map_noSeat);

        map_callBtn = view.findViewById(R.id.map_callBtn);
        map_callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });

        vp = view.findViewById(R.id.SelectGameVP);
        GameSelectVPAdapter adapter = new GameSelectVPAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);

        tab01 = view.findViewById(R.id.tab01);
        tab02 = view.findViewById(R.id.tab02);
        tab03 = view.findViewById(R.id.tab03);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tab01.setImageResource(R.drawable.tab_circle_orange);
                    tab02.setImageResource(R.drawable.tab_circle_grey);
                    tab03.setImageResource(R.drawable.tab_circle_grey);
                } else if (position == 1) {
                    tab01.setImageResource(R.drawable.tab_circle_grey);
                    tab02.setImageResource(R.drawable.tab_circle_orange);
                    tab03.setImageResource(R.drawable.tab_circle_grey);
                } else {
                    tab01.setImageResource(R.drawable.tab_circle_grey);
                    tab02.setImageResource(R.drawable.tab_circle_grey);
                    tab03.setImageResource(R.drawable.tab_circle_orange);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next_btn01 = view.findViewById(R.id.next_btn01);
        next_btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 게임 저장하기.
                reserve_view02.setVisibility(View.GONE);
                reserve_view03.setVisibility(View.VISIBLE);
            }
        });

        reserve_day_btn = view.findViewById(R.id.reserve_day_btn);
        reserve_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        reserve_time_btn = new Button[14];
        reserve_time_btn[0] = view.findViewById(R.id.reserve_9btn);
        reserve_time_btn[1] = view.findViewById(R.id.reserve_10btn);
        reserve_time_btn[2] = view.findViewById(R.id.reserve_11btn);
        reserve_time_btn[3] = view.findViewById(R.id.reserve_12btn);
        reserve_time_btn[4] = view.findViewById(R.id.reserve_13btn);
        reserve_time_btn[5] = view.findViewById(R.id.reserve_14btn);
        reserve_time_btn[6] = view.findViewById(R.id.reserve_15btn);
        reserve_time_btn[7] = view.findViewById(R.id.reserve_16btn);
        reserve_time_btn[8] = view.findViewById(R.id.reserve_17btn);
        reserve_time_btn[9] = view.findViewById(R.id.reserve_18btn);
        reserve_time_btn[10] = view.findViewById(R.id.reserve_19btn);
        reserve_time_btn[11] = view.findViewById(R.id.reserve_20btn);
        reserve_time_btn[12] = view.findViewById(R.id.reserve_21btn);
        reserve_time_btn[13] = view.findViewById(R.id.reserve_22btn);

        isChecked = new boolean[14];
        for (int i = 0; i < 14; i++) {
            isChecked[i] = false;
        }

        blackColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.Black);
        whiteColor = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.White);
        for (int i = 0; i < 14; i++) {
            final int finalI = i;
            reserve_time_btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isChecked[finalI]) {
                        reserve_time_btn[finalI].setBackgroundResource(R.drawable.circle_corner_grey);
                        reserve_time_btn[finalI].setTextColor(blackColor);
                        isChecked[finalI] = false;
                    } else {
                        reserve_time_btn[finalI].setBackgroundResource(R.drawable.circle_corner_gradient);
                        reserve_time_btn[finalI].setTextColor(whiteColor);
                        isChecked[finalI] = true;
                    }
                }
            });
        }

        //이전 화면에서 정보를 넘겨받아 text 바꾸기.
        reserve_day = view.findViewById(R.id.reserve_day);
        reserve_time = view.findViewById(R.id.reserve_time);
        reserve_people = view.findViewById(R.id.reserve_people);
        reserve_name = view.findViewById(R.id.reserve_name);
        reserve_number = view.findViewById(R.id.reserve_number);
        reserve_game = view.findViewById(R.id.reserve_game);
        pay_btn = view.findViewById(R.id.pay_btn);

        next_btn02 = view.findViewById(R.id.next_btn02);
        next_btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder time = new StringBuilder();
                int howLong = 0;
                for (int i = 9; i < 23; i++) {
                    if (isChecked[i - 9]) {
                        howLong++;
                        if (time.length() == 0)
                            time = new StringBuilder(Integer.toString(i));
                        else {
                            time.append(", ").append(i);
                        }
                    }
                }
                reserve_time.setText(time.toString());

                if (howLong == 0) {
                    Toast.makeText(getActivity(), "시간을 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    reserve_view03.setVisibility(View.GONE);
                    reserve_view04.setVisibility(View.VISIBLE);
                }

                gameList = "";
                GameSelectFirstFragment.setGameName();
                GameSelectSecondFragment.setGameName();
                GameSelectThirdFragment.setGameName();

                reserve_game.setText(gameList);
            }
        });

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제하기.
                positionText.setText("지도");
                Toast.makeText(getActivity(), "결제되었습니다.", Toast.LENGTH_SHORT).show();
                reserve_view01.setVisibility(View.VISIBLE);
                reserve_view04.setVisibility(View.GONE);
                initialization();
            }
        });

        setDate();

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng KookMin = new LatLng(37.611166, 126.995550);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(KookMin);
        markerOptions.title("국민대학교");
        markerOptions.snippet("사립대학교");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KookMin, 15));
    }

    private void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String text = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
                reserve_day_btn.setText(text);
                reserve_day.setText(text);
            }
        }, year, month - 1, day);
        datePickerDialog.show();
    }

    private void setDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat simpleMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
        year = Integer.parseInt(simpleYear.format(date));
        month = Integer.parseInt(simpleMonth.format(date));
        day = Integer.parseInt(simpleDate.format(date));

        String text = year + "년 " + month + "월 " + day + "일";
        reserve_day_btn.setText(text);
        reserve_day.setText(text);
    }

    static void goBack() {
        if (reserve_view02.getVisibility() == View.VISIBLE) {
            reserve_view02.setVisibility(View.GONE);
            reserve_view01.setVisibility(View.VISIBLE);
            positionText.setText("지도");
            initialization();
        } else if (reserve_view03.getVisibility() == View.VISIBLE) {
            reserve_view03.setVisibility(View.GONE);
            reserve_view02.setVisibility(View.VISIBLE);
        } else {
            reserve_view04.setVisibility(View.GONE);
            reserve_view03.setVisibility(View.VISIBLE);
        }
    }

    private static void initialization() {
        //초기화 해야할 것들.
        checkedGame = 0;
    }
}
