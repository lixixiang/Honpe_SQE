package com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.bean;

import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;

/**
 * FileName: BiddingBean
 * Author: asus
 * Date: 2021/1/26 12:14
 * Description:
 */
public class BiddingBean {
    private String title;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class ChildBean{
        private String type;
        private String date;
        private String orderNo;
        private String name;
        private String totalPrice;
        private String result;
        private int status;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
