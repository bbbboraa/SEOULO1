package com.example.seoulo1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "schedule.db"; // 데이터베이스 이름
    private static final int DATABASE_VERSION = 1; // 데이터베이스 버전

    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 스케줄 데이터를 저장할 테이블 생성
        String createTableQuery = "CREATE TABLE schedule " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "place TEXT, " +
                "expense TEXT, " +
                "memo TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드 처리 (필요 시)
        // 현재는 빈 메서드이지만, 데이터베이스 스키마가 변경되면 여기에 업그레이드 로직을 추가해야 합니다.
    }
}