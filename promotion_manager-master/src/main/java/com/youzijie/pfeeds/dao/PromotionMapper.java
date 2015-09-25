package com.youzijie.pfeeds.dao;

import com.jami.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
public interface PromotionMapper extends BaseMapper {
    @Select("SELECT * FROM t_marketing_promotion_config LEFT JOIN t_marketing_promotion ON t_marketing_promotion.promotionId=t_marketing_promotion_config.id" +
            " WHERE t_marketing_promotion_config.id=#{promotionId};")
    public PromotionDo query(@Param("promotionId") int promotionId);
}
