package com.shenzhen.honpe.honpe_sqe.app.e_package.child.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.e_package.bean.MeInfoBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: MeInfoAdapter
 * Author: asus
 * Date: 2021/1/19 14:56
 * Description:
 */
public class MeInfoAdapter extends BaseQuickAdapter<MeInfoBean, BaseViewHolder> {
    TextView tvDes;
    List<MeInfoBean> data;
    MeInfoBean entity;

    public MeInfoAdapter(@Nullable List<MeInfoBean> data) {
        super(R.layout.item_me_info, data);
        this.data = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, MeInfoBean entity) {
        this.entity = entity;
//        holder.setText(R.id.tv_content, entity.getContent());
//        tvDes = holder.getView(R.id.tv_des);
//        switch (holder.getAdapterPosition() - 1) {
//            case 0:
//                tvDes.setText(entity.getValue().getName());
//                break;
//            case 1:
//                tvDes.setText(entity.getValue().getEmail());
//                break;
//            case 2:
//                tvDes.setText(entity.getValue().getPhone());
//                break;
//            case 3:
//                tvDes.setText(entity.getValue().getAddress());
//                break;
//            case 4:
//                tvDes.setText(entity.getValue().getCompany());
//                break;
//        }
    }

    public void UpdateData( List<MeInfoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

}










