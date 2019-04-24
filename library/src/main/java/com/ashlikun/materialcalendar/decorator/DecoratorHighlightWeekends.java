package com.ashlikun.materialcalendar.decorator;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.DayViewDecorator;
import com.ashlikun.materialcalendar.DayViewFacade;
import com.ashlikun.materialcalendar.MaterialCalendarView;

import java.util.Calendar;
import java.util.List;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/29 16:55
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：高亮周六周日装饰器
 */
public class DecoratorHighlightWeekends implements DayViewDecorator {

    MaterialCalendarView calendarView;

    public DecoratorHighlightWeekends(MaterialCalendarView calendarView) {
        this.calendarView = calendarView;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        List<CalendarDay> list = calendarView.getSelectedDates();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (day.equals(list.get(i))) {
                    return false;
                }
            }
        }
        Calendar calendar = day.getCalendar();
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.parseColor("#303F9F")));
    }
}
