package com.example.mongodb;

public class Result {

    String placeName;
    String contractType;
    String amount;
    String description;
    int position;

    public Result(){}

    public Result(String description,String placeName, String contractType, String amount,int position) {
        this.description = description;
        this.placeName = placeName;
        this.contractType = contractType;
        this.amount = amount;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getContractType() {
        return contractType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
