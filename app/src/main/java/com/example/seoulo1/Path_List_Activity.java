package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class Path_List_Activity extends AppCompatActivity {

    Calendar calendar;
    TextView calendar_day;

    Button btn_AddrReserch_move;
    Button btn_AddrReserch_move2;
    Button btn_AddrReserch_move3;
    Button btn_AddrReserch_move4;
    Button btn_AddrReserch_move5;

    private ImageButton like_btn, menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_list);

        like_btn = findViewById(R.id.like_btn);
        menu_btn = findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(view -> {

            //PopupMenu 객체 생성
            PopupMenu popup= new PopupMenu(Path_List_Activity.this, view); //두 번째 파라미터가 팝업메뉴가 붙을 뷰
            //PopupMenu popup= new PopupMenu(MainActivity.this, btn2); //첫번째 버튼을 눌렀지만 팝업메뉴는 btn2에 붙어서 나타남
            getMenuInflater().inflate(R.menu.popup, popup.getMenu());

            //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
            popup.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()){
                    case R.id.menu_my_location:
                        Intent intent1 = new Intent(Path_List_Activity.this, MyLocationActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.menu_hot_place:
                        Intent intent2 = new Intent(Path_List_Activity.this, LocalSelectActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.menu_route:
                        Intent intent3 = new Intent(Path_List_Activity.this, CalendarActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu_checklist:
                        break;
                    case R.id.menu_travel_log:
                        break;
                    case R.id.menu_expense_graph:
                        break;
                    case R.id.menu_mypage:
                        Intent intent7 = new Intent(Path_List_Activity.this, MypageActivity.class);
                        startActivity(intent7);
                        break;
                }

                return false;
            });
            popup.show();
        });


        btn_AddrReserch_move = findViewById(R.id.btn_AddrReserch_move);
        btn_AddrReserch_move2 = findViewById(R.id.btn_AddrReserch_move2);
        btn_AddrReserch_move3 = findViewById(R.id.btn_AddrReserch_move3);
        btn_AddrReserch_move4 = findViewById(R.id.btn_AddrReserch_move4);
        btn_AddrReserch_move5 = findViewById(R.id.btn_AddrReserch_move5);

        calendar_day = findViewById(R.id.calendar_day);
        // calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        Intent intent = getIntent();
        long daysDiff = getIntent().getLongExtra("selectDate", 0);

        calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DATE, (int) daysDiff);

        calendar_day.setText("Day " + (daysDiff + 1));


        btn_AddrReserch_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivity(intent); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivity(intent); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivity(intent); // 도로명 주소 검색 액티비티 이동
            }
        });


        btn_AddrReserch_move4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivity(intent); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivity(intent); // 도로명 주소 검색 액티비티 이동
            }
        });
    }
}