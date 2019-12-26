package com.zkyy.icecream.dautil;

import android.app.Activity;

import com.zkyy.icecream.callback.DaVideoPlayCallBack;
import com.zkyy.icecream.constan.DaAdvertiserType;
import com.zkyy.icecream.ttutil.TTVideoUtils;
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
     * @param daAdvertiserType    广告主 枚举  DaAdvertiserType
     * @param adCode              广告位ID
     * @param daVideoPlayCallBack 回调
     */
    public static void play(Activity activity, DaAdvertiserType daAdvertiserType, String adCode, DaVideoPlayCallBack daVideoPlayCallBack) {
        if (activity == null && daVideoPlayCallBack != null) {
            LogUtils.d(TAG + "activity不能为空");
            daVideoPlayCallBack.onDaVideoError("未知错误");
            return;
        }
        if (daAdvertiserType == null && daVideoPlayCallBack != null) {
            LogUtils.d(TAG + "daAdvertiserType不能为空");
            daVideoPlayCallBack.onDaVideoError("未知错误");
            return;
        }
        if (adCode == null && daVideoPlayCallBack != null) {
            LogUtils.d(TAG + "adCode不能为空");
            daVideoPlayCallBack.onDaVideoError("未知错误");
            return;
        }
        if (daVideoPlayCallBack == null && daVideoPlayCallBack != null) {
            LogUtils.d(TAG + "daVideoPlayCallBack不能为空");
            daVideoPlayCallBack.onDaVideoError("未知错误");
            return;
        }
        switch (daAdvertiserType) {
            case BD:
                break;
            case CSJ:
                TTVideoUtils.playCsjAd(activity, adCode, true, daVideoPlayCallBack);
                break;
            case GDT:
                break;
            case MV:
                break;
        }
    }
}
