package com.shenzhen.honpe.honpe_sqe.app.e_package.child.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.e_package.bean.MeInfoBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: UserInfoAdapter
 * Author: asus
 * Date: 2021/7/19 17:50
 * Description:
 */
public class UserInfoAdapter extends BaseQuickAdapter<MeInfoBean, BaseViewHolder> {
    private RecyclerView recyclerView;
    private UserInfoContentAdapter adapter;

    public UserInfoAdapter(@Nullable List<MeInfoBean> data) {
        super(R.layout.item_user_info_head, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, MeInfoBean bean) {
        holder.setText(R.id.tv_tips, bean.getTitle());
        recyclerView = holder.getView(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UserInfoContentAdapter(holder.getAdapterPosition(),bean.getContents());
        recyclerView.setAdapter(adapter);

    }
}
