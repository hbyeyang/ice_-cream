package com.xszn.ime.widget.weatherview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xszn.ime.R;


public class WeatherItemView24 extends LinearLayout {

    private View rootView;
    private TextView tvNightWeather;
    private TemperatureView24 ttvTemp;
    private TextView tvWindOri;
    private TextView tvWindLevel;
    private ImageView ivNightWeather;
    private TextView mTvTime;

    public WeatherItemView24(Context context) {
        this(context, null);
    }

    public WeatherItemView24(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WeatherItemView24(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.weather_item_24, null);
        tvNightWeather = (TextView) rootView.findViewById(R.id.tv_night_weather);
        ttvTemp = (TemperatureView24) rootView.findViewById(R.id.ttv_day24);
        tvWindOri = (TextView) rootView.findViewById(R.id.tv_wind_ori);
        tvWindLevel = (TextView) rootView.findViewById(R.id.tv_wind_level);
        ivNightWeather = (ImageView) rootView.findViewById(R.id.iv_night_weather);
        //tvAirLevel = (TextView) rootView.findViewById(R.id.tv_air_level);
        mTvTime = rootView.findViewById(R.id.tv_time);
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rootView);
    }

    public int getTempX() {
        if (ttvTemp != null)
            return (int) ttvTemp.getX();
        return 0;
    }

    public int getTempY() {
        if (ttvTemp != null)
            return (int) ttvTemp.getY();
        return 0;
    }

    public void setNightWeather(String nightWeather) {
        if (tvNightWeather != null)
            tvNightWeather.setText(nightWeather);
    }

    public void setWindOri(String windOri) {
        if (tvWindOri != null)
            tvWindOri.setText(windOri);
    }

    public void setTime(String time) {
        if (time != null)
            mTvTime.setText(time);
    }

    public void setWindLevel(String windLevel) {
        if (tvWindLevel != null)
            tvWindLevel.setText(windLevel);
    }

//    public void setAirLevel(AirLevel airLevel) {
//        if (tvAirLevel != null) {
//            switch (airLevel) {
//                case EXCELLENT:
//                    tvAirLevel.setBackgroundResource(R.drawable.best_level_shape);
//                    tvAirLevel.setText("优");
//                    break;
//                case GOOD:
//                    tvAirLevel.setBackgroundResource(R.drawable.good_level_shape);
//                    tvAirLevel.setText("良好");
//                    break;
//                case LIGHT:
//                    tvAirLevel.setText("轻度");
//                    tvAirLevel.setBackgroundResource(R.drawable.small_level_shape);
//                    break;
//                case MIDDLE:
//                    tvAirLevel.setBackgroundResource(R.drawable.mid_level_shape);
//                    tvAirLevel.setText("中度");
//                    break;
//                case HIGH:
//                    tvAirLevel.setBackgroundResource(R.drawable.big_level_shape);
//                    tvAirLevel.setText("重度");
//                    break;
//                case POISONOUS:
//                    tvAirLevel.setBackgroundResource(R.drawable.poison_level_shape);
//                    tvAirLevel.setText("有毒");
//                    break;
//            }
//        }
//    }

    public void setDayTemp(int dayTemp) {
        if (ttvTemp != null)
            ttvTemp.setTemperatureDay(dayTemp);
    }

    public void setNightTemp(int nightTemp) {
        if (ttvTemp != null)
            ttvTemp.setTemperatureNight(nightTemp);
    }

    public void setNightImg(int resId) {
        if (ivNightWeather != null)
            ivNightWeather.setImageResource(resId);
    }

    public void setMaxTemp(int max) {
        if (ttvTemp != null)
            ttvTemp.setMaxTemp(max);
    }

    public void setMinTemp(int min) {
        if (ttvTemp != null) {
            ttvTemp.setMinTemp(min);
        }
    }
}
