package com.youzijie.pfeeds.dao;

/**
 * Created by lei on 2015/7/16.
 */
public class PromotionConfigDo {
    private Integer id;
    private String province;
    private String city;
    private String district;
    private String promotionConfigJson;

    public PromotionConfigDo() {
        this.province ="";
        this.city = "";
        this.district = "";
        this.promotionConfigJson = "{}";
        //this.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPromotionConfigJson() {
        return promotionConfigJson;
    }

    public void setPromotionConfigJson(String promotionConfigJson) {
        this.promotionConfigJson = promotionConfigJson;
    }
}
