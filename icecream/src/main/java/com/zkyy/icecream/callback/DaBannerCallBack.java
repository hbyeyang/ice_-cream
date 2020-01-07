package com.zkyy.icecream.callback;

import android.view.View;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.callback
 * @class describe
 * @time 2019-12-26 10:47
 * @change
 * @chang time
 * @class describe
 */
public interface DaBannerCallBack {
    public void onDaBannerError(int code,String message);
    public void onDaBannerClicked(View view, int type);
    public void onDaBannerShow(View view, int type);
    public void onDaBannerSelected(int position, String value);
    public void onDaBannerCancel();
    public void onDaBannerError(String message);
}
