package com.zkyy.icecream.callback;

import android.view.View;

import com.bytedance.sdk.openadsdk.TTNativeAd;

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
public interface DaNativeCallBack {

    public void onDaNativeError(int code,String message);

    public void onDaNativeClicked(View view, TTNativeAd ad);

    public void onDaNativeCreativeClick(View view, TTNativeAd ad);

    public void onDaNativeShow(TTNativeAd ad);

    public void onDaNativeSelected(int position, String value);

    public void onDaNativeCancel();

}
