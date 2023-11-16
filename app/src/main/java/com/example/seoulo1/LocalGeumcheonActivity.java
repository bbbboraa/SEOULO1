package com.example.seoulo1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;



public class LocalGeumcheonActivity extends AppCompatActivity {
    private ImageButton like_btn, menu_btn, list_location;
    private Button button_restaurant, button_cafe, button_shopping, button_tour;
    private ListView listView;
    @Override
    protected void onResume() {
        super.onResume();
        // button_restaurant 버튼 클릭 효과
        button_restaurant.performClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_geumcheon);

        listView = findViewById(R.id.listView);
        button_restaurant = findViewById(R.id.button_restaurant);
        button_cafe =  findViewById(R.id.button_cafe);
        button_tour = findViewById(R.id.button_tour);
        button_shopping =  findViewById(R.id.button_shopping);

        button_restaurant.setOnClickListener(v -> {
            button_restaurant.setSelected(true);
            button_cafe.setSelected(false);
            button_tour.setSelected(false);
            button_shopping.setSelected(false);
            displayListFromAsset("geumcheon_food.txt", "geumcheon_food_url.txt");
        });

        button_cafe.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(true);
            button_tour.setSelected(false);
            button_shopping.setSelected(false);
            displayListFromAsset("geumcheon_cafe.txt", "geumcheon_cafe_url.txt");
        });

        button_tour.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_tour.setSelected(true);
            button_shopping.setSelected(false);
            displayListFromAsset("geumcheon_tour.txt", "geumcheon_tour_url.txt");
        });

        button_shopping.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_tour.setSelected(false);
            button_shopping.setSelected(true);
            displayListFromAsset("geumcheon_shop.txt", "geumcheon_shop_url.txt");
        });


        menu_btn = findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(view -> {

            //PopupMenu 객체 생성
            PopupMenu popup = new PopupMenu(LocalGeumcheonActivity.this, view); //두 번째 파라미터가 팝업메뉴가 붙을 뷰
            //PopupMenu popup= new PopupMenu(MainActivity.this, btn2); //첫번째 버튼을 눌렀지만 팝업메뉴는 btn2에 붙어서 나타남
            getMenuInflater().inflate(R.menu.popup, popup.getMenu());

            //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
            popup.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()) {
                    case R.id.menu_my_location:
                        Intent intent1 = new Intent(LocalGeumcheonActivity.this, MyLocationActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.menu_hot_place:
                        Intent intent2 = new Intent(LocalGeumcheonActivity.this, LocalSelectActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.menu_route:
                        Intent intent3 = new Intent(LocalGeumcheonActivity.this, CalendarActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu_checklist:
                        Intent intent4 = new Intent(LocalGeumcheonActivity.this, CheckListActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.menu_travel_log:
                        Intent intent5 = new Intent(LocalGeumcheonActivity.this, PlanActivity.class);
                        startActivity(intent5);
                        break;
                    case R.id.menu_expense_graph:
                        Intent intent6 = new Intent(LocalGeumcheonActivity.this, GraphActivity.class);
                        startActivity(intent6);
                        break;
                    case R.id.menu_mypage:
                        Intent intent7 = new Intent(LocalGeumcheonActivity.this, MypageActivity.class);
                        startActivity(intent7);
                        break;
                }

                return false;
            });
            popup.show();
        });

        like_btn = findViewById(R.id.like_btn);
        like_btn.setOnClickListener(view ->{
            Intent intent8 = new Intent(LocalGeumcheonActivity.this, LikeActivity.class);
            startActivity(intent8);
        });
    }

    private void displayListFromAsset(String fileName, String urlFileName) {
        ArrayList<HotplaceItem> itemList = new ArrayList<>();

        try {
            InputStream inputStream = getAssets().open("Seoul/Geumcheon/"+fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            InputStream urlInputStream = getAssets().open("Seoul/Geumcheon/"+urlFileName);
            BufferedReader urlBufferedReader = new BufferedReader(new InputStreamReader(urlInputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // URL 읽어오기
                String naverUrl = urlBufferedReader.readLine();
                HotplaceItem hotplaceItem = new HotplaceItem(line, naverUrl);
                itemList.add(hotplaceItem);
            }

            bufferedReader.close();
            urlBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HotplaceAdapter adapter = new HotplaceAdapter(this, R.layout.list_view_items_2, itemList);
        listView.setAdapter(adapter);

        // 클릭 이벤트 처리
        listView.setOnItemClickListener((parent, view, position, id) -> {
            HotplaceItem selectedItem = itemList.get(position);
            String naverUrl = selectedItem.getNaverUrl();
            openNaverUrl(naverUrl);
        });
    }
    // handleIntentUrl 메소드 내부에서 intentUrl을 클래스 멤버 변수로 선언
    private String intentUrl;

    // 네이버 URL을 여는 메서드
    private void openNaverUrl(String naverUrl) {
        // 먼저 intentUrl을 설정
        intentUrl = naverUrl;

        try {
            // URI 객체를 생성하여 유효성을 확인
            URI uri = new URI(naverUrl);

            if ("intent".equals(uri.getScheme())) {
                handleIntentUrl(naverUrl);

            } else {
                setContentView(R.layout.webview_layout); // 레이아웃 파일에 WebView 추가

                WebView webView = findViewById(R.id.webView);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                //Mixed Content 허용 설정
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }

                // WebViewClient 내부에서 사용할 context 변수 선언
                Context context = LocalGeumcheonActivity.this;

                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        Log.e("WebView", "SSL Error: " + error);
                        // 에러 처리 로직 추가
                        handler.proceed(); // 또는 handler.cancel();
                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        Log.e("WebView", "Error: " + error.getDescription());
                        // 에러 처리 로직 추가
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        String url = request.getUrl().toString();
                        Log.d("WebView", "Loading URL: " + url); // 로그 메시지 생성

                        if (url.startsWith("https://") || url.startsWith("http://")) {
                            // HTTP/HTTPS URL인 경우 WebView 내에서 로드하도록 함
                            view.loadUrl(url);
                        } else if (url.startsWith("intent://")) {
                            // "intent://" 스키마인 경우 Intent 처리
                            try {
                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                                // 파싱된 Intent를 로그로 출력
                                Log.d("WebView", "Parsed Intent: " + intent);

                                if (isNaverMapInstalled()) {
                                    // 네이버 지도 앱이 설치되어 있으면 해당 앱을 열기
                                    startActivity(intent);
                                } else {
                                    // 네이버 지도 앱이 설치되어 있지 않으면 웹 브라우저로 해당 URL 열기
                                    Log.e("LocalGeumcheonActivity", "네이버 지도 앱이 설치되어 있지 않습니다.");
                                    Toast.makeText(context, "네이버 지도 앱이 설치되어 있지 않습니다. 웹 브라우저로 열겠습니다.", Toast.LENGTH_SHORT).show();

                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentUrl));
                                    startActivity(browserIntent);
                                }
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // 다른 스키마의 URL은 기본적으로 WebView 내에서 로드
                            view.loadUrl(url);
                        }
                        return true;
                    }
                });
                // URL 로드
                webView.loadUrl(naverUrl);

                // 로그 추가: intentUrl 값 확인
                Log.d("WebView", "intentUrl: " + intentUrl);

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            // 유효하지 않은 URL 에러 처리
            Toast.makeText(this, "유효하지 않은 URL입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // Intent 스키마를 처리하는 메서드
    private void handleIntentUrl(String intentUrl) {
        try {
            Intent intent = Intent.parseUri(intentUrl, Intent.URI_INTENT_SCHEME);

            // Intent 처리를 위해 사용자에게 앱 선택 또는 직접 실행하는 코드
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "해당 Intent를 처리할 수 있는 앱이 설치되어 있지 않습니다.2", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Toast.makeText(this, "유효하지 않은 Intent URL입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNaverMapInstalled() {
        PackageManager packageManager = getPackageManager();
        try {
            // 패키지 정보를 가져옴
            packageManager.getPackageInfo("com.nhn.android.nmap", PackageManager.GET_ACTIVITIES);
            return true; // 패키지가 설치되어 있음
        } catch (PackageManager.NameNotFoundException e) {
            return false; // 패키지가 설치되어 있지 않음
        }
    }

}