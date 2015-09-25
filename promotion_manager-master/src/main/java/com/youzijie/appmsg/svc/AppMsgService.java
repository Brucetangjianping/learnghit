package com.youzijie.appmsg.svc;

import com.jami.exception.BusinessException;
import com.youzijie.appmsg.dao.WechatArticleDo;
import com.youzijie.appmsg.dao.WechatMsgDo;
import com.youzijie.appmsg.po.WechatMsgPo;

import java.util.Date;
import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
public interface AppMsgService {
    public List<WechatMsgPo> list() throws BusinessException;

    public WechatMsgDo getMsg(int msgId) throws BusinessException;

    public List<WechatArticleDo> getArticles(int msgId) throws BusinessException;

    public int saveArticle(WechatArticleDo wechatArticleDo) throws BusinessException;

    public int saveMsg(List<Integer> articleIds, Integer msgId) throws BusinessException;

    public List<String> publish(Integer msgId, List<String> wechatIds, Date schedulePublishTime) throws BusinessException;

    public int copyMsg(int msgId) throws BusinessException;

    public WechatArticleDo getArticle(int articleId) throws BusinessException;
}
