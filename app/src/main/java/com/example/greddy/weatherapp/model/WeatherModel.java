package com.example.greddy.weatherapp.model;

import java.util.List;

/**
 * Created by greddy on 4/3/2017.
 */

public class WeatherModel {

    private List<Weather> weatherList;
    private City city;
    private Weather weather;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
