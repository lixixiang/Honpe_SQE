package com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean;

import java.io.Serializable;
import java.util.List;

/**
 * FileName: RegisterBeann
 * Author: asus
 * Date: 2021/3/8 17:26
 * Description:
 */
public class RegisterBean implements Serializable {
    private String title;
    private String hint;
    private String account;
    private String name;
    private String password;
    private String userpassword;// 登录密码 已经MD5加密
    private String email;
    private String phone;
    private String companyName;
    private String companySimpleName;
    private String organizingCode;
    private String taxID; //税务登记号
    private String contraryBank; //对公银行
    private String contraryAccount; //对公帐号
    private String supplyClassification; //供应分类
    private String uploadBusiness; //上传营业执照
    private String uploadOrganization;//上传组织机构证书
    private String uploadTaxCertificate;//上传税务登记证书
    private String address;//地址
    private String supplyStrId;//供应分类Id
    private String comefrom;
    //图片地址
    private String photoOrganize; //组织机构图
    private String photoTax;
    private String photoLicense; //营业照片图

    public String getSupplyStrId() {
        return supplyStrId;
    }

    public void setSupplyStrId(String supplyStrId) {
        this.supplyStrId = supplyStrId;
    }

    public String getPhotoOrganize() {
        return photoOrganize;
    }

    public void setPhotoOrganize(String photoOrganize) {
        this.photoOrganize = photoOrganize;
    }

    public String getPhotoTax() {
        return photoTax;
    }

    public void setPhotoTax(String photoTax) {
        this.photoTax = photoTax;
    }

    public String getPhotoLicense() {
        return photoLicense;
    }

    public void setPhotoLicense(String photoLicense) {
        this.photoLicense = photoLicense;
    }

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySimpleName() {
        return companySimpleName;
    }

    public void setCompanySimpleName(String companySimpleName) {
        this.companySimpleName = companySimpleName;
    }

    public String getOrganizingCode() {
        return organizingCode;
    }

    public void setOrganizingCode(String organizingCode) {
        this.organizingCode = organizingCode;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getContraryBank() {
        return contraryBank;
    }

    public void setContraryBank(String contraryBank) {
        this.contraryBank = contraryBank;
    }

    public String getContraryAccount() {
        return contraryAccount;
    }

    public void setContraryAccount(String contraryAccount) {
        this.contraryAccount = contraryAccount;
    }

    public String getSupplyClassification() {
        return supplyClassification;
    }

    public void setSupplyClassification(String supplyClassification) {
        this.supplyClassification = supplyClassification;
    }

    public String getUploadBusiness() {
        return uploadBusiness;
    }

    public void setUploadBusiness(String uploadBusiness) {
        this.uploadBusiness = uploadBusiness;
    }

    public String getUploadOrganization() {
        return uploadOrganization;
    }

    public void setUploadOrganization(String uploadOrganization) {
        this.uploadOrganization = uploadOrganization;
    }

    public String getUploadTaxCertificate() {
        return uploadTaxCertificate;
    }

    public void setUploadTaxCertificate(String uploadTaxCertificate) {
        this.uploadTaxCertificate = uploadTaxCertificate;
    }
}
