package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.contact_way;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;

import butterknife.BindView;

/**
 * FileName: ContactWayFragment
 * Author: asus
 * Date: 2021/3/28 12:17
 * Description:
 */
public class ContactWayFragment extends BaseBackFragment {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;

    public static ContactWayFragment newInstance(String title) {
        ContactWayFragment fragment = new ContactWayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_contactway;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        Bundle bundle = getArguments();
        tvTitle.setText("联系方式");
        tvDes.setText("公司名称：红品晶英科技（深圳）有限公司\n\n联系地址：深圳市宝安区福永街道凤凰第二工业区腾丰大道176号\n\n联系人：前台 (0755)3393138");
    }
}
