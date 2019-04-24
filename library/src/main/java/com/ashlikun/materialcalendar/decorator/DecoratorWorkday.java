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
 * 创建时间: 2017/8/29 16:51
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：工作日装饰器
 */


public class DecoratorWorkday implements DayViewDecorator, LineBackgroundSpan {
    protected Context context;
    protected Map<String, String> mDateStringMap;


    public DecoratorWorkday(Context context, Map<String, String> mDateStringMap) {
        this.mDateStringMap = mDateStringMap;
        this.context = context;
    }

    public DecoratorWorkday(Context context) {
        this.context = context;
        mDateStringMap = new HolidaysManager().getHolidays();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        boolean b = mDateStringMap.containsKey(HolidaysManager.formatDate(day.getDate()));

        if (b) {
            String s = mDateStringMap.get(HolidaysManager.formatDate(day.getDate()));
            if (TextUtils.isEmpty(s)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(this);
    }

    /**
     * * 参数简介：坐标系是文本显示的横向区域
     * 即：文本横向是充满父区域，高度自适应
     * 所以：原点是：中间显示区域的左上角
     */
    //绘制上班
    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {

        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#FF212121"));
        RectF rectF = new RectF(0, (-(right - bottom) / 2), dip2px(context, 18), (-(right - bottom) / 2) + dip2px(context, 18));
        c.drawRoundRect(rectF, 0, 0, paint);

        paint.setTextSize(dip2px(context, 14));
        paint.setColor(Color.WHITE);
        c.drawColor(Color.parseColor("#22212121"));
        c.drawText("班", dip2px(context, 2), (-(right - bottom) / 2) + dip2px(context, 14), paint);
    }
}
