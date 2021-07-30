package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.section.ItemNode;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.section.RootNode;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.adapter.BaseOrderAdapter;
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
import com.shenzhen.honpe.honpe_sqe.widget.VirtualKeyboardView;
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

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.All_ORDER_PRICE;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.EXPAND;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.QUOTED_UPDATE_DATA;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.SUCCESS_FAIL;

/**
 * FileName: AFragment
 * Author: asus
 * Date: 2021/1/28 9:34
 * Description:
 */
public class AFragment extends BaseBackFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.re_date_layout)
    RelativeLayout reDateLayout;
    @BindView(R.id.item_1)
    TextView item1;
    @BindView(R.id.item_2)
    TextView item2;
    @BindView(R.id.item_3)
    TextView item3;
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

    private SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy年MM月");
    QuickMultipleEntity entity;
    List<RootNode> rootNodes = new ArrayList<>();
    private View mPopupView;
    private PopupWindow mPopupWindow;
    private ArrayList<Map<String, String>> valueList;
    List<ItemNode> itemNodes = new ArrayList<>();
    public int oneDay = 0; //这个初始日子
    BaseOrderAdapter orderAdapter;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private List<UnQuotedBean> listdata;

    private String curMonth, strNetAPI, firstDay, lastDay, strWhere;
    double tax;

    public static AFragment newInstance(QuickMultipleEntity entity) {
        AFragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.a_fragment;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        entity = (QuickMultipleEntity) bundle.getSerializable("entity");
        tvTitle.setText(entity.getStrTitle());
        initToolbarNav(llBack);
        curMonth = sfMonth.format(new Date());
        tvMonth.setText(curMonth);
        tvNextMonth.setTextColor(getResources().getColor(R.color.grey_home));
        tvNextMonth.setClickable(false);
        tvTagNew.setVisibility(View.VISIBLE);
        initData();
    }

    private void initData() {
        firstDay = DateUtil.getFirstDayOfMonth(sdf);
        lastDay = DateUtil.getLastDayOfMonth(sdf);
        strNetAPI = "SearchQuotedList";
        strWhere = "AND A.发布时间>='" + firstDay + "' AND A.发布时间<='" + lastDay + "'";
        PersonEntity personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        if (personEntity != null) {
            EasyHttp.post(Constants.URL + strNetAPI)
                    .retryCount(0)
                    .params("strSID", personEntity.getValue().getSid())
                    .params("strCode", "")
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
                            LatteLogger.d("onSuccess", s);
                            try {
                                listdata = GsonBuildUtil.create().fromJson(s, new TypeToken<List<UnQuotedBean>>() {}.getType());

                                for (int i = 0; i < listdata.size(); i++) {
                                    UnQuotedBean bean = listdata.get(i);
                                    tax = 0;
                                    for (int j = 0; j < listdata.get(i).getOfferModel().get申请单明细报价记录().size(); j++) {
                                        tax = tax + (bean.getOfferModel().get申请单明细报价记录().get(j).get单价() * bean.getOfferModel().get申请单明细报价记录().get(j).get税率() * bean.getOfferModel().get申请单明细报价记录().get(j).get数量());
                                    }
                                    bean.set税额(tax);
                                }

                                LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
                                mRecyclerView.setHasFixedSize(true);
                                mRecyclerView.setLayoutManager(manager);
                                orderAdapter = new BaseOrderAdapter(listdata, tvTitle.getText().toString());
                                mRecyclerView.setAdapter(orderAdapter);
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }



    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case FinalClass.DELIVER_PRICE: //填写价格
                RootNode node = (RootNode) event.getData();
                showPopup(Gravity.BOTTOM, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                        R.style.Animation_Design_BottomSheetDialog, node, FinalClass.DELIVER_PRICE);
                break;
            case FinalClass.DELIVER_RATE:
                RootNode node2 = (RootNode) event.getData(); //填写税率
                showPopup(Gravity.BOTTOM, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                        R.style.Animation_Design_BottomSheetDialog, node2, FinalClass.DELIVER_RATE);
                break;
            case All_ORDER_PRICE:
                itemNodes = (List<ItemNode>) event.getData();
                break;
            case EXPAND:
                Bundle bundle = (Bundle) event.getData();
                int parentPos = bundle.getInt("parentPos");
                int position = bundle.getInt("position");
                boolean check = bundle.getBoolean("check");
                rootNodes.get(parentPos).getOrderNodes().get(position).setCheck(check);
                LatteLogger.d("orderAdapter", GsonBuildUtil.GsonBuilder(rootNodes));
                break;
            case SUCCESS_FAIL:
            case QUOTED_UPDATE_DATA:
                String success = (String) event.getData();
                if (success.contains("成功")) {
                    initData();
                }
                ToastUtil.getInstance().showToast(success);
                break;
        }
    }

    /**
     * 弹出popupWindow
     * Show popup.
     *
     * @param gravity        the gravity
     * @param width          the width
     * @param height         the height
     * @param animationStyle the animation style
     */
    private void showPopup(int gravity, int width, int height, int animationStyle, RootNode node, int index) {
        mPopupView = LayoutInflater.from(mActivity).inflate(R.layout.pop_enter_pass, null);
        mPopupWindow = new PopupWindow(mPopupView, width, height);
        //以下属性响应空白处消失和实体按键返回消失popup
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //沉浸式模式下，以下两个属性并不起作用
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //重点，此方法可以让布局延伸到状态栏和导航栏
        mPopupWindow.setClippingEnabled(false);
        //设置动画
        mPopupWindow.setAnimationStyle(animationStyle);
        //弹出
        mPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), gravity, 0, 0);
        //弹出后背景alpha值
        backgroundAlpha(0.5f);
        //消失后恢复背景alpha值
        mPopupWindow.setOnDismissListener(() -> backgroundAlpha(1f));
        //适配弹出popup后布局被状态栏和导航栏遮挡问题
        updatePopupView(node, index);
    }

    /**
     * 调整popupWindow里view的Margins值来适配布局被导航栏遮挡问题，因为要适配横竖屏切换，所以代码有点多
     * Update popup view.
     */
    private void updatePopupView(RootNode node, int index) {
        int navigationBarHeight = ImmersionBar.getNavigationBarHeight(this);
        int navigationBarWidth = ImmersionBar.getNavigationBarWidth(this);
        if (mPopupView != null) {
            ImmersionBar.setTitleBar(this, mPopupView.findViewById(R.id.toolbar));
            View rlContent = mPopupView.findViewById(R.id.rlContent);
            VirtualKeyboardView pwdView = mPopupView.findViewById(R.id.pwd_view);
            TextView textAmount = mPopupView.findViewById(R.id.textAmount);
            TextView tvSure = mPopupView.findViewById(R.id.tv_sure);
            GridView gridView = pwdView.getGridView();
            valueList = pwdView.getValueList();
            textAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) {
                        tvSure.setVisibility(View.GONE);
                    } else {
                        tvSure.setVisibility(View.VISIBLE);
                    }
                }
            });

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position < 11 && position != 9) {    //点击0~9按钮
                        String amount = textAmount.getText().toString().trim();
                        amount = amount + valueList.get(position).get("name");
                        textAmount.setText(amount);
                    } else {
                        if (position == 9) {      //点击退格键
                            String amount = textAmount.getText().toString().trim();
                            if (!amount.contains(".")) {
                                amount = amount + valueList.get(position).get("name");
                                textAmount.setText(amount);
                            }
                        }
                        if (position == 11) {      //点击退格键
                            String amount = textAmount.getText().toString().trim();
                            if (amount.length() > 0) {
                                amount = amount.substring(0, amount.length() - 1);
                                textAmount.setText(amount);
                            }
                        }
                    }
                }
            });

            pwdView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });

            mPopupView.post(() -> {
                boolean isPortrait;
                boolean isLandscapeLeft;
                int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
                if (rotation == Surface.ROTATION_90) {
                    isPortrait = false;
                    isLandscapeLeft = true;
                } else if (rotation == Surface.ROTATION_270) {
                    isPortrait = false;
                    isLandscapeLeft = false;
                } else {
                    isPortrait = true;
                    isLandscapeLeft = false;
                }
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rlContent.getLayoutParams();

                if (isPortrait) {
                    layoutParams.setMargins(0, 0, 0, navigationBarHeight);
                } else {
                    if (isLandscapeLeft) {
                        layoutParams.setMargins(0, 0, navigationBarWidth, 0);
                    } else {
                        if (OSUtils.isEMUI3_x()) {
                            layoutParams.setMargins(0, 0, navigationBarWidth, 0);
                        } else {
                            layoutParams.setMargins(navigationBarWidth, 0, 0, 0);
                        }
                    }
                }
                rlContent.setLayoutParams(layoutParams);
            });
        }
    }

    /**
     * 设置弹出popup，背景alpha值
     *
     * @param bgAlpha 0f - 1f
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(lp);
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
    }
}


