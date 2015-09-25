package com.youzijie.mp.controller;

import com.jami.common.JsonModelResult;
import com.jami.exception.BusinessException;
import com.youzijie.mp.dao.WechatDo;
import com.youzijie.mp.svc.MpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */

@Controller
@RequestMapping(value="/api/mp/")
public class MpController {
    @Resource
    MpService mpService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ModelAndView list() throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(mpService.list());
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ModelAndView add(WechatDo mp) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        mpService.add(mp);
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/template", method = RequestMethod.GET)
    public ModelAndView getTemplates(@RequestParam(value = "wechatId")String wechatId) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(mpService.getTemplate(wechatId));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/template", method = RequestMethod.POST)
    public ModelAndView updateTemplate(@RequestParam(value = "wechatId")String wechatId,
                                       @RequestParam(value = "templateName")List<String> tenmplateName,
                                       @RequestParam(value = "templateValue")List<String> templateValue) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(mpService.addTemplate(wechatId, tenmplateName, templateValue));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }
}
