package com.youzijie.appmsg.controller;

import com.jami.common.JsonModelResult;
import com.jami.exception.BusinessException;
import com.youzijie.appmsg.dao.WechatArticleDo;
import com.youzijie.appmsg.svc.AppMsgService;
import com.youzijie.mp.dao.WechatDo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */

@Controller
@RequestMapping(value="/api/appmsg/")
public class AppMsgController {
    @Resource
    AppMsgService appMsgService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ModelAndView list() throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(appMsgService.list());
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/msg", method = RequestMethod.GET)
    public ModelAndView getMsg(@RequestParam("msgId")Integer msgId) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(appMsgService.getArticles(msgId));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/msg", method = RequestMethod.POST)
    public ModelAndView addMsg(@RequestParam(value = "msgId", required = false)Integer msgId,
                               @RequestParam("articleIds")List<Integer> articleIds) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();

        result.setData(appMsgService.saveMsg(articleIds, msgId));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/article", method = RequestMethod.POST)
    public ModelAndView addArticle(WechatArticleDo wechatArticleDo) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();

        String filename = "";

        result.setData(appMsgService.saveArticle(wechatArticleDo));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/query_article", method = RequestMethod.GET)
    public ModelAndView queryArticle(@RequestParam(value = "articleId")Integer articleId) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();

        result.setData(appMsgService.getArticle(articleId));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/publish", method = RequestMethod.POST)
    public ModelAndView publish(@RequestParam(value = "msgId")Integer msgId,
                                @RequestParam(value = "wechatIds", required = false)List<String> wechatIds,
                                @RequestParam(value = "scheduleTime", required = false)Date scheduleTime) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(appMsgService.publish(msgId, wechatIds, scheduleTime));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/copy", method = RequestMethod.POST)
    public ModelAndView copy(@RequestParam(value = "msgId")Integer msgId) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(appMsgService.copyMsg(msgId));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }
}
