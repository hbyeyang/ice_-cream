package com.zkyy.icecream.net;


import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.constan.AdLoc;
import com.zkyy.icecream.constan.PhoneConstan;
import com.zkyy.icecream.utils.HttpParameters;
import com.zkyy.icecream.utils.LogUtils;
import com.zkyy.icecream.utils.SPAdUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author yeyang
 * @name AdDemo
 * @class name：com.lt.bc.mb.qmks.net
 * @class describe
 * @time 2018/9/25 下午1:52
 * @change
 * @chang time
 * @class describe
 */
public class NetAddress {


    // 测试环境
//    private static String BASE_URL_TEST = "http://api.sdk.alpha.letupower.cn/api.php?r=api/index";
    private static String BASE_URL_TEST = "https://ime.letupower.cn/";

    public static String getDaData(){
        return BASE_URL_TEST+"ad/list";
    }

    public static String getDaUpLoadData(){
        return BASE_URL_TEST+"ad/post-data";
    }


    // 线上环境
    private static String BASE_URL_ONLINE = "https://api.ic.letupower.cn/api.php?r=api/index";

    public static String BASE_URL = getUrl(DaUtils.netadress);

    // 测试环境加密
//    public static String BASE_URL = "/2P0Ldmm3fRI3O6igQziGxMQllkgchZv/jAMqUlXs6qWkJ2MWRkENwg3suP+d2R9WM6UIKpZBuA=";

    // 线上环境加密
//    public static String BASE_URL = "IFqDq40P/23nFQvze2RdUw+2bpRhpOJzqBjAhzgzOyQU2OcLzOVkku7xUGVbkP1c";

    //旧域名
//    public static String BASE_URL = "http://api.ad.letupower.cn/api.php?r=api/index";


//    old:广告位|广告位id|操作|状态|广告主|时间
//    new:广告位|广告位id|操作|状态|广告主|次数|时间
//    时间、次数可以为空 时间空-服务器当时时间，次数空 默认1


    /**
     * code100 -- schannelid app参数不对 全民K书->100,
     * code101 -- achannelid 没有推广渠道标识 **_*_*_vivo
     * code102 -- achannelid推广标识未配置或不合法
     * code103 -- apkgname 包名没有传
     * code104 -- 服务端异常
     */

    private static String getUrl(boolean flag) {
        if (flag) {
            return BASE_URL_TEST;
        } else {
            return BASE_URL_ONLINE;
        }
    }

    /**
     * 参数拼接 广告位|代码位ID|操作|成功失败|广告类型
     * <p>
     * <p>
     * 操作   1.请求、2.展示、3、点击、4、请求素材
     * 成功   1           失败      0
     */

    public static void uploadingAd(final HashMap<String, String> parmas) {
        try {
            final StringBuilder mStrBuffer = new StringBuilder();
            LogUtils.d("上报广告url：" + parmas.toString());
            OkHttp3Utils.doPost(NetAddress.getDaUpLoadData(), parmas, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String s = parmas.get(PhoneConstan.ADATA);
                    mStrBuffer.append(SPAdUtils.getString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, ""));
                    mStrBuffer.append(s + "|" + System.currentTimeMillis() + ",");
                    SPAdUtils.saveString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, mStrBuffer.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtils.d("广告上传成功" + response.body().string());
                    SPAdUtils.saveString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, "");
                }
            });
        } catch (Exception e) {
            SPAdUtils.saveString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, "");
        }
    }

    /**
     * 单独上报广告封装(一次拉取多条广告时用的)
     *
     * @param adNum         广告位置
     * @param adCode        广告ID
     * @param operation     操作  1.请求、2.展示、3、点击、4、请求素材
     * @param succeedOrFail 1成功      0失败
     * @param adType        1百度 2广点通
     * @param requestNum    请求次数
     * @param adId          请求次数
     */
    public static void uploadingSignAdNative(int adNum, String adCode, int operation, int succeedOrFail, int adType, int requestNum, int adId) {
        try {
            final StringBuilder mStrBuffer = new StringBuilder();
            final HashMap<String, String> parmas = HttpParameters.getParams(DaUtils.getInstance().mContext, 0);
            parmas.put(PhoneConstan.ADATA, adNum + "|" + adCode + "|" + operation + "|" + succeedOrFail + "|" + adType + "|" + requestNum + "|" + adId);
            LogUtils.d("单独上报广告url：" + parmas.toString());
            OkHttp3Utils.doPost(NetAddress.getDaUpLoadData(), parmas, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String s = parmas.get(PhoneConstan.ADATA);
                    mStrBuffer.append(SPAdUtils.getString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, ""));
                    mStrBuffer.append(s + "|" + System.currentTimeMillis() + ",");
                    SPAdUtils.saveString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG,
                            mStrBuffer.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtils.d("单独广告上传成功" + response.body().string());
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * 单独上报广告封装
     *
     * @param adNum         广告位置
     * @param adCode        广告ID
     * @param operation     操作  1.请求、2.展示、3、点击、4、请求素材
     * @param succeedOrFail 1成功      0失败
     * @param adType        1百度 2广点通
     */
    public static void uploadingSignAd(int adNum, String adCode, int operation, int succeedOrFail, String adType, int requestNum, int adId) {
        try {
            final StringBuilder mStrBuffer = new StringBuilder();
            final HashMap<String, String> parmas = HttpParameters.getParams(DaUtils.getInstance().mContext, 0);
            parmas.put(PhoneConstan.ADATA, adNum + "|" + adCode + "|" + operation + "|" + succeedOrFail + "|" + adType + "|" + requestNum + "|" + adId);
            LogUtils.d("单独上报广告url：" + parmas.toString());
            OkHttp3Utils.doPost(NetAddress.getDaUpLoadData(), parmas, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String s = parmas.get(PhoneConstan.ADATA);
                    mStrBuffer.append(SPAdUtils.getString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, ""));
                    mStrBuffer.append(s + "|1|" + System.currentTimeMillis() + ",");
                    SPAdUtils.saveString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG,
                            mStrBuffer.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtils.d("单独广告上传成功" + response.body().string());
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 单独上报广告封装(新封装，旧的可以过时了)
     *
     * @param codeIndex     广告位置
     * @param index         广告位置
     * @param operation     操作  1.请求、2.展示、3、点击、4、请求素材
     * @param succeedOrFail 1成功      0失败
     */
    public static void newUploadingSignAd(int codeIndex, int index, int operation, int succeedOrFail) {
        try {
            final StringBuilder mStrBuffer = new StringBuilder();
            final HashMap<String, String> parmas = HttpParameters.getParams(DaUtils.getInstance().mContext, 0);
            parmas.put(PhoneConstan.ADATA, codeIndex + "|" + DaUtils.getAdCode(codeIndex,index) + "|" + operation + "|" + succeedOrFail + "|" + DaUtils.getAdvType(codeIndex,index) + "|" + 1 + "|" + DaUtils.getCodeAdId(codeIndex,index));
            LogUtils.d("单独上报广告url：" + parmas.toString());
            OkHttp3Utils.doPost(NetAddress.getDaUpLoadData(), parmas, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String s = parmas.get(PhoneConstan.ADATA);
                    mStrBuffer.append(SPAdUtils.getString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, ""));
                    mStrBuffer.append(s + "|1|" + System.currentTimeMillis() + ",");
                    SPAdUtils.saveString(DaUtils.getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG,
                            mStrBuffer.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtils.d("单独广告上传成功" + response.body().string());
                }
            });
        } catch (Exception e) {
        }
    }

}
