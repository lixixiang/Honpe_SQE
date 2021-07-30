package com.shenzhen.honpe.honpe_sqe.app.a_package.bean.section;

import java.util.List;

/**
 * FileName: ItemNode
 * Author: asus
 * Date: 2021/1/28 13:58
 * Description:
 */
public class ItemNode{
    private String starTime;
    private String PartNum; //零件编号
    private String size; //规格
    private String texture; //材质
    private String numPrice; //数量
    private String note; //后处理要求
    private String inputDeliveryDate; //输入交货日期
    private String inputTaxRate; //输入税率
    private String inputPrice; //输入单价
    private List<ItemChild> itemChild;

    public List<ItemChild> getItemChild() {
        return itemChild;
    }

    public void setItemChild(List<ItemChild> itemChild) {
        this.itemChild = itemChild;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getPartNum() {
        return PartNum;
    }

    public void setPartNum(String partNum) {
        this.PartNum = partNum;
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

    public String getNumPrice() {
        return numPrice;
    }

    public void setNumPrice(String numPrice) {
        this.numPrice = numPrice;
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


    public static class ItemChild {
        private String content;
        private String currentTime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }
    }
}
