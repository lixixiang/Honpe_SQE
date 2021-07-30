package com.shenzhen.honpe.honpe_sqe.app.a_package.bean.section;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * FileName: OrderNode
 * Author: asus
 * Date: 2021/2/26 9:15
 * Description:
 */
public class OrderNode  {

    private List<ItemNode> childNode;

    private String t_orderNum;
    private String t_type;
    private String t_num;
    private String t_deliver; //交货时间
    private String t_accept_time; //接标时间
    private int pos; //属于待报价 已报价 ...
    private int currentPos; //当前的item
    private int currentChildPos; //当前的子item
    public boolean check;


    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public int getCurrentChildPos() {
        return currentChildPos;
    }

    public void setCurrentChildPos(int currentChildPos) {
        this.currentChildPos = currentChildPos;
    }

    public void setChildNode(List<ItemNode> childNode) {
        this.childNode = childNode;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }



    public String getT_orderNum() {
        return t_orderNum;
    }

    public void setT_orderNum(String t_orderNum) {
        this.t_orderNum = t_orderNum;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }

    public String getT_num() {
        return t_num;
    }

    public void setT_num(String t_num) {
        this.t_num = t_num;
    }

    public String getT_deliver() {
        return t_deliver;
    }

    public void setT_deliver(String t_deliver) {
        this.t_deliver = t_deliver;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<ItemNode> getChildNode() {
        return childNode;
    }


}
