package com.zkyy.icecream;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.zkyy.icecream.bean.AdConfigBean;
import com.zkyy.icecream.config.TTAdManagerHolder;
import com.zkyy.icecream.constan.AdLoc;
import com.zkyy.icecream.constan.PhoneConstan;
import com.zkyy.icecream.net.GsonObjectCallback;
import com.zkyy.icecream.net.NetAddress;
import com.zkyy.icecream.net.OkHttp3Utils;
import com.zkyy.icecream.utils.HttpParameters;
import com.zkyy.icecream.utils.LogUtils;
import com.zkyy.icecream.utils.PhoneInfoUtils;
import com.zkyy.icecream.utils.SPAdUtils;
import com.zkyy.icecream.utils.ThreadPool;
import com.zkyy.icecream.utils.UiThredUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream
 * @class describe
 * @time 2019-12-25 16:29
 * @change
 * @chang time
 * @class describe
 */
public class DaUtils {

    private static String TAG = DaUtils.class.getSimpleName()+": ";

    public static boolean allow = BuildConfig.DEBUG;
    /**
     * false 为线上地址，true 为测试地址
     */
    public static boolean netadress = BuildConfig.DEBUG;


    private static DaUtils mInstans;

    public Application mContext;
    /**
     * 时候获取到广告配置标示
     */
    public volatile boolean isInit = false;
    /**
     * 广告配置
     */
    private AdConfigBean mAdConfigBean;

    /**
     * 渠道（下游，每个应用唯一）
     */
    private String mAchannel;
    /**
     * 推广渠道
     */
    private String mChannel;
//    private String mPackageName;

    /**
     * 获取渠道（下游）
     *
     * @return
     */
    public String getAchannel() {
        return mAchannel;
    }

    /**
     * 推广渠道
     *
     * @return
     */
    public String getChannel() {
        return mChannel;
    }

    /**
     * @param boo  true    打印日志    false   不打印日志
     * @param flag false 为线上地址，true 为测试地址
     */
    public static void setLoggable(Boolean boo, Boolean flag) {
        allow = boo;
        netadress = flag;
    }

    private DaUtils() {
        try {
            final Application app = Utils.getApp();
            mContext = app;
            mAdConfigBean = (AdConfigBean) SPAdUtils.getObject(app, AdLoc.AD_CONFIG);
            if (mAdConfigBean != null) {
                isInit = true;
            }
        } catch (Exception ex) {

        }
    }

    public static DaUtils getInstance() {
        if (mInstans == null) {
            //加同步安全
            synchronized (DaUtils.class) {
                if (mInstans == null) {
                    mInstans = new DaUtils();
                }
            }
        }
        return mInstans;
    }


    /**
     * 获取广告配置
     *
     * @param application
     * @param achannel    请联系商务或者开发者获取渠道
     * @param channel     应用推广的渠道，需要先配置才能生效
     */
    public void getAdUtils(Application application, String achannel, String channel) {
        mContext = application;
        mAchannel = achannel;
        mChannel = channel;
        Utils.init(application);
        if (mAdConfigBean == null) {
            mAdConfigBean = (AdConfigBean) SPAdUtils.getObject(application, AdLoc.AD_CONFIG);
            if (mAdConfigBean != null) {
                isInit = true;
            }
            // TODO: 2020-01-06 改下固定值
            LogUtils.d("TTAdManagerHolder初始化");
            TTAdManagerHolder.init(mContext, "5001121");
        }
        if (!PhoneInfoUtils.getNetWorkType(application).equals("unknow")) {
            //判断本地有没有数据，没有就取网上进行配置，有的话也请求网上，下次生效
            getAdConfig(application);
            uploadAdError(application);
            TTAdManagerHolder.init(mContext, "5001121");
        }
    }

    /**
     * 单开个线程上传广告错误日志
     *
     * @param application
     */
    public void uploadAdError(final Application application) {
        final String mAdErrLog = SPAdUtils.getString(getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, "");
        if (!TextUtils.isEmpty(mAdErrLog)) {
            ThreadPool.run(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d("uploadAdError------>>>> " + mAdErrLog);
                    final HashMap<String, String> params = HttpParameters.getParams(application, 1);
                    params.put(PhoneConstan.ADATA, mAdErrLog);
                    params.put(PhoneConstan.ISINDEX, "0");
                    NetAddress.uploadingAd(params);
                }
            });
        }
    }

    /**
     * 获取配置信息
     *
     * @param application
     */
    private void getAdConfig(final Application application) {
        final HashMap<String, String> mParmas = HttpParameters.getParams(application, 1);
        LogUtils.d("parmas:" + mParmas.toString());
        LogUtils.d("url:" + NetAddress.getDaData());
        OkHttp3Utils.doPost(NetAddress.getDaData(), mParmas, new GsonObjectCallback<AdConfigBean>() {
            @Override
            public void onUi(AdConfigBean adConfigBean) {
                LogUtils.d("当前线程" + UiThredUtils.isOnMainThread());
                if (adConfigBean.getRet() != null && adConfigBean.getRet().equals("succ")) {
                    LogUtils.d(adConfigBean.toString());
                    if (adConfigBean != null) {
                        mAdConfigBean = adConfigBean;
                        isInit = true;
//                        if (adConfigBean.getData() != null && !TextUtils.isEmpty(adConfigBean.getData().getPassport())) {
//                            SPAdUtils.saveString(application, AdLoc.PASS_PORT, adConfigBean.getData().getPassport());
//                        }
                        SPAdUtils.saveObject(application, AdLoc.AD_CONFIG, adConfigBean);
                    }
                } else {
                    if (mAdConfigBean != null) {
                        isInit = true;
                    } else {
                        LogUtils.d("获取AD网络配置失败");
                        isInit = false;
                    }
                }
            }

            @Override
            public void onFailedUi(Call call, IOException e) {
                LogUtils.d("当前线程" + UiThredUtils.isOnMainThread());
                if (mAdConfigBean != null) {
                    isInit = true;
                } else {
                    isInit = false;
                }
            }
        });
    }


//    public void initVideoSdk(Activity activity){
//        if (getInstance().mAdConfigBean != null) {
//            isInit = true;
//            LTVideoAdUtils.initAdSdk(getAdids(27),mContext, activity);
//        }
//    }

    /**
     * 设置调换视频播放顺序
     */
//    public void setAdConfigBean() {
//        AdConfigBean.AdsEntity adsEntity = null;
//        if (getAds() != null && getAds().size() > 27) {
//            List<AdConfigBean.AdsEntity> adids = getAdids(27);
//            if (adids != null && adids.size() > 1) {
//                adsEntity = adids.get(0);
//                LogUtils.d("adIds:" + adsEntity.getStype());
//                LogUtils.d("adIds:" + adids.toString());
//                adids.remove(0);
//                LogUtils.d("adIds:" + adids.toString());
//                adids.add(adsEntity);
//                LogUtils.d("adIds:" + adids.toString());
//                setAdids(27, adids);
//            }
//        }
//    }

    /**
     * 获取配置里面有多少个广告位
     *
     * @return
     */
    private static List<AdConfigBean.DataEntity> getAds() {
        if (getInstance().mAdConfigBean != null) {
            if (getInstance().mAdConfigBean.getData() != null) {
                if (getInstance().mAdConfigBean.getData() != null && getInstance().mAdConfigBean.getData().size() > 0) {
                    return getInstance().mAdConfigBean.getData();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取指定索引位置的多个广告位
     *
     * @param codeIndex
     * @return
     */
    private static List<AdConfigBean.AdsEntity> getAd(int codeIndex) {
        if (getInstance().mAdConfigBean != null) {
            if (getInstance().mAdConfigBean.getData() != null) {
                if (getInstance().mAdConfigBean.getData().get(codeIndex) != null) {
                    if (getInstance().mAdConfigBean.getData().get(codeIndex).getAds() != null && getInstance().mAdConfigBean.getData().get(codeIndex).getAds().size() > 0) {
                        return getInstance().mAdConfigBean.getData().get(codeIndex).getAds();
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取广告配置总数
     *
     * @return
     */
    public static int getAdNum() {
        if (getInstance().mAdConfigBean != null) {
            if (getInstance().mAdConfigBean.getData() != null) {
                if (getInstance().mAdConfigBean.getData() != null && getInstance().mAdConfigBean.getData().size() > 0) {
                    return getInstance().mAdConfigBean.getData().size();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 获取广告位集合（广告位包含几个广告位代码）
     *
     * @param codeIndex
     * @return
     */
    public static List<AdConfigBean.AdsEntity> getAdCodes(int codeIndex) {
        LogUtils.d(TAG+codeIndex);
        final List<AdConfigBean.AdsEntity> adsEntities = getAd(codeIndex);
        if (adsEntities != null) {
            LogUtils.d(TAG+adsEntities.toString());
            return adsEntities;
        } else {
            return null;
        }
    }

    /**
     * 替换某个广告位里得配置e
     *
     * @param position
     * @param adidsEntities
     */
    public static void setAdids(int position, List<AdConfigBean.AdsEntity> adidsEntities) {
        if (getInstance().mAdConfigBean != null) {
            getInstance().mAdConfigBean.getData().get(position).setAds(adidsEntities);
        }
    }

    /**
     * 获取广告主类型
     *
     * @param position
     * @param index
     * @return
     */
    public static String getStype(int position, int index) {
        if (getAdCodes(position) != null && getAdCodes(position).size() > 0) {
            if (index < getAdCodes(position).size()) {
                if (getAdCodes(position).get(index) != null) {
                    if (TextUtils.isEmpty(getAdCodes(position).get(index).getAdv_type())) {
                        return getAdCodes(position).get(index).getAdv_type();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 获取指定索引包含的广告
     *
     * @param position
     * @return
     */
    public static int getCondesSize(int position) {
        if (getAdCodes(position) != null && getAdCodes(position).size() > 0) {
            if (getAdCodes(position) != null && getAdCodes(position).size() > 0) {
                return getAdCodes(position).size();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * 获取APPID
     *
     * @param position
     * @param index
     * @return
     */
    public static String getAppid(int position, int index) {
        if (getAdCodes(position) != null && getAdCodes(position).size() > 0) {
            if (index < getAdCodes(position).size()) {
                if (getAdCodes(position).get(index) != null) {
                    if (!TextUtils.isEmpty(getAdCodes(position).get(index).getApp_id())) {
                        return getAdCodes(position).get(index).getApp_id();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 获取广告类型
     *
     * @param codeIndex
     * @param index
     * @return
     */
    public static String getAdvType(int codeIndex, int index) {
        if (getAdCodes(codeIndex) != null && getAdCodes(codeIndex).size() > 0) {
            if (index < getAdCodes(codeIndex).size()) {
                if (getAdCodes(codeIndex).get(index) != null) {
                    return getAdCodes(codeIndex).get(index).getAdv_type();
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 获取广告Code
     *
     * @param codeIndex
     * @param index
     * @return
     */
    public static String getAdCode(int codeIndex, int index) {
        if (getAdCodes(codeIndex) != null && getAdCodes(codeIndex).size() > 0) {
            if (index < getAdCodes(codeIndex).size()) {
                if (getAdCodes(codeIndex).get(index) != null) {
                    if (!TextUtils.isEmpty(getAdCodes(codeIndex).get(index).getAd_code())) {
                        return getAdCodes(codeIndex).get(index).getAd_code();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }


    public static String getAppKey(int position, int index) {
        if (getAdCodes(position) != null && getAdCodes(position).size() > 0) {
            if (index < getAdCodes(position).size()) {
                if (getAdCodes(position).get(index) != null) {
                    if (!TextUtils.isEmpty(getAdCodes(position).get(index).getApp_key())) {
                        return getAdCodes(position).get(index).getApp_key();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 通过posId获取广告位位置
     *
     * @param pos_id
     * @return
     */
    public static int getCodeIndex(int pos_id) {
        if (getAds() != null && getAds().size() > 0) {
            for (int i = 0; i < getAds().size(); i++) {
                if (pos_id == getAds().get(i).getPos_id()) {
                    return i;
                }
            }
        } else {
            return -1;
        }
        return -1;
    }

    /**
     * 返回广告位置ID
     * @param codeIndex
     * @param index
     * @return
     */
    public static int getCodeAdId(int codeIndex, int index) {
        if (getAdCodes(codeIndex) != null) {
            if (getAdCodes(codeIndex).get(index) != null) {
                return getAdCodes(codeIndex).get(index).getAd_id();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * 返回应用名称
     * @param context
     * @return
     */
    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
