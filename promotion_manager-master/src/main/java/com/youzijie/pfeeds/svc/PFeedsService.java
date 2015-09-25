package com.youzijie.pfeeds.svc;

import com.jami.exception.BusinessException;
import com.youzijie.pfeeds.po.LocationPo;
import com.youzijie.pfeeds.po.PromotionConfigPo;
import com.youzijie.pfeeds.po.PromotionPo;

import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
public interface PFeedsService {
    public PromotionPo queryPromotion(int locationId) throws BusinessException;

    public void update(int locationId, String promotionConfig) throws BusinessException;

    public int addLocation(String province, String city, String district) throws BusinessException;

    public List<LocationPo> queryLocations() throws BusinessException;
}
