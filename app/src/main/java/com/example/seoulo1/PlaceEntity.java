package com.example.seoulo1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// PlaceEntity.java (엔터티(테이블) 클래스 생성)
// 주소 정보를 저장할 엔터티 클래스 생성
// Room Database의 테이블 역할

@Entity(tableName = "places")
public class PlaceEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String address;

    private String category;  // 카테고리 정보 추가

    private int position;     // 프래그먼트의 위치 정보 추가

    // 생성자, getter, setter 등 필요한 메소드 구현

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
