package com.ashlikun.materialcalendar.manager;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/29 16:41
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：节假日管理器
 */
public class HolidaysManager {
    Map<String, String> mDateMap;
    protected Calendar calendar = Calendar.getInstance();
    /**
     * 农历部分假日
     */
    protected final static String[] lunarHoliday = new String[]{"0101 春节", "0115 元宵", "0505 端午", "0707 情人", "0715 中元", "0815 中秋", "0909 重阳", "1208 腊八", "1224 小年", "0100 除夕"};
    /**
     * 公历部分节假日
     */
    protected final static String[] solarHoliday = new String[]{ //
            "0101 元旦", "0214 情人", "0308 妇女", "0312 植树", "0315 消费者权益日", "0401 愚人", "0501 劳动", "0504 青年", //
            "0512 护士", "0601 儿童", "0701 建党", "0801 建军", "0808 父亲", "0910 教师", "0928 孔子诞辰",//
            "1001 国庆", "1006 老人", "1024 联合国日", "1112 孙中山诞辰纪念", "1220 澳门回归纪念", "1225 圣诞"};

    public HolidaysManager() {
        mDateMap = new HashMap<>();
        //元旦
        mDateMap.put(getDate(2017, 01, 01), "元旦");
        mDateMap.put(getDate(2017, 01, 02), "");
        //春节
        mDateMap.put(getDate(2017, 01, 22), "");
        mDateMap.put(getDate(2017, 01, 27), "春节");
        mDateMap.put(getDate(2017, 01, 28), "春节");
        mDateMap.put(getDate(2017, 01, 29), "春节");
        mDateMap.put(getDate(2017, 01, 30), "春节");
        mDateMap.put(getDate(2017, 01, 31), "春节");
        mDateMap.put(getDate(2017, 02, 02), "春节");
        mDateMap.put(getDate(2017, 02, 04), "");
        //清明节
        mDateMap.put(getDate(2017, 04, 02), "清明节");
        mDateMap.put(getDate(2017, 04, 03), "清明节");
        mDateMap.put(getDate(2017, 04, 04), "清明节");
        mDateMap.put(getDate(2017, 04, 01), "");
        //劳动节
        mDateMap.put(getDate(2017, 05, 01), "劳动节");
        mDateMap.put(getDate(2017, 04, 29), "劳动节");
        mDateMap.put(getDate(2017, 04, 30), "劳动节");
        //端午节
        mDateMap.put(getDate(2017, 05, 27), "");
        mDateMap.put(getDate(2017, 05, 28), "端午节");
        mDateMap.put(getDate(2017, 05, 29), "端午节");
        mDateMap.put(getDate(2017, 05, 30), "端午节");
        //中秋节、国庆节
        mDateMap.put(getDate(2017, 10, 01), "国庆节");
        mDateMap.put(getDate(2017, 10, 02), "国庆节");
        mDateMap.put(getDate(2017, 10, 03), "国庆节");
        mDateMap.put(getDate(2017, 10, 04), "中秋节");
        mDateMap.put(getDate(2017, 10, 05), "中秋节");
        mDateMap.put(getDate(2017, 10, 06), "国庆节");
        mDateMap.put(getDate(2017, 10, 07), "国庆节");
        mDateMap.put(getDate(2017, 10, 8), "国庆节");
        mDateMap.put(getDate(2017, 9, 30), "");


    }


    private String getDate(int year, int month, int date) {
        calendar.set(year, month - 1, date);
        Date time = calendar.getTime();
        return formatDate(time);
    }

    public Map<String, String> getHolidays() {
        return mDateMap;
    }

    public static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        return format;
    }

    public String containsDate(String date) {
        String name = null;
        if (mDateMap.containsKey(date)) {
            name = mDateMap.get(date);
        }
        return name;
    }

    public String containsDate(int year, int month, int date) {
        return containsDate(getDate(year, month, date));
    }
}
