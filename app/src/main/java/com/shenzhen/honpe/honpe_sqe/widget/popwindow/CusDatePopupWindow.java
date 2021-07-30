package com.shenzhen.honpe.honpe_sqe.widget.popwindow;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.CusDateAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * FileName: CountPopwindow
 * Author: asus
 * Date: 2021/1/23 11:57
 * Description:
 */
public class CusDatePopupWindow extends PopupWindow {
    private View view;
    private Activity activity;
    private RecyclerView mRecyclerView;
    private CusDateAdapter dateAdapter;
    List<String> list;

    public CusDatePopupWindow(Activity activity) {
        super(activity);
        this.activity = activity;
        setPopupWindow();
        init();
    }

    private void init() {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        dateAdapter = new CusDateAdapter(getData());
        mRecyclerView.setAdapter(dateAdapter);
        dateAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (popSelectDate != null) {
                    popSelectDate.onDate(list.get(position),position);
                }
                dismiss();
            }
        });
    }

    private List<String> getData() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -50);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月");
        String lastMonth = dft.format(cal.getTime());
        String curMonth = dft.format(new Date());
        list = DateUtil.queryData(dft, lastMonth, curMonth);
        Utils.reverse(list);
        return list;
    }

    private void setPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.css_count_window_up_layout, null);
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        setContentView(view);
        this.setWidth((int) Math.round(w / 2.5));
        this.setHeight(Math.round(h * 0.35f));
        setFocusable(true);
        setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable();
        setBackgroundDrawable(dw);
        setAnimationStyle(R.style.AnimationPreview);

    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            showAsDropDown(parent, -18, 18);
        } else {
            this.dismiss();
        }
    }

    public PopSelectDate popSelectDate;

    public void setOnPopSelectDate(PopSelectDate date) {
        this.popSelectDate = date;
    }

    public interface PopSelectDate {
        void onDate(String date,int position);
    }
}








