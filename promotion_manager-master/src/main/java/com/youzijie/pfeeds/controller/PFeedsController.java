package com.youzijie.pfeeds.controller;

import com.jami.common.JsonModelResult;
import com.jami.exception.BusinessException;
import com.youzijie.pfeeds.dao.PromotionConfigDo;
import com.youzijie.pfeeds.po.PromotionConfigPo;
import com.youzijie.pfeeds.po.PromotionPo;
import com.youzijie.pfeeds.svc.PFeedsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by lei on 2015/7/16.
 */
@Controller
@RequestMapping(value="/api/pfeeds/")
public class PFeedsController {
    @Resource
    PFeedsService pFeedsService;

    @RequestMapping(value="/locations", method = RequestMethod.GET)
    public ModelAndView queryLocations() throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(pFeedsService.queryLocations());
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/locations", method = RequestMethod.POST)
    public ModelAndView addLocation(@RequestParam("province")String province,
                                    @RequestParam(value = "city", required = false)String city,
                                    @RequestParam(value = "district", required = false)String district) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(pFeedsService.addLocation(province, city, district));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/feeds", method = RequestMethod.GET)
    public ModelAndView addLocation(@RequestParam("locationId")int locationId) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        result.setData(pFeedsService.queryPromotion(locationId));
        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    @RequestMapping(value="/feeds", method = RequestMethod.POST)
    public ModelAndView addLocation(@RequestParam("locationId")int locationId,
                                    @RequestParam("promotion")String promotion) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        pFeedsService.update(locationId, promotion);
        mv.addAllObjects(result.getModelMap());
        return mv;
    }
}
