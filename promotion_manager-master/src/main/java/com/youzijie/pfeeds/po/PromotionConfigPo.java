package com.youzijie.pfeeds.po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
public class PromotionConfigPo {
    private String promotionTitle;

    private List<PFeedsConfigPo> feeds;

    public PromotionConfigPo() {
        promotionTitle = "";
        feeds = new ArrayList<PFeedsConfigPo>();
    }

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public List<PFeedsConfigPo> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<PFeedsConfigPo> feeds) {
        this.feeds = feeds;
    }
}
