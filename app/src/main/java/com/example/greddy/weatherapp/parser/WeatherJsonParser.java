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
 * Created by greddy on 4/3/2017.
 */

public class WeatherJsonParser {

    public static WeatherModel parseFeed(String content) {
        WeatherModel weatherModel = new WeatherModel();
        try {
            List<Weather> weatherList = new ArrayList<>();
            City city = new City();
            List<String> dateList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(content);
            JSONObject cityObject = jsonObject.optJSONObject("city");
            city.setCityName(cityObject.optString("name"));
            city.setCountry(cityObject.getString("country"));

            JSONArray tempList = jsonObject.getJSONArray("list");
            for (int i = 0; i < tempList.length(); i++) {
                Weather weather = new Weather();
                JSONObject tempList1 = tempList.getJSONObject(i);
                JSONObject main = tempList1.optJSONObject("main");
                weather.setTemp(main.optDouble("temp"));
                weather.setTemp_min(main.optDouble("temp_min"));
                weather.setTemp_max(main.optDouble("temp_max"));
                weather.setPressure(main.optDouble("pressure"));
                weather.setHumidity(main.optDouble("humidity"));

                JSONArray weatherArray = tempList1.getJSONArray("weather");
                List<String> descriptionList = new ArrayList<>();
                for (int j = 0; j < weatherArray.length(); j++) {
                    JSONObject weather1 = weatherArray.getJSONObject(j);
                    descriptionList.add(j, weather1.optString("description"));
                }
                weather.setDescription(descriptionList);

                JSONObject wind = tempList1.getJSONObject("wind");
                weather.setWindSpeed(wind.optDouble("speed"));

                dateList.add(i, tempList1.optString("dt_txt"));

                weatherList.add(i, weather);
            }

            city.setDateList(dateList);
            weatherModel.setCity(city);
            weatherModel.setWeatherList(weatherList);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return weatherModel;
    }

    public static WeatherModel parseForecastData(String content) {
        WeatherModel weatherModel = new WeatherModel();

        try {
            List<Weather> weatherList = new ArrayList<>();
            City city = new City();
            List<String> dateList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(content);

            JSONObject cityJsonObject = jsonObject.getJSONObject("city");
            city.setCityName(cityJsonObject.optString("name"));
            city.setCountry(cityJsonObject.optString("country"));

            JSONArray weatherArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherJson = weatherArray.getJSONObject(i);
                dateList.add(i, weatherJson.optString("dt"));

                Weather weather = new Weather();
                JSONObject temp = weatherJson.getJSONObject("temp");
                weather.setTemp_min(temp.getDouble("min"));
                weather.setTemp_max(temp.getDouble("max"));
                weather.setTemp(temp.getDouble("day"));

                JSONArray weatherJsonArray = weatherJson.getJSONArray("weather");
                List<String> descriptionList = new ArrayList<>();
                JSONObject weather1 = weatherJsonArray.getJSONObject(0);
                descriptionList.add(0, weather1.optString("description"));
                weather.setWeatherId(weather1.optInt("id"));
                weather.setDescription(descriptionList);
                weatherList.add(i, weather);
            }
            city.setDateList(dateList);
            weatherModel.setCity(city);
            weatherModel.setWeatherList(weatherList);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return weatherModel;
    }
}
