package com.zkyy.icecream.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeyang
 * @name AdDemo
 * @class name：com.lt.bc.mb.qmks.bean
 * @class describe
 * @time 2018/9/25 下午3:24
 * @change
 * @chang time
 * @class describe
 */
public class AdConfigBean implements Serializable {


    /**
     * ret : succ
     * data : [{"ads":[{"ad_type":10001,"ad_code":"801121648","app_id":"5001121","app_key":"","ad_id":3}],"pos_id":1001},{"ads":[{"ad_type":10001,"ad_code":"801121648","app_id":"5001121","app_key":"","ad_id":3},{"ad_type":10001,"ad_code":"801121648000000","app_id":"5001121","app_key":"","ad_id":4}],"pos_id":1001},{"ads":[{"ad_type":10001,"ad_code":"801121648","app_id":"5001121","app_key":"","ad_id":3},{"ad_type":10001,"ad_code":"801121648000000","app_id":"5001121","app_key":"","ad_id":4},{"ad_type":10003,"ad_code":"901121423","app_id":"5001121","app_key":"","ad_id":5}],"pos_id":1002}]
     */

    private String ret;
    private List<DataEntity> data;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AdConfigBean{" +
                "ret='" + ret + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataEntity implements Serializable{
        /**
         * ads : [{"ad_type":10001,"ad_code":"801121648","app_id":"5001121","app_key":"","ad_id":3}]
         * pos_id : 1001
         */

        private int pos_id;
        private List<AdsEntity> ads;

        public int getPos_id() {
            return pos_id;
        }

        public void setPos_id(int pos_id) {
            this.pos_id = pos_id;
        }

        public List<AdsEntity> getAds() {
            return ads;
        }

        public void setAds(List<AdsEntity> ads) {
            this.ads = ads;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "pos_id=" + pos_id +
                    ", ads=" + ads +
                    '}';
        }
    }

    public static class AdsEntity implements Serializable{
        /**
         * adv_type : 10001
         * ad_code : 801121648
         * app_id : 5001121
         * app_key :
         * ad_id : 3
         */

        private String adv_type;
        private String ad_code;
        private String app_id;
        private String app_key;
        private int ad_id;

        public String getAdv_type() {
            return adv_type;
        }

        public void setAdv_type(String adv_type) {
            this.adv_type = adv_type;
        }

        public String getAd_code() {
            return ad_code;
        }

        public void setAd_code(String ad_code) {
            this.ad_code = ad_code;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getApp_key() {
            return app_key;
        }

        public void setApp_key(String app_key) {
            this.app_key = app_key;
        }

        public int getAd_id() {
            return ad_id;
        }

        public void setAd_id(int ad_id) {
            this.ad_id = ad_id;
        }

        @Override
        public String toString() {
            return "AdsEntity{" +
                    "adv_type='" + adv_type + '\'' +
                    ", ad_code='" + ad_code + '\'' +
                    ", app_id='" + app_id + '\'' +
                    ", app_key='" + app_key + '\'' +
                    ", ad_id=" + ad_id +
                    '}';
        }
    }
}
