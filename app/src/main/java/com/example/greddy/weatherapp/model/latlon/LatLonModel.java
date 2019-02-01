package com.example.greddy.weatherapp.model.latlon;

import com.example.greddy.weatherapp.model.forecast.Weather;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatLonModel {

    private List<Weather> weather;
    private Main main;
    @SerializedName("dt")
    private long date;
    private String name;

    public Weather getWeather() {
        return weather.get(0);
    }

    public Main getMain() {
        return main;
    }

    public long getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
