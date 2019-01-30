package com.example.greddy.weatherapp.utils;

import android.os.AsyncTask;

import com.example.greddy.weatherapp.network.HttpManager;

/**
 * Created by greddy on 4/3/2017.
 */

public class WeatherAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.serveRequest(params[0]);
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
