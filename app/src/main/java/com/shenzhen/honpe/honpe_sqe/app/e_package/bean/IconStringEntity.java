package com.shenzhen.honpe.honpe_sqe.app.e_package.bean;

/**
 * FileName: MeBean
 * Author: asus
 * Date: 2021/1/19 11:11
 * Description:图片，内容
 */
public class IconStringEntity {
    private int icon;
    private String content;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
