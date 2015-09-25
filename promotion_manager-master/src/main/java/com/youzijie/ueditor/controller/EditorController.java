package com.youzijie.ueditor.controller;

import com.jami.common.JsonModelResult;
import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.youzijie.ueditor.svc.EditorService;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lei on 2015/7/17.
 */

@Controller
public class EditorController {
    @Resource
    EditorService editorService;

    private static String filepath = "/data/jweb_static/promotion_manager/upload/";

    @RequestMapping(value="/api/controller", method = RequestMethod.GET)
    public ModelAndView controller(@RequestParam("action")String action,
                                   @RequestParam(value = "start", required = false)Integer start,
                                   @RequestParam(value = "size", required = false)Integer size,
                                   @RequestParam(value = "source", required = false)List<String> source) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        Object o = null;
        if (action == "config") {
            o = editorService.getConfig();
        } else if (action.equals("listimage")) {
            o = editorService.listImage(start, size);
        } else if (action.equals("catchimage")) {
            o = editorService.catchImage(source);
        }

        try {
            ObjectMapper m = new ObjectMapper();
            Map<String,Object> props = m.convertValue(o, Map.class);
            mv.addAllObjects(props);
        } catch (Exception e) {

        }
        return mv;
    }


    @RequestMapping(value="/api/controller",  method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("action")String action,
                                   MultipartFile upfile) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        Object o = null;

        if (action.equals("uploadimage")) {
            o = editorService.uploadImage(upfile);
        } else if (action.equals("uploadvideo")) {
            o = editorService.uploadVideo(upfile);
        } else if (action.equals("uploadfile")) {
            o = editorService.uploadFile(upfile);
        }

        try {
            ObjectMapper m = new ObjectMapper();
            Map<String,Object> props = m.convertValue(o, Map.class);
            mv.addAllObjects(props);
        } catch (Exception e) {

        }
        return mv;
    }
}
