package com.zkyy.icecream.callback;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.callback
 * @class describe
 * @time 2019-12-26 10:46
 * @change
 * @chang time
 * @class describe
 */
public interface DaVideoPlayCallBack {

    public void isDaVideoReady();

    public void onDaVideoError();

    public void onDaVideoError(String s);

    public void onDaVideoShow();

    public void onDaPlayComplete();

    public void onDaVideoClose();
}
