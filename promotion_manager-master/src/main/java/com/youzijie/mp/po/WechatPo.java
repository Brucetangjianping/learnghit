package com.youzijie.mp.po;

import com.youzijie.mp.dao.WechatDo;

/**
 * Created by lei on 2015/7/16.
 */
public class WechatPo {
    private String wechatId;
    private String wechatName;
    private String province;
    private String city;
    private String district;
    private String appId;
    private String appSecret;

    public WechatPo() {}

    public WechatPo(String wechatId, String wechatName, String province, String city, String district, String appId, String appSecret) {
        this.wechatId = wechatId;
        this.wechatName = wechatName;
        this.province = province;
        this.city = city;
        this.district = district;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public WechatPo(WechatDo wechatDo) {
        this.wechatId = wechatDo.getWechatId();
        this.wechatName = wechatDo.getWechatName();
        this.province = wechatDo.getProvince();
        this.city = wechatDo.getCity();
        this.district = wechatDo.getDistrict();
        this.appId = wechatDo.getAppId();
        this.appSecret = wechatDo.getAppSecret();
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
