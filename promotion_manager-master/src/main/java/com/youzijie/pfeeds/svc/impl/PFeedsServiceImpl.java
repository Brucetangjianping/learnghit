package com.youzijie.pfeeds.svc.impl;

import com.alibaba.fastjson.JSON;
import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.youzijie.pfeeds.dao.PromotionConfigDo;
import com.youzijie.pfeeds.dao.PromotionConfigMapper;
import com.youzijie.pfeeds.dao.PromotionDo;
import com.youzijie.pfeeds.dao.PromotionMapper;
import com.youzijie.pfeeds.po.LocationPo;
import com.youzijie.pfeeds.po.PFeedsPo;
import com.youzijie.pfeeds.po.PromotionConfigPo;
import com.youzijie.pfeeds.po.PromotionPo;
import com.youzijie.pfeeds.svc.PFeedsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */

@Service("pFeedsService")
public class PFeedsServiceImpl implements PFeedsService {
    @Resource
    PromotionConfigMapper promotionConfigMapper;

    @Resource
    PromotionMapper promotionMapper;

    @Override
    public PromotionPo queryPromotion(int locationId) throws BusinessException {
        PromotionDo promotionDo = promotionMapper.query(locationId);
        if (promotionDo == null) {
            throw BusinessException.createInstance(BizErrorCode.LOCATION_NOT_EXIST);
        }
        String str = promotionDo.getPromotionConfigJson();
        PromotionPo promotionPo = new PromotionPo();

        try {
            promotionPo = JSON.parseObject(str, PromotionPo.class);
            if (!StringUtils.isBlank(promotionDo.getPromotionJson())) {
                PromotionPo po = JSON.parseObject(promotionDo.getPromotionJson(), PromotionPo.class);
                for (PFeedsPo pFeedsPo : po.getFeeds()) {
                    promotionPo.updateTitle(pFeedsPo.getUrl(), pFeedsPo.getTitle());
                }
            }
        } catch (Exception e) {

        }
        return promotionPo;
    }

    @Override
    public void update(int locationId, String promotionConfig) throws BusinessException {
        PromotionConfigDo promotionConfigDo = promotionConfigMapper.query(locationId);
        if (promotionConfigDo == null) {
            throw BusinessException.createInstance(BizErrorCode.LOCATION_NOT_EXIST);
        }
        PromotionConfigPo promotionConfigPo = JSON.parseObject(promotionConfig, PromotionConfigPo.class);
        if (promotionConfigPo == null) {
            throw BusinessException.createInstance(BizErrorCode.PARAMS_ERROR);
        }

        promotionConfigDo.setPromotionConfigJson(JSON.toJSONString(promotionConfigPo));
        promotionConfigMapper.update(promotionConfigDo);
    }

    @Override
    public int addLocation(String province, String city, String district) throws BusinessException {
        PromotionConfigDo promotionConfigDo = promotionConfigMapper.queryByAddress(province, city, district);
        if (promotionConfigDo != null) {
            throw BusinessException.createInstance(BizErrorCode.LOCATION_EXISTED);
        }
        promotionConfigDo = new PromotionConfigDo();
        promotionConfigDo.setProvince(province);
        promotionConfigDo.setCity(city);
        promotionConfigDo.setDistrict(district);

        promotionConfigMapper.add(promotionConfigDo);

        return promotionConfigDo.getId();
    }

    @Override
    public List<LocationPo> queryLocations() throws BusinessException {
        List<PromotionConfigDo> promotionConfigDos = promotionConfigMapper.queryAll();
        List<LocationPo> locationPos = new ArrayList<LocationPo>();

        for (PromotionConfigDo promotionConfigDo : promotionConfigDos) {
            locationPos.add(new LocationPo(promotionConfigDo));
        }

        return locationPos;
    }
}
