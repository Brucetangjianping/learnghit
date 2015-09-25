package com.youzijie.appmsg.dao;
import com.jami.common.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WechatArticleMapper extends BaseMapper {
    @Select("SELECT * FROM t_marketing_wechat_article WHERE id=#{id};")
    public WechatArticleDo query(@Param("id")Integer id);

    @Select("SELECT * FROM t_marketing_wechat_article WHERE msgId=#{msgId};")
    public List<WechatArticleDo> queryByMsgId(@Param("msgId")Integer msgId);

    @Insert("INSERT INTO t_marketing_wechat_article (msgId,author,title,cover,content,content_source_url,digest) " +
            "VALUES (#{wechatArticleDo.msgId},#{wechatArticleDo.author},#{wechatArticleDo.title},#{wechatArticleDo.cover}," +
            "#{wechatArticleDo.content},#{wechatArticleDo.content_source_url},#{wechatArticleDo.digest});")
    @Options(useGeneratedKeys = true, keyProperty = "wechatArticleDo.id")
    public int add(@Param("wechatArticleDo")WechatArticleDo wechatArticleDo);

    @Update("UPDATE t_marketing_wechat_article SET msgId=#{wechatArticleDo.msgId},author=#{wechatArticleDo.author}," +
            "title=#{wechatArticleDo.title},cover=#{wechatArticleDo.cover},content=#{wechatArticleDo.content}," +
            "content_source_url=#{wechatArticleDo.content_source_url},digest=#{wechatArticleDo.digest} " +
            "WHERE id=#{wechatArticleDo.id};")
    public int update(@Param("wechatArticleDo")WechatArticleDo wechatArticleDo);

    @Update("UPDATE t_marketing_wechat_article SET msgId=0 WHERE msgId=#{msgId};")
    public int clearMsg(@Param("msgId")Integer msgId);
}
