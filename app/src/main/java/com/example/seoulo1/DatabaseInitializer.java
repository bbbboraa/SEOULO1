package com.example.seoulo1;

import android.content.Context;

import androidx.room.Room;


// 앱이 시작될 때 데이터베이스를 초기화하고 인스턴스를 생성하는 클래스 생성
public class DatabaseInitializer {

    private static AppDatabase database;

    public static AppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .build();
        }
        return database;
    }
}
