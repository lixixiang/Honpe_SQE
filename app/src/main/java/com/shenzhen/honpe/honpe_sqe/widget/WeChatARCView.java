package com.shenzhen.honpe.honpe_sqe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;

/**
 * FileName: WeChatARCView
 * Author: asus
 * Date: 2021/1/27 9:37
 * Description:
 */
public class WeChatARCView extends View {
    private int mWidth;
    private int mHeight;
    /**
     * 弧形高度
     *
     * @param context
     */
    private int mArcHeight;

    /**
     * 前景颜色
     *
     * @param context
     */
    private int mBgColor;
    private Paint mPaint;
    private Context mContext;

    public WeChatARCView(Context context) {
        this(context, null);
    }

    public WeChatARCView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeChatARCView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, Utils.dp2px(mContext,100));
        mBgColor = typedArray.getColor(R.styleable.ArcView_bgColor, getContext().getResources().getColor(R.color.google_red));
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBgColor);

        Rect rect = new Rect(0, 0, mWidth, mArcHeight);
        canvas.drawRect(rect, mPaint);

        Path path = new Path();
        path.moveTo(0, mArcHeight);
        path.quadTo(mWidth / 2, 2* mArcHeight, mWidth, mArcHeight);
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}






