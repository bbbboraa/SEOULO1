package com.example.seoulo1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    // 필요한 변수들 선언
    private ListView scheduleListView; // 스케줄 목록을 표시하는 ListView
    private EditText placeEditText; // 장소 입력 필드
    private EditText expenseEditText; // 비용 입력 필드
    private EditText memoEditText; // 메모 입력 필드
    private Button saveButton; // 일정 저장 버튼
    private Button viewButton; // 작성된 일정 보기 버튼
    private ArrayAdapter<String> adapter; // 스케줄 목록을 표시하기 위한 어댑터
    private HashMap<String, List<HashMap<String, String>>> scheduleMap; // 날짜별 스케줄을 저장하는 맵
    private CalendarView calendarView; // 날짜를 선택하는 캘린더 뷰
    private TextView dateTextView; // 선택한 날짜를 표시하는 텍스트 뷰
    private String selectedDate; // 사용자가 선택한 날짜를 저장하는 변수

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // 레이아웃 파일의 UI 요소들과 연결
        scheduleListView = findViewById(R.id.scheduleListView); // ListView 초기화
        placeEditText = findViewById(R.id.placeEditText); // 장소 입력 필드 초기화
        expenseEditText = findViewById(R.id.expenseEditText); // 비용 입력 필드 초기화
        memoEditText = findViewById(R.id.memoEditText); // 메모 입력 필드 초기화
        saveButton = findViewById(R.id.saveButton); // 일정 저장 버튼 초기화
        viewButton = findViewById(R.id.viewButton); // 작성된 일정 보기 버튼 초기화
        calendarView = findViewById(R.id.calendarView); // 캘린더 뷰 초기화
        dateTextView = findViewById(R.id.dateTextView); // 선택한 날짜를 표시하는 텍스트 뷰 초기화

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

                        // 일정을 데이터베이스에 저장
                        saveToDatabase(place, expense, memo);

                        clearFields();
                        Toast.makeText(PlanActivity.this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PlanActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // "작성된 일정 보기" 버튼 클릭 이벤트 처리
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MemoActivity로 전환
                Intent intent = new Intent(PlanActivity.this, MemoActivity.class);

                // 일정 목록을 Intent에 추가
                List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
                if (scheduleList != null) {
                    intent.putExtra("scheduleList", new ArrayList<>(scheduleList));
                }

                startActivity(intent);
            }
        });


    }
    // SQLite 데이터베이스에 일정 저장
    private void saveToDatabase(String place, String expense, String memo) {
        // 데이터베이스 도우미 초기화
        ScheduleDbHelper dbHelper = new ScheduleDbHelper(this);

        // 쓰기 가능한 데이터베이스 가져오기
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 일정 데이터를 저장할 ContentValues 객체 생성
        ContentValues values = new ContentValues();
        values.put("place", place);
        values.put("expense", expense);
        values.put("memo", memo);

        // 데이터를 'schedule' 테이블에 삽입
        long newRowId = db.insert("schedule", null, values);

        // Log for debugging
        Log.d("Database", "Inserted row with ID: " + newRowId);

        // 데이터베이스 연결 닫기
        dbHelper.close();
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
