package com.example.seoulo1;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalendarActivity extends AppCompatActivity {


    private long daysDiff = 0;
    private String[] dateArray;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        //기간 선택
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        builder.setTitleText("여행 일자를 선택하세요");

        //미리 날짜 선택
        // builder.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()));

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        //확인버튼
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                // 이 메서드는 날짜 선택기 다이얼로그에서 확인 버튼을 클릭했을 때 실행됩니다.

                // SimpleDateFormat 객체를 생성하여 날짜 형식을 지정합니다.
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                // 선택된 날짜를 저장할 두 개의 Date 객체를 생성합니다.
                Date date1 = new Date();
                Date date2 = new Date();

                // 선택된 타임스탬프를 사용하여 Date 객체의 시간을 설정합니다.
                date1.setTime(selection.first);
                date2.setTime(selection.second);

                Calendar currentDate = Calendar.getInstance();

                // 선택된 날짜 사이의 밀리초 차이를 계산합니다.
                long millisecondsDiff = Math.abs(date2.getTime() - date1.getTime());

                // TimeUnit을 사용하여 밀리초를 일 수로 변환하여 일 수 차이를 계산합니다.
                long daysDiff = TimeUnit.DAYS.convert(millisecondsDiff, TimeUnit.MILLISECONDS);


                // daysDiff와 dateArray 값을 전달하여 Path_List_Activity로 이동
                Intent intent = new Intent(CalendarActivity.this, Path_List_Activity.class);
                intent.putExtra("selectDate", daysDiff);
                intent.putExtra("Date", dateArray);
                startActivity(intent);
                finish();
            }

        });

        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

    }

}