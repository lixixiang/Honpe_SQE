package com.shenzhen.honpe.honpe_sqe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.shenzhen.honpe.honpe_sqe.bean.StepBean;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;

import java.util.List;

/**
 * FileName: StepsView
 * Author: asus
 * Date: 2021/3/3 17:30
 * Description:
 */
public class StepsView extends View {
    /**
     * 动画执行的时间 230毫秒
     */
    private final static int ANIMATION_TIME = 230;
    /**
     * 动画执行的间隔次数
     */
    private final static int ANIMATION_INTERVAL = 10;
    /**
     * 线段的高度
     */
    private float mCompletedLineHeight = Utils.dp2px(getContext(), 2f);
    /**
     * 数据源
     */
    private List<StepBean> mStepBeanList;

    public StepsView(Context context) {
        this(context, null);
    }

    public StepsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }
}
















