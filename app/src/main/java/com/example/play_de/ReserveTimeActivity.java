package com.example.play_de;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReserveTimeActivity extends AppCompatActivity {
    private Button reserve_day_btn;
    private Button reserve_time_btn[];
    private Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_time);
        reserve_day_btn = findViewById(R.id.reserve_day_btn);
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

        for (int i = 0; i < 14; i++) {
            reserve_time_btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //선택이 되면 노랗게, 그 반대는 원래대로
                }
            });
        }

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 시간을 다음 화면에 인자로 넘겨줌.
            }
        });
    }
}
