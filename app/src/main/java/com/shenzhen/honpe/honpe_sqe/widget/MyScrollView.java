package com.shenzhen.honpe.honpe_sqe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * FileName: MyScrollView
 * Author: asus
 * Date: 2021/3/10 10:21
 * Description:
 */
public class MyScrollView extends NestedScrollView {
    private OnScrollChanged mOnScrollChanged;

    public MyScrollView(@NonNull Context context) {
        this(context,null);
    }

    public MyScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChanged != null) {
            mOnScrollChanged.onScroll(l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mOnScrollChanged != null) {
                    mOnScrollChanged.onTouch(false);
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (mOnScrollChanged != null) {
                    mOnScrollChanged.onTouch(true);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnScrollChanged(OnScrollChanged onScrollChanged) {
        this.mOnScrollChanged = onScrollChanged;
    }
    public interface OnScrollChanged {
        void onScroll(int x, int y, int oldx, int oldy);
        void onTouch(boolean isDown);
    }

}
