package com.youzijie.pfeeds.po;

import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
public class PromotionPo {
    private String promotionTitle;

    private List<PFeedsPo> feeds;

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public List<PFeedsPo> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<PFeedsPo> feeds) {
        this.feeds = feeds;
    }

    public void updateTitle(String url, String title) {
        for (PFeedsPo pFeedsPo : feeds) {
            if (pFeedsPo.getUrl().equals(url)) {
                pFeedsPo.setTitle(title);
            }
        }
    }
}
