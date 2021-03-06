package com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.JsonObject;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.api.DataClass;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.adapter.BottomOrderAdapter;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.ModelOneEntity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.OfferListEntity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.TagInfo;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.CommodityPresenterInf;
import com.shenzhen.honpe.pickview.builder.TimePickerBuilder;
import com.shenzhen.honpe.pickview.listener.CustomListener;
import com.shenzhen.honpe.pickview.listener.OnTimeSelectChangeListener;
import com.shenzhen.honpe.pickview.listener.OnTimeSelectListener;
import com.shenzhen.honpe.pickview.view.TimePickerView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.TOTAL_MONEY;

/**
 * FileName: CommodityPresenter
 * Author: asus
 * Date: 2021/2/20 17:47
 * Description:
 */
public class CommodityPresenter implements CommodityPresenterInf, View.OnClickListener {
    TextView tvOrderNum;
    TextView tvFinishDate;
    TextView tvScore;
    TextView tvNum;
    TextView textAmount, tvSure;
    ImageView ivClose;

    RecyclerView recyclerView;

    /*** ?????????**/
    UnQuotedBean bean;
    PersonEntity personEntity;
    List<UnQuotedBean> listBean;
    List<OfferListEntity> ListOfferEntity = new ArrayList<>();
    /*** ??????*/
    public MyDialog mBottomSheetDialog;
    /*** ???????????????*/
    private Context mContext;
    /*** ????????????*/
    private View contentView;
    /***?????????**/
    private BottomOrderAdapter adapter;

    /***??????????????????????????????**/
    private int index = -1;
    private TimePickerView pvStartTime;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private List<TagInfo> clearingFormMethod, GoodsMethod;
    private static String[] CashTypes = {"??????", "????????????", "??????", "????????????", "????????????", "????????????"};
    private static String[] GoodsTypes = {"??????", "??????", "????????????"};
    EventBusUtil busUtils;

    public CommodityPresenter(Context context, UnQuotedBean bean) {
        mContext = context;
        this.bean = bean;
    }


    /*** ????????????*/
    @Override
    public void showDialog() {
        mBottomSheetDialog = new MyDialog(mContext, R.style.GoodDialog);
        LatteLogger.d("beanbean", GsonBuildUtil.GsonBuilder(bean));
        //??????????????????
        mBottomSheetDialog.outDuration(200);
        mBottomSheetDialog.inDuration(200);
        EventBusUtil.register(this);
        mBottomSheetDialog.onMyStop(busUtils);
        //????????????
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        //????????????
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_by_shop, null);
        mBottomSheetDialog.setContentView(contentView);

        tvOrderNum = contentView.findViewById(R.id.tv_order_num);
        tvFinishDate = contentView.findViewById(R.id.tv_finish_date);
        tvScore = contentView.findViewById(R.id.tv_score);
        tvNum = contentView.findViewById(R.id.tv_num);
        ivClose = contentView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);

        tvOrderNum.setText(bean.get????????????());
        tvFinishDate.setText(bean.get??????????????????());
        tvScore.setText(bean.get????????????());
        if (bean.getRow() != 0) {
            tvNum.setText(bean.get????????????() + "");
        } else {
            tvNum.setText(bean.get??????() + "");
        }
        clearingFormMethod = DataClass.getClearingMethods(CashTypes);
        GoodsMethod = DataClass.getClearingMethods(GoodsTypes);
        /**??????UI**/
        recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        LatteLogger.d("testData", GsonBuildUtil.GsonBuilder(clearingFormMethod));
        adapter = new BottomOrderAdapter(getData(), clearingFormMethod, GoodsMethod,mBottomSheetDialog);
        adapter.addHeaderView(getHeaderView());

        recyclerView.setAdapter(adapter);
        mBottomSheetDialog.show();
    }

    private List<UnQuotedBean> getData() {
        listBean = new ArrayList<>();
        listBean.add(bean);
        return listBean;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case FinalClass.DATA:
                List<UnQuotedBean.OfferModelDTO.???????????????????????????DTO> detailList = (List<UnQuotedBean.OfferModelDTO.???????????????????????????DTO>) event.getData();
                listBean.get(0).getOfferModel().set???????????????????????????(detailList);
                adapter.UpData(listBean);
                break;
            case FinalClass.SUCCESS_FAIL:
                String s = (String) event.getData();
                if (s.contains("??????")) {
                    if (finishOrder != null) {
                        finishOrder.setOrder(bean);
                    }
                }
                ToastUtil.getInstance().showToast(s);
                break;
        }
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recylcer_head_layout, recyclerView, false);
        TextView item1 = view.findViewById(R.id.item_1);
        item1.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    FinishOrder finishOrder;

    public void setOnClickFinishOrder(FinishOrder finishOrder) {
        this.finishOrder = finishOrder;
    }

    public interface FinishOrder {
        void setOrder(UnQuotedBean bean);
    }
}






