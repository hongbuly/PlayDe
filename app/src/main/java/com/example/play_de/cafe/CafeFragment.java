package com.example.play_de.cafe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.example.play_de.main.OnBackPressedListener;
import com.example.play_de.R;
import com.example.play_de.profile.ProfileActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CafeFragment extends Fragment implements OnMapReadyCallback, OnBackPressedListener {
    private Context context;
    private MainActivity main;
    private View view;
    private TextView positionText;
    private ImageButton backBtn, userBtn;

    private LinearLayout search_cafe;
    private int setBtn = 0;
    private Button popularBtn, distanceBtn, priceBtn, registerBtn;
    private RecyclerView cafe_recyclerView;
    private CafeRecyclerAdapter cafe_adapter;
    private RecyclerView.LayoutManager layoutManager;

    private LinearLayout reserve_view01;
    private GoogleMap map;
    private ImageButton map_callBtn;
    private String tel = "tel:01012341234";
    private Button map_seat;
    private Button map_noSeat;

    private GpsTracker gpsTracker;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private double latitude, longitude;

    private LinearLayout reserve_view02;
    private ViewPager vp;
    private ImageView tab01, tab02, tab03;
    private ArrayList<Integer> images;
    static boolean[] checkedGame = new boolean[12];
    static int countChecked = 0, firstChecked = 0;
    static String gameList = "";
    private Button next_btn01;

    private LinearLayout reserve_view03;
    private Button reserve_day_btn;
    private TextView reserve_people_txt;
    private int people = 4;
    private ImageButton minus, plus;
    private int year, month, day;
    private Button[] reserve_time_btn;
    private static boolean[] isChecked = new boolean[14];
    private int blackColor, whiteColor, greyColor;
    private Button next_btn02;

    private LinearLayout reserve_view04;
    private TextView reserve_day, reserve_time, reserve_people, reserve_name, reserve_number, reserve_game, pay_bill;
    private Button pay_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cafe, container, false);
        initialSetup();
        eventListener();
        setDate();
        checkRunTimePermission();
        return view;
    }

    private void initialSetup() {
        main = (MainActivity) getActivity();

        search_cafe = view.findViewById(R.id.search_cafe);
        reserve_view01 = view.findViewById(R.id.reserve_view01);
        reserve_view02 = view.findViewById(R.id.reserve_view02);
        reserve_view03 = view.findViewById(R.id.reserve_view03);
        reserve_view04 = view.findViewById(R.id.reserve_view04);

        positionText = view.findViewById(R.id.positionText);
        backBtn = view.findViewById(R.id.backBtn);
        userBtn = view.findViewById(R.id.userBtn);

        popularBtn = view.findViewById(R.id.popularBtn);
        distanceBtn = view.findViewById(R.id.distanceBtn);
        priceBtn = view.findViewById(R.id.priceBtn);
        registerBtn = view.findViewById(R.id.registerBtn);

        cafe_adapter = new CafeRecyclerAdapter();
        cafe_recyclerView = view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        cafe_recyclerView.setLayoutManager(layoutManager);
        cafe_recyclerView.setAdapter(cafe_adapter);

        //자리가 없을 시 버튼 색깔을 바꿔주어야 함.
        map_seat = view.findViewById(R.id.map_seat);
        map_noSeat = view.findViewById(R.id.map_noSeat);
        map_callBtn = view.findViewById(R.id.map_callBtn);

        vp = view.findViewById(R.id.SelectGameVP);
        GameSelectVPAdapter adapter = new GameSelectVPAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        tab01 = view.findViewById(R.id.tab01);
        tab02 = view.findViewById(R.id.tab02);
        tab03 = view.findViewById(R.id.tab03);
        next_btn01 = view.findViewById(R.id.next_btn01);

        reserve_day_btn = view.findViewById(R.id.reserve_day_btn);
        reserve_people_txt = view.findViewById(R.id.reserve_people_txt);
        minus = view.findViewById(R.id.minus);
        plus = view.findViewById(R.id.plus);
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
        blackColor = ContextCompat.getColor(context, R.color.Black);
        whiteColor = ContextCompat.getColor(context, R.color.White);
        greyColor = ContextCompat.getColor(context, R.color.LineGrey);
        next_btn02 = view.findViewById(R.id.next_btn02);

        //이전 화면에서 정보를 넘겨받아 text 바꾸기.
        reserve_day = view.findViewById(R.id.reserve_day);
        reserve_time = view.findViewById(R.id.reserve_time);
        reserve_people = view.findViewById(R.id.reserve_people);
        reserve_name = view.findViewById(R.id.reserve_name);
        reserve_number = view.findViewById(R.id.reserve_number);
        reserve_game = view.findViewById(R.id.reserve_game);
        pay_bill = view.findViewById(R.id.pay_bill);
        pay_btn = view.findViewById(R.id.pay_btn);
    }

    private void eventListener() {
        backBtn.setOnClickListener(v -> goBack());
        userBtn = view.findViewById(R.id.userBtn);
        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileActivity.class);
            startActivity(intent);
        });

        popularBtn.setOnClickListener(v -> {
            changeBtn(0);
        });

        distanceBtn.setOnClickListener(v -> {
            changeBtn(1);
        });

        priceBtn.setOnClickListener(v -> {
            changeBtn(2);
        });

        registerBtn.setOnClickListener(v -> {
            changeBtn(3);
        });

        cafe_adapter.setOnItemClickListener((view, position) -> {
            search_cafe.setVisibility(View.GONE);
            reserve_view01.setVisibility(View.VISIBLE);
            positionText.setText("지도");
        });

        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        map_seat.setOnClickListener(v -> {
            reserve_view01.setVisibility(View.GONE);
            reserve_view02.setVisibility(View.VISIBLE);
            positionText.setText("카페 이름");
        });

        map_callBtn.setOnClickListener(v -> startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel))));

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

        next_btn01.setOnClickListener(v -> {
            //선택된 게임 저장하기.
            reserve_view02.setVisibility(View.GONE);
            reserve_view03.setVisibility(View.VISIBLE);
        });

        reserve_day_btn.setOnClickListener(v -> showDate());
        minus.setOnClickListener(v -> {
            if (people > 1) {
                reserve_people_txt.setText(Integer.toString(--people));
            }
        });

        plus.setOnClickListener(v -> {
            if (people < 8) {
                reserve_people_txt.setText(Integer.toString(++people));
            }
        });

        for (int i = 0; i < 14; i++) {
            final int finalI = i;
            reserve_time_btn[i].setOnClickListener(v -> {
                if (isChecked[finalI]) {
                    reserve_time_btn[finalI].setBackgroundResource(R.drawable.round_corner_whitegrey30);
                    reserve_time_btn[finalI].setTextColor(blackColor);
                    isChecked[finalI] = false;
                } else {
                    reserve_time_btn[finalI].setBackgroundResource(R.drawable.round_gradient30);
                    reserve_time_btn[finalI].setTextColor(whiteColor);
                    isChecked[finalI] = true;
                }
            });
        }

        next_btn02.setOnClickListener(v -> {
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
            reserve_people.setText(reserve_people_txt.getText().toString());

            if (howLong == 0) {
                Toast.makeText(getActivity(), "시간을 선택해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                reserve_view03.setVisibility(View.GONE);
                reserve_view04.setVisibility(View.VISIBLE);
                gameList = "";
                GameSelectFirstFragment.setGameName();
                GameSelectSecondFragment.setGameName();
                GameSelectThirdFragment.setGameName();

                reserve_game.setText(gameList);
                String bill = reserve_people.getText() + "인/" + howLong + "시간/" + "20,000원";
                pay_bill.setText(bill);
            }
        });

        pay_btn.setOnClickListener(v -> {
            //결제하기.
            main.showBlur(true);

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                positionText.setText("카페");
                main.showBlur(false);
                search_cafe.setVisibility(View.VISIBLE);
                reserve_view04.setVisibility(View.GONE);
                initialization();
            }, 3000);
        });
    }

    private void checkRunTimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(main, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(context, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(main, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(main, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        } else {
            if (checkLocationServicesStatus()) {
                gpsTracker = new GpsTracker(main);
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                refreshCafe();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (!check_result) {
                Toast.makeText(context, "위치 권한 설정을 허용해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        setDefaultLocation();
    }

    private void setDefaultLocation() {
        LatLng KookMin = new LatLng(37.611166, 126.995550);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(KookMin);
        markerOptions.title("국민대학교");
        markerOptions.snippet("사립대학교");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KookMin, 15));
    }

    private void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialog, (view, year, month, dayOfMonth) -> {
            String text = year + "년  " + (month + 1) + "월  " + dayOfMonth + "일";
            reserve_day_btn.setText(text);
            reserve_day.setText(text);
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

        String text = year + "년  " + month + "월  " + day + "일";
        reserve_day_btn.setText(text);
        reserve_day.setText(text);
    }

    private void changeBtn(int setBtn) {
        if (setBtn == 0) {
            popularBtn.setTextColor(whiteColor);
            popularBtn.setBackgroundResource(R.drawable.round_red20);
        } else if (setBtn == 1) {
            distanceBtn.setTextColor(whiteColor);
            distanceBtn.setBackgroundResource(R.drawable.round_red20);
        } else if (setBtn == 2) {
            priceBtn.setTextColor(whiteColor);
            priceBtn.setBackgroundResource(R.drawable.round_red20);
        } else if (setBtn == 3) {
            registerBtn.setTextColor(whiteColor);
            registerBtn.setBackgroundResource(R.drawable.round_red20);
        }

        if (this.setBtn == 0) {
            popularBtn.setTextColor(greyColor);
            popularBtn.setBackgroundResource(R.drawable.round_corner_line20);
        } else if (this.setBtn == 1) {
            distanceBtn.setTextColor(greyColor);
            distanceBtn.setBackgroundResource(R.drawable.round_corner_line20);
        } else if (this.setBtn == 2) {
            priceBtn.setTextColor(greyColor);
            priceBtn.setBackgroundResource(R.drawable.round_corner_line20);
        } else if (this.setBtn == 3) {
            registerBtn.setTextColor(greyColor);
            registerBtn.setBackgroundResource(R.drawable.round_corner_line20);
        }

        this.setBtn = setBtn;
    }

    private void addCafeRecyclerView(int id, String name, String address, String profile, int table_cnt, String open, String close, int like) {
        //서버로부터 데이터 가져와서 추가하기.
        CafeRecyclerItem item = new CafeRecyclerItem();
        int image = R.drawable.cafe01;
        String table = "테이블 수 " + table_cnt + "개";
        String time = "9:00AM~22:00PM";
        String heart = Integer.toString(like);
        item.setData(id, image, name, address, table, time, heart);
        cafe_adapter.addItem(item);
        cafe_adapter.notifyDataSetChanged();
    }

    private void refreshCafe() {
        //카페 목록 새로고침
        cafe_adapter.initialSetUp();
        StringBuilder urlStr = new StringBuilder();
        urlStr.append("https://playde-server-pzovl.run.goorm.io/cafe/list?coords=");
        urlStr.append(latitude);
        urlStr.append(",");
        urlStr.append(longitude);
        urlStr.append("&sort=");
        urlStr.append(setBtn);
        urlStr.append("&range=0,30");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    Log.e("JSONParse", response);
                    cafeJSONParse(response);
                },
                error -> {
                    Toast.makeText(context, "인터넷이 연결되었는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                    Log.e("cafeRefresh", "에러 발생");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);
    }

    private void cafeJSONParse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String cafe = jsonObject.getString("cafe");
            JSONArray jsonArray = new JSONArray(cafe);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                int id = subJsonObject.getInt("id");
                String name = subJsonObject.getString("name");
                String address = subJsonObject.getString("address");
                String profile = subJsonObject.getString("profile");
                int table_cnt = subJsonObject.getInt("table_cnt");
                String open = subJsonObject.getString("open");
                String close = subJsonObject.getString("close");
                int like = subJsonObject.getInt("like");

                addCafeRecyclerView(id, name, address, profile, table_cnt, open, close, like);
            }
        } catch (Exception e) {
            Log.e("cafeJSONParse", "예외 발생");
        }
    }

    private void initialization() {
        for (int i = 0; i < 12; i++) {
            checkedGame[i] = false;
        }
        countChecked = 0;
        firstChecked = 0;
        gameList = "";
        vp.setCurrentItem(0);

        setDate();
        reserve_people_txt.setText("4");
        reserve_people.setText("4");
        people = 4;

        for (int i = 0; i < 14; i++) {
            isChecked[i] = false;
            reserve_time_btn[i].setBackgroundResource(R.drawable.round_corner_whitegrey30);
            reserve_time_btn[i].setTextColor(blackColor);
        }

        try {
            GameSelectFirstFragment.initialization();
            GameSelectSecondFragment.initialization();
            GameSelectThirdFragment.initialization();
        } catch (Exception e) {
            //ThirdFragment 가 OnCreate 안되서 문제가 발생함.
        }
    }

    private void goBack() {
        if (reserve_view01.getVisibility() == View.VISIBLE) {
            search_cafe.setVisibility(View.VISIBLE);
            reserve_view01.setVisibility(View.GONE);
            initialization();
        } else if (reserve_view02.getVisibility() == View.VISIBLE) {
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

    @Override
    public void onBackPressed() {
        if (search_cafe.getVisibility() == View.GONE)
            goBack();
        else
            main.onBackTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        main.setOnBackPressedListener(this, 1);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
