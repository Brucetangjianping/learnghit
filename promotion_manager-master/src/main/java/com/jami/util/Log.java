package com.jami.util;

import org.apache.log4j.Logger;

/**
 * 通用的日志对象
 */
public class Log {

    /**
     * 运行日志
     */
    public static Logger run = Logger.getLogger("run");

    /**
     * 性能日志
     */
    public static Logger perf = Logger.getLogger("perf");

    /**
     * 访问日志
     */
    public static Logger access = Logger.getLogger("access");

    /**
     * 关键日志
     */
    public static Logger key  = Logger.getLogger("key");
    
}
