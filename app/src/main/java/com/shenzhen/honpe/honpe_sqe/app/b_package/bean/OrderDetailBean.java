package com.shenzhen.honpe.honpe_sqe.app.b_package.bean;

/**
 * FileName: OfferBean
 * Author: asus
 * Date: 2021/1/22 15:13
 * Description:外发采购报价单
 */
public class OrderDetailBean {
    private String GoodsName;
    private String GoodsDate;
    private String GoodsSize;
    private String GoodsTexture;
    private String GoodsUnit;
    private String GoodsNo;
    private String GoodsPost;
    private String GoodsNote;

    public OrderDetailBean(String goodsName, String goodsDate, String goodsSize, String goodsTexture, String goodsUnit, String goodsNo, String goodsPost,String goodsNote) {
        GoodsName = goodsName;
        GoodsDate = goodsDate;
        GoodsSize = goodsSize;
        GoodsTexture = goodsTexture;
        GoodsUnit = goodsUnit;
        GoodsNo = goodsNo;
        GoodsPost = goodsPost;
        GoodsNote = goodsNote;
    }

    public String getGoodsNote() {
        return GoodsNote;
    }

    public void setGoodsNote(String goodsNote) {
        GoodsNote = goodsNote;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getGoodsDate() {
        return GoodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        GoodsDate = goodsDate;
    }

    public String getGoodsSize() {
        return GoodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        GoodsSize = goodsSize;
    }

    public String getGoodsTexture() {
        return GoodsTexture;
    }

    public void setGoodsTexture(String goodsTexture) {
        GoodsTexture = goodsTexture;
    }

    public String getGoodsUnit() {
        return GoodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        GoodsUnit = goodsUnit;
    }

    public String getGoodsNo() {
        return GoodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        GoodsNo = goodsNo;
    }

    public String getGoodsPost() {
        return GoodsPost;
    }

    public void setGoodsPost(String goodsPost) {
        GoodsPost = goodsPost;
    }
}
