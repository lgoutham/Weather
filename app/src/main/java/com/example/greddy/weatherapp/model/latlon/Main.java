package com.example.greddy.weatherapp.model.latlon;

import com.google.gson.annotations.SerializedName;

public class Main {

    private float temp;
    private float pressure;
    private float humidity;
    @SerializedName("temp_min")
    private float tempMin;
    @SerializedName("temp_max")
    private float tempMax;

    public float getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }
}
