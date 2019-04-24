package com.ashlikun.materialcalendar;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Collection;

/**
 * Display a month of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
class MonthView extends CalendarPagerView {

    public MonthView(@NonNull MaterialCalendarView view, com.ashlikun.materialcalendar.CalendarDay month,
                     int firstDayOfWeek, boolean showWeekDays) {
        super(view, month, firstDayOfWeek, showWeekDays);
    }

    @Override
    protected void buildDayViews(Collection<com.ashlikun.materialcalendar.DayView> dayViews, Calendar calendar) {
        for (int r = 0; r < DEFAULT_MAX_WEEKS; r++) {
            for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
                addDayView(dayViews, calendar);
            }
        }
    }

    public com.ashlikun.materialcalendar.CalendarDay getMonth() {
        return getFirstViewDay();
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return day.getMonth() == getFirstViewDay().getMonth();
    }

    @Override
    protected int getRows() {
        return showWeekDays ? DEFAULT_MAX_WEEKS + DAY_NAMES_ROW : DEFAULT_MAX_WEEKS;
    }
}
