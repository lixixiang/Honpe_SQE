package com.shenzhen.honpe.honpe_sqe.app.e_package.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: MineAdapter
 * Author: asus
 * Date: 2021/7/8 10:16
 * Description:
 */
public class MineAdapter extends BaseMultiItemQuickAdapter<QuickMultipleEntity, BaseViewHolder> {


    public MineAdapter(@Nullable List<QuickMultipleEntity> data) {
        super(data);
        addItemType(QuickMultipleEntity.CENTER_PERSON_MY_ORDER, R.layout.item_mine_top);
        addItemType(QuickMultipleEntity.IMG_TEXT, R.layout.css_icon_text);
        addItemType(QuickMultipleEntity.NEWS_CONTENT, R.layout.item_top_order);
        addChildClickViewIds(R.id.tv_right);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, QuickMultipleEntity bean) {
        switch (holder.getItemViewType()) {
            case QuickMultipleEntity.CENTER_PERSON_MY_ORDER:
                holder.setText(R.id.tv_title, bean.getTipsLeftText());
                holder.setText(R.id.tv_right, bean.getTipsRightText());
                break;
            case QuickMultipleEntity.IMG_TEXT:
                holder.setImageResource(R.id.iv_icon, bean.getIcon());
                holder.setText(R.id.tv_title, bean.getStrTitle());
                break;
            case QuickMultipleEntity.NEWS_CONTENT:
                holder.setGone(R.id.iv_right_directory, true);
                holder.setText(R.id.tv_title, bean.getTipsLeftText());
                break;
        }
    }
}




















