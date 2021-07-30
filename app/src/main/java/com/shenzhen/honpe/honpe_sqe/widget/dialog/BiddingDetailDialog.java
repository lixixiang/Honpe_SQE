package com.shenzhen.honpe.honpe_sqe.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.BiddingAdapter;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.bean.BiddingBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * FileName: BiddingDetailDialogg
 * Author: asus
 * Date: 2021/1/26 11:59
 * Description:
 */
public class BiddingDetailDialog extends Dialog {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    BiddingAdapter adapter;
    List<String> strList = new ArrayList<>();
    QuickMultipleEntity entity;
    private String[] titles ;

    public BiddingDetailDialog(@NonNull Context context,QuickMultipleEntity entity) {
        super(context,R.style.css_dialog_1);
        this.entity = entity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidding_dialog_layout);
        unbinder = ButterKnife.bind(this);
        titles = getContext().getResources().getStringArray(R.array.dinnering_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BiddingAdapter(getData());
        recyclerView.setAdapter(adapter);
    }

    private List<BiddingBean> getData() {
        strList.add("CNC");
        strList.add(entity.getDate());
        strList.add(entity.getOrder());
        strList.add(entity.getName());
        strList.add(entity.getMoney());
        if (entity.getStatus() == 0) {
            strList.add("中标");
        } else {
            strList.add("未中标");
        }
        strList.add(entity.getResult());

        List<BiddingBean> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            BiddingBean bean = new BiddingBean();
            bean.setTitle(titles[i]);
            bean.setContent(strList.get(i));
            list.add(bean);
        }
        return list;
    }
}





