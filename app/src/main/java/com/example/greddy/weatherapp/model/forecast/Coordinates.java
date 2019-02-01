package com.example.greddy.weatherapp.model.forecast;

import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("lon")
    private double longitude;
    @SerializedName("lat")
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
