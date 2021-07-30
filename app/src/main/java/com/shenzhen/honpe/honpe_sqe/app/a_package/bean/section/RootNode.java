package com.shenzhen.honpe.honpe_sqe.app.a_package.bean.section;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * FileName: RootNode
 * Author: asus
 * Date: 2021/1/28 13:55
 * Description:
 */
public class RootNode {

    private String t_date;
    private List<OrderNode> orderNodes;

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    public List<OrderNode> getOrderNodes() {
        return orderNodes;
    }

    public void setOrderNodes(List<OrderNode> orderNodes) {
        this.orderNodes = orderNodes;
    }
}
