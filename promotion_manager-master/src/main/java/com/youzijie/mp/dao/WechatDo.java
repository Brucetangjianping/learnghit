package com.youzijie.mp.dao;

/**
 * Created by lei on 2015/7/15.
 */
public class WechatDo {
    private String wechatId;
    private String wechatName;
    private String wechatIcon;
    private String wechatBrief;

    private String province;
    private String city;
    private String district;
    private String addressCode;

    private String appId;
    private String appSecret;
    private String accessToken;

    public WechatDo() {
    }

    public WechatDo(String wechatId, String wechatName, String wechatIcon, String wechatBrief, String province, String city, String district, String addressCode, String appId, String appSecret, String accessToken) {
        this.wechatId = wechatId;
        this.wechatName = wechatName;
        this.wechatIcon = wechatIcon;
        this.wechatBrief = wechatBrief;
        this.province = province;
        this.city = city;
        this.district = district;
        this.appId = appId;
        this.appSecret = appSecret;
        this.accessToken = accessToken;
        this.addressCode = addressCode;
    }

    public void copy(WechatDo wechatDo) {
        if (wechatDo.wechatName != null) {
            this.wechatName = wechatDo.wechatName;
        }
        if (wechatDo.wechatIcon != null) {
            this.wechatIcon = wechatDo.wechatIcon;
        }
        if (wechatDo.wechatBrief != null) {
            this.wechatBrief = wechatDo.wechatBrief;
        }
        if (wechatDo.province != null) {
            this.province = wechatDo.province;
        }
        if (wechatDo.city != null) {
            this.city = wechatDo.city;
        }
        if (wechatDo.district != null) {
            this.district = wechatDo.district;
        }
        if (wechatDo.addressCode != null) {
            this.addressCode = wechatDo.addressCode;
        }

        if (wechatDo.appId != null) {
            this.appId = wechatDo.appId;
        }
        if (wechatDo.appSecret != null) {
            this.appSecret = wechatDo.appSecret;
        }

        if (wechatDo.accessToken != null) {
            this.accessToken = wechatDo.accessToken;
        }
    }

    public boolean updateAddresscode() {
        if (!district.isEmpty()) {
            addressCode = "district:" + district;
        } else if (!city.isEmpty()) {
            addressCode = "city:" + city;
        } else if (!province.isEmpty()) {
            addressCode = "province:" + province;
        } else {
            return false;
        }
        return true;
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

    public String getWechatIcon() {
        return wechatIcon;
    }

    public void setWechatIcon(String wechatIcon) {
        this.wechatIcon = wechatIcon;
    }

    public String getWechatBrief() {
        return wechatBrief;
    }

    public void setWechatBrief(String wechatBrief) {
        this.wechatBrief = wechatBrief;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }
}
