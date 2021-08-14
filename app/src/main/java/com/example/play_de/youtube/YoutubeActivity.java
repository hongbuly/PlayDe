package com.example.play_de.youtube;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;

import com.example.play_de.R;
import com.google.android.youtube.player.YouTubeBaseActivity;

public class YoutubeActivity extends YouTubeBaseActivity {
    private WebView playerView;
    private String url = "http://www.youtube.com/embed/37V2ajpMEic";

    private Button fullscreenBtn;
    private ListView listView;
    private YoutubeListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        initPlayer();
        initFullScreenBtn();

        adapter = new YoutubeListViewAdapter();
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        addListView();
    }

    private void initFullScreenBtn(){
        fullscreenBtn = findViewById(R.id.fullScreenBtn);
        fullscreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //전체보기 버튼
           }
        });
    }

    private void initPlayer() {
        playerView = findViewById(R.id.youtubeView);
        playerView.getSettings().setJavaScriptEnabled(true);
        playerView.setAlwaysDrawnWithCacheEnabled(true);
        playerView.setWebChromeClient(new WebChromeClient());
        playerView.setWebViewClient(new WebViewClientClass());
        playerView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && playerView.canGoBack()) {
            playerView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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
