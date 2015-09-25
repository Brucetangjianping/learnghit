package com.jami.springmvc.interceptor;

import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.mc.McUtil;
import com.jami.mc.Module;
import com.jami.mc.impl.JMCMonitor;
import com.jami.springmvc.annotation.MCInfo;
import com.jami.util.Log;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by felixzhao on 14/11/27.
 */
public class MCInterceptor  extends HandlerInterceptorAdapter {

    /**
     * 方法注解缓存
     */
    private Map<String, MCInfo> methodAnnotationCache = new HashMap<String, MCInfo>(128);

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("mcCgiStartTime");


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long beginTime = System.currentTimeMillis();//1、开始时间
        startTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
        return true;//继续流程
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (!needMonitor(handlerMethod)) {
                return;
            }

            // 获取注解中的module信息
            Module module = getModuleInfo(handlerMethod);
            // 计算消耗时间
            long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
            long timeCost = System.currentTimeMillis() - beginTime;//3、消耗的时间

            // 取得errCode
            Integer errCode = (Integer) modelAndView.getModelMap().get("errCode");
            // 不是json接口，默认为成功
            if (errCode == null) {
                errCode = 0;
            }

            Method method = handlerMethod.getMethod();
            mcReport(method.getName(), errCode, module, timeCost);
        } catch (Exception e) {
            Log.run.warn("MCInterceptor postHandle failed", e);
        }
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        try {
            if (ex == null) {
                return;
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (!needMonitor(handlerMethod)) {
                return;
            }

            // 获取注解中的module信息
            Module module = getModuleInfo(handlerMethod);
            // 计算消耗时间
            long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
            long timeCost = System.currentTimeMillis() - beginTime;//3、消耗的时间

            // 取得errCode
            int errCode = 0;
            if (ex instanceof BusinessException) {
                errCode = ((BusinessException) ex).getErrCode();
            } else {
                errCode = BizErrorCode.UNKNOW_ERROR.getCode();
            }

            Method method = handlerMethod.getMethod();
            mcReport(method.getName(), errCode, module, timeCost);
        }catch (Exception e){
            Log.run.warn("MCInterceptor afterCompletion failed", e);
        }
    }

    private Module getModuleInfo(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        Class actionCls = method.getDeclaringClass();
        String controllerName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String keyName = controllerName + "#" + methodName;

        Module module = null;

        MCInfo mcInfoAnnotation = methodAnnotationCache
                .get(keyName);

        if (null == mcInfoAnnotation) {
            // 现在class上面查找
            mcInfoAnnotation = (MCInfo)actionCls.getAnnotation(MCInfo.class);
            if (null == mcInfoAnnotation) {
                // 再去method查找
                mcInfoAnnotation = method.getAnnotation(MCInfo.class);
            }
            if (null != mcInfoAnnotation) {
                methodAnnotationCache.put(keyName, mcInfoAnnotation);
            }
        }

        if (null != mcInfoAnnotation) {
            module = mcInfoAnnotation.module();
        }

        return module;
    }

    private boolean needMonitor(HandlerMethod handlerMethod){
        Method method = handlerMethod.getMethod();
        Class targetCls = method.getDeclaringClass();

        MCInfo mcInfo = (MCInfo)targetCls.getAnnotation(MCInfo.class);
        if (mcInfo == null){
            mcInfo = method.getAnnotation(MCInfo.class);
        }
        // controller的@RequestMapping 方法
        if (mcInfo != null
                && method.getDeclaringClass().isAnnotationPresent(Controller.class)
                && method.getAnnotation(RequestMapping.class) != null){
            return true;
        }
        return false;
    }

    private void mcReport(String interfaceName, int errCode, Module module,
                          long timeCost)
    {
        // 上报模调
        if (module != null && !module.equals(Module.S_MPOS_API_DEFAULT))
        {
            Lock lock = new ReentrantLock();
            try
            {
                Log.run.debug("start mc report module=" + module.getName());
                lock.lock();
                JMCMonitor.getInstance().addResult(
                        module.getDesc(),
                        McUtil.getLocalRealIp(),
                        McUtil.DEFAULT_PORT,
                        McUtil.gernateMethodName(module.getName(),
                                interfaceName), timeCost,
                        McUtil.isNeedReport(errCode));
            } finally
            {
                lock.unlock();
            }
        }
    }

}
