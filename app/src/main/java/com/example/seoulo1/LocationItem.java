package com.example.seoulo1;

public class LocationItem {
    String category_name;
    String name;
    int distance;
    String vicinity;

    public LocationItem(String name, String category_name, String vicinity, int distance){
        this.name=name;
        this.category_name=category_name;
        this.vicinity=vicinity;
        this.distance=distance;
    }
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
