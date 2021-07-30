package com.shenzhen.honpe.honpe_sqe.app.a_package.bean;

import com.shenzhen.honpe.honpe_sqe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: DataBean
 * Author: asus
 * Date: 2021/1/27 10:42
 * Description:
 */
public class DataBean {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;
    public List<ChildDataBean> databeans;

    public DataBean() {
    }

    public DataBean(Integer imageRes) {
        this.imageRes = imageRes;
    }

    public DataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public List<ChildDataBean> getDatabeans() {
        return databeans;
    }

    public void setDatabeans(List<ChildDataBean> databeans) {
        this.databeans = databeans;
    }

    public static List<DataBean> getTestData2() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.hc1));
        list.add(new DataBean(R.mipmap.hc2));
        list.add(new DataBean(R.mipmap.hc3));
        list.add(new DataBean(R.mipmap.hc4));
        return list;
    }

    public static class ChildDataBean {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

















