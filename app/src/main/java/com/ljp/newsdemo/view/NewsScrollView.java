package com.ljp.newsdemo.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/1 20:26.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class NewsScrollView extends RelativeLayout {
    private static final String TAG = "NewsScrollView";
    private Context mContext;
    private float downEvY;//第一次按下去时的ev.getY
    private OnScrollListener mOnScrollListener;
    private int mMaxScrollViewRange = 0;//最大移动距离
    private float mMoveY;
    private boolean isMerge = false;

    public NewsScrollView(Context context) {
        this(context, null);
    }

    public NewsScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();

    }

    private void init() {

    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //点击事件不拦截
        int evY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downEvY = evY;
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent: evY - downEvY = " + (evY - downEvY));
                if (Math.abs(evY - downEvY) <= 10) {
                    return super.onInterceptTouchEvent(ev);
                }
                break;
            default:
                break;
        }
        //其他情况看 是否已经合并了 如果已经合并了  不拦截；如果没合并，拦截掉
        if (isMerge) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //手指距离view顶部的位置
        int evY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downEvY = evY;
                return super.onTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                if (isMerge) {
                    break;
                }
                //最大可移动范围 为scrollview中去除recyclerView的距离
                //计算滚动距离
                mMoveY = downEvY - evY;
                if (mMoveY <= 0) {
                    mMoveY = 0;
                } else if (mMoveY >= mMaxScrollViewRange) {
                    mMoveY = mMaxScrollViewRange;
                }
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrollChange(-mMoveY, mMaxScrollViewRange, false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mOnScrollListener != null) {
                    //如果滑动的距离小于于最大距离的一半 让两个view做动画还原
                    if (Math.abs(mMoveY) < mMaxScrollViewRange / 2) {
                        //还原
                        isMerge = false;
                        mOnScrollListener.onScrollChange(0, mMaxScrollViewRange, true);
                    } else {
                        //合并
                        isMerge = true;
                        mOnScrollListener.onScrollChange(-mMaxScrollViewRange, mMaxScrollViewRange, true);
                    }
                }
                break;
            default:
                break;
        }
        if (isMerge) {
            return super.onTouchEvent(ev);
        } else {
            return true;
        }
    }


    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setMaxScrollViewRange(int measuredHeight) {
        this.mMaxScrollViewRange = measuredHeight;
    }

    public boolean getIsMerge() {
        return isMerge;
    }

    public void setMergeState(boolean isMerge) {
        this.isMerge = isMerge;
    }

    public interface OnScrollListener {
        void onScrollChange(float y, int maxRange, boolean isAnim);
    }
}
