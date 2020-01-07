package com.zkyy.icecream.dautil;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.callback.DaVideoPlayCallBack;
import com.zkyy.icecream.config.TTAdManagerHolder;
import com.zkyy.icecream.constan.AdLoc;
import com.zkyy.icecream.net.NetAddress;
import com.zkyy.icecream.utils.LogUtils;
import com.zkyy.icecream.utils.MyUtils;

import androidx.annotation.NonNull;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.ttutil
 * @class describe
 * @time 2019-12-26 18:53
 * @change
 * @chang time
 * @class describe
 */
public class TTVideoUtils {

    private static String TAG = TTVideoUtils.class.getSimpleName() + ": ";

    //穿山甲
    private static boolean mHasShowDownloadActive = false;
    private static TTRewardVideoAd mttRewardVideoAd;
    private static TTFullScreenVideoAd mttFullVideoAd;
    private static Handler mHandler;

    /**
     * 播放广告
     *
     * @param codeIndex     广告位置
     * @param isReward 是否激励视频
     * @param callback 回调
     */
    public static void playCsjAd(@NonNull Activity activity, int codeIndex,int index, @NonNull DaVideoPlayCallBack callback) {
        LogUtils.d(TAG + "playAd-" + "code=" + DaUtils.getAdCode(codeIndex,index));
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        TTAdNative mTTAdNative = TTAdManagerHolder.get().createAdNative(activity);
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(DaUtils.getAdCode(codeIndex,index))
                .setSupportDeepLink(true)
                .setImageAcceptedSize(MyUtils.getScreenWidth(activity), MyUtils.getScreenHeight(activity))
                .setOrientation(TTAdConstant.VERTICAL)//必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
//        if (isReward) {
//            loadRewardVideoAd(activity, mTTAdNative, adSlot, callback);
//        } else {
//            loadFullScreenVideoAd(activity, mTTAdNative, adSlot, callback);
//        }
        loadRewardVideoAd(activity,codeIndex,index, mTTAdNative, adSlot, callback);
    }

    /**
     * 播放激励视频代码
     *
     * @param codeIndex
     * @param index
     * @param daVideoPlayCallBack
     */
    @SuppressWarnings("SameParameterValue")
    private static void loadRewardVideoAd(final Activity activity, final int codeIndex, final int index, TTAdNative ttAdNative, AdSlot adSlot, final DaVideoPlayCallBack daVideoPlayCallBack) {
        if (daVideoPlayCallBack == null) {
            return;
        }
        //step5:请求广告
        ttAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d("TipDemo", "onError");
                if (index + 1 <= DaUtils.getAdCodes(codeIndex).size()) {
                    DaVideoLoad.loadPlayWay(activity, codeIndex, index + 1, daVideoPlayCallBack);
                } else {
                    daVideoPlayCallBack.onDaVideoError(message);
                }
                NetAddress.newUploadingSignAd(codeIndex,index, AdLoc.AD_REQUEST,AdLoc.REQUEST_FAILED);
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.d("TipDemo", "onRewardVideoCached");
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        LogUtils.d(TAG + "onAdShow");
                        daVideoPlayCallBack.onDaVideoShow();
                        NetAddress.newUploadingSignAd(codeIndex,index, AdLoc.AD_SHOW,AdLoc.REQUEST_SUCCEED);
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        LogUtils.d(TAG + "onAdVideoBarClick");
                        NetAddress.newUploadingSignAd(codeIndex,index, AdLoc.AD_CLICK,AdLoc.REQUEST_SUCCEED);
                    }

                    @Override
                    public void onAdClose() {
                        LogUtils.d(TAG + "onAdClose");
                        daVideoPlayCallBack.onDaVideoClose();
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        LogUtils.d(TAG + "onVideoComplete");
                        daVideoPlayCallBack.onDaPlayComplete();
                    }

                    @Override
                    public void onVideoError() {
                        LogUtils.d(TAG + "onVideoError");
                        if (index + 1 <= DaUtils.getAdCodes(codeIndex).size()) {
                            DaVideoLoad.loadPlayWay(activity, codeIndex, index + 1, daVideoPlayCallBack);
                        } else {
                            daVideoPlayCallBack.onDaVideoError();
                        }
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        LogUtils.d(TAG + "onRewardVerify");
                    }

                    public void onSkippedVideo() {
                        LogUtils.d(TAG + "onSkippedVideo");
                        daVideoPlayCallBack.onDaPlayComplete();
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                            Toast.makeText(activity, "下载失败，点击下载区域重新下载", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Toast.makeText(activity, "下载暂停，点击下载区域继续", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Toast.makeText(activity, "下载失败，点击下载区域重新下载", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Toast.makeText(activity, "下载完成，点击下载区域重新下载", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Toast.makeText(activity, "安装完成，点击下载区域打开", Toast.LENGTH_LONG).show();
                    }
                });
                //必须在主线程中调用播放
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mttRewardVideoAd != null) {
                            //展示广告，并传入广告展示的场景
                            mttRewardVideoAd.showRewardVideoAd(activity);
                            mttRewardVideoAd = null;
                        } else {
                        }
                    }
                });
            }
        });
    }

    /**
     * 播放全屏视频代码
     * 不会播放非激励视频
     *
     * @param callback
     */
    @SuppressWarnings("SameParameterValue")
    private static void loadFullScreenVideoAd(final Activity activity, TTAdNative ttAdNative, AdSlot adSlot, final DaVideoPlayCallBack callback) {
        if (callback == null) {
            return;
        }
        //step5:请求广告
        ttAdNative.loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.d(TAG + "onError-" + message);
                callback.onDaVideoError(message);
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                mttFullVideoAd = ad;
                mttFullVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                    }

                    @Override
                    public void onAdVideoBarClick() {
                    }

                    @Override
                    public void onAdClose() {
                    }

                    @Override
                    public void onVideoComplete() {
                        callback.onDaPlayComplete();
                    }

                    @Override
                    public void onSkippedVideo() {
                        callback.onDaPlayComplete();
                    }

                });
                //必须在主线程中调用播放
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mttFullVideoAd != null) {
                            //展示广告，并传入广告展示的场景
                            mttFullVideoAd.showFullScreenVideoAd(activity);
                            mttFullVideoAd = null;
                        } else {
                            callback.onDaVideoError("播放失败");
                        }
                    }
                });
            }

            @Override
            public void onFullScreenVideoCached() {

            }
        });
    }
}
