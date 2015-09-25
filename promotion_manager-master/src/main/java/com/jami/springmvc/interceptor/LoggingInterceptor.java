package com.jami.springmvc.interceptor;

import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.util.DateUtil;
import com.jami.util.Log;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * access日志打印interceptor
 * Created by felixzhao on 14-5-15.
 */
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("cgiStartTime");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long beginTime = System.currentTimeMillis();//1、开始时间
        startTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
        return true;//继续流程
    }

    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        // 取得errCode
        int errCode = 0;
        if (ex != null) {
            if (ex instanceof BusinessException) {
                errCode = ((BusinessException) ex).getErrCode();
            } else {
                errCode = BizErrorCode.UNKNOW_ERROR.getCode();
            }
        }

        long endTime = System.currentTimeMillis();//2、结束时间
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long consumeTime = endTime - beginTime;//3、消耗的时间
        StringBuffer sb = new StringBuffer();

        sb.append(request.getRequestURI()).append("\t");
        sb.append(request.getQueryString()).append("\t");
        sb.append(errCode).append("\t");
        sb.append(consumeTime).append("ms");
        Log.access.info(sb.toString());
    }


    private String wrapQuote(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        if (str != null) {
            str.replaceAll("\"", "\\\"");
        }
        sb.append(str);
        sb.append("\"");
        return sb.toString();
    }

    private String toStringIfNotNull(String str, String placeholder){
        if (StringUtils.isEmpty(str)){
            return placeholder;
        }else {
            return str;
        }
    }

}
