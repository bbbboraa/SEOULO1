package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartUpActivity extends AppCompatActivity {


    TextView txtSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        txtSplash = findViewById(R.id.txtSplash);

        new Handler(Looper.myLooper()).postDelayed(() -> txtSplash.setVisibility(View.VISIBLE), 900);

        new Handler(Looper.myLooper()).postDelayed(() -> {
            Intent intent = new Intent(StartUpActivity.this, LoginActivity.class);
            startActivity(intent);
        },6000);
    }



}