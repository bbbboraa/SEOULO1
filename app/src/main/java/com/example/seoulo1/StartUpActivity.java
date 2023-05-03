package com.example.seoulo1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import com.example.seoulo1.R;

public class StartUpActivity extends AppCompatActivity {


    TextView txtSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        txtSplash = findViewById(R.id.txtSplash);

        new Handler().postDelayed(() -> txtSplash.setVisibility(View.VISIBLE), 900);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(StartUpActivity.this, LoginActivity.class);
            startActivity(intent);
        },6000);
    }



}