package com.example.seoulo1;

public class LocationItem {
    String category_name;
    String name, placeId, pNum;
    int distance;
    String vicinity;
    String open_now, rating;
    public LocationItem(String placeId, String name, String category_name, String vicinity, int distance, String pNum, String open_now, String rating){
        this.placeId=placeId;
        this.name=name;
        this.category_name=category_name;
        this.vicinity=vicinity;
        this.distance=distance;
        this.pNum= pNum;
        this.open_now= open_now;
        this.rating= rating;
    }
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

}
