package com.zkyy.icecream.dautil;

import android.app.Activity;

import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.callback.DaVideoPlayCallBack;
import com.zkyy.icecream.constan.DaConstan;
import com.zkyy.icecream.utils.LogUtils;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.dautils
 * @class describe
 * @time 2019-12-26 10:59
 * @change
 * @chang time
 * @class describe
 */
public class DaVideoLoad {

    private static String TAG = DaVideoLoad.class.getSimpleName() + ": ";


    /**
     * 调用播放视频
     *
     * @param activity
     * @param posId
     * @param daVideoPlayCallBack 回调
     */
    public static void play(Activity activity, int posId, DaVideoPlayCallBack daVideoPlayCallBack) {
        LogUtils.d(TAG + "play");
        int codeIndex = DaUtils.getCodeIndex(posId);
        int index = 0;
        if (codeIndex < 0 || codeIndex > DaUtils.getAdNum()) {
            LogUtils.d("请查看请求的广告位是否已配置");
            if (daVideoPlayCallBack != null) {
                daVideoPlayCallBack.onDaVideoError("请查看请求的广告位是否已配置");
            }
        }
        if (activity == null) {
            LogUtils.d(TAG + "activity不能为空");
            if (daVideoPlayCallBack != null) {
                daVideoPlayCallBack.onDaVideoError("activity不能为空");
            }
            return;
        }
        if (posId == 0) {
            LogUtils.d(TAG + "posId不能为0");
            if (daVideoPlayCallBack != null) {
                daVideoPlayCallBack.onDaVideoError("posId不能为0");
            }
            return;
        }
        if (daVideoPlayCallBack == null) {
            LogUtils.d(TAG + "daSplashCallBack不能为空");
            if (daVideoPlayCallBack != null) {
                daVideoPlayCallBack.onDaVideoError("daSplashCallBack不能为空");
            }
            return;
        }
        loadPlayWay(activity, codeIndex, index, daVideoPlayCallBack);
    }

    protected static void loadPlayWay(Activity activity, int codeIndex, int index, DaVideoPlayCallBack daVideoPlayCallBack) {
        if (index < DaUtils.getAdCodes(codeIndex).size()) {
            switch (DaUtils.getAdvType(codeIndex, index)) {
                case DaConstan.CSJ:
                    TTVideoUtils.playCsjAd(activity, codeIndex, index, daVideoPlayCallBack);
                    break;
                default:
                    if (daVideoPlayCallBack != null) {
                        daVideoPlayCallBack.onDaVideoError("未知错误");
                    }
                    break;
            }
        } else {
            if (daVideoPlayCallBack != null) {
                daVideoPlayCallBack.onDaVideoError("没有更多广告配置了");
            }
        }
    }
}
