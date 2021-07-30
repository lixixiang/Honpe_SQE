package com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.TimeUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.CommodityPresenterInf;
import com.shenzhen.honpe.wheelview.view.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.shenzhen.honpe.honpe_sqe.utils.TimeUtil.InitWheelViewSet;

/**
 * FileName: BaseTimePieckPresenter
 * Author: asus
 * Date: 2021/3/31 17:05
 * Description:
 */
public class BaseTimePickPresenter implements CommodityPresenterInf, View.OnClickListener {

    private Context mContext;
    public MyDialog mBottomSheetDialog;
    private View contentView;
    private WheelView mYearView, mMonthView, mDayView, mYearView2, mMonthView2, mDayView2;
    private Button btnCancel, btnSubmit;
    private TextView tvTitle;

    private List<WheelView> wheelViews = new ArrayList<>();
    private List<WheelView> wheelViews2 = new ArrayList<>();

    OnDateClickListener onDateClickListener;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    private String search;

    public void setOnDateClickListener(OnDateClickListener listener) {
        onDateClickListener = listener;
    }

    public BaseTimePickPresenter(Context mContext,String search) {
        this.mContext = mContext;
        this.search = search;
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
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_time_pick, null);
        mBottomSheetDialog.setContentView(contentView);
        tvTitle = contentView.findViewById(R.id.tvTitle);
        btnCancel = contentView.findViewById(R.id.btnCancel);
        btnSubmit = contentView.findViewById(R.id.btnSubmit);
        tvTitle.setText("时间选择器");
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        mYearView = contentView.findViewById(R.id.test_wheel_year);
        mMonthView = contentView.findViewById(R.id.test_wheel_month);
        mDayView = contentView.findViewById(R.id.test_wheel_day);
        mYearView2 = contentView.findViewById(R.id.test_wheel_year2);
        mMonthView2 = contentView.findViewById(R.id.test_wheel_month2);
        mDayView2 = contentView.findViewById(R.id.test_wheel_day2);

        wheelViews.add(mYearView);
        wheelViews.add(mMonthView);
        wheelViews.add(mDayView);
        wheelViews2.add(mYearView2);
        wheelViews2.add(mMonthView2);
        wheelViews2.add(mDayView2);

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if (search.equals("search")) {
            startDate.set(Calendar.YEAR,startDate.get(Calendar.YEAR)-1);
            startDate.set(Calendar.MONTH,1);
            startDate.set(Calendar.DATE,0);
            endDate.set(Calendar.YEAR,startDate.get(Calendar.YEAR)+1);
            endDate.set(Calendar.MONTH,11);
            endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else if (search.equals("bottom")){
            startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
            endDate.set(endDate.get(Calendar.YEAR) + 1, endDate.get(Calendar.MONTH) + 11, endDate.get(Calendar.DATE));
        }

        LatteLogger.d("getTime",sf.format(startDate.getTime())+"     "+sf.format(endDate.getTime()));

        TimeUtil.setChangedListener(wheelViews);
        TimeUtil.setChangedListener(wheelViews2);

        InitWheelViewSet(wheelViews, startDate, endDate);
        InitWheelViewSet(wheelViews2, startDate, endDate);

        mBottomSheetDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                mBottomSheetDialog.dismiss();
                break;
            case R.id.btnSubmit:
                if (onDateClickListener != null) {
                    onDateClickListener.setOnDate(sf.format(DateUtil.setDate(TimeUtil.getTime(wheelViews))), sf.format(DateUtil.setDate(TimeUtil.getTime(wheelViews2))));
                }
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    public interface OnDateClickListener {
        void setOnDate(String startDate, String endDate);
    }

}




















