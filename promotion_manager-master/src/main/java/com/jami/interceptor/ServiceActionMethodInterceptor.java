package com.jami.interceptor;

import com.jami.util.Log;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * @className:
 * @desc:
 * @author: felix <felixzhao@tencent.com>
 * @date: 13-11-2
 * @time: 下午2:37
 */
public class ServiceActionMethodInterceptor extends AbstractMonitoringInterceptor {


    private static final String PRE_SUFFIX = "";
    private static final String PRE_SPLIT = "\t";


    /**
     * 只注入Override的方法
     * @param invocation
     * @param logger
     * @return
     */
    @Override
    protected boolean isInterceptorEnabled(MethodInvocation invocation,  org.apache.commons.logging.Log logger) {
        if (invocation.getMethod().isAnnotationPresent(PrintProfiler.class)){
            return true;
        }
        return false;
    }


    @Override
    protected Object invokeUnderTrace(MethodInvocation methodInvocation,  org.apache.commons.logging.Log log) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object resp = methodInvocation.proceed();

        // 1、获取方法名
        String className = methodInvocation.getMethod().getDeclaringClass().getSimpleName();
        String methodName = methodInvocation.getMethod().getName();
        String keyName = className + "#" + methodName;

        // 2、获取注解信息
        Method method = methodInvocation.getMethod();


        String errCode = BeanUtils.getProperty(resp, "errCode");
        String errMsg = BeanUtils.getProperty(resp, "errMsg");
        long timeCost = System.currentTimeMillis() - startTime;

        // 3、打印日志，上报ITIL
        logPerf(Integer.parseInt(errCode), errMsg, keyName, timeCost);

        return resp;
    }

    private void logPerf(int errCode, String errMsg, String keyName, long timeCost)
    {
        StringBuffer sb = new StringBuffer(PRE_SUFFIX);
        sb.append(keyName).append(PRE_SPLIT);

        // 记录性能统计日志
        if (errCode == 0)
        {
            errMsg = "SUCCESS";
        }
        sb.append(errCode).append(PRE_SPLIT).append(errMsg).append(PRE_SPLIT)
                .append(timeCost).append("ms");
        if (errCode != 0)
        {
            Log.perf.warn(sb.toString());
        } else
        {
            Log.perf.info(sb.toString());
        }
    }

}
