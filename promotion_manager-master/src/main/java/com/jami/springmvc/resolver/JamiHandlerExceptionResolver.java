package com.jami.springmvc.resolver;

import com.jami.common.JsonModelResult;
import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.util.Log;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JAMI框架整合SpringMVC统一异常处理机制
 * Date: 14-3-12
 * Time: 下午8:39
 * To change this template use File | Settings | File Templates.
 */
public class JamiHandlerExceptionResolver extends DefaultHandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {

        // 异常处理：1)标注异常，使得access\invoke日志中能显示相应的异常；2)在error日志中记录异常的详细内容；3)根据Cgi类型，返回相应的Response
        ModelAndView view = super.resolveException(httpServletRequest, httpServletResponse, handler, e);
        if (view != null) {
            return view;
        }
        view = new ModelAndView();
        JsonModelResult ret = null;
        if (e instanceof BusinessException) {
            ret = new JsonModelResult();
            ret.setErrCode(((BusinessException) e).getErrCode());
            ret.setRetCode(((BusinessException) e).getErrCode());
            ret.setMsg(((BusinessException) e).getErrMsg());
            Log.run.error("catch a BusinessException: errCode=" + ((BusinessException) e).getErrCode() +
                    " errMsg=" +((BusinessException) e).getErrMsg(), e);
        } else {
            ret = new JsonModelResult();
            ret.setErrCode(BizErrorCode.UNKNOW_ERROR.getCode());
            ret.setRetCode(BizErrorCode.UNKNOW_ERROR.getCode());
            ret.setMsg(BizErrorCode.UNKNOW_ERROR.getMsg() + "[" + e.getMessage() + "]");
        }

        return view.addAllObjects(ret.getModelMap());
    }

    /**
     * Handle the case when a required parameter is missing.
     * 返回一个显示错误信息的json
     * @param ex the MissingServletRequestParameterException to be handled
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the executed handler
     * @return an empty ModelAndView indicating the exception was handled
     * @throws java.io.IOException potentially thrown from response.sendError()
     */
    @Override
    protected ModelAndView handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        ModelAndView view = new ModelAndView();
        JsonModelResult ret = new JsonModelResult();
        ret.setErrCode(BizErrorCode.PARA_ERROR.getCode());
        ret.setRetCode(BizErrorCode.PARA_ERROR.getCode());
        ret.setMsg(BizErrorCode.PARA_ERROR.getMsg() + "[" + ex.getMessage() + "]");
        view.addAllObjects(ret.getModelMap());
        return view;
    }

}
