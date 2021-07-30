package com.shenzhen.honpe.honpe_sqe.app.e_package.child.setting;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.DataClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.e_package.child.MeInfoFragment;
import com.shenzhen.honpe.honpe_sqe.app.e_package.downloadqr.DownloadQRCodeFragment;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginFragment;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.tipsDialog.TipsDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.BACK_DATA;

/**
 * FileName: SettingFragment
 * Author: asus
 * Date: 2021/7/8 17:56
 * Description:
 */
public class SettingFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.table_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.btn_sure)
    Button btnSure;

    SettingAdapter adapter;
    List<QuickMultipleEntity> list;


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.css_title_recyclerview;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        tvTitle.setText("设置");
        GridLayoutManager manager = new GridLayoutManager(mActivity, 1);
        recyclerView.setLayoutManager(manager);
        list = DataClass.getSettingList();
        adapter = new SettingAdapter(list);
        recyclerView.setAdapter(adapter);
        btnSure.setText("退出登录");
        btnSure.setEnabled(true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (position) {
                    case 0:
                        start(MeInfoFragment.newInstance());
                        break;
                    case 1:
                        start(DownloadQRCodeFragment.newInstance());
                        break;
                }
            }
        });
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        TipsDialog dialog = new TipsDialog(mActivity,"是否要退出登录?");
        dialog.show();
        dialog.setOnElseWork(new TipsDialog.elseWork() {
            @Override
            public void setOnClick() {
                startWithPop(LoginFragment.newInstance(SettingFragment.this.getTag()));
                DBUtils.remove(AppConfig.TABLE_NAME_LOGIN,AppConfig.APP_LOGIN);
                PersonEntity personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN,AppConfig.APP_LOGIN);
                LatteLogger.d("DBUtils", GsonBuildUtil.GsonBuilder(personEntity));
                Event<PersonEntity> event = new Event<PersonEntity>(BACK_DATA, personEntity);
                EventBusUtil.sendEvent(event);
            }
        });
    }
}






















