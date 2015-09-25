package com.youzijie.appmsg.po;

import com.youzijie.appmsg.dao.MsgStatus;
import com.youzijie.appmsg.dao.WechatMsgDo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lei on 2015/7/30.
 */
public class WechatMsgPo {
    private int msgId;
    private String title;
    private List<String> wechatNames;
    private Date publishTime;
    private int status;
    private Date schedulePublishTime;
    private String scheduleWechatIds;

    public WechatMsgPo() {
        this.title = "";
        this.wechatNames = new ArrayList<String>();
    }

    public WechatMsgPo(WechatMsgDo wechatMsgDo) {
        this.msgId = wechatMsgDo.getMsgId();
        this.title = wechatMsgDo.getTitle();
        this.wechatNames = new ArrayList<String>();
        this.publishTime = wechatMsgDo.getPublishTime();
        this.status = wechatMsgDo.getStatus();
        this.schedulePublishTime = wechatMsgDo.getSchedulePublishTime();
        this.scheduleWechatIds = wechatMsgDo.getScheduleWechatIds();
    }

    public void addWechatName(String wechatName) {
        this.wechatNames.add(wechatName);
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getWechatNames() {
        return wechatNames;
    }

    public void setWechatNames(List<String> wechatNames) {
        this.wechatNames = wechatNames;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getSchedulePublishTime() {
        return schedulePublishTime;
    }

    public void setSchedulePublishTime(Date schedulePublishTime) {
        this.schedulePublishTime = schedulePublishTime;
    }

    public String getScheduleWechatIds() {
        return scheduleWechatIds;
    }

    public void setScheduleWechatIds(String scheduleWechatIds) {
        this.scheduleWechatIds = scheduleWechatIds;
    }
}
