package com.shenzhen.honpe.honpe_sqe.app.a_package.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;

import java.io.Serializable;
import java.util.List;

/**
 * FileName: QuickMultipleEntity
 * Author: asus
 * Date: 2021/1/25 13:37
 * Description:
 */
public class QuickMultipleEntity implements MultiItemEntity, Serializable {
    public static final int Marquee  = 1; //跑马灯
    public static final int IMG_TEXT = 2;
    public static final int TEXT_UP = 3; //中标公司
    public static final int BANNER_SORT = 4; //加工分类
    public static final int NEWS_CONTENT = 5; //新闻
    public static final int CENTER_PERSON_MY_ORDER = 6; //个人中心 - 我的订单头部
    public static final int CENTER_PERSON_MY_CONTENT = 7;//个人中心 - 全部数据

    public static final int SIZE_1 = 1;
    public static final int SIZE_2 = 2;
    public static final int SIZE_3 = 3;
    public static final int SIZE_4 = 4;
    public static final int SIZE_5 = 5;

    private int itemType;
    private int spanSize;
    private String marquee;//走马灯
    private List<MessageEntity> messages;

    private int icon;
    private String strTitle;
    private String order;
    private String name;
    private int status; //中标状态
    private String money;
    private String date;
    private String time;
    private String totalNum;//含税
    private String result; //原因
    //资讯
    private String newsTitle;
    private String newsDate;
    private String newsIcon;
    private String newsContent;
    private int pos;
    private List<NewsChildEntity> mDatas;
    private String title;
    //个人中心
    private String tipsLeftText;
    private String tipsRightText;
    //个人中心内容
    private UnQuotedBean CenterContent;

    public UnQuotedBean getCenterContent() {
        return CenterContent;
    }

    public void setCenterContent(UnQuotedBean centerContent) {
        CenterContent = centerContent;
    }

    public String getTipsLeftText() {
        return tipsLeftText;
    }

    public void setTipsLeftText(String tipsLeftText) {
        this.tipsLeftText = tipsLeftText;
    }

    public String getTipsRightText() {
        return tipsRightText;
    }

    public void setTipsRightText(String tipsRightText) {
        this.tipsRightText = tipsRightText;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsIcon() {
        return newsIcon;
    }

    public void setNewsIcon(String newsIcon) {
        this.newsIcon = newsIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public List<NewsChildEntity> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<NewsChildEntity> mDatas) {
        this.mDatas = mDatas;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public String getMarquee() {
        return marquee;
    }

    public void setMarquee(String marquee) {
        this.marquee = marquee;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public QuickMultipleEntity() {
    }

    public QuickMultipleEntity(int itemType, int spanSize, int icon, String strTitle) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.icon = icon;
        this.strTitle = strTitle;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public static class MessageEntity{
        public String company;
        public String name;
    }

    public static class NewsChildEntity{
        //资讯子类
        private String newsChildTitle;
        private String newsChildDate;
        private int newsChildIcon;

        public String getNewsChildTitle() {
            return newsChildTitle;
        }

        public void setNewsChildTitle(String newsChildTitle) {
            this.newsChildTitle = newsChildTitle;
        }

        public String getNewsChildDate() {
            return newsChildDate;
        }

        public void setNewsChildDate(String newsChildDate) {
            this.newsChildDate = newsChildDate;
        }

        public int getNewsChildIcon() {
            return newsChildIcon;
        }

        public void setNewsChildIcon(int newsChildIcon) {
            this.newsChildIcon = newsChildIcon;
        }
    }
}
