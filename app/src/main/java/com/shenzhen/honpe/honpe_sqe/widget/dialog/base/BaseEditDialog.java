package com.shenzhen.honpe.honpe_sqe.widget.dialog.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.CommodityPresenterInf;

/**
 * FileName: BaseEditDialog
 * Author: asus
 * Date: 2021/3/22 16:06
 * Description:
 */
public class BaseEditDialog implements CommodityPresenterInf, View.OnClickListener {
    /*** 弹窗*/
    public MyDialog mBottomSheetDialog;
    /*** 引用上下文*/
    private Context mContext;
    /*** 弹窗布局*/
    private View contentView;
    EditText etProgress;
    private String content,title;
    public BaseEditDialog(Context mContext,String title,String content) {
        this.mContext = mContext;
        this.content = content;
        this.title = title;
    }

    /**
     * 显示窗口
     */
    @Override
    public void showDialog() {
        mBottomSheetDialog = new MyDialog(mContext, R.style.GoodDialog);
        //设置退出速度
        mBottomSheetDialog.outDuration(200);
        mBottomSheetDialog.inDuration(200);
        //设置铺满
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
        //解析视图
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_share_bottom, null);
        mBottomSheetDialog.setContentView(contentView);
        LinearLayout llBack = contentView.findViewById(R.id.ll_back);
        TextView tvTitle = contentView.findViewById(R.id.tv_title);
        TextView tvEnd = contentView.findViewById(R.id.tv_end);
        etProgress = contentView.findViewById(R.id.et_progress);
        tvEnd.setText("完成");
        tvTitle.setText(title);
        etProgress.setText(content);

        tvEnd.setVisibility(View.VISIBLE);
        llBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tvEnd.setOnClickListener(this);

        mBottomSheetDialog.show();
    }

    @Override
    public void onClick(View v) {
        Utils.hideSoftInput(mContext, v);
        switch (v.getId()) {
            case R.id.ll_back:
                mBottomSheetDialog.dismiss();
                break;
            case R.id.tv_end:
                if (inputData != null) {
                    inputData.setOnClickData(etProgress.getText().toString());
                }
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    InputData inputData;

    public void setOnClickData(InputData listener) {
        this.inputData = listener;
    }

    public interface InputData {
        void setOnClickData(String strProgress);
    }
}





















