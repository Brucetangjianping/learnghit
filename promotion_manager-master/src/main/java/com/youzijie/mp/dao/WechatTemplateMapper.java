package com.youzijie.mp.dao;
import com.jami.common.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface WechatTemplateMapper extends BaseMapper {
    @Select("SELECT * FROM t_marketing_wechat_template WHERE wechatId=#{wechatId};")
    public WechatTemplateDo query(@Param("wechatId")String wechatId);

    @Insert("INSERT INTO t_marketing_wechat_template (wechatId,templates) VALUES " +
            "(#{wechatTemplateDo.wechatId},'${wechatTemplateDo.templates}');")
    public int add(@Param("wechatTemplateDo")WechatTemplateDo wechatTemplateDo);

    @Update("UPDATE t_marketing_wechat_template SET templates='${wechatTemplateDo.templates}'" +
            " WHERE wechatId=#{wechatTemplateDo.wechatId};")
    public int update(@Param("wechatTemplateDo")WechatTemplateDo wechatTemplateDo);
}
