package com.zkyy.icecream.ttutil;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.zkyy.icecream.callback.DaBannerCallBack;
import com.zkyy.icecream.config.TTAdManagerHolder;
import com.zkyy.icecream.utils.LogUtils;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.ttutil
 * @class describe
 * @time 2019-12-26 18:51
 * @change
 * @chang time
 * @class describe
 */
public class TTBannerUtils {

    private static String TAG = TTBannerUtils.class.getSimpleName() + ": ";

    /**
     * 穿山甲banner
     *
     * @param activity
     * @param adCode
     * @param frameLayout
     * @param daBannerCallBack
     */
    public static void csjBannerLoad(Activity activity, String adCode, final FrameLayout frameLayout, final DaBannerCallBack daBannerCallBack) {
        TTAdNative adNative = TTAdManagerHolder.get().createAdNative(activity);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adCode) //广告位id
                .setSupportDeepLink(true)
                .setImageAcceptedSize(600, 257)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        adNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {

            @Override
            public void onError(int code, String message) {
                LogUtils.d(TAG + "load error : " + code + ", " + message);
                daBannerCallBack.onDaBannerError(code, message);
                frameLayout.removeAllViews();
            }

            @Override
            public void onBannerAdLoad(final TTBannerAd ad) {
                if (ad == null) {
                    return;
                }
                View bannerView = ad.getBannerView();
                if (bannerView == null) {
                    return;
                }
                //设置轮播的时间间隔  间隔在30s到120秒之间的值，不设置默认不轮播
                ad.setSlideIntervalTime(30 * 1000);
                frameLayout.removeAllViews();
                frameLayout.addView(bannerView);
                //设置广告互动监听回调
                ad.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        LogUtils.d(TAG + "广告被点击");
                        daBannerCallBack.onDaBannerClicked(view, type);
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        LogUtils.d(TAG + "广告展示");
                        daBannerCallBack.onDaBannerShow(view, type);
                    }
                });
                //（可选）设置下载类广告的下载监听
                bindDownloadListener(ad);
                //在banner中显示网盟提供的dislike icon，有助于广告投放精准度提升
                ad.setShowDislikeIcon(new TTAdDislike.DislikeInteractionCallback() {
                    @Override
                    public void onSelected(int position, String value) {
                        LogUtils.d(TAG + "点击 " + value);
                        //用户选择不喜欢原因后，移除广告展示
                        frameLayout.removeAllViews();
                        daBannerCallBack.onDaBannerSelected(position, value);
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.d(TAG + "点击取消");
                        daBannerCallBack.onDaBannerCancel();
                    }
                });

            }
        });
    }

    private static boolean mHasShowDownloadActive = false;

    private static void bindDownloadListener(TTBannerAd ad) {
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                LogUtils.d(TAG + "点击图片开始下载");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    LogUtils.d(TAG + "下载中，点击图片暂停");
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                LogUtils.d(TAG + "下载暂停，点击图片继续");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                LogUtils.d(TAG + "下载失败，点击图片重新下载");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                LogUtils.d(TAG + "安装完成，点击图片打开");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                LogUtils.d(TAG + "点击图片安装");
            }
        });
    }
}
