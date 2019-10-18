package com.swufe.bluebook.PersonalCenter.Photo.CityList;

public class MyPlace {
    String cityName,date,picUrl;

    public MyPlace(String cityName, String date, String picUrl) {
        this.cityName = cityName;
        this.date = date;
        this.picUrl = picUrl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
