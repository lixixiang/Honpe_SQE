package com.shenzhen.honpe.honpe_sqe.widget.registerDialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shenzhen.honpe.honpe_sqe.R;

/**
 * FileName: SupplyClassificationDialog
 * Author: asus
 * Date: 2021/3/10 13:51
 * Description:
 */
public class SupplyClassificationDialog extends PopupWindow {
    /*** 引用上下文*/
    private Context mContext;
    /*** 弹窗布局*/
    private View contentView;
    private Button btnCancel, btnSure;
    private TextView tvTitle;
    private String mTitle;
    private RecyclerView mRecyclerView;


    public SupplyClassificationDialog(Context context, String title) {
        this.mContext = context;
        this.mTitle = title;
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_register_supply_classification, null);
        setContentView(contentView);
        initById(contentView);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        btnCancel.setTextColor(mContext.getResources().getColor(R.color.grey_home));
        btnSure.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        tvTitle.setText(mTitle);

    }

    private void initById(View view) {
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSure = view.findViewById(R.id.btnSubmit);
        tvTitle = view.findViewById(R.id.tvTitle);
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }
}






















