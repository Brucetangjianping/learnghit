package com.youzijie.mp.svc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.youzijie.mp.dao.WechatDo;
import com.youzijie.mp.dao.WechatMapper;
import com.youzijie.mp.dao.WechatTemplateDo;
import com.youzijie.mp.dao.WechatTemplateMapper;
import com.youzijie.mp.svc.MpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lei on 2015/7/16.
 */
@Service("mpServic")
public class MpServiceImpl implements MpService {
    @Resource
    WechatMapper wechatMapper;

    @Resource
    WechatTemplateMapper wechatTemplateMapper;

    @Override
    public List<WechatDo> list() throws BusinessException {
        return wechatMapper.queryAll();
    }

    @Override
    public void add(WechatDo wechatDo) throws BusinessException {
        wechatDo.updateAddresscode();
        WechatDo wechatDo1 = wechatMapper.query(wechatDo.getWechatId());
        if (wechatDo1 != null) {
            wechatDo1.copy(wechatDo);
            wechatMapper.update(wechatDo1);
            return;
        }
        try {
            wechatMapper.add(wechatDo);
        } catch (Exception exception) {
            throw BusinessException.createInstance(BizErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public Map<String, String> addTemplate(String wechatId, List<String> templateName, List<String> templateValue) throws BusinessException {
        WechatDo wechatDo = wechatMapper.query(wechatId);
        if (templateName.size() != templateValue.size()) {
            throw BusinessException.createInstance(BizErrorCode.PARAMS_ERROR);
        }
        if (wechatDo != null) {
            WechatTemplateDo wechatTemplateDo = wechatTemplateMapper.query(wechatId);
            if (wechatTemplateDo == null) {
                wechatTemplateDo = new WechatTemplateDo();
                wechatTemplateDo.setWechatId(wechatId);
                Map<String, String> m = new HashMap<String, String>();
                for (int i = 0; i < templateName.size(); ++i) {
                    m.put(templateName.get(i), templateValue.get(i));
                }
                //m.put(templateName, templateValue);
                wechatTemplateDo.setTemplates(JSON.toJSONString(m));
                wechatTemplateMapper.add(wechatTemplateDo);
                return m;
            } else {
                String templates = wechatTemplateDo.getTemplates();
                Map<String, String> m = JSON.parseObject(templates, Map.class);
                for (int i = 0; i < templateName.size(); ++i) {
                    m.put(templateName.get(i), templateValue.get(i));
                }
                wechatTemplateDo.setTemplates(JSON.toJSONString(m));
                wechatTemplateMapper.update(wechatTemplateDo);
                return m;
            }
        } else {
            throw  BusinessException.createInstance(BizErrorCode.WECHAT_NOT_EXIST);
        }
    }

    @Override
    public Map<String, String> getTemplate(String wechatId) throws BusinessException {
        WechatTemplateDo wechatTemplateDo = wechatTemplateMapper.query(wechatId);
        if (wechatTemplateDo == null) {
            Map<String, String> m = new HashMap<String, String>();
            return m;
        } else {
            String templates = wechatTemplateDo.getTemplates();
            Map<String, String> m = JSON.parseObject(templates, Map.class);
            return m;
        }
    }

}
