package com.youzijie.login.controller;

import com.jami.common.JsonModelResult;
import com.jami.exception.BusinessException;
import com.youzijie.login.Po.AdminUserPo;
import com.youzijie.login.svc.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lei on 2015/7/17.
 */

@Controller
public class LoginController {
    @Resource
    LoginService loginService;

    @RequestMapping(value="/api/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("userName")String userName,
                              @RequestParam("password")String password,
                              HttpSession session,
                              HttpServletResponse response) throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();
        AdminUserPo userPo = loginService.auth(userName, password);
        session.setAttribute("userName", userPo.getUserName());
        session.setAttribute("token", userPo.getToken());
        result.setData(userPo);
        mv.addAllObjects(result.getModelMap());
        return mv;
    }
}
