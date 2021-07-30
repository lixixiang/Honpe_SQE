package com.shenzhen.honpe.honpe_sqe.api;

/**
 * FileName: Constants
 * Author: asus
 * Date: 2021/1/20 15:13
 * Description:
 */
public class Constants {
    //http://192.168.18.57:23155/WebServiceAPI.asmx/UploadFile
    public static final String TEST_URL="http://192.168.18.22:23155/WebServiceAPI.asmx/";
    public static final String URL ="http://api.honpe.com:8999/WebServiceAPI.asmx/";
    public static final String U = "http://192.168.18.57:23155/WebServiceAPI.asmx/UploadFile";
    public static final String DownLoad = "http://api.honpe.com:81/honpeapp/supplier.apk";

    public static String token = "?access_token=";

    //获取token地址 用于识别银行卡 百度云
    public static final String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
    public static final String API_KEY = "5LPzIhuet6gLb1C9we2PWIVZ";
    public static final String SECRET_KEY = "rHQVkpGGitWzgtlz5PrxZSUZGWA2DBdc";
    public static final String BANK_CARD = "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard"+token;

}
