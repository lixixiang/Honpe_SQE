package com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean;

import java.io.Serializable;

/**
 * FileName: CompanyEntity
 * Author: asus
 * Date: 2021/3/12 11:09
 * Description:
 */
public class CompanyEntity implements Serializable {
    private String company;// 公司名称
    private String shortName;// 公司简称
    private String orgCode;// 组织机构代码
    private String bank;// 对公银行
    private String bankNum;// 对公帐号
    private String license;// 营业执照图片
    private String organize;// 组织机构证书图片
    private String tax;// 税务登记证书图片
    private int nullify;//default 0待审核
    private String taxcode;// 税务登记号
    private String wftype;// 供应商采购分类（注册标签编码用逗号","分隔）
    private String Introduction;// 公司简介

    public CompanyEntity() {
    }

    public CompanyEntity(String company, String shortName, String orgCode, String bank, String bankNum, String license, String organize, String tax, int nullify, String taxcode, String wftype, String introduction) {
        this.company = company;
        this.shortName = shortName;
        this.orgCode = orgCode;
        this.bank = bank;
        this.bankNum = bankNum;
        this.license = license;
        this.organize = organize;
        this.tax = tax;
        this.nullify = nullify;
        this.taxcode = taxcode;
        this.wftype = wftype;
        Introduction = introduction;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public int getNullify() {
        return nullify;
    }

    public void setNullify(int nullify) {
        this.nullify = nullify;
    }

    public String getTaxcode() {
        return taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public String getWftype() {
        return wftype;
    }

    public void setWftype(String wftype) {
        this.wftype = wftype;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getOrganize() {
        return organize;
    }

    public void setOrganize(String organize) {
        this.organize = organize;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
