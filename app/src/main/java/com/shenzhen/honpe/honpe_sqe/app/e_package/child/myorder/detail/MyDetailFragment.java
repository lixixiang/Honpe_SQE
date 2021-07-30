package com.shenzhen.honpe.honpe_sqe.app.e_package.child.myorder.detail;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;

import butterknife.BindView;

/**
 * FileName: MyDetailFragment
 * Author: asus
 * Date: 2021/7/8 14:57
 * Description:
 */
public class MyDetailFragment extends BaseBackFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;



    public static MyDetailFragment newInstance() {
        MyDetailFragment fragment = new MyDetailFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.css_smart_recyclerview;
    }

    @Override
    protected void initView() {
        GridLayoutManager manager = new GridLayoutManager(mActivity, 5);
        recyclerView.setLayoutManager(manager);
    }
}






















