package com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.bean.BiddingBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: BiddingAdapter
 * Author: asus
 * Date: 2021/1/26 12:14
 * Description:
 */
public class BiddingAdapter extends BaseQuickAdapter<BiddingBean, BaseViewHolder> {

    public BiddingAdapter( @Nullable List<BiddingBean> data) {
        super(R.layout.item_bidding_layout, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, BiddingBean bean) {
        holder.setText(R.id.tv_title, bean.getTitle());
        holder.setText(R.id.tv_content, bean.getContent());
        if (bean.getTitle().equals("驳回原因") && "".equals(bean.getContent()) && holder.getLayoutPosition() == 6) {
            holder.setTextColor(R.id.tv_title, getContext().getResources().getColor(R.color.grey_home));
        } else {
            holder.setTextColor(R.id.tv_title, getContext().getResources().getColor(R.color.grey));
        }
    }
}


























