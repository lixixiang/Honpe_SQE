package com.shenzhen.honpe.honpe_sqe.bean;

/**
 * FileName: StepBean
 * Author: asus
 * Date: 2021/3/3 17:32
 * Description:步骤实体对象
 */
public class StepBean {
    /**
     * 正在进行
     */
    public static final int STEP_CURRENT = 0;
    /**
     * 已完成
     */
    public static final int STEP_COMPLETED = 1;

    private int state;
    private int number;

    public StepBean(int state, int number) {
        this.state = state;
        this.number = number;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
