package com.example.greddy.weatherapp.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greddy.weatherapp.R;

/**
 * Created by greddy on 4/10/2017.
 */

public class HomeDetailsActivity extends AppCompatActivity {

    private TextView mDate;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mSkyStatus;
    private TextView mPlace;
    private ImageView mIcon;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUi();
        mPosition = getIntent().getIntExtra(HomeAdapter.POSITION,0);
    }

    private void loadUi() {
        setContentView(R.layout.home_details_activity_content_view);
        mDate = (TextView) findViewById(R.id.home_details_activity_date);
        mMaxTemp = (TextView) findViewById(R.id.home_details_activity_temp_max);
        mMinTemp = (TextView) findViewById(R.id.home_details_activity_temp_min);
        mSkyStatus = (TextView) findViewById(R.id.home_details_activity_sky_status);
        mPlace = (TextView) findViewById(R.id.home_details_activity_humidity);
        mIcon = (ImageView) findViewById(R.id.home_details_activity_image_view);

        /*Date date = new Date(Integer.parseInt(mWeatherModel.getCity().getDateList().get(0)) * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        mDate.setText("Today\n" + format.format(date));
        String maxTemp = String.valueOf(mWeatherModel.getWeather().getTemp_max()).concat("<span>&#176;</span>");
        mMaxTemp.setText(Html.fromHtml(maxTemp));
        String minTemp = String.valueOf(mWeatherModel.getWeather().getTemp_min()).concat("<span>&#176;</span>");
        mMinTemp.setText(Html.fromHtml(minTemp));
        mSkyStatus.setText(String.valueOf(mWeatherModel.getWeather().getDescriptionList().get(0)));
        String place = String.valueOf(mWeatherModel.getCity().getCityName()).concat("," + mWeatherModel.getCity().getCountry());
        mPlace.setText(place);
        switch (mWeatherModel.getWeather().getDescriptionList().get(0)) {
            case SKY_STATUS_CLEAR:
                mIcon.setImageDrawable(ContextCompat.getDrawable(HomeDetailsActivity.this, R.drawable.art_clear));
                break;
            case SKY_STATUS_FEW_CLOUDS:
            case SKY_STATUS_BROKEN_CLOUDS: {
                mIcon.setImageDrawable(ContextCompat.getDrawable(HomeDetailsActivity.this, R.drawable.art_light_clouds));
                break;
            }
            case SKY_STATUS_OVERCAST_CLOUDS:
            case SKY_STATUS_SCATTERED_CLOUDS: {
                mIcon.setImageDrawable(ContextCompat.getDrawable(HomeDetailsActivity.this, R.drawable.art_clouds));
                break;
            }
            case SKY_STATUS_LIGHT_SNOW:
                mIcon.setImageDrawable(ContextCompat.getDrawable(HomeDetailsActivity.this, R.drawable.art_snow));
                break;
            case SKY_STATUS_LIGHT_RAIN:
                mIcon.setImageDrawable(ContextCompat.getDrawable(HomeDetailsActivity.this, R.drawable.art_light_rain));
                break;
            case SKY_STATUS_MODERATE_RAIN:
                mIcon.setImageDrawable(ContextCompat.getDrawable(HomeDetailsActivity.this, R.drawable.art_rain));
                break;
        }*/
    }
}
