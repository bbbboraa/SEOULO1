package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddrResearchActivity extends AppCompatActivity {

    private EditText mEtAddress;
    Button btn_addr_save;

    private long daysDiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr_research);

        btn_addr_save = findViewById(R.id.btn_addr_save);
        mEtAddress = findViewById(R.id.et_address);

        // block touch
        mEtAddress.setFocusable(false);
        mEtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(AddrResearchActivity.this, SearchActivity.class);
                getSearchResult.launch(intent);

            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            daysDiff = intent.getLongExtra("selectDate", 0);
        }


        btn_addr_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedAddress = mEtAddress.getText().toString();

                Intent intent = new Intent(AddrResearchActivity.this, Path_List_Activity.class);
                intent.putExtra("address", selectedAddress);
                intent.putExtra("selectDate", getIntent().getLongExtra("selectDate", 0));
                setResult(RESULT_OK, intent);
                finish();
            }

        });

    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Search Activity 로부터의 결과 값이 이곳으로 전달 된다 (setResult에 의해)
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String data = result.getData().getStringExtra("data");
                        mEtAddress.setText(data);
                    }
                }
            }
    );
}