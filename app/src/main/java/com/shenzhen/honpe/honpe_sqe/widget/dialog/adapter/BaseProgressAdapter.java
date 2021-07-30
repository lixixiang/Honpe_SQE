package com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.base.BaseProgressDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * FileName: BaseProgressAdapter
 * Author: asus
 * Date: 2021/3/22 20:50
 * Description:
 */
public class BaseProgressAdapter extends BaseQuickAdapter<BaseProgressDialog.ProgressTime, BaseViewHolder> {

    List<BaseProgressDialog.ProgressTime> data;
    public BaseProgressAdapter(@Nullable List<BaseProgressDialog.ProgressTime> data) {
        super(R.layout.item_progress, data);
        this.data = data;
        addChildClickViewIds(R.id.tv_mob);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, BaseProgressDialog.ProgressTime bean) {
        holder.setText(R.id.iv_status, (data.size()- holder.getAdapterPosition()) + "");
        holder.setText(R.id.tv_status, bean.getStrProgress());
        holder.setText(R.id.tv_time, bean.getStrDate());

        if (holder.getAdapterPosition() == 0) {
            holder.setBackgroundResource(R.id.iv_status, R.drawable.shape_circle_green);
            holder.setTextColor(R.id.tv_status, getContext().getResources().getColor(R.color.green));
            holder.setTextColor(R.id.tv_time, getContext().getResources().getColor(R.color.green));
            holder.setBackgroundResource(R.id.tv_line, R.color.green);
            holder.setBackgroundResource(R.id.view_line, R.color.green);
        }else {
            holder.setBackgroundResource(R.id.iv_status, R.drawable.shape_circle_grey);
            holder.setTextColor(R.id.tv_status, getContext().getResources().getColor(R.color.grey));
            holder.setTextColor(R.id.tv_time, getContext().getResources().getColor(R.color.grey));
            holder.setBackgroundResource(R.id.tv_line, R.color.grey);
            holder.setBackgroundResource(R.id.view_line, R.color.grey);
        }
    }

    public void upData(List<BaseProgressDialog.ProgressTime> data){
        this.data =data;
        notifyDataSetChanged();
    }
}
