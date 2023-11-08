package com.example.seoulo1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// AddressDao.java (DAO 인터페이스)
// DAO는 데이터베이스에서 주소 데이터를 조작하는 데 사용
// 데이터베이스에 데이터를 추가, 조회, 업데이트 및 삭제할 수 있는 메서드를 DAO에 추가합니다.
@Dao
public interface AddressDao {
    @Insert
    void insert(Address address);

    @Query("SELECT * FROM addresses")
    List<Address> getAllAddresses();

    // 필요한 다른 메서드 추가
}