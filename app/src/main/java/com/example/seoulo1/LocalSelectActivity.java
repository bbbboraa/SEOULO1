package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;


public class LocalSelectActivity extends AppCompatActivity {
    private ImageButton like_btn, menu_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_select);

        like_btn = findViewById(R.id.like_btn);

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
                        Intent intent4 = new Intent(LocalSelectActivity.this, MyLocationActivity.class);
                        startActivity(intent4);
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
                        break;
                    case R.id.menu_travel_log:
                        break;
                    case R.id.menu_expense_graph:
                        break;
                }

                return false;
            });
            popup.show();
        });

    }

}