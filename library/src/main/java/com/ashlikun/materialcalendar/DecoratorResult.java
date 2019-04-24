package com.ashlikun.materialcalendar;

import com.ashlikun.materialcalendar.DayViewDecorator;
import com.ashlikun.materialcalendar.DayViewFacade;

class DecoratorResult {
    public final com.ashlikun.materialcalendar.DayViewDecorator decorator;
    public final com.ashlikun.materialcalendar.DayViewFacade result;

    DecoratorResult(DayViewDecorator decorator, DayViewFacade result) {
        this.decorator = decorator;
        this.result = result;
    }
}
