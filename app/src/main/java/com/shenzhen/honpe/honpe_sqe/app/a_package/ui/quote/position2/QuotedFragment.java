package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position2;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.basepageradapter.BaseFragmentPagerAdapter;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.DJEditText;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter.BaseScrollPickPresenter;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter.BaseTimePickPresenter;
import com.shenzhen.honpe.honpe_sqe.widget.popwindow.CusDatePopupWindow;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.QUOTED_UPDATE_DATA;

/**
 * FileName: QuotedFragment
 * Author: asus
 * Date: 2021/3/1 13:29
 * Description:
 */
public class QuotedFragment extends BaseBackFragment implements View.OnClickListener {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.re_date_layout)
    RelativeLayout reDateLayout;
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
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.et_search_order)
    DJEditText etSearchOrder;
    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    List<String> titles2 = new ArrayList<>();
    QuickMultipleEntity entity;
    public int oneDay = 0; //这个初始日子
    public String curMonth,addSure;

    private SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy年MM月");
    private ArrayList<UnQuotedBean> listdata;
    private String firstDay, lastDay, strWhere;
    Map<String, ArrayList<UnQuotedBean>> maps;
    private BaseFragmentPagerAdapter adapter;
    private boolean isShowSearch = false;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat myDay = new SimpleDateFormat("dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat md = new SimpleDateFormat("MM年dd日");
    private int curPos;
    PopupWindow window;

    public static QuotedFragment newInstance(QuickMultipleEntity entity) {
        QuotedFragment fragment = new QuotedFragment();
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
        tvEnd.setVisibility(View.VISIBLE);
        tvEnd.setText(getString(R.string.search));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initData(etSearchOrder.getText().toString());
    }

    private void initData(String strCode) {
        PersonEntity personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        if (!TextUtils.isEmpty(addSure)) {
            strWhere = "AND T.报价日期>='" + firstDay + "' AND T.报价日期<='" + lastDay + "'"+addSure;
        } else {
            strWhere = "AND T.报价日期>='" + firstDay + "' AND T.报价日期<='" + lastDay + "'";
        }
        if (TextUtils.isEmpty(strCode)) {
            strCode = "";
        }
        if (personEntity != null) {
            EasyHttp.post(Constants.URL + "OfferedSearch")
                    .retryCount(0)
                    .params("strSID", personEntity.getValue().getSid())
                    .params("strCode", strCode)
                    .params("strCompany", personEntity.getValue().getCompany())
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
                            listdata = GsonBuildUtil.create().fromJson(s, new TypeToken<List<UnQuotedBean>>() {
                            }.getType());

                            for (int i = 0; i < listdata.size(); i++) {
                                UnQuotedBean bean = listdata.get(i);
                                double tax = 0;
                                for (int j = 0; j < listdata.get(i).getOfferModel().get申请单明细报价记录().size(); j++) {
                                    tax = tax + (bean.getOfferModel().get申请单明细报价记录().get(j).get单价() * bean.getOfferModel().get申请单明细报价记录().get(j).get税率() * bean.getOfferModel().get申请单明细报价记录().get(j).get数量());
                                }
                                bean.set税额(tax);
                            }
                            if (maps != null) {
                                maps.clear();
                            } else {
                                maps = new LinkedHashMap<>();
                            }
                            titles = Arrays.asList(mActivity.getResources().getStringArray(R.array.tab_title));

                            for (int i = 0; i < titles.size(); i++) {
                                ArrayList<UnQuotedBean> listDataBean = maps.get(titles.get(i));
                                if (listDataBean == null) {
                                    listDataBean = new ArrayList<>();
                                }
                                listDataBean.clear();
                                for (int j = 0; j < listdata.size(); j++) {
                                    if (i == 1 && listdata.get(j).get中标状态() == 0) { //待处理
                                        listDataBean.add(listdata.get(j));
                                    } else if (i == 2 && listdata.get(j).get中标状态() > 0) { //已中标
                                        listDataBean.add(listdata.get(j));
                                    } else if (i == 3 && listdata.get(j).get中标状态() == -1) { //未中标
                                        listDataBean.add(listdata.get(j));
                                    } else if (i == 0) { //全部
                                        listDataBean.add(listdata.get(j));
                                    }
                                }
                                maps.put(titles.get(i), listDataBean);
                            }

                            int i = 0;
                            if (mViewPager.getAdapter() != null) {
                                for (String key : maps.keySet()) {
                                    listdata = maps.get(key);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("list", listdata);
                                    bundle.putString("title", key);
                                    titles.set(i, key + "(" + listdata.size() + ")");
                                    adapter.replaceFragment(i++, BFragment.newInstance(bundle), titles);
                                }
                            } else {
                                for (String key : maps.keySet()) {
                                    listdata = maps.get(key);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("list", listdata);
                                    bundle.putString("title", key);
                                    fragments.add(BFragment.newInstance(bundle));
                                    titles.set(i++, key + "(" + listdata.size() + ")");
                                }
                                adapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, titles);
                                mViewPager.setAdapter(adapter);
                            }

                            tlTabs.setupWithViewPager(mViewPager);
                            mViewPager.setOffscreenPageLimit(titles.size());
                            mViewPager.setCurrentItem(curPos);
                            curPos = mViewPager.getCurrentItem();
                            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    curPos = position;
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }
                    });
        }
    }


    @OnClick({R.id.tv_last_month, R.id.tv_next_month, R.id.ll_date, R.id.tv_end, R.id.tv_sure})
    public void onButtClick(View view) {
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
                        setOneMonth(oneDay);
                    }
                });
                break;
            case R.id.tv_sure:
                initData(etSearchOrder.getText().toString());
                break;
            case R.id.tv_end:
//                if (isShowSearch) {
//                    searchLayout.setVisibility(View.GONE);
//                    tvEnd.setText("搜索");
//                } else {
//                    searchLayout.setVisibility(View.VISIBLE);
//                    tvEnd.setText("隐藏");
//                }
//                isShowSearch = !isShowSearch;
                ShowPopSearch();
                hideSoftKeyBoard();
                break;

        }
    }

    TextView tv_pop_order_num, tv_pop_order_status,tv_pop_start_date,tv_pop_end_date;

    private void ShowPopSearch() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.pop_search, null);
        View vB = view.findViewById(R.id.v_background);
        tv_pop_order_num = view.findViewById(R.id.tv_pop_order_num);
        tv_pop_order_status = view.findViewById(R.id.tv_pop_order_status);
        tv_pop_start_date = view.findViewById(R.id.tv_pop_start_date);
        tv_pop_end_date = view.findViewById(R.id.tv_pop_end_date);
        RelativeLayout re_pop_date = view.findViewById(R.id.re_pop_date);
        Button btn_pop_sure = view.findViewById(R.id.btn_pop_sure);
        StringUtil.HintUtil(tv_pop_start_date, "开始时间：" + ymd.format(DateUtil.setDate(sdf,firstDay)),12);
        StringUtil.HintUtil(tv_pop_end_date,"结束时间："+ ymd.format(DateUtil.setDate(sdf,lastDay)),12);
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        window = new PopupWindow(view, dm.widthPixels,
                dm.heightPixels
                        - ImmersionBar.getStatusBarHeight(mActivity)
                        - Utils.getViewHeight(toolbar, true)
                        - Utils.getViewHeight(reDateLayout, true),
                true);
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.showAsDropDown(tlTabs, 0, 0);
        vB.setOnClickListener(this);
        tv_pop_order_num.setOnClickListener(this);
        tv_pop_order_status.setOnClickListener(this);
        re_pop_date.setOnClickListener(this);
        btn_pop_sure.setOnClickListener(this);

    }

    List<String> list = new ArrayList<>();
    List<String> list2 = new ArrayList<>();

    String[] status = {"未中标", "已中标", "待处理"};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_background:
                window.dismiss();
                break;
            case R.id.tv_pop_order_num:
                titles = Arrays.asList(mActivity.getResources().getStringArray(R.array.tab_title));
                for (String key : maps.keySet()) {
                    if (titles.get(curPos).equals(key)) {
                        listdata = maps.get(key);
                        for (int i = 0; i < listdata.size(); i++) {
                            list.add(listdata.get(i).get申请单号());
                        }
                    }
                }
                if (list.size() > 0) {
                    StringUtil.removeDuplicate(list);
                    BaseScrollPickPresenter presenter = new BaseScrollPickPresenter(mActivity, list, "单号",tv_pop_order_num.getText().toString());
                    presenter.showDialog();
                    presenter.setOnDateClickListener(new BaseScrollPickPresenter.OnDateClickListener() {
                        @Override
                        public void setOnDate(String data) {
                            tv_pop_order_num.setText(data);
                        }
                    });
                } else {
                    ToastUtil.getInstance().showToast("没有合适的申请单号！");
                }
                break;
            case R.id.tv_pop_order_status:
                list2 = StringUtil.ArrToList(status);
                BaseScrollPickPresenter presenter = new BaseScrollPickPresenter(mActivity, list2, "状态",tv_pop_order_status.getText().toString());
                presenter.showDialog();
                presenter.setOnDateClickListener(new BaseScrollPickPresenter.OnDateClickListener() {
                    @Override
                    public void setOnDate(String data) {
                        tv_pop_order_status.setText(data);
                        if (data.equals("待处理")) {
                            addSure = " and 中标状态=" + 0;
                        } else if (data.equals("已中标")) {
                            addSure = " and  中标状态 >=" + 1;
                        } else if (data.equals("未中标")) {
                            addSure = " and  中标状态 >=" + -1;
                        }
                    }
                });
                break;
            case R.id.re_pop_date:
                BaseTimePickPresenter presenter1 = new BaseTimePickPresenter(mActivity,"search");
                presenter1.showDialog();
                presenter1.setOnDateClickListener(new BaseTimePickPresenter.OnDateClickListener() {
                    @Override
                    public void setOnDate(String startDate, String endDate) {
                        if (DateUtil.hourMinuteBetween(ymd, startDate, startDate, endDate)) {
                            tv_pop_start_date.setText(startDate);
                            tv_pop_end_date.setText(endDate);
                        } else {
                            ToastUtil.getInstance().showToast("开始时间不能大于结束时间");
                        }
                    }
                });
                break;
            case R.id.btn_pop_sure:
                if (!TextUtils.isEmpty(tv_pop_start_date.getText().toString()) && !TextUtils.isEmpty(tv_pop_end_date.getText().toString())) {
                    firstDay =sdf.format(DateUtil.setDate(tv_pop_start_date.getText().toString()));
                    lastDay = sdf.format(DateUtil.setDate(tv_pop_end_date.getText().toString()));
                }
                initData(tv_pop_order_num.getText().toString());
                window.dismiss();
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
        firstDay = DateUtil.getFirstDayOfMonth(sdf, oneDay);
        lastDay = DateUtil.getLastDayOfMonth(sdf, oneDay);
        LatteLogger.d("testOnDay", firstDay + "   " + lastDay + "    " + oneDay);

        initData(etSearchOrder.getText().toString());
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case QUOTED_UPDATE_DATA:
                initData(etSearchOrder.getText().toString());
                break;
        }
    }

}






