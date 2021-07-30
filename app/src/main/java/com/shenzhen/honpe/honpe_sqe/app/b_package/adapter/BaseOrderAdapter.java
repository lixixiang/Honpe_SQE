package com.shenzhen.honpe.honpe_sqe.app.b_package.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.b_package.bean.OrderHomeBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: BaseOrderAdapter
 * Author: asus
 * Date: 2021/1/21 13:29
 * Description:R.layout.item_order
 */
public class BaseOrderAdapter extends BaseQuickAdapter<OrderHomeBean, BaseViewHolder> {

    public BaseOrderAdapter( @Nullable List<OrderHomeBean> data) {
        super(R.layout.item_order,  data);
        addChildClickViewIds(R.id.tv_enter_price);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, OrderHomeBean bean) {
        TextView tvLine = holder.getView(R.id.tv_enter_price);
        tvLine.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.setText(R.id.tv_orderNo, bean.getOrderNum());
        holder.setText(R.id.tv_applyDate, "发布日期："+bean.getApplyDate());
        holder.setText(R.id.tv_principal,"跟单负责人："+bean.getPrincipal());
        holder.setText(R.id.tv_classify,"采购分类："+bean.getClassify());
        if (bean.getStatus() == 1) {
            tvLine.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
            holder.setText(R.id.tv_enter_price, "已报价");
            holder.setTextColor(R.id.tv_enter_price, getContext().getResources().getColor(R.color.grey_home));
            holder.setBackgroundColor(R.id.ll_bg,getContext().getResources().getColor(R.color.grey_e));
        }else {
            tvLine.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.setText(R.id.tv_orderNo, bean.getOrderNum());
            holder.setBackgroundColor(R.id.ll_bg,getContext().getResources().getColor(R.color.white));
        }
    }
}
