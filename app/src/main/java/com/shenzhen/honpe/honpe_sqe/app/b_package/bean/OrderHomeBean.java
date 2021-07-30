package com.shenzhen.honpe.honpe_sqe.app.b_package.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.io.Serializable;

/**
 * FileName: OrderHomeBean
 * Author: asus
 * Date: 2021/1/21 13:30
 * Description:
 */
public class OrderHomeBean implements Serializable {
    private String orderNum;     //订单号
    private String applyDate;    //开始时期
    private String principal;    //跟单员
    private String classify;   //采购分类
    private int status;       //状态 0 未报价 1已报价

    public OrderHomeBean(String orderNum, String applyDate, String principal, String classify,int status) {
        this.orderNum = orderNum;
        this.applyDate = applyDate;
        this.principal = principal;
        this.classify = classify;
        this.status = status;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
