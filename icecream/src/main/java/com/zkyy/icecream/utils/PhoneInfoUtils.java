package com.zkyy.icecream.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PhoneUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author yeyang
 * @name AdDemo
 * @class name：com.lt.bc.mb.qmks.util
 * @class describe
 * @time 2018/9/25 上午11:02
 * @change
 * @chang time
 * @class describe
 */
public class PhoneInfoUtils {
    /**
     * 手机是否已经root
     *
     * @return
     */
    public static boolean isRooted() {
        return DeviceUtils.isDeviceRooted();
    }

    /**
     * 获得系统版本
     */
    public static String getSystemVersion() {
        return Build.VERSION.SDK;
    }


    /**
     * 获得IMSI
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        try {
            String imsi = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获得Androidid
     *
     * @param context
     * @return
     */
    public static String getAndroidid(Context context) {
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        return ANDROID_ID;
    }

    public static String getCCID(Context context) {
        String subString = "";
        try {

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String ccid = tm.getSimSerialNumber();
            if (!TextUtils.isEmpty(ccid)) {
                if (ccid.startsWith("898600")) {
                    // 移动
                    if (ccid.length() > 10) {
                        subString = ccid.substring(8, 10);
                    }
                } else if (ccid.startsWith("898603")) {
                    // 电信
                    if (ccid.length() > 13) {
                        subString = ccid.substring(10, 13);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subString;
    }

    /**
     * 获得运营商
     *
     * @param context
     * @return
     */
    public static String getOperatorName(Context context) {
        String ProvidersName = "unknown";
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = getIMSI(context);
        if (TextUtils.isEmpty(IMSI)) {
            // 如果imsi没获取到的话，根据ccid去判断
            String iccid = getCCID(context);
            if (TextUtils.isEmpty(iccid)) {
                return "unknown";
            } else {
                if (iccid.startsWith("898600") || iccid.startsWith("898602")) {
                    // 移动
                    ProvidersName = "CMCC";
                } else if (iccid.startsWith("898603")) {
                    // 电信
                    ProvidersName = "CUCC";
                } else if (iccid.startsWith("898601")) {
                    // 联通
                    ProvidersName = "CTCC";
                }
                return ProvidersName;
            }
        }
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46020")
                || IMSI.startsWith("46007")) {
            ProvidersName = "CMCC";
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            ProvidersName = "CUCC";
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005") || IMSI.startsWith("46011")) {
            ProvidersName = "CTCC";
        }

        return ProvidersName;
    }

    /**
     * 获取渠道号，正式使用时 渠道号从androidmaninest文件中取
     *
     * @param context
     * @return
     */
//    public static String getChannel(Context context) {
//        String channel = "test_channel";
//        try {
//            channel = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("UMENG_CHANNEL");
//        } catch (Exception e) {
//        }
//        return channel;
//    }


    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态
     */

    public static String getNetWorkType(Context context) {
        NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
        switch (networkType) {
            case NETWORK_WIFI:
                return "WIFI";
            case NETWORK_4G:
                return "4G";
            case NETWORK_3G:
                return "3G";
            case NETWORK_2G:
                return "2G";
            default:
                return "unknow";
        }
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startInstallPermissionSettingActivity(Context mContext) {
        // 注意这个是8 .0 新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + mContext.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 获得设备的厂商品牌
     *
     * @return
     */
    public static String getDeviceBrand() {
        String brand = Build.BRAND;
        String model = Build.MODEL;
        if (TextUtils.isEmpty(brand)) {
            brand = "unknown" + " unknown";
        }
        return brand + " " + model;
    }


    /**
     * 获取当前程序的版本号
     *
     * @param cx
     * @return
     */
    public static String getVersionName(Context cx) {
        return getPackageInfo(cx).versionName;
    }

    /**
     * Util
     * 获取当前程序的内部版本号
     *
     * @param cx
     * @return
     */
    public static int getVersionCode(Context cx) {
        return getPackageInfo(cx).versionCode;
    }


    /**
     * 获得包信息
     *
     * @param c
     * @return
     */
    private static PackageInfo getPackageInfo(Context c) {
        try {
            return c.getPackageManager().getPackageInfo(c.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getImei(Context context) {
        String imei = PhoneUtils.getIMEI();
        if (TextUtils.isEmpty(imei)) {
            TelephonyInfo telInfo = TelephonyInfo.getInstance(context);
            if (telInfo.isDualSIM()) {
                if (TextUtils.isEmpty(telInfo.getImeiSIM1())) {
                    imei = telInfo.getImeiSIM2();
                }
            } else {
                imei = telInfo.getImeiSIM1();
            }

            if (TextUtils.isEmpty(imei)) {
                String mac = getMacAddress(context);
                if (!TextUtils.isEmpty(mac)) {
                    imei = EncryptUtils.encryptMD5ToString(mac).substring(0, 15);
                    return imei;
                }

                mac = getWifiMac(context);
                if (!TextUtils.isEmpty(mac)) {
                    imei = EncryptUtils.encryptMD5ToString(mac).substring(0, 15);
                    return imei;
                }
            }
        }
        return imei;
    }

    public static String getMacAddress(Context context) {
        if (context == null) {
            return "";
        }
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        if (wifiMgr != null) {
            info = wifiMgr.getConnectionInfo();
        }
        if (null != info) {
            if (Build.VERSION.SDK_INT >= 23) {
                return getMac();
            } else {
                return info.getMacAddress() == null ? "" : info.getMacAddress();
            }
        }
        return "";
    }

    /**
     * 获取手机的MAC地址
     *
     * @return
     */
    public static String getMac() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return macSerial;
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    public static boolean isInstalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;

        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (packageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getWifiMac(Context context) {
        WifiManager mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifi.isWifiEnabled()) {
            WifiInfo wifiInfo = mWifi.getConnectionInfo();
            if (wifiInfo != null) {
                return wifiInfo.getBSSID();
            }
        }
        return "";
    }


    public static String[] FILTER_MODEL = new String[]{};

    /**
     * 是否为模拟器
     *
     * @param context
     * @return
     */
    public static boolean isEmulator(Context context) {
        List<String> filterModels = Arrays.asList(FILTER_MODEL);
        if (filterModels.contains(Build.MODEL)) {
            return false;
        }
        try {
            String model = getSystemProperties("ro.product.model");
            if ("sdk".equals(model)) {
                return true;
            }

            String tags = getSystemProperties("ro.build.tags");
            if ("test-keys".equals(tags)) {
                return true;
            }
            String qemu = getSystemProperties("ro.kernel.qemu");
            if ("1".equals(qemu)) {
                return true;
            }
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = tm.getDeviceId();
                if (imei == null || imei.equals("000000000000000")) {
                    return true;
                }
                String cpuInfo = readCpuInfo();
                if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
                    return true;
                }

                String build_model = Build.MODEL;
                return build_model.equals("sdk") || build_model.equals("google_sdk");
            } catch (Exception e) {
            }

        } catch (Exception e) {
        }
        return false;
    }

    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        return result;
    }

    public static String getSystemProperties(String key) {
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
            Object obj = method.invoke(cls, new Object[]{key});
            if (obj != null) {
                return String.valueOf(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getApkDownloadPath(Context context) {
        String path = getSuffixPath(context, "apk");

        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        } else if (!filePath.isDirectory()) {
            filePath.delete();
            filePath.mkdirs();
        }

        return path;
    }

    public static String getSuffixPath(Context context, String suffix) {
        String path;
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            if (context == null) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "toutiao" + File.separator + suffix;
            } else
                path = context.getExternalCacheDir().getAbsolutePath() + File.separator + "toutiao" + File.separator + context.getPackageName() + File.separator + suffix;
        } else {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "toutiao" + File.separator + suffix;
        }
        return path;
    }

    public static String getCropImageLocal(Context context) {
        return getSuffixPath(context, "crop") + ".jpg";
    }

    /**
     * 判断是否打开网络     * @param context     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        boolean isAvailable = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            isAvailable = true;
        }
        return isAvailable;
    }

//    public static List<String> getThirdAppList(Context context) {
//        PackageManager packageManager = context.getPackageManager();
//        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
//        // 判断是否系统应用：
//        //List<PackageInfo> apps = new ArrayList<PackageInfo>();
//        List<String> thirdAPP = new ArrayList<>();
//        for (int i = 0; i < packageInfoList.size(); i++) {
//            PackageInfo pak = (PackageInfo) packageInfoList.get(i);
//            //判断是否为系统预装的应用
//            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
//                // 第三方应用
//                // apps.add(pak);
//                thirdAPP.add(pak.packageName);
//            } else {
//                //系统应用
//            }
//        }
//        return thirdAPP;
//    }

    public static String getThirdAppList(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        // 判断是否系统应用：
        //List<PackageInfo> apps = new ArrayList<PackageInfo>();
//        List<String> thirdAPP = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo pak = (PackageInfo) packageInfoList.get(i);
            //判断是否为系统预装的应用
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // 第三方应用
                // apps.add(pak);
//                thirdAPP.add( pak.packageName);
                stringBuilder.append(pak.packageName + ",");
            } else {
                //系统应用
            }
        }
        String str = String.valueOf(stringBuilder);
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

}
