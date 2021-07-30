package com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean;

/**
 * FileName: SupplyBean
 * Author: asus
 * Date: 2021/3/10 15:59
 * Description:
 */
public class SupplyBean {

    /**
     * 分类编码 : W.114
     * 分类名称 : 钝化
     */

    private String 分类编码;
    private String 分类名称;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String get分类编码() {
        return 分类编码;
    }

    public void set分类编码(String 分类编码) {
        this.分类编码 = 分类编码;
    }

    public String get分类名称() {
        return 分类名称;
    }

    public void set分类名称(String 分类名称) {
        this.分类名称 = 分类名称;
    }
}
