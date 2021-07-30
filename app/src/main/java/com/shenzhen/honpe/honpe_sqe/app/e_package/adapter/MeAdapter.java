package com.shenzhen.honpe.honpe_sqe.app.e_package.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.e_package.bean.IconStringEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * FileName: MeAdapter
 * Author: asus
 * Date: 2021/1/19 11:10
 * Description:
 */
public class MeAdapter extends BaseQuickAdapter<IconStringEntity, BaseViewHolder> {


    public MeAdapter(@Nullable List<IconStringEntity> data) {
        super(R.layout.item_icon_content,data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, IconStringEntity entity) {
        holder.setText(R.id.tv_content, entity.getContent());
        holder.setImageResource(R.id.iv, entity.getIcon());
    }
}
