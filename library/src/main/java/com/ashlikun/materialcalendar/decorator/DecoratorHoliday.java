package com.ashlikun.materialcalendar.decorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.text.style.LineBackgroundSpan;

import com.ashlikun.materialcalendar.manager.HolidaysManager;
import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.DayViewDecorator;
import com.ashlikun.materialcalendar.DayViewFacade;

import java.util.Map;

import static com.ashlikun.materialcalendar.Utils.dip2px;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/29 16:43
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：节假日装饰器
 */

public class DecoratorHoliday implements DayViewDecorator, LineBackgroundSpan {
    protected Map<String, String> mDateStringMap;
    protected Context context;


    public DecoratorHoliday(Context context, Map<String, String> mDateStringMap) {
        this.mDateStringMap = mDateStringMap;
        this.context = context;
    }

    public DecoratorHoliday(Context context) {
        this.context = context;
        mDateStringMap = new HolidaysManager().getHolidays();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        String formatDate = HolidaysManager.formatDate(day.getDate());

        boolean b = mDateStringMap.containsKey(formatDate);

        if (b) {
            String s = mDateStringMap.get(formatDate);
            if (TextUtils.isEmpty(s)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(this);
    }

    //节假日绘制
    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {

        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#FFFF4081"));
        RectF rectF = new RectF(0, (-(right - bottom) / 2), dip2px(context, 18), (-(right - bottom) / 2) + dip2px(context, 18));
        c.drawRoundRect(rectF, 0, 0, paint);

        paint.setTextSize(dip2px(context, 14));
        paint.setColor(Color.WHITE);
        c.drawColor(Color.parseColor("#22FF4081"));
        c.drawText("休", dip2px(context, 2), (-(right - bottom) / 2) + dip2px(context, 14), paint);
    }
}
