package com.example.greddy.weatherapp.ui.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greddy.weatherapp.R;
import com.example.greddy.weatherapp.db.WeatherDatabase;
import com.example.greddy.weatherapp.model.WeatherModel;
import com.example.greddy.weatherapp.parser.WeatherJsonParser;
import com.example.greddy.weatherapp.ui.settings.SettingsActivity;
import com.example.greddy.weatherapp.utils.Utility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.greddy.weatherapp.utils.Utility.COUNT;
import static com.example.greddy.weatherapp.utils.Utility.IMPERIAL_UNITS;
import static com.example.greddy.weatherapp.utils.Utility.METRIC_UNITS;

;

/**
 * Created by greddy on 4/2/2017.
 */

public class HomeActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int REQUEST_PERMISSION = 0x6;
    public static final int SETTINGS_REQUEST_CODE = 1;

    private static final String TAG = "HomeActivity";

    private boolean isGpsEnabled = false;
    private LocationManager mLocationManager;
    private GoogleApiClient mGoogleApiClient;
    private Location mPresentLocation;

    private AlertDialog mDialog;
    private RelativeLayout mProgressBar;
    private LinearLayout mPresentDataLayout;

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private TextView mDate;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mSkyStatus;
    private TextView mPlace;
    private ImageView mIcon;
    private View mLayout;

    private RequestQueue mRequestQueue;

    private WeatherModel mWeatherModel;
    private WeatherDatabase mWeatherDatabase;
    private String mSettingsTempValue;
    private String mPlaceSearched;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mRequestQueue = Volley.newRequestQueue(this);
        setUpSettingsPreferences();
        loadUi();
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        if (isDeviceOnline()) {
//            checkLocation();
            requestForForecastData();
        } else {
            ShowNoNetworkDialog();
        }
        mWeatherDatabase = new WeatherDatabase(this);
    }

    private void setUpSettingsPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSettingsTempValue = sharedPreferences.getString(getString(R.string.settings_temp_unit), getString(R.string.settings_temp_celsius_value));
        mPlaceSearched = sharedPreferences.getString(getString(R.string.settings_place), getString(R.string.bangalore));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadUi() {
        setContentView(R.layout.home_activity_content_view);
        mLayout = findViewById(R.id.home_activity_content_coordinatorLayout);
        mProgressBar = (RelativeLayout) findViewById(R.id.home_activity_content_progressbar);
        mPresentDataLayout = (LinearLayout) findViewById(R.id.home_activity_present_data);
        mPresentDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeDetailsActivity.class);
                startActivity(intent);
            }
        });
        mDate = (TextView) findViewById(R.id.home_activity_date);
        mMaxTemp = (TextView) findViewById(R.id.home_activity_temp_max);
        mMinTemp = (TextView) findViewById(R.id.home_activity_temp_min);
        mSkyStatus = (TextView) findViewById(R.id.home_activity_sky_status);
        mPlace = (TextView) findViewById(R.id.home_activity_place);
        mIcon = (ImageView) findViewById(R.id.home_activity_image_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.home_activity_content_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mHomeAdapter = new HomeAdapter(this);
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public boolean isDeviceOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isDeviceOnline = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if (isDeviceOnline) {
            NetworkInfo[] networks = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo n : networks) {
                if (n.getState() == NetworkInfo.State.CONNECTED || n.getState() == NetworkInfo.State.CONNECTING) {
                    if (n.getType() == ConnectivityManager.TYPE_MOBILE || n.getType() == ConnectivityManager.TYPE_WIFI) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkLocation() {
        isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsEnabled)
            ShowGpsDialog();
        else
            prepareUrlAndRequestData();
    }

    private void prepareUrlAndRequestData() {
        if (mPresentLocation != null) {
            String url = prepareUrl(mPresentLocation.getLatitude(), mPresentLocation.getLongitude());
            Log.d(TAG, "checkLocation: url" + url);
            requestForLatLngData(url);
        }
    }

    private String prepareUrl(double latitude, double longitude) {
        String url = Utility.BASE_URL + "lat=" + latitude + "&lon=" + longitude + Utility.APPID + Utility.UNITS;
        if (mSettingsTempValue.equals(getString(R.string.settings_temp_celsius_value))) {
            url = url.concat(METRIC_UNITS);
        } else {
            url = url.concat(IMPERIAL_UNITS);
        }
        return url;
    }

    private void requestForLatLngData(String url) {
        StringRequest stringLatLonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", "onResponse: " + response);
                        if (response != null) {
                            onLocationResponse(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("onErrorResponse", "onErrorResponse: " + error);
                    }
                });
        mRequestQueue.add(stringLatLonRequest);
    }

    private void requestForForecastData() {
        StringRequest stringDataRequest = new StringRequest(Request.Method.GET, getForecastUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mWeatherModel = WeatherJsonParser.parseForecastData(response);
                        onLocationResponse(response);
                        mHomeAdapter.UpdateData(mWeatherModel, mSettingsTempValue);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onErrorResponse", "onErrorResponse: " + error);
            }
        });
        mRequestQueue.add(stringDataRequest);
    }

    private String getForecastUrl() {
        String url = Utility.FORECAST_URL + mPlaceSearched + Utility.APPID + Utility.UNITS;
        if (mSettingsTempValue.equals(getString(R.string.settings_temp_celsius_value))) {
            url = url.concat(METRIC_UNITS);
        } else {
            url = url.concat(IMPERIAL_UNITS);
        }
        return url.concat(COUNT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isDeviceOnline()) {
//            checkLocation();
            requestForForecastData();
        } else {
            ShowNoNetworkDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                mDialog.dismiss();
        }
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Snackbar.make(mLayout, R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.lbl_Ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSION);
                        }
                    })
                    .show();
        }
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions();
        } else {
            mPresentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            prepareUrlAndRequestData();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.location_permision_available,
                        Snackbar.LENGTH_SHORT).show();
                prepareUrlAndRequestData();
            } else {
                Snackbar.make(mLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    private void ShowNoNetworkDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.no_network_title));
        alertDialogBuilder.setMessage(getResources().getString(R.string.no_network_message))
                .setNegativeButton(getResources().getString(R.string.lbl_Ok), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                        finish();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.lbl_Retry), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
                        mDialog.dismiss();
                    }
                })
                .setCancelable(false);
        mDialog = alertDialogBuilder.create();
        mDialog.show();
    }

    private void ShowGpsDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.no_gps_title));
        alertDialogBuilder.setMessage(getResources().getString(R.string.permission_location_rationale))
                .setNegativeButton(getResources().getString(R.string.lbl_Cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.lbl_Enable_Gps), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                })
                .setCancelable(false);
        mDialog = alertDialogBuilder.create();
        mDialog.show();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getResources().getString(R.string.settings_temp_unit))) {
            mSettingsTempValue = sharedPreferences.getString(getString(R.string.settings_temp_unit), getString(R.string.settings_temp_celsius_value));
        } else if (key.equals(getResources().getString(R.string.settings_place))) {
            mPlaceSearched = sharedPreferences.getString(getString(R.string.settings_place), getString(R.string.bangalore));
        }
    }

    private void onLocationResponse(String response) {
//        mWeatherModel = WeatherLatLonParser.parseLatLonFeed(response);
        mProgressBar.setVisibility(View.GONE);
        mPresentDataLayout.setVisibility(View.VISIBLE);
        Date date = new Date(Integer.parseInt(mWeatherModel.getCity().getDateList().get(0)) * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String dateString = "Today\n" + format.format(date);
        mDate.setText(dateString);
        String tempUnit;
        if (mSettingsTempValue.equals(getString(R.string.settings_temp_celsius_value))) {
            tempUnit = "\u2103";
        } else {
            tempUnit = "\u2109";
        }
        String maxTemp = String.valueOf(mWeatherModel.getWeatherList().get(0).getTemp_max()).concat(tempUnit);
        mMaxTemp.setText(maxTemp);
        String minTemp = String.valueOf(mWeatherModel.getWeatherList().get(0).getTemp_min()).concat(tempUnit);
        mMinTemp.setText(minTemp);
        mSkyStatus.setText(String.valueOf(mWeatherModel.getWeatherList().get(0).getDescriptionList().get(0)));
        String place = String.valueOf(mWeatherModel.getCity().getCityName()).concat("," + mWeatherModel.getCity().getCountry());
        mPlace.setText(place);

        int imageResourceId = Utility.getWeatherConditionResourceId(mWeatherModel.getWeatherList().get(0).getWeatherId());
        mIcon.setImageResource(imageResourceId);

        mWeatherDatabase = new WeatherDatabase(this);
        long rowCount = mWeatherDatabase.getCityCount();
        Log.d(TAG, "onLocationResponse: Row count" + rowCount);
        if (mWeatherDatabase.getCityCount() > 0) {
            mWeatherDatabase.deleteCityData();
        }
        mWeatherDatabase.insertCity(mWeatherModel.getCity());
        Log.d(TAG, "onLocationResponse: Row count" + mWeatherDatabase.getCityCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        mLocationManager = null;
        mRecyclerView = null;
        mHomeAdapter = null;
        mDate = null;
        mMaxTemp = null;
        mMinTemp = null;
        mSkyStatus = null;
        mIcon = null;
        mRequestQueue = null;
        mWeatherModel = null;
        mWeatherDatabase = null;
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
