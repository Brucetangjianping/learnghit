package com.youzijie.appmsg.dao;

import java.util.Date;
import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
public class WechatMsgDo {
    private int msgId;
    private String title;
    private String wechatIds;
    private Date publishTime;
    private Date schedulePublishTime;
    private String scheduleWechatIds;

    private int status;

    public WechatMsgDo() {
        title = "";
        wechatIds = "";
        status = MsgStatus.INIT;
    }

    public void addWechatId(String wechatId) {
        if (this.wechatIds.isEmpty()) {
            this.wechatIds = wechatId;
        } else {
            this.wechatIds += "," + wechatId;
        }
    }

    public String[] getWechatIdList() {
        return wechatIds.split(",");
    }
    public String[] getScheduleWechatIdList() { return scheduleWechatIds.split(","); }

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

    public String getWechatIds() {
        return wechatIds;
    }

    public void setWechatIds(String wechatIds) {
        this.wechatIds = wechatIds;
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

    public boolean editAble() {
        return status < MsgStatus.PUBLISHING;
    }
}
