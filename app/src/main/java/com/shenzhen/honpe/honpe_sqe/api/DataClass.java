package com.shenzhen.honpe.honpe_sqe.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.DataBean;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.MenuEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.b_package.bean.OrderDetailBean;
import com.shenzhen.honpe.honpe_sqe.app.b_package.bean.OrderHomeBean;
import com.shenzhen.honpe.honpe_sqe.app.e_package.bean.IconStringEntity;
import com.shenzhen.honpe.honpe_sqe.app.e_package.bean.MeInfoBean;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.CommodityEntity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.TagInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: DataClass
 * Author: asus
 * Date: 2021/1/19 11:29
 * Description:模拟数据
 */
public class DataClass {
    public static List<IconStringEntity> list = new ArrayList<>();
    public static int[] icons_me = {R.mipmap.icon_head, R.mipmap.icon_key};
    public static String[] text_me = {"个人信息", "APP二维码"};
    public static String[] person_detail = {"个体户", "邮箱", "联系电话", "联系地址"};
    public static String[] person_detail2 = {"银行卡号", "银行名称", "选填框"};
    public static String[] person_type = {"个人信息","认证信息"};

    public static String[] strOrders = {"WF2008098", "WF2001548", "WF2007775", "WF20033507", "WF20015852"};
    public static String[] strApplyDates = {"2020-11-17 10:10", "2020-11-30 16:10", "2020-12-07 09:50", "2020-12-19 10:10", "2020-12-27 10:10"};
    public static String[] strNames = {"黄志坚", "李圣", "刘佳琦", "古龙", "黄石"};
    public static String[] strTypes = {"CNC", "复模件", "钣金件", "CNC", "CNC"};

    private static String[] GoodsNames = {"10B001", "10B002", "10B003", "10B004", "10B005", "10B006"};
    private static String[] GoodsDates = {"2020-07-30", "2020-07-30", "2020-07-30", "2020-07-30", "2020-07-30", "2020-07-30"};
    private static String[] GoodsSize = {"109X16XB0", "109X15825", "10926511B", "1097852CB", "109X20554B", "109X155B0"};
    private static String[] GoodsPost = {"阻燃94V0,高绝缘,绝缘阻燃烤漆，颜色参考色彩计划", "阻燃94V0,高绝缘,绝缘阻燃烤漆，颜色参考色彩计划", "阻燃94V0,高绝缘,绝缘阻燃烤漆，颜色参考色彩计划",
            "阻燃94V0,高绝缘,绝缘烤漆，颜色为pantone 137C 高光金属漆", "阻燃94V0,高绝缘,绝缘烤漆，颜色为pantone 137C 高光金属漆", "阻燃94V0,高绝缘,绝缘烤漆，颜色为pantone 137C 高光金属漆",};

    public static String[] strTabs = {"外协招标", "供应商管理", "订单管理", "快速接单", "平台报价",
    "生产管理","物料管理","品质管理","资源中心","更多"};
    public static int[] icons = {R.mipmap.ic_home_mode00, R.mipmap.ic_home_mode01, R.mipmap.ic_home_mode02,R.mipmap.ic_home_mode03,R.mipmap.ic_home_mode04,
            R.mipmap.ic_home_mode00, R.mipmap.ic_home_mode01, R.mipmap.ic_home_mode02,R.mipmap.ic_home_mode03,R.mipmap.ic_home_mode04};
    public static String[] dates = {"20210126", "20210122"};
    public static String[] tenderDates = {"2021-01-25 20:12:55", "2021-01-22 12:12:55"};
    public static String[] Goods = {"WF2008098", "WF2001548"};
    public static String[] GoodsId = {"02ET55", "02ET56"};
    public static String[] GoodsNo = {"23pcs", "32pcs"};
    public static String[] GoodsTextures = {"纯PEI琥珀色", "天然红宝石蓝"};
    public static String[] money = {"20000.00", "0.00"};
    public static String[] names = {"黄志坚", "李圣"};
    public static String[] result = {"", "价位合理，适合我们公司生产，所以我们选择另一家公司的产品"};

    public static String[] Didding = {"中仪国际技术有限公司", "深圳立新三维有限公司", "深圳三湘有限公司", "深圳清木有限公司", "深圳金利达有限公司"};
    private static List<QuickMultipleEntity.MessageEntity> messages = new ArrayList<>();

    private static String[] newsTitle = {"五金加工技术你懂的多少？", "复模有又新方法，教你一个简单快捷的方式。", "一个成功的3D打印，需要解决哪些问题？"};

    private static String[] detailQuest = {"常见问题", "业务指南"};
    private static String[] NewsDetail = {"来料加工有误差", "喷油颜色有误差", "复模交期时间出现延迟", "验收不合格退回返工"};

    private static String[] CashTypes = {"现金", "银行汇款", "信汇", "商业汇票", "银行汇票", "票据结算"};
    private static String[] GoodsTypes = {"快递", "自取", "送货上门"};

    private static String[] newsIcons = {"https://pics3.baidu.com/feed/242dd42a2834349b3f1935423490b8c837d3be5f.jpeg?token=2ba53f9eef8c8bfe79052bea7f611493&qq-pf-to=pcqq.c2c",
            "https://pics5.baidu.com/feed/e824b899a9014c08ad4a344ffea13c0079f4f4ce.jpeg?token=e48d292b296e04a4fed345df71308bfc&qq-pf-to=pcqq.c2c",
            "https://pics6.baidu.com/feed/0824ab18972bd4076385d4766064a0590db309eb.jpeg?token=f94fa4fea8248a44cdf2b52bccc99a1e&qq-pf-to=pcqq.c2c",
            "https://pics6.baidu.com/feed/3b87e950352ac65c31dd8f18dfda8d1992138ab5.jpeg?token=0d28f92b73e14033d50d232394a18399&qq-pf-to=pcqq.c2c",
    "https://pics5.baidu.com/feed/6a600c338744ebf8161ddff2a8df97226159a7b9.jpeg?token=895217b91ba1e9b6973302ef281301b4&qq-pf-to=pcqq.c2c"};
    private static String[] newTitles = {"3D打印能否替代传统零部件制造技术？",
            "汽车未来五年会有哪些变化？",
            "芯片巨头亮出170亿美元和3nm芯片，超车开始，略胜台积电一筹",
            "科技巨头养出新巨兽，百亿黑马横空出世，7nm芯片即将量产",
            "iPhone 12 Pro Max被权威杂志《消费者报告》评为年度最佳智能手机"};
    private static String[] newsDates = {"2020-05-17 18:59",
            "2021-03-25 13:10",
            "2021-03-25 14:30",
            "2021-03-25 23:25",
            "2021-03-26 13:17"};
    public static String[] newsContents = {"https://baijiahao.baidu.com/s?id=1666935188267664047&wfr=spider&for=pc&qq-pf-to=pcqq.c2c",
    "https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9317107769603480315%22%7D&n_type=1&p_from=4&qq-pf-to=pcqq.c2c",
    "https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10158092770307175791%22%7D&n_type=0&p_from=1&qq-pf-to=pcqq.c2c",
    "https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9260836323515524941%22%7D&n_type=0&p_from=1&qq-pf-to=pcqq.c2c",
    "https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9454881137885762091%22%7D&n_type=0&p_from=1&qq-pf-to=pcqq.c2c"};

    public static String[] myOrder = {"待付款","待发货","待收货","评价","退货/售后"};
    private static int[] myOrderIcons = {R.mipmap.ic_center1,R.mipmap.ic_center2,R.mipmap.ic_center3,R.mipmap.ic_center6,R.mipmap.ic_center5};

    private static String[] allOrder = {"全部","待付款","待发货","待收货","待评价"};

    private static String[] SettingTitles = {"用户认证","App二维码"};

    private static int[] personIcons = {R.mipmap.ic_center1,R.mipmap.ic_center2,R.mipmap.ic_center3,R.mipmap.ic_center6,R.mipmap.ic_center5,R.mipmap.ic_center6,R.mipmap.ic_center5};
    private static String[] personTitles = {"当前所在公司","任务中心","我的商品","地址管理","我的招标","发票管理","系统设置"};


    public static List<IconStringEntity> MeData() {
        for (int i = 0; i < icons_me.length; i++) {
            IconStringEntity entity = new IconStringEntity();
            entity.setIcon(icons_me[i]);
            entity.setContent(text_me[i]);
            list.add(entity);
        }
        return list;
    }

    public static List<MeInfoBean> MeInfoData() {
        List<MeInfoBean> list = new ArrayList<>();
        for (int i = 0; i < person_type.length; i++) {
            MeInfoBean bean = new MeInfoBean();
            bean.setTitle(person_type[i]);
                if (i == 0) {
                    bean.setContents(StringUtil.ArrToList(person_detail));
                } else if (i == 1) {
                   bean.setContents(StringUtil.ArrToList(person_detail2));
                }
            list.add(bean);
        }
        return list;
    }

    public static List<OrderHomeBean> orderHomeData() {
        List<OrderHomeBean> list = new ArrayList<>();
        for (int i = 0; i < strOrders.length; i++) {
            OrderHomeBean bean = new OrderHomeBean(strOrders[i], strApplyDates[i], strNames[i], strTypes[i], 0);
            if (i == 2 || i == 4) {
                bean.setStatus(1);
            }
            list.add(bean);
        }
        return list;
    }

    public static List<OrderDetailBean> orderDetailData() {
        List<OrderDetailBean> list = new ArrayList<>();
        for (int i = 0; i < GoodsNames.length; i++) {
            OrderDetailBean bean = new OrderDetailBean(GoodsNames[i], GoodsDates[i], GoodsSize[i],
                    "纯PEI 琥珀色", "PCS", "70", GoodsPost[i], "");
            if (i == 1) {
                bean.setGoodsNote("有新图");
            } else if (i == 3) {
                bean.setGoodsNote("用余量切，不用订料");
            }
            list.add(bean);
        }
        return list;
    }

    public static TabLayout tabHomeData(Context context, TabLayout tabLayout) {
        for (int i = 0; i < strTabs.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(context, i));
            }
        }
        return tabLayout;
    }

    private static View getTabView(Context context, int currentPosition) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout_text, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_top_item);
        textView.setText(strTabs[currentPosition]);
        return view;
    }

    public static List<QuickMultipleEntity> getMultipleItemData(List<MenuEntity> indexDataList) {
        List<QuickMultipleEntity> list = new ArrayList<>();
        for (int i = 0; i < indexDataList.size(); i++) {
            QuickMultipleEntity bean2 = new QuickMultipleEntity();
            bean2.setItemType(QuickMultipleEntity.IMG_TEXT);
            bean2.setSpanSize(QuickMultipleEntity.SIZE_1);
            int drawableId = MyApplication.getContext().getResources().getIdentifier(indexDataList.get(i).getIco(),"mipmap",
                    MyApplication.getContext().getPackageName());
            bean2.setIcon(drawableId);
            bean2.setStrTitle(indexDataList.get(i).getTitle());
            list.add(bean2);
        }

        QuickMultipleEntity bean4 = new QuickMultipleEntity();
        bean4.setItemType(QuickMultipleEntity.BANNER_SORT);
        bean4.setSpanSize(QuickMultipleEntity.SIZE_5);
        list.add(bean4);

        for (int i = 0; i < newTitles.length; i++) {
            QuickMultipleEntity bean5 = new QuickMultipleEntity();
            bean5.setItemType(QuickMultipleEntity.NEWS_CONTENT);
            bean5.setSpanSize(QuickMultipleEntity.SIZE_5);
            bean5.setNewsTitle(newTitles[i]);
            bean5.setNewsDate(newsDates[i]);
            bean5.setNewsIcon(newsIcons[i]);
            bean5.setNewsContent(newsContents[i]);
            list.add(bean5);
        }
        return list;
    }

    public static List<QuickMultipleEntity> getPersonCenter(){
        List<QuickMultipleEntity> list = new ArrayList<>();
        for (int i = 0; i < personTitles.length; i++) {
            QuickMultipleEntity entity = new QuickMultipleEntity();
            entity.setIcon(personIcons[i]);
            entity.setTitle(personTitles[i]);
            list.add(entity);
        }
        return list;
    }

    public static List<QuickMultipleEntity> getCenterPerson(){
        List<QuickMultipleEntity> list = new ArrayList<>();
        QuickMultipleEntity bean = new  QuickMultipleEntity();
        bean.setTipsLeftText("我的订单");
        bean.setTipsRightText("查看全部");
        bean.setItemType(QuickMultipleEntity.CENTER_PERSON_MY_ORDER);
        bean.setSpanSize(QuickMultipleEntity.SIZE_5);
        list.add(bean);
        for (int i = 0; i < myOrder.length; i++) {
            QuickMultipleEntity bean2 = new QuickMultipleEntity();
            bean2.setItemType(QuickMultipleEntity.IMG_TEXT);
            bean2.setSpanSize(QuickMultipleEntity.SIZE_1);
            bean2.setIcon(myOrderIcons[i]);
            bean2.setStrTitle(myOrder[i]);
            list.add(bean2);
        }
        QuickMultipleEntity bean3 = new  QuickMultipleEntity();
        bean3.setTipsLeftText("最近3个月订单");
        bean3.setItemType(QuickMultipleEntity.NEWS_CONTENT);
        bean3.setSpanSize(QuickMultipleEntity.SIZE_5);
        list.add(bean3);
//
//        if (listdata != null) {
//            for (int i = 0; i < listdata.size(); i++) {
//                UnQuotedBean bean1 = listdata.get(i);
//                QuickMultipleEntity bean2 = new QuickMultipleEntity();
//                bean2.setItemType(QuickMultipleEntity.CENTER_PERSON_MY_CONTENT);
//                bean2.setSpanSize(QuickMultipleEntity.SIZE_5);
//                bean2.setCenterContent(bean1);
//                list.add(bean2);
//            }
//        }

        return list;
    }

    public static List<QuickMultipleEntity> getSettingList(){
        List<QuickMultipleEntity> list = new ArrayList<>();
        for (int i = 0; i < SettingTitles.length; i++) {
            QuickMultipleEntity bean = new QuickMultipleEntity();
            bean.setItemType(QuickMultipleEntity.CENTER_PERSON_MY_ORDER);
            bean.setSpanSize(QuickMultipleEntity.SIZE_1);
            bean.setStrTitle(SettingTitles[i]);
            list.add(bean);
        }
        return list;
    }


    public static List<QuickMultipleEntity> getHomeData() {
        List<QuickMultipleEntity> list = new ArrayList<>();
        for (int i = 0; i < dates.length; i++) {
            QuickMultipleEntity entity = new QuickMultipleEntity();
            entity.setItemType(QuickMultipleEntity.IMG_TEXT);
            entity.setSpanSize(QuickMultipleEntity.SIZE_4);
            entity.setOrder(Goods[i]);
            entity.setMoney(money[i]);
            entity.setDate(dates[i]);
            entity.setTime(tenderDates[i]);
            entity.setName(names[i]);
            entity.setResult(result[i]);
            if (i == 0) {
                entity.setStatus(0);
            } else {
                entity.setStatus(1);
            }
            list.add(entity);
        }
        return list;
    }

    public static List<DataBean> getDetailData() {
        List<DataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < detailQuest.length; i++) {
            DataBean dataBean = new DataBean();
            List<DataBean.ChildDataBean> listData = new ArrayList<>();
            dataBean.title = detailQuest[i];
            for (int j = 0; j < NewsDetail.length; j++) {
                DataBean.ChildDataBean childDataBean = new DataBean.ChildDataBean();
                childDataBean.setContent(NewsDetail[j]);
                listData.add(childDataBean);
            }
            dataBean.setDatabeans(listData);
            dataBeanList.add(dataBean);
        }
        return dataBeanList;
    }

    public static List<TagInfo> getClearingMethods(String[] dates){
        List<TagInfo> tagInfos = new ArrayList<>();
        for (int i = 0; i < dates.length; i++) {
            TagInfo info3 = new TagInfo(dates[i]);
            info3.setPosition(i);
            info3.setSelect(false);
            info3.setChecked(false);
            tagInfos.add(info3);
        }
        return tagInfos;
    }

    
    public static List<CommodityEntity> getCommodityData(UnQuotedBean node) {
        List<CommodityEntity> dataBeanList = new ArrayList<>();
        List<TagInfo> rates = new ArrayList<>();
        List<TagInfo> ClearingMethods = new ArrayList<>();
        for (int i = 0; i < CashTypes.length; i++) {
            TagInfo info3 = new TagInfo(CashTypes[i]);
            info3.setPosition(i);
            info3.setSelect(false);
            info3.setChecked(false);
            ClearingMethods.add(info3);
        }
        List<TagInfo> GoodsMethods = new ArrayList<>();
        for (int i = 0; i < GoodsTypes.length; i++) {
            TagInfo info4 = new TagInfo(GoodsTypes[i]);
            GoodsMethods.add(info4);
        }

        for (int i = 0; i < node.getOfferModel().get申请单明细报价记录().size(); i++) {
            CommodityEntity entity = new CommodityEntity();
            UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO dto = node.getOfferModel().get申请单明细报价记录().get(i);
            entity.setPartNum(dto.get物品名称());
            entity.setSize(dto.get规格());
            entity.setTexture(dto.get材质());
            entity.setNum(dto.get数量() + "");
            entity.setNote(dto.get后处理要求());
            entity.setInputPrice(StringUtil.formatDouble(dto.get单价()));
            entity.setInputTaxRate(StringUtil.formatDouble(dto.get税率()));
            entity.setItemType(CommodityEntity.LIST_PARAM);
            entity.setRate3(false);
            entity.setRate13(false);
            entity.setSpanSize(4);
            dataBeanList.add(entity);
        }
        CommodityEntity entity2 = new CommodityEntity();
        entity2.setItemType(CommodityEntity.LIST_PARAM2);
        entity2.setSpanSize(4);
        dataBeanList.add(entity2);
        CommodityEntity entity3 = new CommodityEntity();
        entity3.setItemType(CommodityEntity.LIST_PARAM3);
        entity3.setClearingFormMethod(ClearingMethods);
        entity3.setInputFinishNoDate(String.valueOf(node.get制作周期()));

        entity3.setStartDate(node.get报价有效开始时间());
        entity3.setEndDate(node.get报价有效结束时间());
        entity3.setCountAmountMethod(node.get结算方式());
        entity3.setDeliver(node.get送货方式());
        entity3.setGoodsMethod(GoodsMethods);
        entity3.setCountAmountMethod(node.get结算方式());
        entity3.setDeliver(node.get送货方式());
        entity3.setSpanSize(4);
        dataBeanList.add(entity3);
        return dataBeanList;
    }



    public static List<String> getAllOrder(){
      return StringUtil.ArrToList(allOrder);
    }

}












