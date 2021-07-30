package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.widget.VirtualKeyboardView;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FileName: BaseDetailAdapter
 * Author: asus
 * Date: 2021/3/24 20:48
 * Description:
 */
public class BaseDetailAdapter extends BaseQuickAdapter<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO, BaseViewHolder> implements TextWatcher, AdapterView.OnItemClickListener, View.OnClickListener {
    private RadioGroup rg;
    private RadioButton rb3, rb13;
    private TextView tvPrice, tvSure, textAmount;
    private String newRate;
    private List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> data;
    private MyDialog mPriceDialog;
    private int curPos;
    /***输入价格键值对***/
    private ArrayList<Map<String, String>> valueList;

    public BaseDetailAdapter(@Nullable List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> data) {
        super(R.layout.css_commodity_entity, data);
        this.data = data;
        addChildClickViewIds(R.id.tv_unit_price);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO entity) {
        holder.setText(R.id.tv_id, (holder.getAdapterPosition() + 1) + "");
        holder.setText(R.id.tv_part_num, entity.get物品名称());
        holder.setText(R.id.tv_size, entity.get规格());
        holder.setText(R.id.tv_texture, entity.get材质());
        holder.setText(R.id.tv_unit, entity.get数量() + "");
        holder.setText(R.id.tv_note, entity.get备注());
        tvPrice = holder.getView(R.id.tv_unit_price);
        if (entity.get单价() == 0) {
            tvPrice.setText("");
        } else {
            tvPrice.setText(StringUtil.formatDouble(entity.get单价()));
        }

        holder.setTextColor(R.id.tv_unit_price, getContext().getResources().getColor(R.color.colorPrimary));
        StringUtil.HintUtil(((TextView) holder.getView(R.id.tv_unit_price)), "输入含税金额",14);
        rg = holder.getView(R.id.rg_rate);
        rb3 = holder.getView(R.id.rb_3);
        rb13 = holder.getView(R.id.rb_13);
        rb3.setChecked(false);
        rb13.setChecked(false);
        if (entity.get税率() == 0.03) {
            rb3.setChecked(true);
        } else {
            rb3.setChecked(false);
        }
        if (entity.get税率() == 0.13) {
            rb13.setChecked(true);
        } else {
            rb13.setChecked(false);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_3:
                        newRate = rb3.getText().toString().substring(0, rb3.getText().toString().length() - 1);
                        entity.set税率(Double.parseDouble(newRate) / 100);
                        data.set(holder.getAdapterPosition(), entity);
                        UpDetailData(data);
                        break;
                    case R.id.rb_13:
                        newRate = rb13.getText().toString().substring(0, rb13.getText().toString().length() - 1);
                        entity.set税率(Double.parseDouble(newRate) / 100);
                        data.set(holder.getAdapterPosition(), entity);
                        UpDetailData(data);
                        break;
                }

            }
        });

        tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPriceDialog(holder);
                mPriceDialog.show();
            }
        });
    }

    private void ShowPriceDialog(BaseViewHolder holder) {
        curPos = holder.getAdapterPosition();
        mPriceDialog = new MyDialog(getContext(), R.style.GoodDialog);
        mPriceDialog.outDuration(200);
        mPriceDialog.inDuration(200);
        mPriceDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        View priceView = LayoutInflater.from(getContext()).inflate(R.layout.pop_enter_pass, null);
        mPriceDialog.setContentView(priceView);
        VirtualKeyboardView pwdView = mPriceDialog.findViewById(R.id.pwd_view);
        textAmount = mPriceDialog.findViewById(R.id.textAmount);
        StringUtil.HintUtil(textAmount, "按数字键输入物品含税价格",14);
        tvSure = mPriceDialog.findViewById(R.id.tv_sure);
        valueList = pwdView.getValueList();
        GridView gridView = pwdView.getGridView();
        textAmount.addTextChangedListener(this);
        gridView.setOnItemClickListener(this);
        tvSure.setOnClickListener(this);
        pwdView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPriceDialog.dismiss();
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            tvSure.setVisibility(View.VISIBLE);
        } else {
            tvSure.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                data.get(curPos).set单价(Double.parseDouble(textAmount.getText().toString()));
                UpDetailData(data);
                mPriceDialog.dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 11 && position != 9) {
            String amount = textAmount.getText().toString().trim();
            amount = amount + valueList.get(position).get("name");
            textAmount.setText(amount);
        } else {
            if (position == 9) {
                String amount = textAmount.getText().toString().trim();
                if (!amount.contains(".")) {
                    amount = amount + valueList.get(position).get("name");
                    textAmount.setText(amount);
                }
            }
            if (position == 11) {
                String amount = textAmount.getText().toString().trim();
                if (amount.length() > 0) {
                    amount = amount.substring(0, amount.length() - 1);
                    textAmount.setText(amount);
                }
            }
        }
    }

    /**
     * 更新数据
     ***/
    public void UpDetailData(List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> entities) {
        if (entities != null) {
            this.data = entities;
            Event<List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO>> event =
                    new Event<List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO>>(FinalClass.DATA, data);
            EventBusUtil.sendEvent(event);
        }
    }

    public List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> getUpDetailData() {
        return data;
    }
}
