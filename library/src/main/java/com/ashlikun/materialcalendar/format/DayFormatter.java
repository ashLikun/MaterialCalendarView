package com.ashlikun.materialcalendar.format;

import android.support.annotation.NonNull;

import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.format.DateFormatDayFormatter;

import java.text.SimpleDateFormat;

/**
 * Supply labels for a given day. Default implementation is to format using a {@linkplain SimpleDateFormat}
 */
public interface DayFormatter {

    /**
     * Format a given day into a string
     *
     * @param day the day
     * @return a label for the day
     */
    @NonNull
    String format(@NonNull CalendarDay day);

    /**
     * Default implementation used by {@linkplain com.ashlikun.materialcalendar.MaterialCalendarView}
     */
    DayFormatter DEFAULT = new DateFormatDayFormatter();
}
