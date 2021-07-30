package com.shenzhen.honpe.honpe_sqe.app.e_package;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment;

/**
 * FileName: MeFragment
 * Author: asus
 * Date: 2021/7/22 9:00
 * Description:
 */
public class MeFragment extends BaseMainFragment {

    public static MeFragment newInstance(){
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

    }
}
