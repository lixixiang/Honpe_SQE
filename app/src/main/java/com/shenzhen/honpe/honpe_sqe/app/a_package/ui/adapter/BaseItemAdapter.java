package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.section.ItemNode;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: BaseChildAdapter
 * Author: asus
 * Date: 2021/2/1 15:14
 * Description:
 */
public class BaseItemAdapter extends BaseQuickAdapter<ItemNode, BaseViewHolder> {

    private int pos;
    private String curScrollTitle;

    public BaseItemAdapter(@Nullable List<ItemNode> data, int pos,String curScrollTitle) {
        super(R.layout.item_child_node, data);
        this.pos = pos;
        this.curScrollTitle = curScrollTitle;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ItemNode bean) {
        LatteLogger.d("holderView",curScrollTitle);
        holder.setText(R.id.tv_part_num, bean.getPartNum());
        holder.setText(R.id.tv_size, bean.getSize());
        holder.setText(R.id.tv_texture, bean.getTexture());
        holder.setText(R.id.tv_unit, bean.getNumPrice());
    }
}



























