package com.example.greddy.weatherapp.utils;

import com.example.greddy.weatherapp.R;

/**
 * Created by greddy on 4/3/2017.
 */

public class Utility {

    /*  Weather Conditions*/
    // Clear
    private static final int CLEAR = 800;
    // Clouds
    private static final int FEW_CLOUDS = 801;
    private static final int SCATTERED_CLOUDS = 802;
    private static final int BROKEN_CLOUDS = 803;
    private static final int OVERCAST_CLOUDS = 804;
    // Snow
    private static final int LIGHT_SNOW = 600;
    private static final int SNOW = 601;
    private static final int HEAVY_SNOW = 602;
    // Rain
    private static final int LIGHT_RAIN = 500;
    private static final int MODERATE_RAIN = 501;
    private static final int HEAVY_INTENSITY_RAIN = 502;
    private static final int VERY_HEAVY_RAIN = 503;
    private static final int EXTREME_RAIN = 504;

    /*Weather URL's*/
    public static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    public static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    public static String APPID = "Your Key";
    public static String UNITS = "&units=";
    public static String METRIC_UNITS = "metric";
    public static String IMPERIAL_UNITS = "imperial";
    public static String COUNT = "&cnt=7";
    public static String URL = "http://api.openweathermap.org/data/2.5/weather";
    public static String ZIP = "?zip=";

    public static int getWeatherConditionResourceId(int weatherId) {
        int weatherConditionId;
        switch (weatherId) {
            case CLEAR:
                weatherConditionId = R.drawable.sun;
                break;
            case FEW_CLOUDS:
                weatherConditionId = R.drawable.cloud;
                break;
            case BROKEN_CLOUDS:
                weatherConditionId = R.drawable.cloudy;
                break;
            case OVERCAST_CLOUDS:
            case SCATTERED_CLOUDS: {
                weatherConditionId = R.drawable.clouds;
            }
            break;
            case LIGHT_SNOW:
                weatherConditionId = R.drawable.light_snow;
                break;
            case SNOW:
                weatherConditionId = R.drawable.snowing;
                break;
            case HEAVY_SNOW:
                weatherConditionId = R.drawable.heavy_snow;
                break;
            case LIGHT_RAIN:
                weatherConditionId = R.drawable.light_rain;
                break;
            case MODERATE_RAIN:
            case HEAVY_INTENSITY_RAIN: {
                weatherConditionId = R.drawable.moderate_rain;
            }
            break;
            case VERY_HEAVY_RAIN:
            case EXTREME_RAIN: {
                weatherConditionId = R.drawable.rain;
            }
            break;
            default:
                weatherConditionId = 0;
        }
        return weatherConditionId;
    }
}
