package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * FileName: CommodityEntity
 * Author: asus
 * Date: 2021/2/21 10:00
 * Description:
 */
public class CommodityEntity implements MultiItemEntity , Serializable {
    public static final int LIST_PARAM = 0x01;
    public static final int LIST_PARAM2 = 0x02;
    public static final int LIST_PARAM3 = 0x03;
    private int itemType;
    private int spanSize;
    private int rowid;
    private String PartNum; //零件编号
    private String size; //规格
    private String texture; //材质
    private String totalNum;//总数量
    private String num; //数量
    private String note; //后处理要求
    private String inputDeliveryDate; //输入交货日期
    private String inputTaxRate; //输入税率
    private String inputPrice; //输入含税单价
    private String totalPrice; //含税总额
    private String totalRate;  //总税额
    private String totalActualAmount; //实际金额
    private String inputFinishNoDate; //周期
    private String startDate; //开始日期
    private String endDate;  //结束日期
    private String countAmountMethod; //结算
    private String deliver; //送货

    private List<TagInfo> clearingFormMethod;
    private List<TagInfo> GoodsMethod;
    private boolean rate3;
    private boolean rate13;

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public String getCountAmountMethod() {
        return countAmountMethod;
    }

    public void setCountAmountMethod(String countAmountMethod) {
        this.countAmountMethod = countAmountMethod;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInputFinishNoDate() {
        return inputFinishNoDate;
    }

    public void setInputFinishNoDate(String inputFinishNoDate) {
        this.inputFinishNoDate = inputFinishNoDate;
    }

    public boolean isRate13() {
        return rate13;
    }

    public void setRate13(boolean rate13) {
        this.rate13 = rate13;
    }

    public boolean isRate3() {
        return rate3;
    }

    public void setRate3(boolean rate3) {
        this.rate3 = rate3;
    }

    public List<TagInfo> getClearingFormMethod() {
        return clearingFormMethod;
    }

    public void setClearingFormMethod(List<TagInfo> clearingFormMethod) {
        this.clearingFormMethod = clearingFormMethod;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public String getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(String totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }

    public List<TagInfo> getGoodsMethod() {
        return GoodsMethod;
    }

    public void setGoodsMethod(List<TagInfo> goodsMethod) {
        GoodsMethod = goodsMethod;
    }


    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getPartNum() {
        return PartNum;
    }

    public void setPartNum(String partNum) {
        PartNum = partNum;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInputDeliveryDate() {
        return inputDeliveryDate;
    }

    public void setInputDeliveryDate(String inputDeliveryDate) {
        this.inputDeliveryDate = inputDeliveryDate;
    }

    public String getInputTaxRate() {
        return inputTaxRate;
    }

    public void setInputTaxRate(String inputTaxRate) {
        this.inputTaxRate = inputTaxRate;
    }

    public String getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(String inputPrice) {
        this.inputPrice = inputPrice;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}












