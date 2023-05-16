package com.example.seoulo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LocalHotplaceActivity extends AppCompatActivity {
    private TextView selectedlocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_hotplace);

        selectedlocal = (TextView) findViewById(R.id.selectedlocal);
        Intent intent = getIntent();
        String selected_local = intent.getStringExtra("local");

        selectedlocal.setText(selected_local);
    }
}