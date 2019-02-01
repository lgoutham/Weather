package com.example.greddy.weatherapp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecastDayModel {

    @SerializedName("dt")
    private long date;
    @SerializedName("temp")
    private Temperature temperature;
    private double pressure;
    private double humidity;
    private List<Weather> weather;
    private float speed;
    private float deg;

    public long getDate() {
        return date;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public Weather getWeather() {
        return weather.get(0);
    }

    public float getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }
}
