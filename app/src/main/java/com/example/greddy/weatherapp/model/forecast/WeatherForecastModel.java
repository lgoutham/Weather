package com.example.greddy.weatherapp.model.forecast;

import com.example.greddy.weatherapp.model.forecast.City;

import java.util.List;

public class WeatherForecastModel {

    private City city;
    private List<WeatherForecastDayModel> list;

    public City getCity() {
        return city;
    }

    public List<WeatherForecastDayModel> getList() {
        return list;
    }
}
