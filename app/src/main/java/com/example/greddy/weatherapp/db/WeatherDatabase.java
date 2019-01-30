package com.example.greddy.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.greddy.weatherapp.model.City;
import com.example.greddy.weatherapp.model.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greddy on 4/5/2017.
 */

public class WeatherDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "WeatherDatabase";
    private SQLiteDatabase mSqLiteDatabase;
    private ContentValues mContentValues;
    private Cursor mCursor = null;

    private City mCity;
    private Weather mWeather;

    public WeatherDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CITY_TABLE = "CREATE TABLE " + SunshineTables.City.TABLE_NAME_CITY + " (" + SunshineTables.City._ID + " INTEGER, " +
                SunshineTables.City.COLUMN_CITY_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                SunshineTables.City.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                SunshineTables.City.COLUMN_CITY_COUNTRY + " TEXT NOT NULL, " +
                SunshineTables.City.COLUMN_CITY_SUNRISE + " REAL NOT NULL, " +
                SunshineTables.City.COLUMN_CITY_SUNSET + " REAL NOT NULL, " +
                SunshineTables.City.COLUMN_CITY_LAT + " REAL NOT NULL, " +
                SunshineTables.City.COLUMN_CITY_LON + " REAL NOT NULL, " +
                SunshineTables.City.COLUMN_CITY_DATE + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + SunshineTables.Weather.TABLE_NAME_WEATHER + " (" +
                SunshineTables.Weather._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SunshineTables.Weather.COLUMN_WEATHER_CITY_ID + " INTEGER NOT NULL, " +
                SunshineTables.Weather.COLUMN_TEMPERATURE + " REAL NOT NULL, " +
                SunshineTables.Weather.COLUMN_TEMP_MAX + " REAL NOT NULL, " +
                SunshineTables.Weather.COLUMN_TEMP_MIN + " REAL NOT NULL, " +
                SunshineTables.Weather.COLUMN_PRESSURE + " REAL NOT NULL, " +
                SunshineTables.Weather.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                SunshineTables.Weather.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                SunshineTables.Weather.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
                SunshineTables.Weather.COLUMN_SNOW + " REAL NOT NULL, " +

                // Set up the cityID column as a foreign key to City table.
                " FOREIGN KEY (" + SunshineTables.Weather.COLUMN_WEATHER_CITY_ID + ") REFERENCES " +
                SunshineTables.City.TABLE_NAME_CITY + " (" + SunshineTables.City.COLUMN_CITY_ID + ") " + " );";

        db.execSQL(SQL_CREATE_CITY_TABLE);
        db.execSQL(SQL_CREATE_WEATHER_TABLE);
        Log.d(TAG, "onCreate: " + db.getPageSize());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SunshineTables.City.TABLE_NAME_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + SunshineTables.Weather.TABLE_NAME_WEATHER);
        onCreate(db);
    }

    public SQLiteDatabase getReadModeDb() {
        return this.getReadableDatabase();
    }

    public SQLiteDatabase getWriteModeDb() {
        return this.getWritableDatabase();
    }

    public void insertCity(City city){
        mSqLiteDatabase = getWriteModeDb();
        mContentValues = new ContentValues();
        mContentValues.clear();
        mContentValues.put(SunshineTables.City.COLUMN_CITY_ID,city.getCityId());
        mContentValues.put(SunshineTables.City.COLUMN_CITY_NAME,city.getCityName());
        mContentValues.put(SunshineTables.City.COLUMN_CITY_COUNTRY,city.getCountry());
        mContentValues.put(SunshineTables.City.COLUMN_CITY_SUNRISE,city.getSunrise());
        mContentValues.put(SunshineTables.City.COLUMN_CITY_SUNSET,city.getSunset());
        mContentValues.put(SunshineTables.City.COLUMN_CITY_LAT,city.getLat());
        mContentValues.put(SunshineTables.City.COLUMN_CITY_LON,city.getLon());
        mContentValues.put(SunshineTables.City.COLUMN_CITY_DATE,city.getDateList().get(0));
        mSqLiteDatabase.insert(SunshineTables.City.TABLE_NAME_CITY, null, mContentValues);
        mSqLiteDatabase.close();
    }

    public long getCityCount() {
        mSqLiteDatabase = getReadModeDb();
        return DatabaseUtils.queryNumEntries(mSqLiteDatabase, SunshineTables.City.TABLE_NAME_CITY);
    }

    public City getCity() {
        mSqLiteDatabase = getReadModeDb();
        mCursor = mSqLiteDatabase.query(SunshineTables.City.TABLE_NAME_CITY, new String[]{
                SunshineTables.City.COLUMN_CITY_ID,
                SunshineTables.City.COLUMN_CITY_NAME,
                SunshineTables.City.COLUMN_CITY_COUNTRY,
                SunshineTables.City.COLUMN_CITY_SUNRISE,
                SunshineTables.City.COLUMN_CITY_SUNSET,
                SunshineTables.City.COLUMN_CITY_LAT,
                SunshineTables.City.COLUMN_CITY_LON,
                SunshineTables.City.COLUMN_CITY_DATE}, null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
            mCity = new City();
            mCity.setCityId(Long.parseLong(mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_ID))));
            mCity.setCityName(mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_NAME)));
            mCity.setCountry(mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_COUNTRY)));
            mCity.setSunrise(Long.parseLong(mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_SUNRISE))));
            mCity.setSunset(Long.parseLong(mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_SUNSET))));
            mCity.setLat(Double.parseDouble(mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_LAT))));
            mCity.setLon(Double.parseDouble(mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_LON))));
            List<String> dateList = new ArrayList<>();
            dateList.add(0, mCursor.getColumnName(mCursor.getColumnIndex(SunshineTables.City.COLUMN_CITY_DATE)));
            mCity.setDateList(dateList);
            return mCity;
        }
        return null;
    }

    public void deleteCityData() {
        mSqLiteDatabase = getWriteModeDb();
        mSqLiteDatabase.delete(SunshineTables.City.TABLE_NAME_CITY, null, null);
    }
}
