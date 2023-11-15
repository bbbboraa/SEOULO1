package com.example.seoulo1;

import java.io.Serializable;

public class LocationItem implements Serializable {
    String category_name, name, placeId, pNum;
    int distance;
    String vicinity, open_now, rating;
    double lat, lng;
    boolean status;
    @Override
    public String toString() {
        return "LocationItem{" +
                "placeId='" + placeId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + category_name + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", distance=" + distance +
                ", phoneNumber='" + pNum + '\'' +
                ", openNow='" + open_now + '\'' +
                ", rating='" + rating + '\'' +", lat='" + lat + '\'' +", lng='" + lng + '\''+",status='" + status+ '}';
    }
    public LocationItem(String placeId, String name, String category_name, String vicinity, int distance, String pNum, String open_now, String rating, double lat, double lng, boolean status){
        this.placeId=placeId;
        this.name=name;
        this.category_name=category_name;
        this.vicinity=vicinity;
        this.distance=distance;
        this.pNum= pNum;
        this.open_now= open_now;
        this.rating= rating;
        this.lat= lat;
        this.lng= lng;
        this.status=status;
    }
    public Double getLat() {return lat;}
    public Double getLng() {return lng;}
    public Boolean getStatus() {return status;}
    public void setStatus(boolean status) {this.status = status;}

    public String getOpen_now() {return open_now;}
    public String getRating() {return rating;}

    public String getPlaceId() {return placeId;}
    public String getpNum() {return pNum;}

    public String getLName() {
        return name;
    }
    public void setLName(String name) {
        this.name = name;
    }

    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getVicinity() {
        return vicinity;
    }
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    public int getDistance(){
        return distance;
    }
    public void setDistance(int distance){
        this.distance=distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LocationItem other = (LocationItem) obj;
        return name.equals(other.name); // 여기에서 name은 LocationItem 객체를 고유하게 식별하는 어떤 값인지에 따라 달라집니다.
    }

    }
