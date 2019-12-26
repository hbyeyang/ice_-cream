package com.zkyy.icecream.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 项目名称：yueduTest
 * 包名:com.yuereader.utils
 * 类名:SPAdUtils
 * 类描述：简单数据存储/取出
 * 创建人：YY
 * 创建时间：16/4/22 上午11:04
 * 修改人：YY(hbyeyang@yeah.net)
 * 修改时间：16/4/22 上午11:04
 */
public class SPAdUtils {
    public static final String SP_NAME = "yy_ad_config";
    private static SharedPreferences sp;

    public static void saveBoolean(Context ct, String key, boolean value) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ct, String key, boolean defValue) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);

        return sp.getBoolean(key, defValue);

    }

    public static void saveString(Context ct, String key, String value) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context ct, String key, String defValue) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);

        return sp.getString(key, defValue);
    }

    public static void saveInt(Context ct, String key, int value) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);

        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ct, String key, int defValue) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);

        return sp.getInt(key, defValue);
    }

    public static Long getLong(Context ct, String key, long defValue) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);

        return sp.getLong(key, defValue);
    }

    public static void saveLong(Context ct, String key, long defValue) {
        if (sp == null)
            sp = ct.getSharedPreferences(SP_NAME, 0);

        sp.edit().putLong(key, defValue).commit();
    }

    /**
     * @param context
     * @param key
     * @param obj
     */
    public static void saveObject(Context context, String key, Object obj) {
        try {
            // 保存对象
            if (sp == null)
                sp = context.getSharedPreferences(SP_NAME, 0);
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sp.edit().putString(key, bytesToHexString).commit();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d("IOException--->>>> " + e.toString());
        }
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param context
     * @param key
     * @return modified:
     */
    public static Object getObject(Context context, String key) {
        try {
            if (sp == null)
                sp = context.getSharedPreferences(SP_NAME, 0);
            if (sp.contains(key)) {
                String string = sp.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (Exception e) {
            LogUtils.d("Exception--->>>> " + e.toString());
        }
        //所有异常返回null
        return null;

    }

    /**
     * desc:将16进制的数据转为数组
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
