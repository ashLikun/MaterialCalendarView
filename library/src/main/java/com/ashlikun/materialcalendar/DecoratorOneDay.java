package com.ashlikun.materialcalendar;

import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

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

    /**
     * We're changing the internals, so make sure to call
     * {@ linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
