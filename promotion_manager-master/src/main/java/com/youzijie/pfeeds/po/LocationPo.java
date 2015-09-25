package com.youzijie.pfeeds.po;

import com.youzijie.pfeeds.dao.PromotionConfigDo;

/**
 * Created by lei on 2015/7/15.
 */
public class LocationPo {
    private int id;
    private String province;
    private String city;
    private String district;

    public LocationPo() {

    }

    public LocationPo(PromotionConfigDo promotionConfigDo) {
        this.id = promotionConfigDo.getId();
        this.province = promotionConfigDo.getProvince();
        this.city = promotionConfigDo.getCity();
        this.district = promotionConfigDo.getDistrict();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
