package com.example.seoulo1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class PathListPagerActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private PathListPagerAdapter pathListPagerAdapter;
    private long daysDiff;

    private int position; // 추가: 프래그먼트의 위치 정보

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_list_pager);

        // CalendarActivity에서 전달한 'selectDate' extra 값을 가져옴
        daysDiff = getIntent().getLongExtra("selectDate", 0); // 'selectDate' 값을 가져와 'daysDiff' 변수에 저장

        position = getIntent().getIntExtra("position", 0); // 추가: 'position' 값을 가져옴

        viewPager = findViewById(R.id.viewPager);

        pathListPagerAdapter = new PathListPagerAdapter(this, daysDiff+1);
        viewPager.setAdapter(pathListPagerAdapter);
    }
}