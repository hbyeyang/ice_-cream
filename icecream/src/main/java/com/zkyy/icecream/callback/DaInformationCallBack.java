package com.zkyy.icecream.callback;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.callback
 * @class describe
 * @time 2019-12-26 10:49
 * @change
 * @chang time
 * @class describe
 */
public interface DaInformationCallBack {

    public void isDaReady();

    public void onError();

    public void onDaShow();

    public void onTimeOver();

    public void onLoadSucceed();

    public void onDaClicked();

    public void onDaSkip();
}
