package com.example.play_de;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {
    private YouTubePlayerView playerView;
    private YouTubePlayer player;

    private Button fullscreenBtn;
    private ListView listView;
    private YoutubeListViewAdapter adapter;

    private static String API_KEY = "AIzaSyDRjLjbuX8A63rHcw1DpJvnWla-rI67diY";
    private static final String TAG = "P11_1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        initPlayer();
        initFullScreenBtn();

        adapter = new YoutubeListViewAdapter();
        listView = findViewById(R.id.P11_1_listView);
        listView.setAdapter(adapter);
        addListView();
    }

    private void initFullScreenBtn(){
        fullscreenBtn = findViewById(R.id.p11_FullScreenBtn);
        fullscreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //전체보기 버튼
           }
        });
    }

    private void initPlayer() {
        playerView = findViewById(R.id.youtubeView);
        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;

                //보드게임 종류에 맞는 영상 재생하기.
                player.loadVideo("37V2ajpMEic");

                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String id) {
                        Log.d(TAG, "onLoaded: " + id);
                        player.play();
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {
                        Log.d(TAG, "onError: " + errorReason);
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitFailure");
            }
        });
    }

    void addListView() {
        String title;
        String content;

        for (int i = 1; i < 8; i++) {
            title = "0" + i + ". 가이드 " + i +"번";
            content = "가이드 " + i + "번 설명";
            adapter.addItem(title, content);
        }
        adapter.notifyDataSetChanged();
    }
}
