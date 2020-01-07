package com.zkyy.icecream.callback;

import com.bytedance.sdk.openadsdk.TTSplashAd;

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
public interface DaSplashCallBack {

    public void onDaSplashError(int code,String message);

    public void onDaSplashTimeout();

    public void onDaSplashAdLoad(TTSplashAd ad);

    /**
     * 跳转主页
     */
    public void onDaToMain();

    public void onDaError(String message);
}
