package com.example.mongodb;

public class Result {

    String placeName;
    String description;
    int position;

    public Result(){}

    public Result(String placeName, String description, int position) {
        this.placeName = placeName;
        this.description = description;
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public String getPlaceName() {
        return placeName;
    }

    public int getPosition() {
        return position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
