package com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean;


import java.io.Serializable;

/**
 * FileName: PersonEntity
 * Author: asus
 * Date: 2021/3/12 11:15
 * Description:
 */
public class PersonEntity implements Serializable {
    private String username;// 登录账号
    private String userpassword;// 登录密码 已经MD5加密
    private String email;//  E - mail;
    private String phone;// 手机
    private String name;// 显示名称(姓名)
    private String company;// 公司名称
    private String Adress;// 联系地址
    private String password;//未加密码
    private boolean isCheck; //检查是否已经是选中状态
    private String key;
    private LoginBean.Value value;
    private String comefrom;


    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LoginBean.Value getValue() {
        return value;
    }

    public void setValue(LoginBean.Value value) {
        this.value = value;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }


}
