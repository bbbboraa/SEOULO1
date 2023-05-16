package com.example.seoulo1;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarActivity extends AppCompatActivity {

    Calendar calendar;
    EditText calendar_day;

    Button btn_AddrReserch_move;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        btn_AddrReserch_move = findViewById(R.id.btn_AddrReserch_move);
        calendar_day = findViewById(R.id.calendar_day);
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        btn_AddrReserch_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, AddrResearchActivity.class);
                startActivity(intent); // 도로명 주소 검색 액티비티 이동
            }
        });


        //기간 선택
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        builder.setTitleText("여행 일정을 선택하세요");

        //미리 날짜 선택
        builder.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()));

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        //확인버튼
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date1 = new Date();
                Date date2 = new Date();

                date1.setTime(selection.first);
                date2.setTime(selection.second);

                String dateString1 = simpleDateFormat.format(date1);
                String dateString2 = simpleDateFormat.format(date2);


                calendar_day.setText(dateString1 + "~" + dateString2);

            }
        });
    }
}