package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.adapter;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.FlowTagLayout;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.CommodityEntity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.TagInfo;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.OnTagSelectListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CLEAR;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.DELIVERY;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.TOTAL_MONEY;

/**
 * FileName: CommodityAdapter
 * Author: asus
 * Date: 2021/2/21 9:58
 * Description:
 */
public class CommodityAdapter extends BaseMultiItemQuickAdapter<CommodityEntity, BaseViewHolder> {
    private ProperyTagAdapter rateAdapter, moneyAdapter, goodsAdapter;
    private int ratePosition = 0, moneyPosition = 0, goodsPosition = 0;
    List<CommodityEntity> data;

    private TextView tvUnitPrice, tvAmount, tvPriceRate, tvActualAmount;
    private RadioGroup rg;
    private RadioButton rb3, rb13;

    public CommodityAdapter(@Nullable List<CommodityEntity> data) {
        super(data);
        addItemType(CommodityEntity.LIST_PARAM, R.layout.css_commodity_entity);
        addItemType(CommodityEntity.LIST_PARAM2, R.layout.css_commodity_entity2);
        addItemType(CommodityEntity.LIST_PARAM3, R.layout.css_commodity_entity_3);
        addChildClickViewIds(R.id.tv_unit_price, R.id.tv_week_date, R.id.tv_start_date, R.id.tv_end_date);
        this.data = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, CommodityEntity entity) {
        switch (holder.getItemViewType()) {
            case CommodityEntity.LIST_PARAM:
                holder.setText(R.id.tv_id, holder.getAdapterPosition() + "");
                holder.setText(R.id.tv_part_num, entity.getPartNum());
                holder.setText(R.id.tv_size, entity.getSize());
                holder.setText(R.id.tv_texture, entity.getTexture());
                holder.setText(R.id.tv_unit, entity.getNum());
                holder.setText(R.id.tv_note, entity.getNote());
                tvUnitPrice = holder.getView(R.id.tv_unit_price);
                tvUnitPrice.setText(entity.getInputPrice());
                tvUnitPrice.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                StringUtil.HintUtil(tvUnitPrice, "输入含税金额",14);
                rg = holder.getView(R.id.rg_rate);
                rb3 = holder.getView(R.id.rb_3);
                rb13 = holder.getView(R.id.rb_13);
                if (entity.getInputTaxRate().equals("0.03") || entity.getInputTaxRate().equals("3%")) {
                    entity.setRate3(true);
                    entity.setInputTaxRate("3%");
                } else {
                    entity.setRate3(false);
                }
                if (entity.getInputTaxRate().equals("0.13") || entity.getInputTaxRate().equals("13%") || entity.getInputTaxRate().equals("0.14")) {
                    entity.setRate13(true);
                    entity.setInputTaxRate("13%");
                } else {
                    entity.setRate13(false);
                }

                rb3.setChecked(entity.isRate3());
                rb13.setChecked(entity.isRate13());
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb_3:
                                entity.setInputTaxRate(rb3.getText().toString());
                                data.set(holder.getAdapterPosition() - 1, entity);
                                break;
                            case R.id.rb_13:
                                entity.setInputTaxRate(rb13.getText().toString());
                                data.set(holder.getAdapterPosition() - 1, entity);
                                break;
                        }
                        UpData(data);
                    }
                });
                break;
            case CommodityEntity.LIST_PARAM2:
                tvAmount = holder.getView(R.id.tv_amount);
                tvPriceRate = holder.getView(R.id.tv_price_rate);
                tvActualAmount = holder.getView(R.id.tv_actual_amount);
                Bundle b = new Bundle();
                double sum = 0, rate = 0, actualAmount = 0;
                LatteLogger.d("testInputTaxRate", GsonBuildUtil.GsonBuilder(data));
                LatteLogger.d("entity", entity.getInputTaxRate() + "     "+ entity.getInputPrice());
                if (entity.getInputTaxRate() !=null&&entity.getInputPrice()!=null) {
                    for (int i = 0; i < data.size(); i++) {
                        if ((Integer.parseInt(data.get(i).getInputTaxRate()) != 0 && Integer.parseInt(data.get(i).getInputPrice()) != 0)||
                                (entity.getInputTaxRate() !=null&&entity.getInputPrice()!=null)) {
                            sum = (sum + Double.parseDouble(data.get(i).getInputPrice())) * Integer.parseInt(data.get(i).getNum());
                            rate = (rate + Double.parseDouble(data.get(i).getInputPrice()) *
                                    (Double.parseDouble(data.get(i).getInputTaxRate().substring(0, data.get(i).getInputTaxRate().length() - 1))) / 100) *
                                    Integer.parseInt(data.get(i).getNum());
                            actualAmount = sum - rate;
                            b.putDouble("sum", sum);
                            b.putDouble("priceRate", rate);
                            b.putDouble("actualAmount", actualAmount);
                            b.putInt("position", holder.getLayoutPosition() - 1);
                            Event<Bundle> event = new Event<Bundle>(TOTAL_MONEY, b);
                            EventBusUtil.sendEvent(event);
                        }
                    }
                }

                if (TextUtils.isEmpty(entity.getTotalActualAmount())) {
                    tvAmount.setText("");
                    tvPriceRate.setText("");
                    tvActualAmount.setText("");
                } else {
                    tvAmount.setText(StringUtil.changeStrColor("¥" + entity.getTotalPrice(), 0));
                    tvPriceRate.setText(StringUtil.changeStrColor("¥" + entity.getTotalRate(), 0));
                    tvActualAmount.setText(StringUtil.changeStrColor("¥" + entity.getTotalActualAmount(), 0));
                }
                break;
            case CommodityEntity.LIST_PARAM3:
                holder.setText(R.id.tv_week_date, entity.getInputFinishNoDate());
                holder.setText(R.id.tv_start_date, entity.getStartDate());
                holder.setText(R.id.tv_end_date, entity.getEndDate());
               


                moneyAdapter = new ProperyTagAdapter(getContext(), entity.getClearingFormMethod());
                FlowTagLayout flClearingForm = holder.getView(R.id.rl_clearing_form_method);
                flClearingForm.setAdapter(moneyAdapter);
                moneyAdapter.notifyDataSetChanged();

                flClearingForm.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                for (TagInfo tagInfo : entity.getClearingFormMethod()) {
                    if (tagInfo.getText().equals(entity.getCountAmountMethod())) {
                        tagInfo.setSelect(true);
                    }
                }

                for (TagInfo tagInfo : entity.getGoodsMethod()) {
                    if (tagInfo.getText().equals(entity.getDeliver())) {
                        tagInfo.setSelect(true);
                    }
                }
                flClearingForm.setOnTagSelectListener(new OnTagSelectListener() {
                    @Override
                    public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                        moneyPosition = selectedList.get(0);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", holder.getAdapterPosition() - 1);
                        bundle.putString("closeAnCount", entity.getClearingFormMethod().get(moneyPosition).getText());
                        Event<Bundle> event = new Event<Bundle>(CLEAR, bundle);
                        EventBusUtil.sendEvent(event);
                    }
                });
                goodsAdapter = new ProperyTagAdapter(getContext(), entity.getGoodsMethod());
                FlowTagLayout flGoodsMethod = holder.getView(R.id.rl_goods_method);
                flGoodsMethod.setAdapter(goodsAdapter);
                goodsAdapter.notifyDataSetChanged();
                flGoodsMethod.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                flGoodsMethod.setOnTagSelectListener(new OnTagSelectListener() {
                    @Override
                    public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                        goodsPosition = selectedList.get(0);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", holder.getAdapterPosition() - 1);
                        bundle.putString("delivery", entity.getGoodsMethod().get(goodsPosition).getText());
                        Event<Bundle> event = new Event<Bundle>(DELIVERY, bundle);
                        EventBusUtil.sendEvent(event);
                    }
                });
                break;
        }
    }

    /**
     * 更新数据
     ***/
    public void UpData(List<CommodityEntity> entities) {
        if (entities != null) {
            this.data = entities;
            notifyDataSetChanged();
        }
    }

    public List<CommodityEntity> getCommodityEntity() {
        return data;
    }
}








