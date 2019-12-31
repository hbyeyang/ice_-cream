package com.zkyy.icecream.config;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.zkyy.icecream.bean.AdConfigBean;
import com.zkyy.icecream.service.AppDownloadStatusListener;

/**
 * 可以用一个单例来保存TTAdManager实例，在需要初始化sdk的时候调用
 */
public class TTAdManagerHolder {

    private static boolean sInit;

    public static TTAdManager get() {
        if (!sInit) {
            throw new RuntimeException("TTAdSdk is not init, please check.");
        }
        return TTAdSdk.getAdManager();
    }

    public static void init(Context context) {
        doInit(context);
    }

    public static void init(Context context, String appId) {
        doInit(context, appId);
    }

    public static void init(Context context, AdConfigBean.AdidsEntity adidsEntity) {
        doInit(context, adidsEntity);
    }

    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    private static void doInit(Context context) {
        if (!sInit) {
            TTAdSdk.init(context, buildConfig(context));
            sInit = true;
        }
    }

    private static void doInit(Context context, String appId) {
        if (!sInit) {
            TTAdSdk.init(context, buildConfig(context, appId));
            sInit = true;
        }
    }

    private static void doInit(Context context, AdConfigBean.AdidsEntity adidsEntity) {
        if (!sInit) {
            TTAdSdk.init(context, buildConfig(context, adidsEntity));
            sInit = true;
        }
    }

    private static TTAdConfig buildConfig(Context context) {
        return new TTAdConfig.Builder()
                .appId("5001121")
//                .appId(adidsEntity.getAppid())
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName("APP测试媒体")
//                .appName(DaUtils.getAppName(context))
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .globalDownloadListener(new AppDownloadStatusListener(context)) //下载任务的全局监听
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_4G, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                .supportMultiProcess(false)
                .build();
    }

    private static TTAdConfig buildConfig(Context context, String appId) {
        return new TTAdConfig.Builder()
                .appId(appId)
//                .appId(adidsEntity.getAppid())
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName("APP测试媒体")
//                .appName(DaUtils.getAppName(context))
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .globalDownloadListener(new AppDownloadStatusListener(context)) //下载任务的全局监听
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_4G, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                .supportMultiProcess(false)
                .build();
    }

    private static TTAdConfig buildConfig(Context context, AdConfigBean.AdidsEntity adidsEntity) {
        return new TTAdConfig.Builder()
                .appId("5001121")
//                .appId(adidsEntity.getAppid())
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName("APP测试媒体")
//                .appName(DaUtils.getAppName(context))
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .globalDownloadListener(new AppDownloadStatusListener(context)) //下载任务的全局监听
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_4G, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                .supportMultiProcess(false)
                .build();
    }
}
