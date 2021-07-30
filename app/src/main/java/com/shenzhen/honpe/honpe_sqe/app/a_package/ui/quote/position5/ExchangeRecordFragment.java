package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position5;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.adapter.BaseOrderAdapter;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.basepageradapter.BaseFragmentPagerAdapter;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.popwindow.CusDatePopupWindow;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.QUOTED_UPDATE_DATA;

/**
 * FileName: ExchangeRecordFragment
 * Author: asus
 * Date: 2021/3/25 18:00
 * Description:退货记录
 */
public class ExchangeRecordFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.tv_last_month)
    TextView tvLastMonth;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_next_month)
    TextView tvNextMonth;
    @BindView(R.id.tv_tag_new)
    TextView tvTagNew;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.table_title)
    LinearLayout tableTitle;
    @BindView(R.id.item_4)
    TextView item4;
    @BindView(R.id.item_3)
    TextView item3;
    QuickMultipleEntity entity;
    BaseOrderAdapter orderAdapter;

    public int oneDay = 0; //这个初始日子
    public String curMonth;
    private SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy年MM月");
    private ArrayList<UnQuotedBean> listdata;
    private String firstDay, lastDay, strWhere;
    Map<String, ArrayList<UnQuotedBean>> maps;
    private BaseFragmentPagerAdapter adapter;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat myDay = new SimpleDateFormat("dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat md = new SimpleDateFormat("MM年dd日");
    private int curPos;


    public static ExchangeRecordFragment newInstance(QuickMultipleEntity entity) {
        ExchangeRecordFragment fragment = new ExchangeRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.b_fragment;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        initToolbarNav(llBack);
        entity = (QuickMultipleEntity) bundle.getSerializable("entity");
        tvTitle.setText(entity.getStrTitle());
        curMonth = sfMonth.format(new Date());
        tvMonth.setText(curMonth);
        tvNextMonth.setTextColor(getResources().getColor(R.color.grey_home));
        tvNextMonth.setClickable(false);
        tvTagNew.setVisibility(View.VISIBLE);
        firstDay = DateUtil.getFirstDayOfMonth(sdf);
        lastDay = DateUtil.getLastDayOfMonth(sdf);
        tlTabs.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        tableTitle.setVisibility(View.VISIBLE);
        item4.setText("收货状态");
        initData();
    }

    private void initData() {
        PersonEntity personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        strWhere = "AND A.退货时间>='" + firstDay + "'AND A.退货时间<='" + lastDay + "'";

         if (personEntity !=null)
        EasyHttp.post(Constants.URL + "exchangeRecord")
                .retryCount(0)
                .params("strSID", personEntity.getValue().getSid())
                .params("strCode", "")
                .params("strCompany", personEntity.getValue().getCompany())
                .params("iCode", "0")
                .params("strWhere", strWhere)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        ProgressUtils.disLoadView(mActivity, 1);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        ProgressUtils.disLoadView(mActivity, 0);
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.getInstance().showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            listdata = GsonBuildUtil.create().fromJson(s, new TypeToken<List<UnQuotedBean>>() {}.getType());
                            LatteLogger.d("testlistdata", GsonBuildUtil.GsonBuilder(listdata));

                            LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
                            mRecyclerView.setHasFixedSize(true);
                            mRecyclerView.setLayoutManager(manager);
                            orderAdapter = new BaseOrderAdapter(listdata, tvTitle.getText().toString());
                            mRecyclerView.setAdapter(orderAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.getInstance().showToast(e.getMessage());
                        }
                    }
                });
    }


    @OnClick({R.id.tv_last_month, R.id.tv_next_month, R.id.ll_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_last_month:
                oneDay--;
                setOneMonth(oneDay);
                break;
            case R.id.tv_next_month:
                oneDay++;
                setOneMonth(oneDay);
                break;
            case R.id.ll_date:
                CusDatePopupWindow pop = new CusDatePopupWindow(_mActivity);
                pop.showPopupWindow(llDate);
                pop.setOnPopSelectDate(new CusDatePopupWindow.PopSelectDate() {
                    @Override
                    public void onDate(String date, int position) {
                        oneDay = -position;
                        tvMonth.setText(date);
                        if (tvMonth.getText().toString().equals(curMonth)) {
                            tvNextMonth.setBackgroundResource(android.R.color.transparent);
                            tvNextMonth.setTextColor(getResources().getColor(R.color.grey_home));
                            tvNextMonth.setClickable(false);
                            tvTagNew.setVisibility(View.VISIBLE);
                            tvTagNew.setVisibility(View.VISIBLE);
                        } else {
                            tvNextMonth.setBackgroundResource(android.R.color.transparent);
                            tvNextMonth.setTextColor(getResources().getColor(R.color.white));
                            tvNextMonth.setClickable(true);
                            tvTagNew.setVisibility(View.GONE);
                        }
                    }
                });
                break;
        }
    }

    public void setOneMonth(int oneDay) {
        this.oneDay = oneDay;
        tvMonth.setText(DateUtil.getLastMonth(oneDay, sfMonth));
        if (tvMonth.getText().toString().equals(curMonth)) {
            tvNextMonth.setBackgroundResource(android.R.color.transparent);
            tvNextMonth.setTextColor(getResources().getColor(R.color.grey_home));
            tvNextMonth.setClickable(false);
            tvTagNew.setVisibility(View.VISIBLE);
        } else {
            tvNextMonth.setBackgroundResource(android.R.color.transparent);
            tvNextMonth.setTextColor(getResources().getColor(R.color.white));
            tvNextMonth.setClickable(true);
            tvTagNew.setVisibility(View.GONE);
        }
        LatteLogger.d("testOnDay", curMonth + "   " + tvMonth.getText().toString() + "    " + oneDay);
        firstDay = DateUtil.getFirstDayOfMonth(sdf, oneDay);
        lastDay = DateUtil.getLastDayOfMonth(sdf, oneDay);
        initData();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case QUOTED_UPDATE_DATA:
//                listdata = (ArrayList<UnQuotedBean>) event.getData();
//                List<String> ListTitle = Arrays.asList(mActivity.getResources().getStringArray(R.array.tab_title));
//                titles.set(curPos, ListTitle.get(curPos) + "(" + listdata.size() + ")");
//                LatteLogger.d("QUOTED_UPDATE_DATA", GsonBuildUtil.GsonBuilder(listdata) + "\n" + GsonBuildUtil.GsonBuilder(titles));
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("list", listdata);
//                bundle.putString("title", titles.get(curPos));
//                adapter.replaceFragment(curPos, BFragment.newInstance(bundle), titles);
                initData();
                break;
        }
    }
}


