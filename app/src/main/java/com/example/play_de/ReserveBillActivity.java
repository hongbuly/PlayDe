package com.example.play_de;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReserveBillActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_reserve_bill);
        reserve_day = findViewById(R.id.reserve_day);
        reserve_time = findViewById(R.id.reserve_time);
        reserve_people = findViewById(R.id.reserve_people);
        reserve_name = findViewById(R.id.reserve_name);
        reserve_number = findViewById(R.id.reserve_number);
        reserve_game = findViewById(R.id.reserve_game);
        reserve_bill = findViewById(R.id.reserve_bill);
        pay_btn = findViewById(R.id.pay_btn);

        //이전 화면에서 정보를 넘겨받아 text 바꾸기.

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제하기.
            }
        });
    }
}