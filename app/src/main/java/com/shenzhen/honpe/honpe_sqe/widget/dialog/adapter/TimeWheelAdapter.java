package com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter;

import com.shenzhen.honpe.wheelview.adapter.WheelAdapter;

/**
 * FileName: YearWheelAdapter
 * Author: asus
 * Date: 2021/3/31 11:36
 * Description:
 */
public class TimeWheelAdapter implements WheelAdapter {

    private int minValue;
    private int maxValue;

    public TimeWheelAdapter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue +1;
    }


    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return value;
        }
        return 0;
    }


    @Override
    public int indexOf(Object o) {
        try {
            return (int)o -minValue;
        } catch (Exception e) {
            return 0;
        }
    }
}
















