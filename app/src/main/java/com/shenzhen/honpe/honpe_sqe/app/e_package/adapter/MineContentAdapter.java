package com.shenzhen.honpe.honpe_sqe.app.e_package.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: MineContentAdapter
 * Author: asus
 * Date: 2021/7/9 16:13
 * Description:
 */
public class MineContentAdapter extends BaseQuickAdapter<UnQuotedBean, BaseViewHolder> {

    RecyclerView orderRecyclerView;
    MineContentDetailAdapter mineContentDetailAdapter;
    ImageView ivStatus;
    public MineContentAdapter(@Nullable List<UnQuotedBean> data) {
        super(R.layout.item_center_content, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, UnQuotedBean bean) {
        if (!TextUtils.isEmpty(bean.get报价日期())) {
            String strMothDay = DateUtil.md.format(DateUtil.setDate(DateUtil.ymd, StringUtil.replaceT(bean.get报价日期())));
            holder.setText(R.id.tv_date, strMothDay);
        }
        holder.setGone(R.id.item_part_num, true);
        holder.setText(R.id.item_order_num, bean.get申请单号());
        holder.setText(R.id.item_type,bean.get加工类型());
        holder.setText(R.id.item_num, bean.get加工数量() + "");
        holder.setText(R.id.item_deliver, bean.get报价金额());
        holder.setGone(R.id.item_down, true);
        orderRecyclerView = holder.getView(R.id.order_recyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mineContentDetailAdapter = new MineContentDetailAdapter(bean.getOfferModel().get申请单明细报价记录());
        mineContentDetailAdapter.addHeaderView(getHeaderView());
        orderRecyclerView.setAdapter(mineContentDetailAdapter);
        ivStatus = holder.getView(R.id.iv_status);
        if (bean.get中标状态() == -1) {
            holder.setBackgroundResource(R.id.ll_bg,R.color.grey_e);
            holder.setBackgroundResource(R.id.ll_child_bg, R.color.grey_e);
            ivStatus.setImageResource(R.mipmap.ic_miss);
        } else {
            holder.setBackgroundResource(R.id.ll_bg,R.color.white);
            holder.setBackgroundResource(R.id.ll_child_bg, R.color.white);
            ivStatus.setImageResource(R.drawable.ic_feed_list_mail);
        }
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.recylcer_head_layout, orderRecyclerView, false);
        return view;
    }
}












































