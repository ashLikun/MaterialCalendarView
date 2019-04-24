package com.ashlikun.materialcalendar.decorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.ashlikun.materialcalendar.manager.LunarManage;
import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.DayViewDecorator;
import com.ashlikun.materialcalendar.DayViewFacade;

import java.util.Calendar;

import static com.ashlikun.materialcalendar.Utils.dip2px;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/29 16:59
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：农历绘制装饰器
 */

public class DecoratorLunar implements DayViewDecorator, LineBackgroundSpan {
    protected Context context;
    protected int year;
    protected int month;

    public DecoratorLunar(Context context, int year, int month) {
        this.context = context;
        this.year = year;
        this.month = month;
    }

    public void setData(int year, int month) {
        this.year = year;
        this.month = month;
    }

    /**
     * 这个方法是先于 decorate() 执行的
     *
     * @param day
     * @return
     */
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(this);
    }


    //农历绘制
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(text.toString()));

        LunarManage lunar = new LunarManage(calendar.getTime());
        String chinaDayString = lunar.getChinaDayString();
        Paint paint = new Paint();
        paint.setTextSize(dip2px(context, 10));
        paint.setColor(Color.parseColor("#cccccc"));
        c.drawText(chinaDayString, (right - left) / 2 - dip2px(context, 10), (bottom - top) / 2 + dip2px(context, 17), paint);
    }
}