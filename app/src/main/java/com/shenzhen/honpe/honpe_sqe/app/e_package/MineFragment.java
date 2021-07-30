package com.shenzhen.honpe.honpe_sqe.app.e_package;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.GridSpanSizeLookup;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.shenzhen.honpe.honpe_sqe.MainFragment;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.api.DataClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.e_package.adapter.MineAdapter;
import com.shenzhen.honpe.honpe_sqe.app.e_package.adapter.MineContentAdapter;
import com.shenzhen.honpe.honpe_sqe.app.e_package.adapter.PersonCenterAdapter;
import com.shenzhen.honpe.honpe_sqe.app.e_package.child.mob.MobIconFragment;
import com.shenzhen.honpe.honpe_sqe.app.e_package.child.myorder.MyOrderFragment;
import com.shenzhen.honpe.honpe_sqe.app.e_package.child.setting.SettingFragment;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginFragment;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginProgress.InputUserInfoFragment;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.BACK_DATA;

/**
 * FileName: MineFragment
 * Author: asus
 * Date: 2021/7/7 17:26
 * Description:我的界面
 */
public class MineFragment extends BaseMainFragment {
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.ll_is_login)
    LinearLayout llIsLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.ll_login_with_register)
    LinearLayout llLoginWithRegister;
    @BindView(R.id.re_un_login)
    LinearLayout reUnLogin;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.toolbar_avatar)
    CircleImageView toolbarAvatar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.buttonBarLayout)
    RelativeLayout buttonBarLayout;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;

    private int mOffset = 0;
    private int mScrollY = 0;
    private MineAdapter adapter;
    private PersonEntity personEntity;
    private String firstDay, lastDay, addSure, strWhere, strCode;
    private ArrayList<UnQuotedBean> listdata;
    Map<String, ArrayList<UnQuotedBean>> maps;
    private MineContentAdapter mineContentAdapter;
    private PersonCenterAdapter personCenterAdapter;

    Bundle bundle = new Bundle();

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_me;
    }

    @Override
    protected void initView() {
        //状态栏透明和间距处理
//        StatusBarUtil.immersive(mActivity, R.color.black, 1);
//        StatusBarUtil.setPaddingSmart(mActivity, toolbar);
//        StatusBarUtil.setPaddingSmart(mActivity, recyclerView);
        initRecyclerView();
//        refreshLayout.setOnMultiListener(new SimpleMultiListener() {
//            @Override
//            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//                mOffset = offset / 2;
//                reUnLogin.setTranslationY(mOffset - mScrollY);
//                toolbar.setAlpha(1 - Math.min(percent, 1));
//            }
//        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            private int lastScrollY = 0;
//            private int h = SmartUtil.dp2px(170);
//            private int color = ContextCompat.getColor(mActivity, R.color.colorPrimary) & 0x00ffffff;
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if (lastScrollY < dy) {
//                    dy = Math.min(h, dy);
//                    mScrollY = dy > h ? h : dy;
//                    reUnLogin.setAlpha(1f * mScrollY / h);
//                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
//                    toolbar.setVisibility(View.VISIBLE);
//                } else {
//                    toolbar.setVisibility(View.GONE);
//                }
//                lastScrollY = dy;
//                LatteLogger.d("testScrollY", lastScrollY + "   " + dy);
//            }
//        });
    }

    List<QuickMultipleEntity> list;

    private void initRecyclerView() {
        personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        Event<PersonEntity> event = new Event<PersonEntity>(BACK_DATA, personEntity);
        EventBusUtil.sendEvent(event);
        list = DataClass.getPersonCenter();
        personCenterAdapter = new PersonCenterAdapter(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(personCenterAdapter);


     //   initData();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(mActivity).fitsSystemWindows(true).statusBarColor(R.color.colorPrimary).init();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case BACK_DATA:
                personEntity = (PersonEntity) event.getData();
                if (personEntity.getKey().contains("成功")) {
                    llIsLogin.setVisibility(View.VISIBLE);
                    llLoginWithRegister.setVisibility(View.GONE);
                    ivSetting.setVisibility(View.VISIBLE);
                    ivSetting.setColorFilter(getResources().getColor(R.color.white));
                    if (personEntity.getValue().getName().equals(personEntity.getValue().getUsername())) {
                        tvUserName.setText("点击设置昵称");
                    } else {
                        tvUserName.setText(personEntity.getValue().getName());
                    }
                    tvEnd.setVisibility(View.VISIBLE);
                    tvEnd.setText("用户id：" + personEntity.getValue().getUserid());

                    Glide.with(getContext()).load(personEntity.getValue().getIcons()).placeholder(R.drawable.selector_men).into(imageHead);
                    DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN, personEntity);
                } else {
                    imageHead.setImageResource(R.drawable.selector_men);
                    llLoginWithRegister.setVisibility(View.VISIBLE);
                    llIsLogin.setVisibility(View.GONE);
                    ivSetting.setVisibility(View.GONE);
                    tvEnd.setVisibility(View.GONE);
                }
                break;

        }
    }

    @OnClick({R.id.image_head, R.id.tv_login_register, R.id.iv_setting, R.id.tv_userName})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_userName:
                ((MainFragment) getParentFragment()).startBrotherFragment(InputUserInfoFragment.newInstance(personEntity));
                break;
            case R.id.iv_setting:
                ((MainFragment) getParentFragment()).startBrotherFragment(SettingFragment.newInstance());
                break;
            case R.id.image_head:
                bundle.putString("title", "头像");
                bundle.putSerializable("person", personEntity);
                ((MainFragment) getParentFragment()).startBrotherFragment(MobIconFragment.newInstance(bundle));
                break;
            case R.id.tv_login_register:
                ((MainFragment) getParentFragment()).startBrotherFragment(LoginFragment.newInstance(MineFragment.this.getTag()));
                break;
        }
    }
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private void initData() {
        firstDay = DateUtil.getFirstDayOfMonth(sdf, -3, 1);
        lastDay = DateUtil.getLastDayOfMonth(sdf);
        if (!TextUtils.isEmpty(addSure)) {
            strWhere = "AND T.报价日期>='" + firstDay + "' AND T.报价日期<='" + lastDay + "'" + addSure;
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
                            List<String> titles = Arrays.asList(mActivity.getResources().getStringArray(R.array.tab_title));

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

//                            listdata = maps.get("全部");
//                            LatteLogger.d("testTime", GsonBuildUtil.GsonBuilder(listdata));
//                            recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
//                            list = DataClass.getCenterPerson();
//                            adapter = new MineAdapter(list);
//                            recyclerView.setNestedScrollingEnabled(false);
//                            final GridLayoutManager manager = new GridLayoutManager(mActivity, 5);
//                            recyclerView.setLayoutManager(manager);
//
//                            recyclerView.setAdapter(adapter);
//
//                            Utils.reverse(listdata);
//                            recyclerViewContent.setLayoutManager(new LinearLayoutManager(mActivity));
//                            mineContentAdapter = new MineContentAdapter(listdata);
//                            recyclerViewContent.setAdapter(mineContentAdapter);

                            adapter.setGridSpanSizeLookup(new GridSpanSizeLookup() {
                                @Override
                                public int getSpanSize(@NonNull GridLayoutManager gridLayoutManager, int viewType, int position) {
                                    return list.get(position).getSpanSize();
                                }
                            });

                            adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                                    switch (position) {
                                        case 0:
                                            ((MainFragment) getParentFragment()).startBrotherFragment(MyOrderFragment.newInstance(list.get(position).getStrTitle()));
                                            break;
                                    }
                                }
                            });
                        }
                    });
        }
    }
}



























