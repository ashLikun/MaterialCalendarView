package com.ashlikun.materialcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

import static com.ashlikun.materialcalendar.Utils.dip2px;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/29 16:44
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：今天装饰器
 */


public class DecoratorToday implements DayViewDecorator, LineBackgroundSpan {
    Context context;

    public DecoratorToday(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Date date = new Date();
        String dateStr = Utils.date2String(date, "yyyy-MM-dd");
        Date parse = Utils.string2Date(dateStr, "yyyy-MM-dd");
        if (day.getDate().equals(parse)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(this);
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //消除锯齿
        paint.setStyle(Paint.Style.STROKE);//绘制空心圆或 空心矩形
        int ringWidth = dip2px(context, 1);//圆环宽度
        //绘制圆环
        paint.setColor(Color.parseColor("#303F9F"));
        paint.setStrokeWidth(ringWidth);
        c.drawCircle((right - left) / 2, (bottom - top) / 2 /*+ CircleBackGroundSpan.dip2px(4)*/,
                /*CircleBackGroundSpan.dip2px(20),*/
                right / 2 - dip2px(context, 1),
                paint);
    }
}
