package com.example.greddy.weatherapp.utils;

import com.example.greddy.weatherapp.model.forecast.WeatherForecastModel;
import com.example.greddy.weatherapp.model.latlon.LatLonModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {

    public static WeatherForecastModel GetWeatherForecastModel(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(response, WeatherForecastModel.class);
    }

    public static LatLonModel GetLatLonModel(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(response, LatLonModel.class);
    }
}
