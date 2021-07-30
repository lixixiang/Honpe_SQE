package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs.bean;

import java.util.List;

/**
 * created by lxx at 2019/11/20 10:08
 * 描述:获取快递公司
 */
public class KdApiOrderBean {

    /**
     * LogisticCode : 75310813494280
     * Shippers : [{"ShipperName":"中通速递","ShipperCode":"ZTO"}]
     * EBusinessID : 1602424
     * Code : 100
     * Success : true
     */

    private String LogisticCode;
    private String EBusinessID;
    private String Code;
    private boolean Success;
    private List<ShippersBean> Shippers;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<ShippersBean> getShippers() {
        return Shippers;
    }

    public void setShippers(List<ShippersBean> Shippers) {
        this.Shippers = Shippers;
    }

    public static class ShippersBean {
        /**
         * ShipperName : 中通速递
         * ShipperCode : ZTO
         */

        private String ShipperName;
        private String ShipperCode;

        public String getShipperName() {
            return ShipperName;
        }

        public void setShipperName(String ShipperName) {
            this.ShipperName = ShipperName;
        }

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String ShipperCode) {
            this.ShipperCode = ShipperCode;
        }
    }
}
