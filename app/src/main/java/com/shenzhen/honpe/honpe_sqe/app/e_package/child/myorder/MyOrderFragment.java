package com.shenzhen.honpe.honpe_sqe.app.e_package.child.myorder;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.DataClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.basepageradapter.BaseFragmentPagerAdapter;
import com.shenzhen.honpe.honpe_sqe.app.e_package.child.myorder.detail.MyDetailFragment;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * FileName: MyOrderFragment
 * Author: asus
 * Date: 2021/7/8 14:31
 * Description:我的订单
 */
public class MyOrderFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    List<Fragment> mFragments = new ArrayList<>();
    List<String> strings = new ArrayList<>();
    private BaseFragmentPagerAdapter adapter;
    private int curPos;

    public static MyOrderFragment newInstance(String title) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        strings = DataClass.getAllOrder();
        tvTitle.setText("我的订单");

        for (int i = 0; i < strings.size(); i++) {
            mFragments.add(MyDetailFragment.newInstance());
        }

        adapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), mFragments, StringUtil.ArrToList(StringUtil.ListToArr(strings)));
        viewpager.setAdapter(adapter);
        tlTabs.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
}



























