package com.shenzhen.honpe.honpe_sqe.app.d_package;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment;

/**
 * FileName: ChatFragment
 * Author: asus
 * Date: 2021/7/22 8:48
 * Description:聊天窗口
 */
public class MessageFragment extends BaseMainFragment {

    public static MessageFragment newInstance(){
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView() {

    }
}
