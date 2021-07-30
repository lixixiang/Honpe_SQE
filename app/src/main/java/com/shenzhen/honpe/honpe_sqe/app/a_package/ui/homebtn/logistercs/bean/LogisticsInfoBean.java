package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs.bean;

import java.util.List;

/**
 * 包名: com.example.lxx.myalipay.bean
 * 作者: lxx
 * 日期: 2019/3/19 16:23
 * 描述: 获取详细的物流信息
 */
public class LogisticsInfoBean {

    /**
     * LogisticCode : 75310813494280
     * ShipperCode : ZTO
     * Traces : [{"AcceptStation":"【西安市】  【西安草滩】（15721968991、029-86512700） 的 西安草滩（13991360537） 已揽收","AcceptTime":"2019-11-12 18:50:12"},{"AcceptStation":"【西安市】  快件离开 【西安草滩】 已发往 【西安】","AcceptTime":"2019-11-12 18:50:17"},{"AcceptStation":"【西安市】  快件已经到达 【西安中转】","AcceptTime":"2019-11-13 00:53:41"},{"AcceptStation":"【西安市】  快件离开 【西安中转】 已发往 【深圳中心】","AcceptTime":"2019-11-13 00:55:22"},{"AcceptStation":"【深圳市】  快件已经到达 【深圳中心】","AcceptTime":"2019-11-14 07:36:07"},{"AcceptStation":"【深圳市】  快件离开 【深圳中心】 已发往 【深圳新福永】","AcceptTime":"2019-11-14 08:28:52"},{"AcceptStation":"【深圳市】  快件已经到达 【深圳新福永】","AcceptTime":"2019-11-14 12:41:33"},{"AcceptStation":"【深圳市】  【深圳新福永】 的凤凰周爱军（13480945036） 正在第1次派件, 请保持电话畅通,并耐心等待（95720为中通快递员外呼专属号码，请放心接听）","AcceptTime":"2019-11-14 20:43:12"},{"AcceptStation":"【深圳市】  快件已在 【深圳新福永】 签收, 签收人: 拍照签收, 如有疑问请电联:13480945036 / 0755-36551113, 您的快递已经妥投。风里来雨里去, 只为客官您满意。上有老下有小, 赏个好评好不好？【请在评价快递员处帮忙点亮五颗星星哦~】","AcceptTime":"2019-11-14 23:26:49"}]
     * State : 3
     * EBusinessID : 1602424
     * Success : true
     */

    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String EBusinessID;
    private boolean Success;
    private List<TracesBean> Traces;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean {
        /**
         * AcceptStation : 【西安市】  【西安草滩】（15721968991、029-86512700） 的 西安草滩（13991360537） 已揽收
         * AcceptTime : 2019-11-12 18:50:12
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}

