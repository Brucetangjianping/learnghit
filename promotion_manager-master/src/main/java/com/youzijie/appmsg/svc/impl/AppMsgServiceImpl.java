package com.youzijie.appmsg.svc.impl;

import com.alibaba.fastjson.JSON;
import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.util.Config;
import com.jami.util.Log;
import com.youzijie.appmsg.dao.*;
import com.youzijie.util.WechatHelper;
import com.youzijie.appmsg.po.WechatMsgPo;
import com.youzijie.appmsg.svc.AppMsgService;
import com.youzijie.mp.dao.WechatDo;
import com.youzijie.mp.dao.WechatMapper;
import com.youzijie.mp.dao.WechatTemplateDo;
import com.youzijie.mp.dao.WechatTemplateMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lei on 2015/7/16.
 */

@Service
public class AppMsgServiceImpl implements AppMsgService {
    private static final String PREVIEW_WECHAT_ID = "yzsh";
    @Resource
    WechatArticleMapper wechatArticleMapper;

    @Resource
    WechatMapper wechatMapper;

    @Resource
    WechatMsgMapper wechatMsgMapper;

    @Resource
    WechatTemplateMapper wechatTemplateMapper;

    @Resource
    private Config config;

    @Override
    public List<WechatMsgPo> list() throws BusinessException {
        List<WechatMsgPo> wechatMsgPos = new ArrayList<WechatMsgPo>();
        List<WechatMsgDo> wechatMsgDos = wechatMsgMapper.queryAll();
        List<WechatDo> wechatDos = wechatMapper.queryAll();;
        Map<String, String> idToName = new HashMap<String, String>();
        for (WechatDo wechatDo : wechatDos) {
            idToName.put(wechatDo.getWechatId(),  wechatDo.getWechatName());
        }

        for (WechatMsgDo wechatMsgDo : wechatMsgDos) {
            WechatMsgPo wechatMsgPo = new WechatMsgPo(wechatMsgDo);
            for (String id : wechatMsgDo.getWechatIdList()) {
                wechatMsgPo.addWechatName(idToName.get(id));
            }
            wechatMsgPos.add(wechatMsgPo);
        }
        Comparator<WechatMsgPo> comparator = new Comparator<WechatMsgPo>() {
            public int compare(WechatMsgPo r, WechatMsgPo l) {
                return l.getMsgId() - r.getMsgId();
            }
        };
        Collections.sort(wechatMsgPos, comparator);
        return wechatMsgPos;
    }

    @Override
    public WechatMsgDo getMsg(int msgId) throws BusinessException {
        return wechatMsgMapper.query(msgId);
    }

    @Override
    public List<WechatArticleDo> getArticles(int msgId) throws BusinessException {
        List<WechatArticleDo> wechatArticleDos =  wechatArticleMapper.queryByMsgId(msgId);
        for (WechatArticleDo wechatArticleDo : wechatArticleDos) {
            wechatArticleDo.loadContent();
        }
        return wechatArticleDos;
    }

    @Override
    public int saveMsg(List<Integer> articleIds, Integer msgId) throws BusinessException {
        String title = "";
        if (articleIds.isEmpty()) {
            return 0;
        }
        int oldMsg = 0;
        for (Integer articleId : articleIds) {
            WechatArticleDo wechatArticleDo = wechatArticleMapper.query(articleId);
            if (wechatArticleDo == null) {
                continue;
            }
            if (title.isEmpty()) {
                title = wechatArticleDo.getTitle();
            }

            if (wechatArticleDo.getMsgId() != null && wechatArticleDo.getMsgId() > 0) {
                oldMsg = wechatArticleDo.getMsgId();
            }
        }
        if (msgId == null || msgId == 0) {
            if (oldMsg > 0) {
                throw BusinessException.createInstance(BizErrorCode.MSG_SAVE_FAILED);
            }
            WechatMsgDo wechatMsgDo = new WechatMsgDo();
            wechatMsgDo.setTitle(title);
            wechatMsgMapper.add(wechatMsgDo);
            Log.key.info("Add message " + wechatMsgDo.getMsgId() + " succeed");
            msgId = wechatMsgDo.getMsgId();
        } else {
            WechatMsgDo wechatMsgDo = wechatMsgMapper.query(msgId);
            if (wechatMsgDo == null) {
                throw BusinessException.createInstance(BizErrorCode.ARTICLE_NOT_EXIST);
            }
            if (!wechatMsgDo.editAble()) {  //文章已经发布,不能更改
                throw BusinessException.createInstance(BizErrorCode.PERMISSION_DENIED);
            }
            wechatMsgDo.setTitle(title);
            wechatMsgDo.setStatus(MsgStatus.SAVED);
            wechatMsgMapper.update(wechatMsgDo);
            wechatArticleMapper.clearMsg(msgId);
            Log.key.info("Save message " + msgId + " succeed");
        }
        for (Integer articleId : articleIds) {
            WechatArticleDo wechatArticleDo = wechatArticleMapper.query(articleId);
            if (wechatArticleDo != null) {
                wechatArticleDo.setMsgId(msgId);
                wechatArticleMapper.update(wechatArticleDo);
            }
        }
        return msgId;
    }

    @Override
    public int saveArticle(WechatArticleDo articleDo) throws BusinessException {
        if (articleDo.getId() != null && articleDo.getId() != 0) {
            WechatArticleDo wechatArticleDo = wechatArticleMapper.query(articleDo.getId());
            if (wechatArticleDo == null) {
                throw BusinessException.createInstance(BizErrorCode.ARTICLE_NOT_EXIST);
            }

            if (wechatArticleDo.getMsgId() != null) {
                WechatMsgDo wechatMsgDo = wechatMsgMapper.query(wechatArticleDo.getMsgId());

                if (wechatMsgDo != null && !wechatMsgDo.editAble()) {  //文章已经发布,不能更改
                    throw BusinessException.createInstance(BizErrorCode.PERMISSION_DENIED);
                }
            }

            wechatArticleDo.copy(articleDo);
            wechatArticleDo.saveContent();
            wechatArticleMapper.update(wechatArticleDo);
            Log.key.info("Save article " + articleDo.getId() + " succeed");
            return articleDo.getId();
        } else {
            WechatArticleDo wechatArticleDo = new WechatArticleDo();
            wechatArticleDo.copy(articleDo);
            wechatArticleDo.saveContent();
            wechatArticleMapper.add(wechatArticleDo);
            wechatArticleDo.loadContent();
            wechatArticleDo.saveContent();
            wechatArticleMapper.update(wechatArticleDo);
            Log.key.info("Add article " + wechatArticleDo.getId() + " succeed");
            return wechatArticleDo.getId();
        }
    }

    @Override
    public List<String> publish(Integer msgId, List<String> scheduleWechatIds, Date schedulePublishTime) throws BusinessException {
        List<String> publishResultPos = new ArrayList<String>();
        WechatMsgDo wechatMsgDo = wechatMsgMapper.query(msgId);

        /*if (wechatMsgDo.getStatus() != 0) {
            throw BusinessException.createInstance(BizErrorCode.MSG_PUBLISH_FAILED);
        }*/

        if (scheduleWechatIds == null) {   // 预览消息
            if (!previewMsg(wechatMsgDo)) {
                throw BusinessException.createInstance(BizErrorCode.PREVIEW_FAILED);
            }
            Log.key.info("Preview message " + msgId + " success.");
            return publishResultPos;
        }

        String scheduleWechatIdsStr = StringUtils.join(scheduleWechatIds, ", ");
        Log.key.info("Try to publish msg " + msgId + " to wechat:" + scheduleWechatIdsStr);
        if (schedulePublishTime == null) {
            wechatMsgDo.setSchedulePublishTime(new Date());
        } else {
            wechatMsgDo.setSchedulePublishTime(schedulePublishTime);
        }
        wechatMsgDo.setScheduleWechatIds(scheduleWechatIdsStr);
        wechatMsgDo.setStatus(MsgStatus.PRE_PUBLISH);
        wechatMsgMapper.update(wechatMsgDo);
        if (schedulePublishTime == null) {
            publishInternal(wechatMsgDo);
        }

        /*
        List<String> publishedWechatIds = Arrays.asList(wechatMsgDo.getWechatIdList());
        for (String wechatId : wechatIds) {
            if (publishedWechatIds.contains(wechatId)) {
                Log.key.info("Publish message " + msgId + " to " + wechatId + ", already published");
                continue;
            }

            if (publishMsg(wechatMsgDo, wechatId)) {
                Log.key.info("Publish message " + msgId + " to " + wechatId + " succeed");
                wechatMsgDo.setPublishTime(new Date());
                wechatMsgDo.addWechatId(wechatId);
                publishResultPos.add(wechatId);
            } else {
                Log.key.info("Publish message " + msgId + " to " + wechatId + "failed");
            }
            if (!publishResultPos.isEmpty()) {
                Log.key.info("Publish message " + msgId + " success. Success wechat id is:" + wechatMsgDo.getWechatIds());
            } else {
                throw BusinessException.createInstance(BizErrorCode.PUBLISH_FAILED);
            }
        }
        wechatMsgMapper.update(wechatMsgDo);*/
        return publishResultPos;
    }

    private Set<String> getUnPublishWechatIds(WechatMsgDo wechatMsgDo) {
        Set<String> scheduleWechatIds = new HashSet<String>(Arrays.asList(wechatMsgDo.getScheduleWechatIdList()));
        Set<String> publishWechatIds = new HashSet<String>(Arrays.asList(wechatMsgDo.getWechatIdList()));
        scheduleWechatIds.removeAll(publishWechatIds);
        return scheduleWechatIds;
    }

    public void publishJob() {
        List<WechatMsgDo> wechatMsgDos = wechatMsgMapper.queryScheduled();
        Log.run.info("Run period job, " + wechatMsgDos.size() + " message to be published");
        for (WechatMsgDo wechatMsgDo : wechatMsgDos) {
            publishInternal(wechatMsgDo);
        }
    }

    public void publishInternal(WechatMsgDo wechatMsgDo) {
        wechatMsgDo.setStatus(MsgStatus.PUBLISHING);
        wechatMsgMapper.update(wechatMsgDo);

        List<String> publishedWechatIds = Arrays.asList(wechatMsgDo.getWechatIdList());
        String[] wechatIds = wechatMsgDo.getScheduleWechatIds().split(",");
        for (String wechatId : wechatIds) {
            if (publishedWechatIds.contains(wechatId)) {
                Log.key.info("Publish message " + wechatMsgDo.getMsgId() + " to " + wechatId + ", already published");
                continue;
            }

            if (publishMsg(wechatMsgDo, wechatId)) {
                Log.key.info("Publish message " + wechatMsgDo.getMsgId() + " to " + wechatId + " succeed");
                wechatMsgDo.setPublishTime(new Date());
                wechatMsgDo.addWechatId(wechatId);
                // publishResultPos.add(wechatId);
            } else {
                Log.key.info("Publish message " + wechatMsgDo.getMsgId() + " to " + wechatId + "failed");
            }
            /*if (!publishResultPos.isEmpty()) {
                Log.key.info("Publish message " + msgId + " success. Success wechat id is:" + wechatMsgDo.getWechatIds());
            } else {
                throw BusinessException.createInstance(BizErrorCode.PUBLISH_FAILED);
            }*/
        }
        if (getUnPublishWechatIds(wechatMsgDo).isEmpty()) {
            wechatMsgDo.setStatus(MsgStatus.FULL_PUBLISHED);
        } else {
            wechatMsgDo.setStatus(MsgStatus.PARTIAL_PUBLISHED);
        }
        wechatMsgMapper.update(wechatMsgDo);
    }

    @Override
    public int copyMsg(int msgId) throws BusinessException {
        Log.run.info("Copy message " + msgId);

        WechatMsgDo wechatMsgDo = wechatMsgMapper.query(msgId);
        WechatMsgDo wechatMsgCopy = new WechatMsgDo();
        wechatMsgCopy.setTitle(wechatMsgDo.getTitle());
        wechatMsgMapper.add(wechatMsgCopy);

        int copyMsgId = wechatMsgCopy.getMsgId();

        List<WechatArticleDo> wechatArticleDos = wechatArticleMapper.queryByMsgId(wechatMsgDo.getMsgId());
        Log.run.info("Copy articles from " + msgId + " to " + copyMsgId);

        for (WechatArticleDo wechatArticleDo : wechatArticleDos) {
            Log.run.info("Copy article " + wechatArticleDo.getId());
            WechatArticleDo wechatArticleCopy = new WechatArticleDo();
            wechatArticleCopy.copy(wechatArticleDo);
            wechatArticleCopy.setMsgId(copyMsgId);
            wechatArticleMapper.add(wechatArticleCopy);
            Log.run.info("Reload content of article " + wechatArticleCopy.getId());
            wechatArticleCopy.loadContent();
            wechatArticleCopy.saveContent();
            wechatArticleMapper.update(wechatArticleCopy);
        }
        return copyMsgId;
    }

    @Override
    public WechatArticleDo getArticle(int articleId) throws BusinessException {
        WechatArticleDo wechatArticleDo = wechatArticleMapper.query(articleId);
        if (wechatArticleDo == null) {
            return null;
        } else {
            wechatArticleDo.loadContent();
        }
        return wechatArticleDo;
    }

    private boolean previewMsg(WechatMsgDo wechatMsgDo) {
        String publisher_id = config.getString("preview_publisher_id");
        WechatTemplateDo wechatTemplateDo = wechatTemplateMapper.query(publisher_id);
        Map<String, String> templates = JSON.parseObject(
                wechatTemplateDo == null ? "{}" : wechatTemplateDo.getTemplates(), Map.class);
        WechatDo wechatDo = wechatMapper.query(publisher_id);

        List<WechatArticleDo> wechatArticleDos = wechatArticleMapper.queryByMsgId(wechatMsgDo.getMsgId());

        WechatHelper wechatHelper = new WechatHelper(wechatDo, templates);
        if (wechatHelper.previewMsg(wechatArticleDos, config.getString("preview_receiver_id"))) {
            //wechatMsgDo.setMediaId(media_id);
            wechatDo.setAccessToken(wechatHelper.getAccessToken());
            wechatMapper.update(wechatDo);
            wechatMsgMapper.update(wechatMsgDo);
            return true;
        }
        return false;
    }

    private boolean publishMsg(WechatMsgDo wechatMsgDo, String wechatId) {
        WechatDo wechatDo = wechatMapper.query(wechatId);
        if (wechatMsgDo == null || wechatDo == null) {
            return false;
        }
        WechatTemplateDo wechatTemplateDo = wechatTemplateMapper.query(wechatId);
        Map<String, String> templates = JSON.parseObject(
                wechatTemplateDo == null ? "{}" : wechatTemplateDo.getTemplates(), Map.class);

        List<WechatArticleDo> wechatArticleDos = wechatArticleMapper.queryByMsgId(wechatMsgDo.getMsgId());

        WechatHelper wechatHelper = new WechatHelper(wechatDo, templates);
        if (wechatHelper.publishMsg(wechatArticleDos)) {
            wechatDo.setAccessToken(wechatHelper.getAccessToken());
            wechatMapper.update(wechatDo);
            wechatMsgMapper.update(wechatMsgDo);
            return true;
        } else {
            return false;
        }
    }
}
