package com.shenzhen.honpe.honpe_sqe.app.c_package;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment;

/**
 * FileName: ChatFragment
 * Author: asus
 * Date: 2021/7/22 8:48
 * Description:聊天窗口
 */
public class BusinessFragment extends BaseMainFragment {

    public static BusinessFragment newInstance(){
        BusinessFragment fragment = new BusinessFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_bussiness;
    }

    @Override
    protected void initView() {

    }
}
