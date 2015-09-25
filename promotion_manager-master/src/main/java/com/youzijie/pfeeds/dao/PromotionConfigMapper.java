package com.youzijie.pfeeds.dao;

import com.jami.common.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PromotionConfigMapper extends BaseMapper {
    @Select("SELECT * FROM t_marketing_promotion_config;")
    public List<PromotionConfigDo> queryAll();

    @Select("SELECT * FROM t_marketing_promotion_config WHERE id=#{id};")
    public PromotionConfigDo query(@Param("id")int id);

    @Select("SELECT * FROM t_marketing_promotion_config WHERE province=#{province} and city=#{city} and district=#{district};")
    public PromotionConfigDo queryByAddress(@Param("province")String province, @Param("city")String city, @Param("district")String district);

    @Insert("INSERT INTO t_marketing_promotion_config (province,city,district,promotionConfigJson)" +
            " VALUES (#{promotionConfigDo.province},#{promotionConfigDo.city},#{promotionConfigDo.district}," +
            "'${promotionConfigDo.promotionConfigJson}');")
    @Options(useGeneratedKeys = true, keyProperty = "promotionConfigDo.id")
    public int add(@Param("promotionConfigDo")PromotionConfigDo promotionConfigDo);

    @Update("UPDATE t_marketing_promotion_config SET province=#{promotionConfigDo.province},city=#{promotionConfigDo.city}," +
            "district=#{promotionConfigDo.district},promotionConfigJson='${promotionConfigDo.promotionConfigJson}'" +
            " WHERE id=#{promotionConfigDo.id};")
    public int update(@Param("promotionConfigDo")PromotionConfigDo promotionConfigDo);
}
