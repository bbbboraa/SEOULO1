package com.example.seoulo1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Address.java (데이터 모델 클래스)
// 주소 정보를 나타내는 클래스 (주소, 위도, 경도 포함)
@Entity(tableName = "addresses")
public class Address {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String address;
    private double latitude; // 위도
    private double longitude; // 경도

    // 생성자, getter, setter 등의 메서드 작성

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
