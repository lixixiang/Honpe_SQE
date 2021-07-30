package com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: CustDateAdapter
 * Author: asus
 * Date: 2021/2/26 15:10
 * Description:
 */
public class CusDateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CusDateAdapter(@Nullable List<String> data) {
        super(R.layout.item_data_popwindow, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, String str) {
        holder.setText(R.id.tv_date, str);
    }
}
