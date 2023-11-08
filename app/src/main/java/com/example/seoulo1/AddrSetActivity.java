package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddrSetActivity extends AppCompatActivity {

    private EditText clickedEditText;
    private EditText DepartureAddr;
    private EditText ArrivalAddr;
    private String departureLatitude;
    private String departureLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr_set);

        Button btn_addr_save = findViewById(R.id.btn_addr_save);
        DepartureAddr = findViewById(R.id.departure_addr);
        ArrivalAddr = findViewById(R.id.arrival_addr);

        // block touch
        DepartureAddr.setFocusable(false);
        // DepartureAddr 클릭 시 SearchActivity로 이동
        DepartureAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedEditText = DepartureAddr; // clickedEditText 초기화
                Intent intent = new Intent(AddrSetActivity.this, SearchActivity.class);
                startForResult.launch(intent);
            }
        });


        ArrivalAddr.setFocusable(false);
        // ArivalAddr 클릭 시 SearchActivity로 이동
        ArrivalAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedEditText = ArrivalAddr; // clickedEditText 초기화
                Intent intent = new Intent(AddrSetActivity.this, SearchActivity.class);
                startForResult.launch(intent);
            }
        });


        btn_addr_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String departureAddress = DepartureAddr.getText().toString();
                String arrivalAddress = ArrivalAddr.getText().toString();

                if (departureLatitude == null || departureLongitude == null) {
                    // 출발 장소의 위도 및 경도가 아직 얻어지지 않았으면 사용자에게 메시지를 표시하고 반환
                    Toast.makeText(AddrSetActivity.this, "출발 장소의 위치 정보를 가져오는 중입니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (arrivalAddress.isEmpty()) {
                    Toast.makeText(AddrSetActivity.this, "도착 장소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 이제 departureLatitude 및 departureLongitude를 사용하여 출발 장소의 위치 정보를 저장하고 활용할 수 있습니다.
                // DB에 저장 또는 다른 작업을 수행할 수 있습니다.

                Intent intent = new Intent(AddrSetActivity.this, RouteRecommActivity.class);
                startForResult.launch(intent);

//                // 출발 장소를 데이터베이스에 저장
//                Address departureAddress = new Address();
//                departureAddress.setAddress("출발 장소 주소");
//                departureAddress.setLatitude(출발장소의 위도);
//                departureAddress.setLongitude(출발장소의 경도);
//
//                // 도착 장소를 데이터베이스에 저장
//                Address arrivalAddress = new Address();
//                arrivalAddress.setAddress("도착 장소 주소");
//                arrivalAddress.setLatitude(도착장소의 위도);
//                arrivalAddress.setLongitude(도착장소의 경도);
//
//                // Room 데이터베이스를 초기화합니다.
//                AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "address-database").build();
//
//                // 출발 장소와 도착 장소를 데이터베이스에 저장합니다.
//                database.addressDao().insert(departureAddress);
//                database.addressDao().insert(arrivalAddress);
//
//                // AddrSetActivity 종료 또는 다른 동작 수행




            }

        });

    }

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String data = result.getData().getStringExtra("data");
                        // 여기서 DepartureAddr 또는 ArivalAddr 중 어떤 EditText에 값을 설정할 것인지 확인
                        if (clickedEditText == DepartureAddr) {
                            DepartureAddr.setText(data);
                        } else if (clickedEditText == ArrivalAddr) {
                            ArrivalAddr.setText(data);
                        }
                    }
                }
            }
    );



}