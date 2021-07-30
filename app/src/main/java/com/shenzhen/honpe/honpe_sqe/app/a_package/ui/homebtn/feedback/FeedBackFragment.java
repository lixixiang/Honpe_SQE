package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * FileName: FeedBackFragment
 * Author: asus
 * Date: 2021/3/28 11:39
 * Description: 问题反馈
 */
public class FeedBackFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.et_progress)
    EditText etProgress;


    public static FeedBackFragment newInstance(String title) {
        FeedBackFragment fragment = new FeedBackFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_share_bottom;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        initToolbarNav(llBack);
        tvTitle.setText(bundle.getString("title"));
        tvEnd.setVisibility(View.VISIBLE);
        tvEnd.setText("提交反馈");
    }

    @OnClick(R.id.tv_end)
    public void onClick() {
        ToastUtil.getInstance().showToast("提交成功!");
        _mActivity.onBackPressed();
    }
}
