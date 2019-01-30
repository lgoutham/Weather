package com.example.greddy.weatherapp.utils;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.greddy.weatherapp.R;
import com.example.greddy.weatherapp.ui.home.HomeActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;

import static com.example.greddy.weatherapp.ui.home.HomeActivity.REQUEST_PERMISSION;

/**
 * Created by greddy on 4/6/2017.
 */

public class DetectLocation extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Location mPresentLocation;
    private View mLayout;

    public DetectLocation(Context context) {
        mContext = context;
        mLayout = ((HomeActivity) mContext).findViewById(R.id.home_activity_content_coordinatorLayout);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Location getPresentLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions();
        } else {
            mPresentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        return mPresentLocation;
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((HomeActivity) mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Snackbar.make(mLayout, R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.lbl_Ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions((HomeActivity) mContext,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSION);
                        }
                    })
                    .show();
        }
        ActivityCompat.requestPermissions((HomeActivity) mContext,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.location_permision_available,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions();
        } else {
            mPresentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void stopUsingGPS() {
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    /*public boolean hasPermission() {
        boolean hasPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            String[] permissionArray;
            int locationPermission = ContextCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            if (locationPermission == PackageManager.PERMISSION_DENIED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissionList != null) {
                permissionArray = permissionList.toArray(new String[permissionList.size()]);
                if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, permissionArray[0])) {
                    ActivityCompat.requestPermissions(this, permissionArray, REQUEST_PERMISSION);
                } else {
                    ActivityCompat.requestPermissions(this, permissionArray, REQUEST_PERMISSION);
                }
                hasPermission = false;
            } else {
                hasPermission = true;
            }
        } else {
            hasPermission = true;
        }
        return hasPermission;
    }*/
}
