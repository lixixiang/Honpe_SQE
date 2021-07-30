package com.shenzhen.honpe.honpe_sqe.app.a_package.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.widget.RxTextViewVerticalMore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: HomeAdapter
 * Author: asus
 * Date: 2021/1/25 13:35
 * Description: 首页多布局适配器
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<QuickMultipleEntity, BaseViewHolder> {

    public HomeAdapter(List<QuickMultipleEntity> data) {
        super(data);
        addItemType(QuickMultipleEntity.IMG_TEXT, R.layout.css_icon_text);
        addItemType(QuickMultipleEntity.BANNER_SORT, R.layout.css_server_center);
        addItemType(QuickMultipleEntity.NEWS_CONTENT, R.layout.css_news_about);
        addChildClickViewIds(R.id.ll_icon_text,R.id.ll_new);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, QuickMultipleEntity entity) {
        switch (holder.getItemViewType()) {
            case QuickMultipleEntity.IMG_TEXT:
                holder.setImageResource(R.id.iv_icon, entity.getIcon());
                holder.setText(R.id.tv_title, entity.getStrTitle());
                break;
            case QuickMultipleEntity.BANNER_SORT:
                break;
            case QuickMultipleEntity.NEWS_CONTENT:
                holder.setText(R.id.tv_news_title, entity.getNewsTitle());
                holder.setText(R.id.tv_news_date, entity.getNewsDate());
                Glide.with(getContext()).load(entity.getNewsIcon()).fitCenter().override(600,200).into(((ImageView) holder.getView(R.id.iv_news_icon)));
                break;
        }
    }

}










