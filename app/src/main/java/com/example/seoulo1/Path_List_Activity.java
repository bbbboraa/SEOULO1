package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class Path_List_Activity extends AppCompatActivity {

    private static final int MAX_ADDRESS_COUNT = 3;
    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private ArrayList<String> addressList;

    Calendar calendar;
    TextView calendar_day;

    Button btn_AddrReserch_move;
    Button btn_AddrReserch_move2;
    Button btn_AddrReserch_move3;
    Button btn_AddrReserch_move4;
    Button btn_AddrReserch_move5;
    Button Route_Recom;

    TextView Restaurant1, Restaurant2, Restaurant3;
    TextView Cafe1, Cafe2, Cafe3;
    TextView Shopping1, Shopping2, Shopping3;
    TextView Culture_Tour1, Culture_Tour2, Culture_Tour3;
    TextView Hotel1, Hotel2, Hotel3;



    private ImageButton like_btn, menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_list);

        setupRecyclerView();
        handleAddressResult();

        like_btn = findViewById(R.id.like_btn);
        menu_btn = findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(view -> {

            //PopupMenu 객체 생성
            PopupMenu popup = new PopupMenu(Path_List_Activity.this, view); //두 번째 파라미터가 팝업메뉴가 붙을 뷰
            //PopupMenu popup= new PopupMenu(MainActivity.this, btn2); //첫번째 버튼을 눌렀지만 팝업메뉴는 btn2에 붙어서 나타남
            getMenuInflater().inflate(R.menu.popup, popup.getMenu());

            //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
            popup.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()) {
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
        Route_Recom = findViewById(R.id.Route_Recom);

        //Restaurant1 = findViewById(R.id.Restaurant1);
        //Restaurant2 = findViewById(R.id.Restaurant2);
        //Restaurant3 = findViewById(R.id.Restaurant3);

        //Cafe1 = findViewById(R.id.Cafe1);
        //Cafe2 = findViewById(R.id.Cafe2);
        //Cafe3 = findViewById(R.id.Cafe3);

        //Shopping1 = findViewById(R.id.Shopping1);
        //Shopping2 = findViewById(R.id.Shopping2);
        //Shopping3 = findViewById(R.id.Shopping3);

//        Culture_Tour1 = findViewById(R.id.Culture_Tour1);
//        Culture_Tour2 = findViewById(R.id.Culture_Tour2);
//        Culture_Tour3 = findViewById(R.id.Culture_Tour3);
//
//        Hotel1 = findViewById(R.id.Hotel1);
//        Hotel2 = findViewById(R.id.Hotel2);
//        Hotel3 = findViewById(R.id.Hotel3);

        recyclerView = findViewById(R.id.rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter(addressList);
        recyclerView.setAdapter(addressAdapter);

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
                startActivityForResult(intent, 1); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivityForResult(intent, 1); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivityForResult(intent, 1);// 도로명 주소 검색 액티비티 이동
            }
        });


        btn_AddrReserch_move4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivityForResult(intent, 1); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivityForResult(intent, 1); // 도로명 주소 검색 액티비티 이동
            }
        });

        Route_Recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void setupRecyclerView() {
        RecyclerView rv1 = findViewById(R.id.rv1);
        rv1.setLayoutManager(new LinearLayoutManager(this));

        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter(addressList);
        rv1.setAdapter(addressAdapter);
    }

    private void handleAddressResult() {
        // 이전 Activity로부터 전달받은 주소 검색 결과를 처리하는 로직을 작성해주세요.
        String searchResult = getIntent().getStringExtra("searchResult");
        if (addressList.size() >= MAX_ADDRESS_COUNT) {
            addressList.remove(0); // 가장 오래된 항목 삭제
            addressAdapter.notifyItemRemoved(0);
        }
        addressList.add(searchResult);
        addressAdapter.notifyItemInserted(addressList.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String address = data.getStringExtra("address");
            if (address != null) {
                if (addressList == null) {
                    addressList = new ArrayList<>();
                }
                addressList.add(address);
                addressAdapter.notifyDataSetChanged();
            }
        }
    }
}