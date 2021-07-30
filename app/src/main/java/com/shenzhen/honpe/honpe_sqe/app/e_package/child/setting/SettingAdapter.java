package com.shenzhen.honpe.honpe_sqe.app.e_package.child.setting;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: SettingAdapter
 * Author: asus
 * Date: 2021/7/8 18:00
 * Description:
 */
public class SettingAdapter extends BaseMultiItemQuickAdapter<QuickMultipleEntity, BaseViewHolder> {

    public SettingAdapter(@Nullable List<QuickMultipleEntity> data) {
        super(data);
        addItemType(QuickMultipleEntity.CENTER_PERSON_MY_ORDER,R.layout.css_horizontal_icon_text);

    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, QuickMultipleEntity entity) {
        switch (entity.getItemType()) {
            case QuickMultipleEntity.CENTER_PERSON_MY_ORDER:
                holder.setText(R.id.tv_title, entity.getStrTitle());
                break;

        }
    }
}























