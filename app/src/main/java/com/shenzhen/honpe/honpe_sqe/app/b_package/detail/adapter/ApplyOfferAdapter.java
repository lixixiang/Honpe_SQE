package com.shenzhen.honpe.honpe_sqe.app.b_package.detail.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.b_package.bean.OrderDetailBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: ApplyOfferAdapter
 * Author: asus
 * Date: 2021/1/22 15:37
 * Description: 订单详情列表
 */
public class ApplyOfferAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {

    boolean isOpen = false;

    public ApplyOfferAdapter(@Nullable List<OrderDetailBean> data) {
        super(R.layout.item_order_offer, data);
        addChildClickViewIds(R.id.btn_calculate_quotation,R.id.ll_table);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, OrderDetailBean bean) {
        holder.setText(R.id.tv_texture_unit, bean.getGoodsTexture() + " | " + bean.getGoodsNo() + " | " + bean.getGoodsUnit());
        holder.setText(R.id.tv_orderNo, bean.getGoodsName());
        holder.setText(R.id.tv_rate, "要求交期：" + bean.getGoodsDate());
        holder.setText(R.id.tv_size, "规格：" + bean.getGoodsSize());
        holder.setText(R.id.tv_post, bean.getGoodsPost());
        if (!"".equals(bean.getGoodsNote())) {
            holder.setGone(R.id.tv_note, false);
            holder.setText(R.id.tv_note, "备注：" + bean.getGoodsNote());
        } else {
            holder.setGone(R.id.tv_note, true);
        }
        holder.getView(R.id.btn_calculate_quotation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    holder.setGone(R.id.ll_table, true);
                } else {
                    holder.setGone(R.id.ll_table, false);
                }
                isOpen =!isOpen;
            }
        });
    }
}
