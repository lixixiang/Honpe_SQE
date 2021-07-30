package com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean;


import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * FileName: LoginBean
 * Author: asus
 * Date: 2021/3/16 10:18
 * Description:
 */
public class LoginBean implements Serializable{
    private String key;
    private Value value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * username : honpe001
     * userid : 0a9c08fc9661444499a1869c4abcae97
     * sid : D.0146
     * rolename : admin
     * name : 深圳市三鼎盛科技有限公司
     * company : 深圳市三鼎盛科技有限公司
     * email : 313572552@qq.com
     * Adress : 深圳市
     * phone : 13125441460
     */
    public static class Value implements Serializable{
        private String username;
        private String userid;
        private String sid;
        private String rolename;
        private String name;
        private String company;
        private String email;
        private String Address;
        private String phone;
        private String userpassword;
        private String headImage; //bitmap

        public String getIcons() {
            return headImage;
        }

        public void setIcons(String icons) {
            this.headImage = icons;
        }

        public String getUserpassword() {
            return userpassword;
        }

        public void setUserpassword(String userpassword) {
            this.userpassword = userpassword;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}

