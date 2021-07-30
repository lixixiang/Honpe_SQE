package com.shenzhen.honpe.honpe_sqe.widget;

import android.util.Log;

import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs.bean.KdApiOrderBean;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs.bean.LogisticsInfoBean;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.START_CAMERA_RESULT;

/**
 * @ProjectName: Honpe
 * @CreateDate: 2020/7/17 13:42
 * @Author: 李熙祥
 * @Description: java类作用描述
 * 快递鸟物流轨迹即时查询接口
 *
 * @技术QQ群: 456320272
 * @see: http://www.kdniao.com/YundanChaxunAPI.aspx
 * @copyright: 深圳市快金数据技术服务有限公司
 *
 * DEMO中的电商ID与私钥仅限测试使用，正式环境请单独注册账号
 * 单日超过500单查询量，建议接入我方物流轨迹订阅推送接口
 *
 * ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
 */
public class KdniaoTrackQueryAPI {
    String requestData;


    //电商ID
    private String EBusinessID="1602424";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey="3f42bcfc-b6c5-41b5-9a06-f0e533cd9c40";
    //请求url
    private String ReqURL="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";


    /**
     * Json方式 查询订单物流轨迹
     * @throws Exception
     */
    public void getOrderTracesByJson(String expNo) throws Exception {
        String requestOrder= "{'LogisticCode':'" + expNo + "'}";
        String dataOrder = encrypt(requestOrder,AppKey,"UTF-8");

        EasyHttp.post(ReqURL)
                .params("RequestData", urlEncoder(requestOrder, "UTF-8"))
                .params("EBusinessID", EBusinessID)
                .params("RequestType", "2002")
                .params("DataSign", urlEncoder(dataOrder, "UTF-8"))
                .params("DataType", "2")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.getInstance().showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("aaaaaaaaaaaa",s);
                        KdApiOrderBean OrderBean = GsonBuildUtil.fromJson(s, KdApiOrderBean.class);

                        if (OrderBean.isSuccess()) {
                            try {
                                String requestData= "{'OrderCode':'','ShipperCode':'" +
                                        OrderBean.getShippers().get(0).getShipperCode() + "','LogisticCode':'" + expNo + "'}";
                                String dataSign=encrypt(requestData, AppKey, "UTF-8");
                                EasyHttp.post(ReqURL)
                                        .params("RequestData", urlEncoder(requestData, "UTF-8"))
                                        .params("EBusinessID", EBusinessID)
                                        .params("RequestType", "1002")
                                        .params("DataSign", urlEncoder(dataSign, "UTF-8"))
                                        .params("DataType", "2")
                                        .execute(new SimpleCallBack<String>() {
                                            @Override
                                            public void onError(ApiException e) {

                                            }

                                            @Override
                                            public void onSuccess(String s) {
                                                Log.d("RequestData", s);
                                                LogisticsInfoBean bean = GsonBuildUtil.fromJson(s, LogisticsInfoBean.class);
                                                if (bean.isSuccess()) {
                                                    Event<String> event = new Event<String>(START_CAMERA_RESULT, s);
                                                    EventBusUtil.sendEvent(event);
                                                }else {
                                                    ToastUtil.getInstance().showToast("快递信息查询失败！");
                                                }
                                            }
                                        });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            ToastUtil.getInstance().showToast("快递公司查询失败");
                        }
                    }
                });

        //根据公司业务处理返回的信息......

    }

    /**
     * MD5加密
     * @param str 内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     * @param str 内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     * @param content 内容
     * @param keyValue Appkey
     * @param charset 编码方式
     * @throws UnsupportedEncodingException ,Exception
     * @return DataSign签名
     */
    @SuppressWarnings("unused")
    private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
    {
        if (keyValue != null)
        {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    private static char[] base64EncodeChars = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/' };

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
}

