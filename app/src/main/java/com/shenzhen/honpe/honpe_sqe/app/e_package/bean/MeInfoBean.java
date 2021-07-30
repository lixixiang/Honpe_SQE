package com.shenzhen.honpe.honpe_sqe.app.e_package.bean;

import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.CompanyEntity;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.RegisterBean;

import java.util.List;

/**
 * FileName: MeInfoBean
 * Author: asus
 * Date: 2021/1/20 11:12
 * Description:
 */
public class MeInfoBean  {
    private String title;
    private List<String> contents;
    private List<RegisterBean> registerBeans;
    private CompanyEntity companyEntity;

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RegisterBean> getRegisterBeans() {
        return registerBeans;
    }

    public void setRegisterBeans(List<RegisterBean> registerBeans) {
        this.registerBeans = registerBeans;
    }
}
