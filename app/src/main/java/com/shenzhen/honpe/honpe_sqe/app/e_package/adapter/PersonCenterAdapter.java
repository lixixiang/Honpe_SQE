package com.shenzhen.honpe.honpe_sqe.app.e_package.adapter;

import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: PersonCenterAdapter
 * Author: asus
 * Date: 2021/7/20 17:22
 * Description:
 */
public class PersonCenterAdapter extends BaseQuickAdapter<QuickMultipleEntity, BaseViewHolder> {

    RelativeLayout reBg;
    public PersonCenterAdapter(@Nullable List<QuickMultipleEntity> data) {
        super(R.layout.item_person_center, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, QuickMultipleEntity entity) {
        reBg = holder.getView(R.id.item_bg);
        holder.setImageResource(R.id.item_icon, entity.getIcon());
        holder.setText(R.id.item_title, entity.getTitle());

    }
}
























