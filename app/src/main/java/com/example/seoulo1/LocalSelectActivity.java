package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;


public class LocalSelectActivity extends AppCompatActivity {
    private ImageButton like_btn, menu_btn;
    private Button gangseo, mapo, sdm, ddm, eunpyeong, jongno, dobong, gangbuk, seongbuk, nowon;
    private Button jungnang, gwangjin, seongdong, jung, yongsan, yangcheon, guro, geumcheon, ydp, dongjak;
    private Button gwanak, gangdong, songpa, gangnam, seocho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_select);

        like_btn = findViewById(R.id.like_btn);


        gangseo = findViewById(R.id.gangseo);
        mapo = findViewById(R.id.mapo);
        sdm = findViewById(R.id.sdm);
        ddm = findViewById(R.id.ddm);
        eunpyeong = findViewById(R.id.eunpyeong);
        jongno = findViewById(R.id.jongno);
        dobong = findViewById(R.id.dobong);
        gangbuk = findViewById(R.id.gangbuk);
        seongbuk = findViewById(R.id.seongbuk);
        nowon = findViewById(R.id.nowon);

        jungnang = findViewById(R.id.jungnang);
        gwangjin = findViewById(R.id.gwangjin);
        seongdong = findViewById(R.id.seongdong);
        jung = findViewById(R.id.jung);
        yongsan = findViewById(R.id.yongsan);
        yangcheon = findViewById(R.id.yangcheon);
        guro = findViewById(R.id.guro);
        geumcheon = findViewById(R.id.geumcheon);
        ydp = findViewById(R.id.ydp);
        dongjak = findViewById(R.id.dongjak);

        gwanak = findViewById(R.id.gwanak);
        gangdong = findViewById(R.id.gangdong);
        songpa = findViewById(R.id.songpa);
        gangnam = findViewById(R.id.gangnam);
        seocho = findViewById(R.id.seocho);


        menu_btn = findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(view -> {

            //PopupMenu 객체 생성
            PopupMenu popup= new PopupMenu(LocalSelectActivity.this, view); //두 번째 파라미터가 팝업메뉴가 붙을 뷰
            //PopupMenu popup= new PopupMenu(MainActivity.this, btn2); //첫번째 버튼을 눌렀지만 팝업메뉴는 btn2에 붙어서 나타남
            getMenuInflater().inflate(R.menu.popup, popup.getMenu());

            //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
            popup.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()){
                    case R.id.menu_my_location:
                        Intent intent1 = new Intent(LocalSelectActivity.this, MyLocationActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.menu_hot_place:
                        Intent intent2 = new Intent(LocalSelectActivity.this, LocalSelectActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.menu_route:
                        Intent intent3 = new Intent(LocalSelectActivity.this, CalendarActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu_checklist:
                        Intent intent4 = new Intent(LocalSelectActivity.this, CheckListActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.menu_travel_log:
                        Intent intent5 = new Intent(LocalSelectActivity.this, PlanActivity.class);
                        startActivity(intent5);
                        break;
                    case R.id.menu_expense_graph:
                        break;
                    case R.id.menu_mypage:
                        Intent intent7 = new Intent(LocalSelectActivity.this, MypageActivity.class);
                        startActivity(intent7);
                        break;
                }

                return false;
            });
            popup.show();
        });

        gangseo.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGangseoActivity.class);
            startActivity(intent);
        });
        mapo.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalMapoActivity.class);
            startActivity(intent);
        });
        sdm.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalSdmActivity.class);
            startActivity(intent);
        });
        ddm.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalDdmActivity.class);
            startActivity(intent);
        });
        eunpyeong.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalEunpyeongActivity.class);
            startActivity(intent);
        });
        jongno.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalJongnoActivity.class);
            startActivity(intent);
        });
        dobong.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalDobongActivity.class);
            startActivity(intent);
        });
        gangbuk.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGangbukActivity.class);
            startActivity(intent);
        });
        seongbuk.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalSeongbukActivity.class);
            startActivity(intent);
        });
        nowon.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalNowonActivity.class);
            startActivity(intent);
        });

        jungnang.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalJungnangActivity.class);
            startActivity(intent);
        });
        gwangjin.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGwangjinActivity.class);
            startActivity(intent);
        });
        seongdong.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalSeongdongActivity.class);
            startActivity(intent);
        });
        jung.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalJungActivity.class);
            startActivity(intent);
        });
        yongsan.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalYongsanActivity.class);
            startActivity(intent);
        });
        yangcheon.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalYangcheonActivity.class);
            startActivity(intent);
        });
        guro.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGuroActivity.class);
            startActivity(intent);
        });
        geumcheon.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGeumcheonActivity.class);
            startActivity(intent);
        });
        ydp.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalYdpActivity.class);
            startActivity(intent);
        });
        dongjak.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalDongjakActivity.class);
            startActivity(intent);
        });

        gwanak.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGwanakActivity.class);
            startActivity(intent);
        });
        gangdong.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGangdongActivity.class);
            startActivity(intent);
        });
        songpa.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalSongpaActivity.class);
            startActivity(intent);
        });
        gangnam.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalGangnamActivity.class);
            startActivity(intent);
        });
        seocho.setOnClickListener(v -> {
            Intent intent = new Intent(LocalSelectActivity.this, LocalSeochoActivity.class);
            startActivity(intent);
        });



    }

}