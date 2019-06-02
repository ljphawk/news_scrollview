package com.ljp.newsdemo.widget;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/2 0:41.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class NewsViewPager extends ViewPager {

    public NewsViewPager(@NonNull Context context) {
        super(context);
    }

    public NewsViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) {
                    height = h;
                }
            }
        }
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(dm.heightPixels, MeasureSpec.EXACTLY);
        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        Log.d("===", "onMeasure:height = " + height);
        Log.d("===", "onMeasure:dm = " + dm.heightPixels);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
