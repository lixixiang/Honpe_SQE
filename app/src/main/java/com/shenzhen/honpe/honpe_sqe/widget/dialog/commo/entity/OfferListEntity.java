package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity;

/**
 * FileName: OfferList
 * Author: asus
 * Date: 2021/3/18 11:18
 * Description:为传入的 外发采购申请单明细报价记录实体数组
 */
public class OfferListEntity {
    private int  rowid;// 第一次报价传入为空，修改报价的的时候不为空
    private double 报价单价;
    private String 零件编码;
    private String 备注;// 手机APP安卓端（苹果端）报价系统入库，这个字段用于以后出问题追踪哪个传入数据有问题
    private double 税率; //= 0;//默认为0,假设税率为16%则传入数值为0.16,传入小数
    private int 数量;

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public double get报价单价() {
        return 报价单价;
    }

    public void set报价单价(double 报价单价) {
        this.报价单价 = 报价单价;
    }

    public String get零件编码() {
        return 零件编码;
    }

    public void set零件编码(String 零件编码) {
        this.零件编码 = 零件编码;
    }

    public String get备注() {
        return 备注;
    }

    public void set备注(String 备注) {
        this.备注 = 备注;
    }

    public double get税率() {
        return 税率;
    }

    public void set税率(double 税率) {
        this.税率 = 税率;
    }

    public int get数量() {
        return 数量;
    }

    public void set数量(int 数量) {
        this.数量 = 数量;
    }
}
