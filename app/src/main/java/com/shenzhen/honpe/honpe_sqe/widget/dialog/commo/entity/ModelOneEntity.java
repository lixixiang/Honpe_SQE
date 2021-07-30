package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity;

/**
 * FileName: ModelOneEntity
 * Author: asus
 * Date: 2021/3/18 11:22
 * Description:外发采购申请单报价主表实体
 */
public class ModelOneEntity {
    private int  rowid; // 第一次报价传入为空，修改报价的的时候不为空
    private String 申请单号;
    private String 报价单号; // 第一次报价传入为空，修改报价不为空，传入报价单号
    private String 报价日期;
    private String 报价人;
    private String 供应商代码;
    private String 供应商名称;
    private String 报价金额;
    private int 报价类型 = 1;// 0 采购员报价  1 供应商报价
    private int 制作周期;
    private String  报价有效开始时间;
    private String 报价有效结束时间;
    private String 结算方式;// 银行汇款, 现金, 信汇, 商业汇票, 银行汇票, 票据结算中的任一一种类型
    private String 送货方式;// 快递, 自取, 送货上门中的任一一种类型

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public String get申请单号() {
        return 申请单号;
    }

    public void set申请单号(String 申请单号) {
        this.申请单号 = 申请单号;
    }

    public String get报价单号() {
        return 报价单号;
    }

    public void set报价单号(String 报价单号) {
        this.报价单号 = 报价单号;
    }

    public String get报价日期() {
        return 报价日期;
    }

    public void set报价日期(String 报价日期) {
        this.报价日期 = 报价日期;
    }

    public String get报价人() {
        return 报价人;
    }

    public void set报价人(String 报价人) {
        this.报价人 = 报价人;
    }

    public String get供应商代码() {
        return 供应商代码;
    }

    public void set供应商代码(String 供应商代码) {
        this.供应商代码 = 供应商代码;
    }

    public String get供应商名称() {
        return 供应商名称;
    }

    public void set供应商名称(String 供应商名称) {
        this.供应商名称 = 供应商名称;
    }

    public String get报价金额() {
        return 报价金额;
    }

    public void set报价金额(String 报价金额) {
        this.报价金额 = 报价金额;
    }

    public int get报价类型() {
        return 报价类型;
    }

    public void set报价类型(int 报价类型) {
        this.报价类型 = 报价类型;
    }

    public int get制作周期() {
        return 制作周期;
    }

    public void set制作周期(int 制作周期) {
        this.制作周期 = 制作周期;
    }

    public String get报价有效开始时间() {
        return 报价有效开始时间;
    }

    public void set报价有效开始时间(String 报价有效开始时间) {
        this.报价有效开始时间 = 报价有效开始时间;
    }

    public String get报价有效结束时间() {
        return 报价有效结束时间;
    }

    public void set报价有效结束时间(String 报价有效结束时间) {
        this.报价有效结束时间 = 报价有效结束时间;
    }

    public String get结算方式() {
        return 结算方式;
    }

    public void set结算方式(String 结算方式) {
        this.结算方式 = 结算方式;
    }

    public String get送货方式() {
        return 送货方式;
    }

    public void set送货方式(String 送货方式) {
        this.送货方式 = 送货方式;
    }
}
