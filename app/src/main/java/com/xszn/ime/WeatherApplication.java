package com.xszn.ime;

import android.app.Application;
import android.content.Context;

import com.zkyy.icecream.DaUtils;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.xszn.ime
 * @class describe
 * @time 2019-12-25 15:46
 * @change
 * @chang time
 * @class describe
 */
public class WeatherApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        DaInit.init(this,"5018834");
        DaUtils.getInstance().getAdUtils(this,"200", "");
    }

    public static Context getInstance(){
        return mContext;
    }

}
