package com.example.greddy.weatherapp.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greddy.weatherapp.R;
import com.example.greddy.weatherapp.model.forecast.WeatherForecastDayModel;
import com.example.greddy.weatherapp.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by greddy on 4/3/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    public static String POSITION = "POSITION";
    public static String WEATHER_MODEL = "WEATHER_MODEL";

    private Context mContext;
    private List<WeatherForecastDayModel> mWeatherList;
    private String mSettingsTempValue;
    private int mPosition;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    public void UpdateData(List<WeatherForecastDayModel> weatherModel, String mSettingsTempValue) {
        this.mWeatherList = weatherModel;
        this.mSettingsTempValue = mSettingsTempValue;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_activity_recycler_item_view, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mContext, HomeDetailsActivity.class);
                intent.putExtra(POSITION, mPosition);
                mContext.startActivity(intent);*/
            }
        });
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        mPosition = position + 1;
        if (mPosition > getItemCount()) {
            return;
        }
        Date date = new Date(mWeatherList.get(mPosition).getDate());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = format.format(date);
        holder.mDate.setText(dateString);
        String tempUnit;
        if (mSettingsTempValue.equals(mContext.getString(R.string.settings_temp_celsius_value))) {
            tempUnit = "\u2103";
        } else {
            tempUnit = "\u2109";
        }
        String maxTemp = String.valueOf(mWeatherList.get(mPosition).getTemperature().getMax()).concat(tempUnit);
        holder.mMaxTemp.setText(maxTemp);
        String minTemp = String.valueOf(mWeatherList.get(mPosition).getTemperature().getMin()).concat(tempUnit);
        holder.mMinTemp.setText(minTemp);
        holder.mSky.setText(mWeatherList.get(mPosition).getWeather().getDescription());

        int imageResourceId = Utility.getWeatherConditionResourceId(mWeatherList.get(mPosition).getWeather().getId());
        holder.mIcon.setImageResource(imageResourceId);
    }

    @Override
    public int getItemCount() {
        return mWeatherList != null ? mWeatherList.size() - 1 : 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIcon;
        private TextView mMinTemp;
        private TextView mMaxTemp;
        private TextView mSky;
        private TextView mDate;

        public HomeViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.home_activity_recycler_item_icon);
            mDate = (TextView) itemView.findViewById(R.id.home_activity_recycler_item_date);
            mSky = (TextView) itemView.findViewById(R.id.home_activity_recycler_item_sky);
            mMaxTemp = (TextView) itemView.findViewById(R.id.home_activity_recycler_item_max_temp);
            mMinTemp = (TextView) itemView.findViewById(R.id.home_activity_recycler_item_min_temp);
        }
    }
}
