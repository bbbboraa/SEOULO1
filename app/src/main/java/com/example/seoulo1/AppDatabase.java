package com.example.seoulo1;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// AppDatabase.java (데이터베이스 클래스)
// 데이터베이스 생성, 테이블 생성 및 데이터베이스 버전 관리를 담당
// 데이터베이스에 사용할 테이블의 스키마를 정의
// Room Database에 대한 클래스 생성


@Database(entities = {PlaceEntity.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();

    // 마이그레이션을 실패하면 데이터베이스를 파괴하고 재구성
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 여기에 이전 버전에서 새로운 버전으로의 마이그레이션 로직을 작성
            // 주의: 이것은 개발 중에만 사용해야 하며, 프로덕션에 배포하기 전에 주의해야 합니다.
        }
    };

}
