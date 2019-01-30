package com.example.greddy.weatherapp.parser;

import com.example.greddy.weatherapp.model.City;
import com.example.greddy.weatherapp.model.Weather;
import com.example.greddy.weatherapp.model.WeatherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greddy on 4/4/2017.
 */

public class WeatherLatLonParser {

    public static WeatherModel parseLatLonFeed(String content) {
        try {
            WeatherModel weatherModel = new WeatherModel();
            Weather weather = new Weather();
            City city = new City();
            JSONObject jsonObject = new JSONObject(content);
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            List<String> descriptionList = new ArrayList<>();
            for (int j = 0; j < weatherArray.length(); j++) {
                JSONObject weatherObject = weatherArray.getJSONObject(j);
                descriptionList.add(j, weatherObject.optString("description"));
            }
            weather.setDescription(descriptionList);

            JSONObject main = jsonObject.optJSONObject("main");
            weather.setTemp(main.optDouble("temp"));
            weather.setTemp_min(main.optDouble("temp_min"));
            weather.setTemp_max(main.optDouble("temp_max"));
            weather.setPressure(main.optDouble("pressure"));
            weather.setHumidity(main.optDouble("humidity"));

            JSONObject wind = jsonObject.getJSONObject("wind");
            weather.setWindSpeed(wind.optDouble("speed"));

            List<String> dt = new ArrayList<>();
            dt.add(0,jsonObject.optString("dt"));
            city.setDateList(dt);

            city.setCityId(jsonObject.optInt("id"));
            city.setCityName(jsonObject.optString("name"));
            JSONObject country = jsonObject.getJSONObject("sys");
            city.setCountry(country.optString("country"));
            city.setSunrise(country.optLong("sunrise"));
            city.setSunset(country.optLong("sunset"));

            JSONObject coord = jsonObject.getJSONObject("coord");
            city.setLat(coord.optDouble("lat"));
            city.setLon(coord.optDouble("lon"));

            weatherModel.setWeather(weather);
            weatherModel.setCity(city);

            return weatherModel;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
