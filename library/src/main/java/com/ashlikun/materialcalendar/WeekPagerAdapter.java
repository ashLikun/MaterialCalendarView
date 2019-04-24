package com.ashlikun.materialcalendar;

import android.support.annotation.NonNull;

import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.CalendarPagerAdapter;
import com.ashlikun.materialcalendar.DateRangeIndex;
import com.ashlikun.materialcalendar.Experimental;
import com.ashlikun.materialcalendar.MaterialCalendarView;
import com.ashlikun.materialcalendar.WeekView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Experimental
public class WeekPagerAdapter extends com.ashlikun.materialcalendar.CalendarPagerAdapter<WeekView> {

    public WeekPagerAdapter(MaterialCalendarView mcv) {
        super(mcv);
    }

    @Override
    protected WeekView createView(int position) {
        return new WeekView(mcv, getItem(position), mcv.getFirstDayOfWeek(), showWeekDays);
    }

    @Override
    protected int indexOf(WeekView view) {
        com.ashlikun.materialcalendar.CalendarDay week = view.getFirstViewDay();
        return getRangeIndex().indexOf(week);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof WeekView;
    }

    @Override
    protected com.ashlikun.materialcalendar.DateRangeIndex createRangeIndex(com.ashlikun.materialcalendar.CalendarDay min, com.ashlikun.materialcalendar.CalendarDay max) {
        return new Weekly(min, max, mcv.getFirstDayOfWeek());
    }

    public static class Weekly implements com.ashlikun.materialcalendar.DateRangeIndex {

        private static final int DAYS_IN_WEEK = 7;
        private final com.ashlikun.materialcalendar.CalendarDay min;
        private final int count;

        public Weekly(@NonNull com.ashlikun.materialcalendar.CalendarDay min, @NonNull com.ashlikun.materialcalendar.CalendarDay max, int firstDayOfWeek) {
            this.min = getFirstDayOfWeek(min, firstDayOfWeek);
            this.count = weekNumberDifference(this.min, max) + 1;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int indexOf(com.ashlikun.materialcalendar.CalendarDay day) {
            return weekNumberDifference(min, day);
        }

        @Override
        public com.ashlikun.materialcalendar.CalendarDay getItem(int position) {
            long minMillis = min.getDate().getTime();
            long millisOffset = TimeUnit.MILLISECONDS.convert(
                    position * DAYS_IN_WEEK,
                    TimeUnit.DAYS);
            long positionMillis = minMillis + millisOffset;
            return com.ashlikun.materialcalendar.CalendarDay.from(positionMillis);
        }

        private int weekNumberDifference(@NonNull com.ashlikun.materialcalendar.CalendarDay min, @NonNull com.ashlikun.materialcalendar.CalendarDay max) {
            long millisDiff = max.getDate().getTime() - min.getDate().getTime();

            int dstOffsetMax = max.getCalendar().get(Calendar.DST_OFFSET);
            int dstOffsetMin = min.getCalendar().get(Calendar.DST_OFFSET);

            long dayDiff = TimeUnit.DAYS.convert(millisDiff + dstOffsetMax - dstOffsetMin, TimeUnit.MILLISECONDS);
            return (int) (dayDiff / DAYS_IN_WEEK);
        }

        /*
         * Necessary because of how Calendar handles getting the first day of week internally.
         * TODO: WTF IS THIS
         */
        private com.ashlikun.materialcalendar.CalendarDay getFirstDayOfWeek(@NonNull com.ashlikun.materialcalendar.CalendarDay min, int wantedFirstDayOfWeek) {
            Calendar calendar = Calendar.getInstance();
            min.copyTo(calendar);
            while (calendar.get(Calendar.DAY_OF_WEEK) != wantedFirstDayOfWeek) {
                calendar.add(Calendar.DAY_OF_WEEK, -1);
            }
            return CalendarDay.from(calendar);
        }
    }
}
