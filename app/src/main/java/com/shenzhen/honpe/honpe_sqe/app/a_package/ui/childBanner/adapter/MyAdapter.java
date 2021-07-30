package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.childBanner.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.DataBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: MyAdapter
 * Author: asus
 * Date: 2021/2/3 10:58
 * Description:
 */
public class MyAdapter extends BaseQuickAdapter<DataBean.ChildDataBean, BaseViewHolder> {

    public MyAdapter(@Nullable List<DataBean.ChildDataBean> data) {
        super(R.layout.css_text, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, DataBean.ChildDataBean childDataBean) {
        holder.setText(R.id.tv, childDataBean.getContent());
    }
}
