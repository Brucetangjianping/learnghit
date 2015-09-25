package com.youzijie.appmsg.dao;

import com.jami.common.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by lei on 2015/7/17.
 */
public interface WechatMsgMapper extends BaseMapper {
    @Select("SELECT * FROM t_marketing_wechat_msg;")
    public List<WechatMsgDo> queryAll();

    @Select("SELECT * FROM t_marketing_wechat_msg WHERE schedulePublishTime <= NOW() AND status < 2;")
    public List<WechatMsgDo> queryScheduled();

    @Select("SELECT * FROM t_marketing_wechat_msg WHERE msgId=#{msgId};")
    public WechatMsgDo query(@Param("msgId")int msgId);

    @Insert("INSERT INTO t_marketing_wechat_msg (title,status,publishTime,wechatIds, schedulePublishTime, scheduleWechatIds) VALUES" +
            " (#{wechatMsgDo.title},#{wechatMsgDo.status},#{wechatMsgDo.publishTime},#{wechatMsgDo.wechatIds},#{wechatMsgDo.schedulePublishTime},#{wechatMsgDo.scheduleWechatIds);")
    @Options(useGeneratedKeys = true, keyProperty = "wechatMsgDo.msgId")
    public int add(@Param("wechatMsgDo")WechatMsgDo wechatMsgDo);

    @Update("UPDATE t_marketing_wechat_msg SET status=#{wechatMsgDo.status},title=#{wechatMsgDo.title}," +
            "publishTime=#{wechatMsgDo.publishTime},wechatIds=#{wechatMsgDo.wechatIds}," +
            " schedulePublishTime=#{wechatMsgDo.schedulePublishTime}, scheduleWechatIds=#{wechatMsgDo.scheduleWechatIds}" +
            " WHERE msgId=#{wechatMsgDo.msgId};")
    public int update(@Param("wechatMsgDo")WechatMsgDo wechatMsgDo);
}
