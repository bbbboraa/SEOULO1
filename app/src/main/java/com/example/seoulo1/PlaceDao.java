package com.example.seoulo1;

import androidx.room.Dao;
import androidx.room.Delete;
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

    // 프래그먼트의 위치(position)을 기반으로 데이터 조회
    @Query("SELECT * FROM places WHERE position = :position")
    List<PlaceEntity> getPlacesByPosition(int position);

    // 추가: 주소와 프래그먼트 위치(position)을 기반으로 데이터 조회
    @Query("SELECT * FROM places WHERE address = :address AND position = :position")
    PlaceEntity getPlaceByAddressAndPosition(String address, int position);

    @Delete
    void delete(PlaceEntity place); // 추가된 delete 메서드

    @Query("DELETE FROM places WHERE address = :address")
    void deleteAddress(String address);

    // 추가: 주소와 프래그먼트 위치(position)을 기반으로 데이터 삭제
    @Query("DELETE FROM places WHERE address = :address AND position = :position")
    void deleteByAddressAndPosition(String address, int position);


    // 추가: 해당 위치(position)의 출발지와 도착지 데이터를 삭제
    @Query("DELETE FROM places WHERE position = :position AND (category = '출발지' OR category = '도착지')")
    void deleteDepartureArrivalByPosition(int position);

}
