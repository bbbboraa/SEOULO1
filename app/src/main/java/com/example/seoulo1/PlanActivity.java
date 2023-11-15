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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    // 필요한 변수들 선언
    private List<String> scheduleTextList; // RecyclerView 어댑터에 사용할 스케줄 텍스트 목록

    private RecyclerView scheduleRecyclerView; // 스케줄 목록을 표시하는 RecyclerView
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

    private String selectedPlaceCategory; //선택된 장소 카테고리 저장 변수
    private Button selectedCategoryButton; // 현재 선택된 카테고리를 나타내는 버튼

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Button categoryRestaurant = findViewById(R.id.categoryRestaurant);
        Button categoryCafe = findViewById(R.id.categoryCafe);
        Button categoryCultureTourism = findViewById(R.id.categoryCultureTourism);
        Button categoryShopping = findViewById(R.id.categoryShopping);
        Button categoryOther = findViewById(R.id.categoryOther);

        // 레이아웃 파일의 UI 요소들과 연결
        // RecyclerView 초기화
        scheduleRecyclerView = findViewById(R.id.scheduleRecyclerView);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));        placeEditText = findViewById(R.id.placeEditText); // 장소 입력 필드 초기화
        expenseEditText = findViewById(R.id.expenseEditText); // 비용 입력 필드 초기화
        memoEditText = findViewById(R.id.memoEditText); // 메모 입력 필드 초기화
        saveButton = findViewById(R.id.saveButton); // 일정 저장 버튼 초기화
        viewButton = findViewById(R.id.viewButton); // 작성된 일정 보기 버튼 초기화
        calendarView = findViewById(R.id.calendarView); // 캘린더 뷰 초기화
        dateTextView = findViewById(R.id.dateTextView); // 선택한 날짜를 표시하는 텍스트 뷰 초기화

        // 스케줄 목록을 저장할 맵 초기화
        scheduleMap = new HashMap<>();
        scheduleTextList = new ArrayList<>();

        // RecyclerView 어댑터 초기화
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(scheduleTextList);
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        // 각 카테고리 버튼에 대한 onClickListener 설정
        categoryRestaurant.setOnClickListener(v -> {
            updateSelectedCategoryButton(categoryRestaurant);
            selectedPlaceCategory = "음식점";
        });

        categoryCafe.setOnClickListener(v -> {
            updateSelectedCategoryButton(categoryCafe);
            selectedPlaceCategory = "카페";
        });

        categoryCultureTourism.setOnClickListener(v -> {
            updateSelectedCategoryButton(categoryCultureTourism);
            selectedPlaceCategory = "문화/관광";
        });

        categoryShopping.setOnClickListener(v -> {
            updateSelectedCategoryButton(categoryShopping);
            selectedPlaceCategory = "쇼핑";
        });

        categoryOther.setOnClickListener(v -> {
            updateSelectedCategoryButton(categoryOther);
            selectedPlaceCategory = "기타";
        });


        // 캘린더에서 날짜 선택 이벤트 처리
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // 선택한 날짜 표시
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            dateTextView.setText(selectedDate);

            // 선택한 날짜에 해당하는 일정 목록 가져오기
            List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
            if (scheduleList != null) {
                updateRecyclerView(scheduleList);
            } else {
                // 선택한 날짜에 일정이 없을 경우 목록 비우기
                updateRecyclerView(new ArrayList<>());
            }
        });

        // "저장" 버튼 클릭 이벤트 처리
        saveButton.setOnClickListener(v -> {
            String place = placeEditText.getText().toString();
            String expenseString = expenseEditText.getText().toString();
            String memo = memoEditText.getText().toString();

            if (!place.isEmpty() || !expenseString.isEmpty() || !memo.isEmpty()) {
                if (selectedDate != null) {
                    if (selectedPlaceCategory != null) { // 카테고리가 선택되었는지 확인
                        // 숫자 여부 확인
                        if (isNumeric(expenseString)) {
                            // 숫자로 변환
                            double expense = Double.parseDouble(expenseString);

                            // 선택된 날짜에 해당하는 일정 목록 가져오기
                            List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
                            if (scheduleList == null) {
                                scheduleList = new ArrayList<>();
                                scheduleMap.put(selectedDate, scheduleList);
                            }

                            // 새로운 일정 만들기
                            HashMap<String, String> schedule = new HashMap<>();
                            schedule.put("place", place);
                            schedule.put("category", selectedPlaceCategory); // 선택한 카테고리 저장
                            schedule.put("expense", String.valueOf(expense)); // 숫자로 변환한 값을 저장
                            schedule.put("memo", memo);
                            scheduleList.add(schedule);

                            // 일정 목록 업데이트
                            updateRecyclerView(scheduleList);

                            // 데이터베이스에 일정 저장
                            saveToDatabase(place, selectedPlaceCategory, String.valueOf(expense), memo);

                            clearFields();
                            Toast.makeText(PlanActivity.this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            // 숫자가 아닌 경우 사용자에게 메시지 표시
                            Toast.makeText(PlanActivity.this, "사용 금액은 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PlanActivity.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PlanActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // "작성된 일정 보기" 버튼 클릭 이벤트 처리
        viewButton.setOnClickListener(v -> {
            // MemoActivity로 전환
            Intent intent = new Intent(PlanActivity.this, MemoActivity.class);

            // 일정 목록을 Intent에 추가
            List<HashMap<String, String>> scheduleList = scheduleMap.get(selectedDate);
            if (scheduleList != null) {
                intent.putExtra("scheduleList", new ArrayList<>(scheduleList));
            }

            startActivity(intent);
        });


    }

    // 카테고리 버튼 클릭 이벤트 처리
    public void onCategoryButtonClick(View view) {
        Button clickedButton = (Button) view;
        updateSelectedCategoryButton(clickedButton);

        // 뷰 ID를 사용하여 어떤 카테고리 버튼이 클릭되었는지 판별
        switch (view.getId()) {
            case R.id.categoryRestaurant:
                selectedPlaceCategory = "음식점";
                break;
            case R.id.categoryCafe:
                selectedPlaceCategory = "카페";
                break;
            case R.id.categoryCultureTourism:
                selectedPlaceCategory = "문화/관광";
                break;
            case R.id.categoryShopping:
                selectedPlaceCategory = "쇼핑";
                break;
            case R.id.categoryOther:
                selectedPlaceCategory = "기타";
                break;
        }
    }

    // 숫자 여부를 확인하는 메서드
    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    // 선택된 카테고리 버튼을 업데이트하는 메서드
    private void updateSelectedCategoryButton(Button clickedButton) {
        if (selectedCategoryButton != null) {
            selectedCategoryButton.setSelected(false); // 이전에 선택된 버튼을 선택 해제
        }
        clickedButton.setSelected(true); // 새로 클릭된 버튼을 선택으로 표시
        selectedCategoryButton = clickedButton; // 현재 선택된 버튼으로 업데이트
    }

    // SQLite 데이터베이스에 일정 저장
    private void saveToDatabase(String place, String category, String expense, String memo) {
        // 데이터베이스 도우미 초기화
        ScheduleDbHelper dbHelper = new ScheduleDbHelper(this);

        // 쓰기 가능한 데이터베이스 가져오기
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 일정 데이터를 저장할 ContentValues 객체 생성
        ContentValues values = new ContentValues();
        values.put("place", place);
        values.put("category", category); // Update the column name to 'category'
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
    private void updateRecyclerView(List<HashMap<String, String>> scheduleList) {
        scheduleTextList.clear(); // 기존 데이터 지우기

        // 일정이 비어있지 않은 경우에만 가장 최근 일정 표시
        if (!scheduleList.isEmpty()) {
            HashMap<String, String> mostRecentSchedule = scheduleList.get(scheduleList.size() - 1);
            String place = mostRecentSchedule.get("place");
            String category = mostRecentSchedule.get("category");
            String expense = mostRecentSchedule.get("expense");
            String memo = mostRecentSchedule.get("memo");
            String scheduleText = "최근 작성된 일정 입니다." + "\n\n장소: " + place + "\n(카테고리: " + category + ")\n사용 금액: " + expense + "\n일정: " + memo;
            scheduleTextList.add(scheduleText);
        }

        // 어댑터를 새로 생성하고 RecyclerView에 설정
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(scheduleTextList);
        scheduleRecyclerView.setAdapter(scheduleAdapter);
        // 어댑터에 데이터 세트가 변경되었음을 알림
        if (scheduleAdapter != null) {
            scheduleAdapter.notifyDataSetChanged();
        }
    }




    // 입력 필드를 지우는 메서드
    private void clearFields() {
        placeEditText.getText().clear();
        expenseEditText.getText().clear();
        memoEditText.getText().clear();
        resetSelectedCategoryButton(); // Reset selected category
    }

    private void resetSelectedCategoryButton() {
        if (selectedCategoryButton != null) {
            selectedCategoryButton.setSelected(false);
            selectedCategoryButton = null;
        }
    }
}