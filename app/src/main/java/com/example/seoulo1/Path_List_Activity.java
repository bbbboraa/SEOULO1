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
    private RecyclerView rv1, rv2, rv3, rv4, rv5;
    private AddressAdapter rv1Adapter, rv2Adapter, rv3Adapter, rv4Adapter, rv5Adapter;
    private ArrayList<String> rv1DataList, rv2DataList, rv3DataList, rv4DataList, rv5DataList;

    private RecyclerView.LayoutManager rv1LayoutManager, rv2LayoutManager, rv3LayoutManager, rv4LayoutManager, rv5LayoutManager;
    Calendar calendar;
    TextView calendar_day;

    Button btn_AddrReserch_move;
    Button btn_AddrReserch_move2;
    Button btn_AddrReserch_move3;
    Button btn_AddrReserch_move4;
    Button btn_AddrReserch_move5;
    Button Route_Recom;



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
                        Intent intent4 = new Intent(Path_List_Activity.this, CheckListActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.menu_travel_log:
                        Intent intent5 = new Intent(Path_List_Activity.this, PlanActivity.class);
                        startActivity(intent5);
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

        rv1 = findViewById(R.id.rv1);
        rv2 = findViewById(R.id.rv2);
        rv3 = findViewById(R.id.rv3);
        rv4 = findViewById(R.id.rv4);
        rv5 = findViewById(R.id.rv5);

        rv1LayoutManager = new LinearLayoutManager(this);
        rv2LayoutManager = new LinearLayoutManager(this);
        rv3LayoutManager = new LinearLayoutManager(this);
        rv4LayoutManager = new LinearLayoutManager(this);
        rv5LayoutManager = new LinearLayoutManager(this);

        rv1DataList = new ArrayList<>();
        rv2DataList = new ArrayList<>();
        rv3DataList = new ArrayList<>();
        rv4DataList = new ArrayList<>();
        rv5DataList = new ArrayList<>();

        rv1Adapter = new AddressAdapter(rv1DataList);
        rv2Adapter = new AddressAdapter(rv2DataList);
        rv3Adapter = new AddressAdapter(rv3DataList);
        rv4Adapter = new AddressAdapter(rv4DataList);
        rv5Adapter = new AddressAdapter(rv5DataList);

        rv1.setLayoutManager(new LinearLayoutManager(this));
        rv1.setAdapter(rv1Adapter);

        rv2.setLayoutManager(new LinearLayoutManager(this));
        rv2.setAdapter(rv2Adapter);

        rv3.setLayoutManager(new LinearLayoutManager(this));
        rv3.setAdapter(rv3Adapter);

        rv4.setLayoutManager(new LinearLayoutManager(this));
        rv4.setAdapter(rv4Adapter);

        rv5.setLayoutManager(new LinearLayoutManager(this));
        rv5.setAdapter(rv5Adapter);

        calendar_day = findViewById(R.id.calendar_day);

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
                startActivityForResult(intent, 2); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivityForResult(intent, 3);// 도로명 주소 검색 액티비티 이동
            }
        });


        btn_AddrReserch_move4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivityForResult(intent, 4); // 도로명 주소 검색 액티비티 이동
            }
        });

        btn_AddrReserch_move5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Path_List_Activity.this, AddrResearchActivity.class);
                intent.putExtra("selectDate", daysDiff);
                startActivityForResult(intent, 5); // 도로명 주소 검색 액티비티 이동
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

        RecyclerView rv2= findViewById(R.id.rv2);
        rv2.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView rv3 = findViewById(R.id.rv3);
        rv3.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView rv4 = findViewById(R.id.rv4);
        rv4.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView rv5 = findViewById(R.id.rv5);
        rv5.setLayoutManager(new LinearLayoutManager(this));

        rv1DataList = new ArrayList<>();
        rv1Adapter = new AddressAdapter(rv1DataList);
        rv1.setAdapter(rv1Adapter);

        rv2DataList = new ArrayList<>();
        rv2Adapter = new AddressAdapter(rv2DataList);
        rv2.setAdapter(rv2Adapter);

        rv3DataList = new ArrayList<>();
        rv3Adapter = new AddressAdapter(rv3DataList);
        rv3.setAdapter(rv3Adapter);

        rv4DataList = new ArrayList<>();
        rv4Adapter = new AddressAdapter(rv4DataList);
        rv4.setAdapter(rv4Adapter);

        rv5DataList = new ArrayList<>();
        rv5Adapter = new AddressAdapter(rv5DataList);
        rv5.setAdapter(rv5Adapter);
    }

    private void handleAddressResult() {
        // 이전 Activity로부터 전달받은 주소 검색 결과를 처리하는 로직을 작성
        String searchResult = getIntent().getStringExtra("searchResult");
        if (rv1DataList.size() >= MAX_ADDRESS_COUNT) {
            rv1DataList.remove(0); // 가장 오래된 항목 삭제
            rv1Adapter.notifyItemRemoved(0);
        }
        rv1DataList.add(searchResult);
        rv1Adapter.notifyItemInserted(rv1DataList.size() - 1);

        if (rv2DataList.size() >= MAX_ADDRESS_COUNT) {
            rv2DataList.remove(0);
            rv2Adapter.notifyItemRemoved(0);
        }
        rv2DataList.add(searchResult);
        rv2Adapter.notifyItemInserted(rv2DataList.size() - 1);

        if (rv3DataList.size() >= MAX_ADDRESS_COUNT) {
            rv3DataList.remove(0);
            rv3Adapter.notifyItemRemoved(0);
        }
        rv3DataList.add(searchResult);
        rv3Adapter.notifyItemInserted(rv3DataList.size() - 1);

        if (rv4DataList.size() >= MAX_ADDRESS_COUNT) {
            rv4DataList.remove(0);
            rv4Adapter.notifyItemRemoved(0);
        }
        rv4DataList.add(searchResult);
        rv4Adapter.notifyItemInserted(rv4DataList.size() - 1);

        if (rv5DataList.size() >= MAX_ADDRESS_COUNT) {
            rv5DataList.remove(0);
            rv5Adapter.notifyItemRemoved(0);
        }
        rv5DataList.add(searchResult);
        rv5Adapter.notifyItemInserted(rv5DataList.size() - 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String address = data.getStringExtra("address");
            if (address != null) {
                if (requestCode == 1) {
                    rv1DataList.add(address);
                    rv1Adapter.notifyDataSetChanged();
                } else if (requestCode == 2) {
                    rv2DataList.add(address);
                    rv2Adapter.notifyDataSetChanged();
                } else if (requestCode == 3) {
                    rv3DataList.add(address);
                    rv3Adapter.notifyDataSetChanged();
                } else if (requestCode == 4) {
                    rv4DataList.add(address);
                    rv4Adapter.notifyDataSetChanged();
                } else if (requestCode == 5) {
                    rv5DataList.add(address);
                    rv5Adapter.notifyDataSetChanged();
                }
            }
        }
    }
}