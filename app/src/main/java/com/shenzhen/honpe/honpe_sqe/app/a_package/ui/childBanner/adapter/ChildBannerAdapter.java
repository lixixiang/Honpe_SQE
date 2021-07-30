package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.childBanner.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.DataBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: ChildBannerAdapter
 * Author: asus
 * Date: 2021/2/3 10:09
 * Description:
 */
public class ChildBannerAdapter extends BaseQuickAdapter<DataBean, BaseViewHolder> {


    public ChildBannerAdapter(@Nullable List<DataBean> data) {
        super(R.layout.child_banner, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, DataBean dataBean) {
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        holder.setText(R.id.tv_news_title, dataBean.title);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter(dataBean.getDatabeans());
        recyclerView.setAdapter(adapter);
    }
}


















