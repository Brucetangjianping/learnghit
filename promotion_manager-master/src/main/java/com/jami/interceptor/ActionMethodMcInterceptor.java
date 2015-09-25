package com.jami.interceptor;

import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.mc.McUtil;
import com.jami.mc.Module;
import com.jami.mc.impl.JMCMonitor;
import com.jami.springmvc.annotation.MCInfo;
import com.jami.util.Log;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by felixzhao on 14/11/22.
 */
public class ActionMethodMcInterceptor  extends AbstractMonitoringInterceptor {

    /**
     * 方法注解缓存
     */
    private Map<String, MCInfo> methodAnnotationCache = new HashMap<String, MCInfo>(128);

    /**
     * @param invocation
     * @param logger
     * @return
     */
    @Override
    protected boolean isInterceptorEnabled(MethodInvocation invocation,  org.apache.commons.logging.Log logger) {
        Method method = invocation.getMethod();
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

    @Override
    protected Object invokeUnderTrace(MethodInvocation methodInvocation,  org.apache.commons.logging.Log log) throws Throwable {

        Method method = methodInvocation.getMethod();
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

        // 开始计时
        Object resp = null;
        long timeCost = 0;
        try {

            long startTime = System.currentTimeMillis();
            resp = methodInvocation.proceed();
            timeCost = System.currentTimeMillis() - startTime;
            ModelAndView mv = (ModelAndView)resp;
            Integer errCode = (Integer)mv.getModelMap().get("errCode");

            mcReport(methodName, errCode, module, timeCost);
        }catch (BusinessException e){
            mcReport(methodName, e.getErrCode(), module, timeCost);
            throw e;
        }catch (Throwable e){
            mcReport(methodName, BizErrorCode.UNKNOW_ERROR.getCode(), module, timeCost);
            throw e;
        }

        return resp;
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
