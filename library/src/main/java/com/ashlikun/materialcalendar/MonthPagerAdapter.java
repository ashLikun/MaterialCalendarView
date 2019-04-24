package com.ashlikun.materialcalendar;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.CalendarPagerAdapter;
import com.ashlikun.materialcalendar.DateRangeIndex;
import com.ashlikun.materialcalendar.MaterialCalendarView;
import com.ashlikun.materialcalendar.MonthView;

/**
 * Pager adapter backing the calendar view
 */
class MonthPagerAdapter extends com.ashlikun.materialcalendar.CalendarPagerAdapter<MonthView> {

    MonthPagerAdapter(MaterialCalendarView mcv) {
        super(mcv);
    }

    @Override
    protected MonthView createView(int position) {
        return new MonthView(mcv, getItem(position), mcv.getFirstDayOfWeek(), showWeekDays);
    }

    @Override
    protected int indexOf(MonthView view) {
        com.ashlikun.materialcalendar.CalendarDay month = view.getMonth();
        return getRangeIndex().indexOf(month);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof MonthView;
    }

    @Override
    protected com.ashlikun.materialcalendar.DateRangeIndex createRangeIndex(com.ashlikun.materialcalendar.CalendarDay min, com.ashlikun.materialcalendar.CalendarDay max) {
        return new Monthly(min, max);
    }

    public static class Monthly implements com.ashlikun.materialcalendar.DateRangeIndex {

        private final com.ashlikun.materialcalendar.CalendarDay min;
        private final int count;

        private SparseArrayCompat<com.ashlikun.materialcalendar.CalendarDay> dayCache = new SparseArrayCompat<>();

        public Monthly(@NonNull com.ashlikun.materialcalendar.CalendarDay min, @NonNull com.ashlikun.materialcalendar.CalendarDay max) {
            this.min = com.ashlikun.materialcalendar.CalendarDay.from(min.getYear(), min.getMonth(), 1);
            max = com.ashlikun.materialcalendar.CalendarDay.from(max.getYear(), max.getMonth(), 1);
            this.count = indexOf(max) + 1;
        }

        public int getCount() {
            return count;
        }

        public int indexOf(com.ashlikun.materialcalendar.CalendarDay day) {
            int yDiff = day.getYear() - min.getYear();
            int mDiff = day.getMonth() - min.getMonth();

            return (yDiff * 12) + mDiff;
        }

        public com.ashlikun.materialcalendar.CalendarDay getItem(int position) {

            com.ashlikun.materialcalendar.CalendarDay re = dayCache.get(position);
            if (re != null) {
                return re;
            }

            int numY = position / 12;
            int numM = position % 12;

            int year = min.getYear() + numY;
            int month = min.getMonth() + numM;
            if (month >= 12) {
                year += 1;
                month -= 12;
            }

            re = CalendarDay.from(year, month, 1);
            dayCache.put(position, re);
            return re;
        }
    }
}
