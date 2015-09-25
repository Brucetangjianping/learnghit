package com.jami.springmvc.interceptor;

import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.util.EnvManager;
import com.youzijie.login.dao.AdminUserDo;
import com.youzijie.login.dao.AdminUserMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by lei on 2015/7/8.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private AdminUserMapper adminUserMapper;

    private Set unAuthUrls;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String basePath = getBasePath(request);
        String requestURI = request.getRequestURI();
        for (Object url : unAuthUrls) {
            if (requestURI.contains((String)url)) {
                return true;
            }
        }
        if (EnvManager.isLocalEnvCurrent()){
            return true;
        }

        String userName = "";
        String token = "";

        // 获取userName和token
        // 如果是登录页
        userName = request.getParameter("userName");
        token = request.getParameter("token");
        if (userName != null && token != null) {
            request.getSession().setAttribute("userName", userName);
            request.getSession().setAttribute("token", token);
        }else {
            Object obj = request.getSession().getAttribute("userName");
            userName = obj == null ? "" : obj.toString();

            obj = request.getSession().getAttribute("token");
            token = obj == null ? "" : obj.toString();
        }

        // 如果为空,重定向至登录页面。
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(token)) {
            // response.sendRedirect(basePath + "/html/index.html");
            throw BusinessException.createInstance(BizErrorCode.AUTH_FAILED);
            // return false;
        }

        // 校验登录
        boolean authRet = checkToken(userName, token);

        if (authRet) {
            request.getSession().setAttribute("userName", userName);
            request.getSession().setAttribute("token", token);
        }

        // 如果token不通过
        if (!authRet) {
            // response.sendRedirect(basePath + "/html/index.html");
            throw BusinessException.createInstance(BizErrorCode.AUTH_FAILED);
        }

        // 如果继续
        return true;
    }

    private boolean checkToken(String userName, String token) {
        AdminUserDo adminUserDo = adminUserMapper.queryUserByName(userName);
        if (adminUserDo == null) {
            return false;
        }
        if (!adminUserDo.getToken().equals(token)) {
            return false;
        }

        return true;
    }

    private String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName();
        int port = request.getServerPort();
        if (port != 80) {
            basePath += ":" + port;
        }
        basePath += path;
        return basePath;
    }

    public Set getUnAuthUrls() {
        return unAuthUrls;
    }

    public void setUnAuthUrls(Set unAuthUrls) {
        this.unAuthUrls = unAuthUrls;
    }
}
