package com.ashlikun.materialcalendar;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ashlikun.materialcalendar.MaterialCalendarView.ShowOtherDates;
import com.ashlikun.materialcalendar.format.DayFormatter;
import com.ashlikun.materialcalendar.format.TitleFormatter;
import com.ashlikun.materialcalendar.format.WeekDayFormatter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pager adapter backing the calendar view
 */
abstract class CalendarPagerAdapter<V extends CalendarPagerView> extends PagerAdapter {

    private final ArrayDeque<V> currentViews;

    protected final MaterialCalendarView mcv;
    private final com.ashlikun.materialcalendar.CalendarDay today;

    private TitleFormatter titleFormatter = null;
    private Integer color = null;
    private Integer dateTextAppearance = null;
    private Integer weekDayTextAppearance = null;
    @ShowOtherDates
    private int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;
    private com.ashlikun.materialcalendar.CalendarDay minDate = null;
    private com.ashlikun.materialcalendar.CalendarDay maxDate = null;
    private DateRangeIndex rangeIndex;
    private List<com.ashlikun.materialcalendar.CalendarDay> selectedDates = new ArrayList<>();
    private WeekDayFormatter weekDayFormatter = WeekDayFormatter.DEFAULT;
    private DayFormatter dayFormatter = DayFormatter.DEFAULT;
    private DayFormatter dayFormatterContentDescription = dayFormatter;
    private List<DayViewDecorator> decorators = new ArrayList<>();
    private List<DecoratorResult> decoratorResults = null;
    private boolean selectionEnabled = true;
    boolean showWeekDays;

    CalendarPagerAdapter(MaterialCalendarView mcv) {
        this.mcv = mcv;
        this.today = com.ashlikun.materialcalendar.CalendarDay.today();
        currentViews = new ArrayDeque<>();
        currentViews.iterator();
        setRangeDates(null, null);
    }

    public void setDecorators(List<DayViewDecorator> decorators) {
        this.decorators = decorators;
        invalidateDecorators();
    }

    public void invalidateDecorators() {
        decoratorResults = new ArrayList<>();
        for (DayViewDecorator decorator : decorators) {
            DayViewFacade facade = new DayViewFacade();
            decorator.decorate(facade);
            if (facade.isDecorated()) {
                decoratorResults.add(new DecoratorResult(decorator, facade));
            }
        }
        for (V pagerView : currentViews) {
            pagerView.setDayViewDecorators(decoratorResults);
        }
    }

    @Override
    public int getCount() {
        return rangeIndex.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFormatter == null ? "" : titleFormatter.format(getItem(position));
    }

    public CalendarPagerAdapter<?> migrateStateAndReturn(CalendarPagerAdapter<?> newAdapter) {
        newAdapter.titleFormatter = titleFormatter;
        newAdapter.color = color;
        newAdapter.dateTextAppearance = dateTextAppearance;
        newAdapter.weekDayTextAppearance = weekDayTextAppearance;
        newAdapter.showOtherDates = showOtherDates;
        newAdapter.minDate = minDate;
        newAdapter.maxDate = maxDate;
        newAdapter.selectedDates = selectedDates;
        newAdapter.weekDayFormatter = weekDayFormatter;
        newAdapter.dayFormatter = dayFormatter;
        newAdapter.dayFormatterContentDescription = dayFormatterContentDescription;
        newAdapter.decorators = decorators;
        newAdapter.decoratorResults = decoratorResults;
        newAdapter.selectionEnabled = selectionEnabled;
        return newAdapter;
    }

    public int getIndexForDay(com.ashlikun.materialcalendar.CalendarDay day) {
        if (day == null) {
            return getCount() / 2;
        }
        if (minDate != null && day.isBefore(minDate)) {
            return 0;
        }
        if (maxDate != null && day.isAfter(maxDate)) {
            return getCount() - 1;
        }
        return rangeIndex.indexOf(day);
    }

    protected abstract V createView(int position);

    protected abstract int indexOf(V view);

    protected abstract boolean isInstanceOfView(Object object);

    protected abstract DateRangeIndex createRangeIndex(com.ashlikun.materialcalendar.CalendarDay min, com.ashlikun.materialcalendar.CalendarDay max);

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (!(isInstanceOfView(object))) {
            return POSITION_NONE;
        }
        CalendarPagerView pagerView = (CalendarPagerView) object;
        com.ashlikun.materialcalendar.CalendarDay firstViewDay = pagerView.getFirstViewDay();
        if (firstViewDay == null) {
            return POSITION_NONE;
        }
        int index = indexOf((V) object);
        if (index < 0) {
            return POSITION_NONE;
        }
        return index;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        V pagerView = createView(position);
        pagerView.setContentDescription(mcv.getCalendarContentDescription());
        pagerView.setAlpha(0);
        pagerView.setSelectionEnabled(selectionEnabled);

        pagerView.setWeekDayFormatter(weekDayFormatter);
        pagerView.setDayFormatter(dayFormatter);
        pagerView.setDayFormatterContentDescription(dayFormatterContentDescription);
        if (color != null) {
            pagerView.setSelectionColor(color);
        }
        if (dateTextAppearance != null) {
            pagerView.setDateTextAppearance(dateTextAppearance);
        }
        if (weekDayTextAppearance != null) {
            pagerView.setWeekDayTextAppearance(weekDayTextAppearance);
        }
        pagerView.setShowOtherDates(showOtherDates);
        pagerView.setMinimumDate(minDate);
        pagerView.setMaximumDate(maxDate);
        pagerView.setSelectedDates(selectedDates);

        container.addView(pagerView);
        currentViews.add(pagerView);

        pagerView.setDayViewDecorators(decoratorResults);

        return pagerView;
    }

    public void setShowWeekDays(boolean showWeekDays) {
        this.showWeekDays = showWeekDays;
    }

    public boolean isShowWeekDays() {
        return showWeekDays;
    }

    public void setSelectionEnabled(boolean enabled) {
        selectionEnabled = enabled;
        for (V pagerView : currentViews) {
            pagerView.setSelectionEnabled(selectionEnabled);
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        V pagerView = (V) object;
        currentViews.remove(pagerView);
        container.removeView(pagerView);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setTitleFormatter(@NonNull TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter;
    }

    public void setSelectionColor(int color) {
        this.color = color;
        for (V pagerView : currentViews) {
            pagerView.setSelectionColor(color);
        }
    }

    public void setDateTextAppearance(int taId) {
        if (taId == 0) {
            return;
        }
        this.dateTextAppearance = taId;
        for (V pagerView : currentViews) {
            pagerView.setDateTextAppearance(taId);
        }
    }

    public void setShowOtherDates(@ShowOtherDates int showFlags) {
        this.showOtherDates = showFlags;
        for (V pagerView : currentViews) {
            pagerView.setShowOtherDates(showFlags);
        }
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        this.weekDayFormatter = formatter;
        for (V pagerView : currentViews) {
            pagerView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        dayFormatterContentDescription = dayFormatterContentDescription == dayFormatter ?
                formatter : dayFormatterContentDescription;
        this.dayFormatter = formatter;
        for (V pagerView : currentViews) {
            pagerView.setDayFormatter(formatter);
        }
    }

    public void setDayFormatterContentDescription(DayFormatter formatter) {
        dayFormatterContentDescription = formatter;
        for (V pagerView : currentViews) {
            pagerView.setDayFormatterContentDescription(formatter);
        }
    }

    @ShowOtherDates
    public int getShowOtherDates() {
        return showOtherDates;
    }

    public void setWeekDayTextAppearance(int taId) {
        if (taId == 0) {
            return;
        }
        this.weekDayTextAppearance = taId;
        for (V pagerView : currentViews) {
            pagerView.setWeekDayTextAppearance(taId);
        }
    }

    public void setRangeDates(com.ashlikun.materialcalendar.CalendarDay min, com.ashlikun.materialcalendar.CalendarDay max) {
        this.minDate = min;
        this.maxDate = max;
        for (V pagerView : currentViews) {
            pagerView.setMinimumDate(min);
            pagerView.setMaximumDate(max);
        }

        if (min == null) {
            min = com.ashlikun.materialcalendar.CalendarDay.from(today.getYear() - 200, today.getMonth(), today.getDay());
        }

        if (max == null) {
            max = com.ashlikun.materialcalendar.CalendarDay.from(today.getYear() + 200, today.getMonth(), today.getDay());
        }

        rangeIndex = createRangeIndex(min, max);

        notifyDataSetChanged();
        invalidateSelectedDates();
    }

    public DateRangeIndex getRangeIndex() {
        return rangeIndex;
    }

    public void clearSelections() {
        selectedDates.clear();
        invalidateSelectedDates();
    }

    public void setDateSelected(com.ashlikun.materialcalendar.CalendarDay day, boolean selected) {
        if (selected) {
            if (!selectedDates.contains(day)) {
                selectedDates.add(day);
                invalidateSelectedDates();
            }
        } else {
            if (selectedDates.contains(day)) {
                selectedDates.remove(day);
                invalidateSelectedDates();
            }
        }
    }

    private void invalidateSelectedDates() {
        validateSelectedDates();
        for (V pagerView : currentViews) {
            pagerView.setSelectedDates(selectedDates);
        }
    }

    private void validateSelectedDates() {
        for (int i = 0; i < selectedDates.size(); i++) {
            com.ashlikun.materialcalendar.CalendarDay date = selectedDates.get(i);

            if ((minDate != null && minDate.isAfter(date)) || (maxDate != null && maxDate.isBefore(date))) {
                selectedDates.remove(i);
                mcv.onDateUnselected(date);
                i -= 1;
            }
        }
    }

    public com.ashlikun.materialcalendar.CalendarDay getItem(int position) {
        return rangeIndex.getItem(position);
    }

    @NonNull
    public List<CalendarDay> getSelectedDates() {
        return Collections.unmodifiableList(selectedDates);
    }

    protected int getDateTextAppearance() {
        return dateTextAppearance == null ? 0 : dateTextAppearance;
    }

    protected int getWeekDayTextAppearance() {
        return weekDayTextAppearance == null ? 0 : weekDayTextAppearance;
    }
}
