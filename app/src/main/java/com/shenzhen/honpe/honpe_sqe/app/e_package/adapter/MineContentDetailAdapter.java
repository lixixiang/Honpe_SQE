package com.shenzhen.honpe.honpe_sqe.app.e_package.adapter;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: MineContentDetailAdapter
 * Author: asus
 * Date: 2021/7/9 17:38
 * Description:
 */
public class MineContentDetailAdapter extends BaseQuickAdapter<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO, BaseViewHolder> {
    LinearLayout llNote;
    public MineContentDetailAdapter(@Nullable List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> data) {
        super(R.layout.item_child_node, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO bean) {
        holder.setText(R.id.item_material, bean.get物品名称());
        holder.setText(R.id.item_size, bean.get规格());
        holder.setText(R.id.item_texture, bean.get材质());
        holder.setText(R.id.item_unit, bean.get数量() + "");
        holder.setText(R.id.item_note, bean.get备注());
        llNote = holder.getView(R.id.ll_note);
    }
}
