package com.ashlikun.materialcalendar;

import com.ashlikun.materialcalendar.CalendarDay;

/**
 * Use math to calculate first days of months by position from a minimum date.
 */
interface DateRangeIndex {

    int getCount();

    int indexOf(com.ashlikun.materialcalendar.CalendarDay day);

    CalendarDay getItem(int position);
}
