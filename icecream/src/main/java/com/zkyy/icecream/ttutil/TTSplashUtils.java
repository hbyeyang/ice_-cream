package com.zkyy.icecream.ttutil;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.zkyy.icecream.callback.DaSplashCallBack;
import com.zkyy.icecream.config.TTAdManagerHolder;
import com.zkyy.icecream.utils.LogUtils;
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
    private static String TAG = TTSplashUtils.class.getSimpleName() + ": ";

    public static void loadCsjSplash(Activity activity, String adCode, final FrameLayout frameLayout, final DaSplashCallBack daSplashCallBack) {
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
                if (ttSplashAd == null) {
                    return;
                }
                //获取SplashView
                View view = ttSplashAd.getSplashView();
                if (view != null) {
                    frameLayout.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    frameLayout.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                } else {
                    daSplashCallBack.onDaToMain();
                }
                ttSplashAd.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        LogUtils.d(TAG+ "onAdClicked");
//                        showToast("开屏广告点击");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        LogUtils.d(TAG+"onAdShow");
//                        showToast("开屏广告展示");
                    }

                    @Override
                    public void onAdSkip() {
                        LogUtils.d(TAG+"onAdSkip");
//                        showToast("开屏广告跳过");
                        daSplashCallBack.onDaToMain();

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
//                        showToast("开屏广告倒计时结束");
                        daSplashCallBack.onDaToMain();
                    }
                });

                if (ttSplashAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ttSplashAd.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {

                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
//                                showToast("下载中...");
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                            showToast("下载暂停...");

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                            showToast("下载失败...");

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {

                        }
                    });
                }
            }
        });
    }
}
