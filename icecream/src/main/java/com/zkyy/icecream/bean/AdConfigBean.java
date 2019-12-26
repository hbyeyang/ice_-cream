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

    @Override
    public String toString() {
        return "AdConfigBean{" +
                "ret='" + ret + '\'' +
                ", data=" + data +
                ", tips='" + tips + '\'' +
                '}';
    }

    /**
     * ret : succ
     * data : {"ctime":"2018-09-25 09:23:03","rules":[{"sign":1,"adids":[{"stype":1,"appid":"f5a67067","adid":"5902551"}]},{"sign":2,"adids":[{"stype":2,"appid":"1107203022","adid":"2030239885771016"}]}]}
     */

    private String ret;
    private DataEntity data;
    private String tips;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable {
        @Override
        public String toString() {
            return "DataEntity{" +
                    "ctime='" + ctime + '\'' +
                    ", rules=" + rules +
                    '}';
        }

        /**
         * ctime : 2018-09-25 09:23:03
         * rules : [{"sign":1,"adids":[{"stype":1,"appid":"f5a67067","adid":"5902551"}]},{"sign":2,"adids":[{"stype":2,"appid":"1107203022","adid":"2030239885771016"}]}]
         */

        private String ctime;
        private String passport;
        private String fhlopen;
        private List<RulesEntity> rules;

        public String getFhlopen() {
            return fhlopen;
        }

        public void setFhlopen(String fhlopen) {
            this.fhlopen = fhlopen;
        }

        public String getPassport() {
            return passport;
        }

        public void setPassport(String passport) {
            this.passport = passport;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public List<RulesEntity> getRules() {
            return rules;
        }

        public void setRules(List<RulesEntity> rules) {
            this.rules = rules;
        }


    }
    public static class RulesEntity implements Serializable {
        @Override
        public String toString() {
            return "RulesEntity{" +
                    "sign=" + sign +
                    ", adids=" + adids +
                    ", bhour='" + bhour + '\'' +
                    ", ehour='" + ehour + '\'' +
                    ", fnum=" + fnum +
                    '}';
        }

        /**
         * sign : 1 百度  2   广点通
         * adids : [{"stype":1,"appid":"f5a67067","adid":"5902551"}]
         */

        private int sign;
        private List<AdidsEntity> adids;

        private String bhour = "00:00";
        private String ehour = "00:00";
        private int fnum = 0;

        public String getBhour() {
            return bhour;
        }

        public void setBhour(String bhour) {
            this.bhour = bhour;
        }

        public String getEhour() {
            return ehour;
        }

        public void setEhour(String ehour) {
            this.ehour = ehour;
        }

        public int getFnum() {
            return fnum;
        }

        public void setFnum(int fnum) {
            this.fnum = fnum;
        }

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public List<AdidsEntity> getAdids() {
            return adids;
        }

        public void setAdids(List<AdidsEntity> adids) {
            this.adids = adids;
        }


    }
    public static class AdidsEntity implements Serializable {
        /**
         * stype : 1
         * appid : f5a67067
         * adid : 5902551
         */

        private int stype = -1;
        private String appid;
        private String adid;
        private String secret;

        @Override
        public String toString() {
            return "AdidsEntity{" +
                    "stype=" + stype +
                    ", appid='" + appid + '\'' +
                    ", adid='" + adid + '\'' +
                    ", secret='" + secret + '\'' +
                    '}';
        }

        public int getStype() {
            return stype;
        }

        public void setStype(int stype) {
            this.stype = stype;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

}
