package com.shenzhen.honpe.honpe_sqe.app.e_package.child;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.app.e_package.bean.MeInfoBean;
import com.shenzhen.honpe.honpe_sqe.app.e_package.child.adapter.UserInfoAdapter;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.tipsDialog.SelectorTypeDialog;

import java.util.List;

import butterknife.BindView;

import static com.shenzhen.honpe.honpe_sqe.api.DataClass.MeInfoData;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.BACK_DATA;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.BACK_HEAD_ICON;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.MOB_PASS;

/**
 * FileName: MeInfo
 * Author: asus
 * Date: 2021/1/19 13:51
 * Description:个人信息页面
 */
public class MeInfoFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recycler;

    UserInfoAdapter adapter;
    List<MeInfoBean> lists;
    ImageView ivHead;
    PersonEntity personEntity;
    TextView tvFinish;
    SelectorTypeDialog dialog;
    int res=R.layout.fragment_me_info;

    public static MeInfoFragment newInstance() {
        MeInfoFragment fragment = new MeInfoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        dialog = new SelectorTypeDialog(mActivity);
        dialog.show();
        dialog.setOnSelectorPerson(new SelectorTypeDialog.personInfo() {
            @Override
            public void strType(String type) {
                if (type.contains("个人")) {
                    res = R.layout.fragment_me_info;
                } else {
                    res = R.layout.fragment_company_info;
                }
            }
        });

        return res;
    }

    @Override
    protected void initView() {
        personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        initToolbarNav(llBack);
        tvTitle.setText("编辑资料");
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(manager);
        if (lists != null) {
            lists.clear();
        }


        lists = MeInfoData();
        adapter = new UserInfoAdapter(lists);

//        View headView = LayoutInflater.from(_mActivity).inflate(R.layout.head_view_info, recycler, false);
//        View footView = LayoutInflater.from(_mActivity).inflate(R.layout.foot_view_info, recycler, false);
//        adapter.addHeaderView(headView);
//        adapter.addFooterView(footView);
        recycler.setAdapter(adapter);
//        ivHead = headView.findViewById(R.id.iv_head);
//        if (personEntity.getValue().getIcons() == null) {
//            ivHead.setImageResource(R.drawable.selector_men);
//        } else {
//            Glide.with(getContext()).load(personEntity.getValue().getIcons()).into(ivHead);
//        }
//        if (personEntity.getValue().getIcons() == null) {
//            ivHead.setImageResource(R.drawable.selector_men);
//        } else {
//            Glide.with(getContext()).load(personEntity.getValue().getIcons()).into(ivHead);
//        }
//
//        tvFinish = footView.findViewById(R.id.tv_pass_finish);
//        headView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("title", "头像");
//                start(MobIconFragment.newInstance(bundle));
//            }
//        });
//
//        footView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                start(MobPassFragment.newInstance());
//            }
//        });

//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("title", lists.get(position).getContent());
//                bundle.putString("content", lists.get(position).getDescription());
//                bundle.putInt("position", position);
//                start(MeInfoDetailFragment.newInstance(bundle));
//            }
//        });
    }

    private void person() {

    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case BACK_DATA:
                _mActivity.onBackPressed();
                break;
            case BACK_HEAD_ICON:
                personEntity = (PersonEntity) event.getData();
                Glide.with(getContext()).load(personEntity.getValue().getIcons()).into(ivHead);
                break;
            case MOB_PASS:
                personEntity = (PersonEntity) event.getData();
                tvFinish.setText("已修改");
                break;
        }
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.toolbar).keyboardEnable(true).init();
    }
}










