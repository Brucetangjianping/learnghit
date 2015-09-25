package com.youzijie.pfeeds.dao;

/**
 * Created by lei on 2015/7/16.
 */
public class PromotionDo {
    private int id;
    private int promotionId;
    private String address;
    private String promotionConfigJson;

    private String promotionJson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPromotionConfigJson() {
        return promotionConfigJson;
    }

    public void setPromotionConfigJson(String promotionConfigJson) {
        this.promotionConfigJson = promotionConfigJson;
    }

    public String getPromotionJson() {
        return promotionJson;
    }

    public void setPromotionJson(String promotionJson) {
        this.promotionJson = promotionJson;
    }
}
