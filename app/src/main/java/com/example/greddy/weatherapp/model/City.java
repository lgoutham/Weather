package com.example.greddy.weatherapp.model;

import java.util.List;

/**
 * Created by greddy on 4/3/2017.
 */

public class City {

    private long cityId;
    private String cityName;
    private String country;
    private long sunrise;
    private long sunset;
    private double lat;
    private double lon;
    private List<String> date;

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public List<String> getDateList() {
        return date;
    }

    public void setDateList(List<String> date) {
        this.date = date;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
