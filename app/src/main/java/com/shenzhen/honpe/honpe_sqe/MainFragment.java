package com.shenzhen.honpe.honpe_sqe;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.shenzhen.honpe.honpe_sqe.app.a_package.HomeFragment;
import com.shenzhen.honpe.honpe_sqe.app.b_package.SupplierFragment;
import com.shenzhen.honpe.honpe_sqe.app.c_package.BusinessFragment;
import com.shenzhen.honpe.honpe_sqe.app.e_package.MeFragment;
import com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.widget.BottomBar;
import com.shenzhen.honpe.honpe_sqe.widget.BottomBarTab;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.FIRST;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.FOURTH;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.SECOND;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.THIRD;

/**
 * FileName: MainFragment
 * Author: asus
 * Date: 2021/1/19 9:14
 * Description:
 */
public class MainFragment extends BaseMainFragment {
    private static final int REQ_MSG = 10;
    @BindView(R.id.fl_tab_container)
    FrameLayout flTabContainer;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private SupportFragment[] mFragments = new SupportFragment[4];

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportFragment firstFragment = findChildFragment(HomeFragment.class);

        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = SupplierFragment.newInstance();
            mFragments[THIRD] = BusinessFragment.newInstance();
            mFragments[FOURTH] = MeFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
            mFragments[FOURTH]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(SupportFragment.class);
            mFragments[THIRD] = findFragment(BusinessFragment.class);
            mFragments[FOURTH] = findFragment(MeFragment.class);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_fragment;
    }

    public void initView() {
        bottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.bottom1, getString(R.string.text_home)))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.bottom2, "供应商"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.bottom3, "聊天"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.bottom4, getString(R.string.text_me)));
        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                BottomBarTab tab = bottomBar.getItem(FIRST);
//                if (position == FIRST) {
//                    tab.setUnreadCount(0);
//                } else {
//                    tab.setUnreadCount(tab.getUnreadCount() + 1);
//                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {

        }
    }
}

















