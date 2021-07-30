package com.shenzhen.honpe.honpe_sqe.widget.popwindow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;

/**
 * FileName: searchPop
 * Author: asus
 * Date: 2021/4/2 11:14
 * Description:自定义搜索window
 */
public class SearchPop extends PopupWindow {
    private Context mContext;
    private TextView tv_pop_order_num,tv_pop_order_status,tv_pop_start_date,tv_pop_end_date;
    private Button btn_pop_sure;
    private RelativeLayout re_pop_date;


    public SearchPop(Context context){
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_search, null);
       getPhoneScreenW(mContext);

        tv_pop_order_num = view.findViewById(R.id.tv_pop_order_num);
        tv_pop_order_status = view.findViewById(R.id.tv_pop_order_status);
        tv_pop_start_date = view.findViewById(R.id.tv_pop_start_date);
        tv_pop_end_date = view.findViewById(R.id.tv_pop_end_date);
        btn_pop_sure = view.findViewById(R.id.btn_pop_sure);
        re_pop_date = view.findViewById(R.id.re_pop_date);

    }

    private void getPhoneScreenW(Context mContext) {
        float scaledDensity = mContext.getResources().getDisplayMetrics().scaledDensity;
        float density = mContext.getResources().getDisplayMetrics().density;
        float xdpi = mContext.getResources().getDisplayMetrics().xdpi;
        float ydpi = mContext.getResources().getDisplayMetrics().ydpi;
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;
    }

}
























