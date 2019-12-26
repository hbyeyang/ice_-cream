package com.zkyy.icecream.bean;

/**
 * @author yeyang
 * @name Letu_KBooks_App_Android
 * @class name：com.lt.ad.library.bean
 * @class describe
 * @time 2019/2/19 8:30 PM
 * @change
 * @chang time
 * @class describe
 */
public class PhoneInfoBean {
    private String imei;
    private String device_id;
    private String nt;
    private String apps;
    private String os;

    public PhoneInfoBean() {
    }

    public PhoneInfoBean(String imei, String device_id, String nt, String apps, String os) {
        this.imei = imei;
        this.device_id = device_id;
        this.nt = nt;
        this.apps = apps;
        this.os = os;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
    }

    public String getApps() {
        return apps;
    }

    public void setApps(String apps) {
        this.apps = apps;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
