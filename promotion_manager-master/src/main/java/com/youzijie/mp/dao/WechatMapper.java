package com.youzijie.mp.dao;

import com.jami.common.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by lei on 2015/7/15.
 */
public interface WechatMapper extends BaseMapper {
    @Select("SELECT * FROM t_marketing_wechat")
   public List<WechatDo> queryAll();

    @Select("SELECT * FROM t_marketing_wechat WHERE wechatId=#{wechatId};")
    public WechatDo query(@Param("wechatId")String wechatId);

    @Insert("REPLACE INTO t_marketing_wechat (wechatId,wechatName,wechatIcon,wechatBrief,province,city,district, addressCode, appId,appSecret, accessToken)" +
            " VALUES (#{wechatDo.wechatId},#{wechatDo.wechatName},#{wechatDo.wechatIcon},#{wechatDo.wechatBrief},#{wechatDo.province}," +
            "#{wechatDo.city},#{wechatDo.district}, #{wechatDo.addressCode}, #{wechatDo.appId},#{wechatDo.appSecret}, #{wechatDo.accessToken});")
    // @Options(useGeneratedKeys = true, keyProperty = "wechatDo.id")
    public int add(@Param("wechatDo")WechatDo wechatDo);

    @Update("UPDATE t_marketing_wechat SET wechatName=#{wechatDo.wechatName},wechatIcon=#{wechatDo.wechatIcon},wechatBrief=#{wechatDo.wechatBrief}," +
            "province=#{wechatDo.province},city=#{wechatDo.city},district=#{wechatDo.district}, addressCode=#{wechatDo.addressCode}, appId=#{wechatDo.appId}," +
            "appSecret=#{wechatDo.appSecret}," +
            "accessToken = #{wechatDo.accessToken} WHERE wechatId=#{wechatDo.wechatId};")
    public int update(@Param("wechatDo")WechatDo wechatDo);
}