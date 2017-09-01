package com.ashlikun.materialcalendar;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/29　16:46
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class Utils {
    /**
     * 把日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String date2String(Date date, String format) {
        if (date == null) {
            return "";
        }
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            result = "";
        } finally {
            formater = null;
        }
        return result;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    public static Date string2Date(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);// 严格解析
            d = formater.parse(dateStr);
        } catch (Exception e) {
            d = null;
        } finally {
            formater = null;
        }
        return d;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
