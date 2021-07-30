package com.shenzhen.honpe.honpe_sqe.app.login_with_register.adapter;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.SupplyBean;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CHECK_EVENT;

/**
 * FileName: SupplyClassificationAdapter
 * Author: asus
 * Date: 2021/3/10 15:49
 * Description:
 */
public class SupplyClassificationAdapter extends BaseQuickAdapter<SupplyBean, BaseViewHolder> {

    CheckBox checkBox;
    List<SupplyBean> multiCheck;
    private Map<Integer, Boolean> map = new HashMap<>();

    public SupplyClassificationAdapter(@Nullable List<SupplyBean> data) {
        super(R.layout.item_check_box, data);
        this.multiCheck = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, SupplyBean bean) {
        checkBox = holder.getView(R.id.item_checkbox);
        checkBox.setText(bean.get分类名称());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    map.put(holder.getAdapterPosition(), true);
                    bean.setCheck(map.get(holder.getAdapterPosition()));
                    multiCheck.set(holder.getAdapterPosition(), bean);
                } else {
                    map.remove(holder.getAdapterPosition());
                    bean.setCheck(false);
                    multiCheck.set(holder.getAdapterPosition(), bean);
                }
                Event<List<SupplyBean>> event = new Event<List<SupplyBean>>(CHECK_EVENT, multiCheck);
                EventBusUtil.sendEvent(event);
            }
        });
        if (map != null && map.containsKey(holder.getAdapterPosition())) {
            checkBox.setChecked(true);
        } else if (bean.isCheck()) {
            checkBox.setChecked(bean.isCheck());
        } else {
            checkBox.setChecked(false);
        }
    }
}









