package com.example.seoulo1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// AddressDao.java (DAO 인터페이스)
// DAO는 데이터베이스에서 데이터를 조작하는 데 사용
// 데이터베이스와 상호 작용하는 메소드를 정의

@Dao
public interface PlaceDao {

    @Insert
    void insert(PlaceEntity place);

    //@Query("SELECT * FROM places")
    //List<PlaceEntity> getAllPlaces();

    @Query("SELECT * FROM places WHERE category = :category")
    List<PlaceEntity> getPlacesByCategory(String category);

    //Query("SELECT * FROM places WHERE address = :address")
    //List<PlaceEntity> getPlacesByAddress(String address);

}
