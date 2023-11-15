package com.example.seoulo1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

public class MemoActivity extends AppCompatActivity {
    private ListView scheduleListView; // 스케줄 목록을 표시하는 ListView
    private EditText placeEditText; // 장소 입력 필드
    private EditText expenseEditText; // 비용 입력 필드
    private EditText memoEditText; // 메모 입력 필드
    private ArrayAdapter<String> adapter; // 스케줄 목록을 표시하기 위한 어댑터
    private ArrayAdapter<String> memoAdapter;
    private List<HashMap<String, String>> scheduleList;
    private HashMap<String, List<HashMap<String, String>>> scheduleMap; // 날짜별 스케줄을 저장하는 맵
    private String selectedDate; // 사용자가 선택한 날짜를 저장하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        scheduleListView = findViewById(R.id.memoListView);
        memoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        scheduleListView.setAdapter(memoAdapter);


        // Intent로 전달된 일정 목록을 가져오기
        scheduleList = (List<HashMap<String, String>>) getIntent().getSerializableExtra("scheduleList");

        // 가져온 일정 목록을 화면에 표시
        if (scheduleList != null) {
            for (HashMap<String, String> schedule : scheduleList) {
                String place = schedule.get("place");
                String expense = schedule.get("expense");
                String memo = schedule.get("memo");
                String scheduleText = "장소: " + place + "\n쓴 금액: " + expense + "\n일정: " + memo;
                memoAdapter.add(scheduleText);
            }
        }

        // 스케줄 목록을 길게 눌렀을 때 삭제 이벤트 처리
        scheduleListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 길게 눌린 스케줄 삭제
                deleteSchedule(position);
                return true; // 이벤트 소비됨
            }
        });
    }

    // 스케줄 삭제 메서드
    private void deleteSchedule(int position) {
        if (scheduleList != null && position < scheduleList.size()) {
            // 삭제할 스케줄 데이터 가져오기
            HashMap<String, String> deletedSchedule = scheduleList.get(position);

            // SQLite 데이터베이스에서 스케줄 삭제
            deleteFromDatabase(deletedSchedule);

            // 목록에서 스케줄 제거
            scheduleList.remove(position);
            updateListView();
            Toast.makeText(MemoActivity.this, "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 데이터베이스에서 스케줄 삭제 메서드
    private void deleteFromDatabase(HashMap<String, String> schedule) {
        // 데이터베이스 도우미 초기화
        ScheduleDbHelper dbHelper = new ScheduleDbHelper(this);

        // 쓰기 가능한 데이터베이스 가져오기
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 선택 및 선택 인수 정의
        String selection = "place = ? AND expense = ? AND memo = ?";
        String[] selectionArgs = {schedule.get("place"), schedule.get("expense"), schedule.get("memo")};

        // 'schedule' 테이블에서 스케줄 삭제
        db.delete("schedule", selection, selectionArgs);

        // 데이터베이스 연결 닫기
        dbHelper.close();
    }


    // 스케줄 목록을 업데이트하는 메서드
    private void updateListView() {
        memoAdapter.clear();
        for (HashMap<String, String> schedule : scheduleList) {
            String place = schedule.get("place");
            String expense = schedule.get("expense");
            String memo = schedule.get("memo");
            String scheduleText = "장소: " + place + "\n쓴 금액: " + expense + "\n일정: " + memo;
            memoAdapter.add(scheduleText);
        }
        memoAdapter.notifyDataSetChanged();
    }
}