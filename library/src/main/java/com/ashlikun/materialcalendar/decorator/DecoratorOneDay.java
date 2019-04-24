package com.ashlikun.materialcalendar.decorator;

import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.DayViewDecorator;
import com.ashlikun.materialcalendar.DayViewFacade;

import java.util.Calendar;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/29 17:04
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：某一天  装饰器，默认今天
 */
public class DecoratorOneDay implements DayViewDecorator {
    private CalendarDay date;

    public DecoratorOneDay() {
        date = CalendarDay.today();
    }

    public DecoratorOneDay(CalendarDay date) {
        this.date = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
    }


    public void setDate(Calendar date) {
        this.date = CalendarDay.from(date);
    }
}
