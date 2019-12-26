package com.zkyy.icecream.utils;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.constan.AdLoc;
import com.zkyy.icecream.constan.PhoneConstan;

import java.util.HashMap;

/**
 * @author yeyang
 * @name Letu_KBooks_App_Android
 * @class name：com.lt.ad.library.util
 * @class describe
 * @time 2019/1/20 11:07 AM
 * @change
 * @chang time
 * @class describe
 */
public class HttpParameters {

    private static HashMap<String, String> baseParams;

    public static HashMap<String, String> getParams(Context mContext, int type) {
        if (baseParams == null) {
            baseParams = getBaseParams(mContext);
        }
        if (type == 0) {
            baseParams.put(PhoneConstan.ISINDEX, "0");
        } else if (type == 1) {
            baseParams.put(PhoneConstan.ISINDEX, "1");
        }
        HashMap<String, String> params = (HashMap<String, String>) baseParams.clone();
        return params;
    }
    /**
     * 组装基本参数
     *
     * @param mContext
     * @return
     */
    private static HashMap<String, String> getBaseParams(Context mContext) {
        HashMap<String, String> baseParams = new HashMap();
        try {
            if (baseParams == null) {
                baseParams = new HashMap<>();
            }

            if (!TextUtils.isEmpty(SPAdUtils.getString(mContext, AdLoc.AD_MD5_BS, ""))) {
                baseParams.put(PhoneConstan.TSIGN, SPAdUtils.getString(mContext, AdLoc.AD_MD5_BS, ""));
            } else {
                String guid = MyUtils.GetGUID();
                SPAdUtils.saveString(mContext, AdLoc.AD_MD5_BS, guid);
                baseParams.put(PhoneConstan.TSIGN, guid);
            }
            if (!TextUtils.isEmpty(SPAdUtils.getString(mContext, AdLoc.PASS_PORT, ""))) {
                baseParams.put(PhoneConstan.PASSPORT, SPAdUtils.getString(mContext, AdLoc.PASS_PORT, ""));
            } else {
                baseParams.put(PhoneConstan.PASSPORT, "");//初次请求没有，之后有，待后面沟通确认
            }
            baseParams.put(PhoneConstan.APKGNAME, AppUtils.getAppPackageName());
//            baseParams.put(PhoneConstan.SCHANNELID, "100");//现在因为是自己用，配置默认100，如果其他，需要问
            baseParams.put(PhoneConstan.SCHANNELID, DaUtils.getInstance().getAchannel());//现在因为是自己用，配置默认100，如果其他，需要问
            baseParams.put(PhoneConstan.AVERNAME, PhoneInfoUtils.getVersionName(mContext));
            baseParams.put(PhoneConstan.AVERCODE, String.valueOf(PhoneInfoUtils.getVersionCode(mContext)));
//            baseParams.put(PhoneConstan.ACHANNELID, PhoneInfoUtils.getChannel(mContext));
            baseParams.put(PhoneConstan.ACHANNELID, DaUtils.getInstance().getChannel());

            baseParams.put(PhoneConstan.VERCODE, "10");//自己定的，暂不确定   历史版本：1/2/3/4
            baseParams.put(PhoneConstan.VERNAME, "2.0");//自己定的，暂不确定 历史版本号：1.0/1.1/1.2/1.3

            baseParams.put(PhoneConstan.IMEI, PhoneInfoUtils.getImei(mContext));
            baseParams.put(PhoneConstan.MAC, PhoneInfoUtils.getMacAddress(mContext));
            baseParams.put(PhoneConstan.ANDROID_ID, PhoneInfoUtils.getAndroidid(mContext));
            baseParams.put(PhoneConstan.NET_NAME, PhoneInfoUtils.getOperatorName(mContext));
            baseParams.put(PhoneConstan.NET_STAT, PhoneInfoUtils.getNetWorkType(mContext));
            baseParams.put(PhoneConstan.IS_ROOT, PhoneInfoUtils.isRooted() ? "1" : "0");
            baseParams.put(PhoneConstan.ANDROID_VER, PhoneInfoUtils.getSystemVersion());
            baseParams.put(PhoneConstan.BRAND, PhoneInfoUtils.getDeviceBrand());
            if (PhoneInfoUtils.isEmulator(mContext)) {
                baseParams.put(PhoneConstan.IS_EMULATOR, "1");
            } else {
                baseParams.put(PhoneConstan.IS_EMULATOR, "0");
            }
            return baseParams;
        } catch (Exception e) {
            e.printStackTrace();
            return baseParams;
        }
    }

}
