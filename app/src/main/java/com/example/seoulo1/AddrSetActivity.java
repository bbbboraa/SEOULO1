package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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

                String StartAddress = DepartureAddr.getText().toString();

                // 주소를 Intent에 담아 현재 액티비티로 결과를 반환
                Intent resultIntent = new Intent();
                resultIntent.putExtra("address", StartAddress);
                setResult(RESULT_OK, resultIntent);
                finish();

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