package com.example.abhijith.weatheronmove.Model;

/**
 * Created by Abhijith on 07-Sep-16.
 */
public class WeatherData {
    private String temp; //temp
    private String humidity; //humidity
    private String cloudStatus; //cloud description like broken clouds, rain etc
    private String imageName; //image id
    private String name; //name of the place
    private String day;
    private String date;
    private String countryName;

    public WeatherData(String temp, String humidity, String cloudStatus, String imageName, String name,String countryName, String day, String date) {
        this.temp = temp;
        this.humidity = humidity;
        this.cloudStatus = cloudStatus;
        this.imageName = imageName;
        this.name = name;
        this.countryName = countryName;
        this.day = day;
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCloudStatus() {
        return cloudStatus;
    }

    public void setCloudStatus(String cloudStatus) {
        this.cloudStatus = cloudStatus;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
