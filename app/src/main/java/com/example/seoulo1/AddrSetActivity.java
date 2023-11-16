package com.example.seoulo1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddrSetActivity extends AppCompatActivity {

    private EditText clickedEditText;
    private EditText DepartureAddr;
    private EditText ArrivalAddr;


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
                startAddressSearch(6); // 출발지를 나타내는 requestCode 6 전달
            }
        });


        ArrivalAddr.setFocusable(false);
        // ArivalAddr 클릭 시 SearchActivity로 이동
        ArrivalAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedEditText = ArrivalAddr; // clickedEditText 초기화
                startAddressSearch(7); // 도착지를 나타내는 requestCode 7 전달
            }
        });


        btn_addr_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //기본 코드
                Intent intent = new Intent(AddrSetActivity.this, RouteRecommActivity.class);
                startForResult.launch(intent);
            }

        });

    }

    private void startAddressSearch(int requestCode) {
        Intent intent = new Intent(AddrSetActivity.this, SearchActivity.class);
        intent.putExtra("requestCode", requestCode);
        startForResult.launch(intent);
    }

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String data = result.getData().getStringExtra("data");

                        // 여기서 DepartureAddr 또는 ArrivalAddr 중 어떤 EditText에 값을 설정할 것인지 확인
                        if (clickedEditText == DepartureAddr) {
                            DepartureAddr.setText(data);
                            handleAddressResult(data, DepartureAddr, getCategoryByRequestCode(6));
                            //saveAddressToDatabase(data, getCategoryByRequestCode(6)); // 출발지를 나타내는 requestCode 6 전달
                        } else if (clickedEditText == ArrivalAddr) {
                            ArrivalAddr.setText(data);
                            handleAddressResult(data, ArrivalAddr, getCategoryByRequestCode(7));
                            //saveAddressToDatabase(data, getCategoryByRequestCode(7)); // 도착지를 나타내는 requestCode 7 전달
                        }
                    }
                }
            }
    );

    private void handleAddressResult(String address, EditText editText, String category) {
        AsyncTask.execute(() -> {
            AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "places-db")
                    .fallbackToDestructiveMigration()
                    .build();

            // 이전에 저장된 주소 가져오기
            PlaceEntity existingPlace = database.placeDao().getPlaceByCategory(category);

            if (existingPlace != null) {
                // 이전에 저장된 주소가 있을 경우 업데이트
                existingPlace.setAddress(address);
                try {
                    database.placeDao().update(existingPlace);
                    runOnUiThread(() -> editText.setText(address));
                    Log.d("Database", "Address updated: " + address + ", category:" + category);
                } catch (Exception e) {
                    Log.e("Database", "Error updating address: " + address, e);
                }
            } else {
                // 이전에 저장된 주소가 없을 경우 추가
                PlaceEntity newPlace = new PlaceEntity();
                newPlace.setAddress(address);
                newPlace.setCategory(category);
                try {
                    database.placeDao().insert(newPlace);
                    runOnUiThread(() -> editText.setText(address));
                    Log.d("Database", "Address saved: " + address + ", category:" + category);
                } catch (Exception e) {
                    Log.e("Database", "Error saving address: " + address, e);
                }
            }
        });
    }

    // 주소를 데이터베이스에 저장하는 메서드
//    private void saveAddressToDatabase(String address, String category) {
//        AsyncTask.execute(() -> {
//            AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "places-db")
//                    .fallbackToDestructiveMigration()
//                    .build();
//
//            // PlaceEntity 객체 생성 및 데이터 설정
//            PlaceEntity place = new PlaceEntity();
//            place.setAddress(address);
//            place.setCategory(category);
//
//            try {
//                // 데이터베이스에 저장
//                database.placeDao().insert(place);
//
//                // 성공적으로 삭제되었음을 로그에 출력
//                Log.d("Database", "arrival/departure Address saved: " + address + "category"+ category);
//
//            } catch (Exception e) {
//                // 삭제 중 오류가 발생했음을 로그에 출력
//                Log.e("Database", "Error saving arrival/departure address: " + address, e);
//            }
//        });
//    }

    // 카테고리를 반환하는 메서드
    private String getCategoryByRequestCode(int requestCode) {
        switch (requestCode) {
            case 1:
                return "음식점";
            case 2:
                return "카페";
            case 3:
                return "쇼핑";
            case 4:
                return "문화/관광";
            case 5:
                return "숙박";
            case 6:
                return "출발지";
            case 7:
                return "도착지";
            default:
                return "";
        }
    }
}