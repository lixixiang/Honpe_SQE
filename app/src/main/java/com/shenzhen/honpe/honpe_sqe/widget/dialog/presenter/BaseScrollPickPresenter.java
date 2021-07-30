package com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.CommodityPresenterInf;
import com.shenzhen.honpe.pickview.adapter.ArrayWheelAdapter;
import com.shenzhen.honpe.wheelview.listener.OnItemSelectedListener;
import com.shenzhen.honpe.wheelview.view.WheelView;

import java.util.List;

/**
 * FileName: BaseTimePieckPresenter
 * Author: asus
 * Date: 2021/3/31 17:05
 * Description:
 */
public class BaseScrollPickPresenter implements CommodityPresenterInf, View.OnClickListener {
    private Context mContext;
    public MyDialog mBottomSheetDialog;
    private View contentView;
    private WheelView mWv;
    private Button btnCancel, btnSubmit;
    private TextView tvTitle;
    private List<String> list;
    private String strIndex,title,currentTxt;

    OnDateClickListener onDateClickListener;

    public void setOnDateClickListener(OnDateClickListener listener) {
        onDateClickListener = listener;
    }

    public BaseScrollPickPresenter(Context mContext, List<String> list,String title,String currentTxt) {
        this.mContext = mContext;
        this.list = list;
        this.title = title;
        this.currentTxt = currentTxt;
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
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        //解析视图
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_single_scroll_pick, null);
        mBottomSheetDialog.setContentView(contentView);
        tvTitle = contentView.findViewById(R.id.tvTitle);
        btnCancel = contentView.findViewById(R.id.btnCancel);
        btnSubmit = contentView.findViewById(R.id.btnSubmit);
        tvTitle.setText(title+"选择器");
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        mWv = contentView.findViewById(R.id.wv);
        mWv.setCyclic(false);
        mWv.setItemsVisibleCount(5);
        if ("".equals(currentTxt) || TextUtils.isEmpty(currentTxt)) {
            mWv.setCurrentItem(0);
            strIndex =list.get(mWv.getItemsCount());
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (currentTxt.equals(list.get(i))) {
                    mWv.setCurrentItem(i);
                    strIndex = list.get(i);
                }
            }
        }

        mWv.setAdapter(new ArrayWheelAdapter(list));
        returnData(strIndex);
        LatteLogger.d("strIndex",currentTxt);
        mWv.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                strIndex = list.get(index);
            }
        });
        mBottomSheetDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                mBottomSheetDialog.dismiss();
                break;
            case R.id.btnSubmit:
                returnData(strIndex);
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    public interface OnDateClickListener {
        void setOnDate(String data);
    }

    public void returnData(String strIndex) {
        if (onDateClickListener != null) {
            onDateClickListener.setOnDate(strIndex);
        }
    }

}




















