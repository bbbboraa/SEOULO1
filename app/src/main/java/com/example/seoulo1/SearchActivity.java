package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class SearchActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADDR_RESEARCH = 1;
    private WebView webView;
    private AppDatabase database;

    private int requestCode; // 추가: 버튼에 대한 요청 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 데이터베이스 초기화 (한 번만 호출)
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "places-db")
                .fallbackToDestructiveMigration()
                .build();
        //database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "places-db").build();

        Intent intent = getIntent();
        requestCode = intent.getIntExtra("requestCode", 0); // 추가: 요청 코드 받아오기

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new BridgeInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Android -> JavaScript 함수 호출!
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        // 최초 웹뷰 로드
        webView.loadUrl("https://seoulo-4d3ad.web.app");


    }


    private class BridgeInterface {
        @JavascriptInterface
        public void processDATA(String data) {
            // 다음(카카오) 주소 검색 API 결과 값이 브릿지 통로를 통해 전달 받는다. (from Javascript)
            Intent intent = new Intent();
            intent.putExtra("data", data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADDR_RESEARCH && resultCode == RESULT_OK && data != null) {
            String address = data.getStringExtra("address");

            // PathListFragment로 결과 값 전달
            Intent intent = new Intent();
            intent.putExtra("address", address);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}