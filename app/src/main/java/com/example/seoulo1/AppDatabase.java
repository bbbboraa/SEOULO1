package com.example.seoulo1;


import androidx.room.Database;
import androidx.room.RoomDatabase;

// AppDatabase.java (데이터베이스 헬퍼 클래스)
// 데이터베이스 생성, 테이블 생성 및 데이터베이스 버전 관리를 담당
// 데이터베이스에 사용할 테이블의 스키마를 정의
// 스키마에는 주소 데이터를 저장하는 데 필요한 열(주소, 위도, 경도)을 정의

@Database(entities = {Address.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AddressDao addressDao();
}