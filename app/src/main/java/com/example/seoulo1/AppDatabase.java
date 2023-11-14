package com.example.seoulo1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// AppDatabase.java (데이터베이스 클래스)
// 데이터베이스 생성, 테이블 생성 및 데이터베이스 버전 관리를 담당
// 데이터베이스에 사용할 테이블의 스키마를 정의
// Room Database에 대한 클래스 생성

@Database(entities = {PlaceEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
}
