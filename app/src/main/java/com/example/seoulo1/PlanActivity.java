package com.example.seoulo1;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    // 필요한 변수들 선언
    private ListView scheduleListView;
    private EditText placeEditText;
    private EditText expenseEditText;
    private EditText memoEditText;
    private Button saveButton;
    private ArrayAdapter<String> adapter;
    private HashMap<String, List<HashMap<String, String>>> scheduleMap;
    private CalendarView calendarView;
    private TextView dateTextView;
    private String selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃 파일의 UI 요소들과 연결
        scheduleListView = findViewById(R.id.scheduleListView);
        placeEditText = findViewById(R.id.placeEditText);
        expenseEditText = findViewById(R.id.expenseEditText);
        memoEditText = findViewById(R.id.memoEditText);
        saveButton = findViewById(R.id.saveButton);
        calendarView = findViewById(R.id.calendarView);
        dateTextView = findViewById(R.id.dateTextView);

        // 스케줄 목록을 저장할 맵 초기화
        scheduleMap = new HashMap<>();

        // 스케줄 목록을 저장할 어댑터 초기화
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        scheduleListView.setAdapter(adapter);

        // 캘린더에서 날짜 선택 이벤트 처리
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 선택한 날짜 표시
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                dateTextView.setText(selectedDate);

                // 선택한 날짜에 해당하는 일정 목록 가져오기
                List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
                if (scheduleList != null) {
                    updateListView(scheduleList);
                } else {
                    // 선택한 날짜에 일정이 없을 경우 목록 비우기
                    updateListView(new ArrayList<>());
                }
            }
        });

        // "저장" 버튼 클릭 이벤트 처리
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = placeEditText.getText().toString();
                String expense = expenseEditText.getText().toString();
                String memo = memoEditText.getText().toString();

                if (!place.isEmpty() || !expense.isEmpty() || !memo.isEmpty()) {
                    if (selectedDate != null) {
                        // 선택한 날짜에 해당하는 일정 목록 가져오기
                        List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
                        if (scheduleList == null) {
                            // 선택한 날짜에 일정 목록이 없을 경우 새로 생성
                            scheduleList = new ArrayList<>();
                            scheduleMap.put(selectedDate, scheduleList);
                        }

                        // 새로운 일정 생성
                        HashMap<String, String> schedule = new HashMap<>();
                        schedule.put("place", place);
                        schedule.put("expense", expense);
                        schedule.put("memo", memo);
                        scheduleList.add(schedule);

                        // 일정 목록 업데이트
                        updateListView(scheduleList);
                        clearFields();
                        Toast.makeText(PlanActivity.this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PlanActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 스케줄 목록을 클릭했을 때 이벤트 처리
        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭된 스케줄의 정보를 가져와서 필드에 표시
                List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
                if (scheduleList != null && position < scheduleList.size()) {
                    HashMap<String, String> schedule = scheduleList.get(position);
                    String place = schedule.get("place");
                    String expense = schedule.get("expense");
                    String memo = schedule.get("memo");

                    placeEditText.setText(place);
                    expenseEditText.setText(expense);
                    memoEditText.setText(memo);

                    // 클릭한 스케줄을 삭제
                    scheduleList.remove(position);
                    updateListView(scheduleList);
                }
            }
        });

        // 스케줄 목록을 롱클릭했을 때 이벤트 처리
        scheduleListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 롱클릭된 스케줄을 삭제하고 목록을 업데이트
                List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
                if (scheduleList != null && position < scheduleList.size()) {
                    scheduleList.remove(position);
                    updateListView(scheduleList);
                    clearFields();
                }
                return true;
            }
        });
    }

    // 스케줄 목록을 업데이트하는 메서드
    private void updateListView(List<HashMap<String, String>> scheduleList) {
        List<String> scheduleTextList = new ArrayList<>();
        for (HashMap<String, String> schedule : scheduleList) {
            String place = schedule.get("place");
            String expense = schedule.get("expense");
            String memo = schedule.get("memo");
            String scheduleText = "장소: " + place + "\n쓴 금액: " + expense + "\n일정: " + memo;
            scheduleTextList.add(scheduleText);
        }
        adapter.clear();
        adapter.addAll(scheduleTextList);
        adapter.notifyDataSetChanged();
    }

    // 입력 필드를 지우는 메서드
    private void clearFields() {
        placeEditText.getText().clear();
        expenseEditText.getText().clear();
        memoEditText.getText().clear();
    }
}