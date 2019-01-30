package com.example.greddy.weatherapp.db;

import android.provider.BaseColumns;

/**
 * Created by greddy on 4/5/2017.
 */

public class SunshineTables {

    public static final class City implements BaseColumns {

        // Table name
        public static final String TABLE_NAME_CITY = "city";
        // Table columns
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_CITY_COUNTRY = "country";
        public static final String COLUMN_CITY_SUNRISE = "sunrise";
        public static final String COLUMN_CITY_SUNSET = "sunset";
        public static final String COLUMN_CITY_LAT = "lat";
        public static final String COLUMN_CITY_LON = "lon";
        public static final String COLUMN_CITY_DATE = "date";

    }

    public static final class Weather implements BaseColumns {

        // Table name
        public static final String TABLE_NAME_WEATHER = "weather";
        // Table columns
        public static final String COLUMN_WEATHER_CITY_ID = "city_id";
        public static final String COLUMN_TEMPERATURE = "temp";
        public static final String COLUMN_TEMP_MAX = "temp_max";
        public static final String COLUMN_TEMP_MIN = "temp_min";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_SNOW = "snow";
    }
}
