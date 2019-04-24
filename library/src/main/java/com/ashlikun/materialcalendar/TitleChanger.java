package com.ashlikun.materialcalendar;

import android.animation.Animator;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.ashlikun.materialcalendar.AnimatorListener;
import com.ashlikun.materialcalendar.CalendarDay;
import com.ashlikun.materialcalendar.MaterialCalendarView;
import com.ashlikun.materialcalendar.format.TitleFormatter;

class TitleChanger {

    public static final int DEFAULT_ANIMATION_DELAY = 400;
    public static final int DEFAULT_Y_TRANSLATION_DP = 20;

    private final TextView title;
    private TitleFormatter titleFormatter;

    private final int animDelay;
    private final int animDuration;
    private final int translate;
    private final Interpolator interpolator = new DecelerateInterpolator(2f);

    private int orientation = com.ashlikun.materialcalendar.MaterialCalendarView.VERTICAL;

    private long lastAnimTime = 0;
    private com.ashlikun.materialcalendar.CalendarDay previousMonth = null;

    public TitleChanger(TextView title) {
        this.title = title;

        Resources res = title.getResources();

        animDelay = DEFAULT_ANIMATION_DELAY;

        animDuration = res.getInteger(android.R.integer.config_shortAnimTime) / 2;

        translate = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_Y_TRANSLATION_DP, res.getDisplayMetrics()
        );
    }

    public void change(final com.ashlikun.materialcalendar.CalendarDay currentMonth) {
        long currentTime = System.currentTimeMillis();

        if (currentMonth == null) {
            return;
        }

        if (TextUtils.isEmpty(title.getText()) || (currentTime - lastAnimTime) < animDelay) {
            doChange(currentTime, currentMonth, false);
        }

        if (currentMonth.equals(previousMonth) ||
                (currentMonth.getMonth() == previousMonth.getMonth()
                        && currentMonth.getYear() == previousMonth.getYear())) {
            return;
        }

        doChange(currentTime, currentMonth, true);
    }

    private void doChange(final long now, final com.ashlikun.materialcalendar.CalendarDay currentMonth, boolean animate) {

        title.animate().cancel();
        doTranslation(title, 0);

        title.setAlpha(1);
        lastAnimTime = now;

        final CharSequence newTitle = titleFormatter.format(currentMonth);

        if (!animate) {
            title.setText(newTitle);
        } else {
            final int translation = translate * (previousMonth.isBefore(currentMonth) ? 1 : -1);
            final ViewPropertyAnimator viewPropertyAnimator = title.animate();

            if (orientation == com.ashlikun.materialcalendar.MaterialCalendarView.HORIZONTAL) {
                viewPropertyAnimator.translationX(translation * -1);
            } else {
                viewPropertyAnimator.translationY(translation * -1);
            }

            viewPropertyAnimator
                    .alpha(0)
                    .setDuration(animDuration)
                    .setInterpolator(interpolator)
                    .setListener(new com.ashlikun.materialcalendar.AnimatorListener() {

                        @Override
                        public void onAnimationCancel(Animator animator) {
                            doTranslation(title, 0);
                            title.setAlpha(1);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            title.setText(newTitle);
                            doTranslation(title, translation);

                            final ViewPropertyAnimator viewPropertyAnimator = title.animate();
                            if (orientation == com.ashlikun.materialcalendar.MaterialCalendarView.HORIZONTAL) {
                                viewPropertyAnimator.translationX(0);
                            } else {
                                viewPropertyAnimator.translationY(0);
                            }

                            viewPropertyAnimator
                                    .alpha(1)
                                    .setDuration(animDuration)
                                    .setInterpolator(interpolator)
                                    .setListener(new com.ashlikun.materialcalendar.AnimatorListener())
                                    .start();
                        }
                    }).start();
        }

        previousMonth = currentMonth;
    }

    private void doTranslation(final TextView title, final int translate) {
        if (orientation == MaterialCalendarView.HORIZONTAL) {
            title.setTranslationX(translate);
        } else {
            title.setTranslationY(translate);
        }
    }

    public TitleFormatter getTitleFormatter() {
        return titleFormatter;
    }

    public void setTitleFormatter(TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setPreviousMonth(CalendarDay previousMonth) {
        this.previousMonth = previousMonth;
    }
}
