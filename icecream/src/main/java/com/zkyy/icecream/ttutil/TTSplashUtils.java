package com.zkyy.icecream.ttutil;

import android.app.Activity;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.zkyy.icecream.callback.DaSplashCallBack;
import com.zkyy.icecream.config.TTAdManagerHolder;
import com.zkyy.icecream.utils.MyUtils;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.ttutil
 * @class describe
 * @time 2019-12-26 18:46
 * @change
 * @chang time
 * @class describe
 */
public class TTSplashUtils {
    public static void loadCsjSplash(Activity activity, String adCode, FrameLayout frameLayout, final DaSplashCallBack daSplashCallBack) {
        TTAdManagerHolder.init(activity);
        TTAdNative mTTAdNative = TTAdManagerHolder.get().createAdNative(activity);
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adCode)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(MyUtils.getScreenWidth(activity), MyUtils.getScreenHeight(activity))
                .build();
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int i, String s) {
                daSplashCallBack.onDaSplashError(i, s);
            }

            @Override
            public void onTimeout() {
                daSplashCallBack.onDaSplashTimeout();
            }

            @Override
            public void onSplashAdLoad(TTSplashAd ttSplashAd) {
                daSplashCallBack.onDaSplashAdLoad(ttSplashAd);
            }
        });
    }
}
