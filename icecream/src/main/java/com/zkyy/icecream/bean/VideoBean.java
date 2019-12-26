package com.zkyy.icecream.bean;

import java.io.Serializable;

/**
 * @author yeyang
 * @name Letu_ime
 * @class name：com.lt.ad.library.bean
 * @class describe
 * @time 2019/3/4 2:03 PM
 * @change
 * @chang time
 * @class describe
 */
public class VideoBean implements Serializable {
    private String Type;

    public VideoBean(String type) {
        Type = type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
