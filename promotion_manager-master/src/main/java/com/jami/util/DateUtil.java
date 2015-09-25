package com.jami.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 提供时间格式等处理逻辑
 * Created with IntelliJ IDEA.
 * User: winsonwu
 * Date: 14-3-17
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    /**
     * 日期格式：yyyyMMdd
     * sample：20140317
     */
    public static final SimpleDateFormat FORMAT_DATE_YMD = new SimpleDateFormat("yyyyMMdd");

    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final SimpleDateFormat FORMAT_DATE_YMD_1 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 日期格式：MM月dd日
     */
    public static final SimpleDateFormat FORMAT_DATE_MD_2 = new SimpleDateFormat("MM月dd日");

    /**
     * 日期格式：hh:mm
     * sample：2014-03-17
     */
    public static final SimpleDateFormat FORMAT_TIME_HM_1 = new SimpleDateFormat("HH:mm");

    /**
     * 时间格式：yyyyMMddHHmmss
     * sample: 20140317103952
     */
    public static final SimpleDateFormat FORMAT_DATETIME_YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat FORMAT_DATETIME_YMDHMS_11 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 日期格式：MM月dd日 hh:mm
     * sample：2014-03-17
     */
    public static final SimpleDateFormat FORMAT_DATE_M_D_HM = new SimpleDateFormat("MM月dd日 HH:mm");
    
    public static final SimpleDateFormat FORMAT_DATE_YMD_ZH = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

    /**
     * 将时间Date对象转换成相应格式的字符串，吃掉异常，返回空字符串
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, SimpleDateFormat format) {
        if (date != null && format != null) {
            try {
                return format.format(date);
            } catch (Exception e) {
                //ignore
            }
        }
        return "";
    }

}
