package com.youzijie.mp.svc;

import com.jami.exception.BusinessException;
import com.youzijie.mp.dao.WechatDo;
import com.youzijie.mp.dao.WechatTemplateDo;

import java.util.List;
import java.util.Map;

/**
 * Created by lei on 2015/7/16.
 */
public interface MpService {
    public List<WechatDo> list() throws BusinessException;
    public void add(WechatDo wechatDo) throws BusinessException;
    public Map<String, String> addTemplate(String wechatId, List<String> templateName, List<String> templateValue) throws BusinessException;
    public Map<String, String> getTemplate(String wechatId) throws BusinessException;
}
