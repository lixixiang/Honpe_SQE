package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity;

/**
 * FileName: TagInfo
 * Author: asus
 * Date: 2021/2/21 14:34
 * Description:
 */
public class TagInfo {
    private boolean isChecked;
    private String text;
    private boolean isSelect;
    private int position=0;

    public TagInfo() {
    }

    public TagInfo(boolean isChecked, String text) {
        this.isChecked = isChecked;
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public TagInfo(String text) {
        this.isChecked = true;
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

