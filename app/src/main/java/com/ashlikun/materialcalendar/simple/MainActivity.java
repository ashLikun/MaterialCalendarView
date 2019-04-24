package com.ashlikun.materialcalendar.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ashlikun.materialcalendar.decorator.DecoratorEnd;
import com.ashlikun.materialcalendar.decorator.DecoratorLunar;
import com.ashlikun.materialcalendar.decorator.DecoratorStart;
import com.ashlikun.materialcalendar.decorator.DecoratorToday;
import com.ashlikun.materialcalendar.manager.HolidaysManager;
import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.MaterialCalendarView;
import com.ashlikun.materialcalendar.OnDateSelectedListener;
import com.ashlikun.materialcalendar.OnMonthChangedListener;
import com.ashlikun.materialcalendar.OnRangeSelectedListener;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener, OnRangeSelectedListener {
    MaterialCalendarView calendarView;
    public DecoratorLunar mLunarDecorator;
    Calendar curr;
    HolidaysManager holidaysManager = new HolidaysManager();
    DecoratorStart decoratorStart;
    DecoratorEnd decoratorEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        calendarView.setCurrentDate(curr = Calendar.getInstance());
        //在头和尾显示其他月的日期
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
        //当点击头和尾处的其他月的日期时，跳转到其他月
        calendarView.setAllowClickDaysOutsideCurrentMonth(true);
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);
        calendarView.setOnRangeSelectedListener(this);
        mLunarDecorator = new DecoratorLunar(this, curr.get(Calendar.YEAR), curr.get(Calendar.MONTH));

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        calendarView.addDecorators(new DecoratorToday(this),//当前日期的标志
                mLunarDecorator,//显示农历
                decoratorStart = new DecoratorStart(this),
                decoratorEnd = new DecoratorEnd(this)
//                new DecoratorOneDay(),//本月第一天
//                new DecoratorHighlightWeekends(calendarView),//周末日期的标志
//                new DecoratorHoliday(this),
//                new DecoratorWorkday(this)
        );
    }

    public void onClick(View view) {

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        decoratorStart.setSelectData(widget.getSelectedDates());
        decoratorEnd.setSelectData(widget.getSelectedDates());
        widget.invalidateDecorators();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        mLunarDecorator.setData(date.getYear(), date.getMonth());
    }

    //范围选中后
    @Override
    public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
        decoratorStart.setSelectData(widget.getSelectedDates());
        decoratorEnd.setSelectData(widget.getSelectedDates());
        widget.invalidateDecorators();
    }
}
