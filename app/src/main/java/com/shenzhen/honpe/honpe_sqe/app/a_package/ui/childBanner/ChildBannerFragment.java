package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.childBanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.DataClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.childBanner.adapter.ChildBannerAdapter;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;

import butterknife.BindView;

/**
 * FileName: ChildBannerFragment
 * Author: asus
 * Date: 2021/2/2 15:06
 * Description:
 */
public class ChildBannerFragment extends BaseBackFragment {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    QuickMultipleEntity entity;
    ChildBannerAdapter adapter;


    public static ChildBannerFragment newInstance(QuickMultipleEntity entity) {
        ChildBannerFragment fragment = new ChildBannerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_banner_view;
    }

    @Override
    protected void initView() {
        adapter = new ChildBannerAdapter(DataClass.getDetailData());

        Bundle bundle = getArguments();
        entity = (QuickMultipleEntity) bundle.getSerializable("entity");

        initToolbarNav(llBack);
        tvTitle.setText(entity.getNewsTitle());
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter.addHeaderView(getHeadView());
        recyclerView.setAdapter(adapter);

    }

    private View getHeadView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.css_icon_head_name_tips, recyclerView, false);
        TextView tvTips = view.findViewById(R.id.tv_tips);
        tvTips.setText("欢迎来到"+entity.getNewsTitle()+"，很高兴为您服务。");
        return view;
    }
}








