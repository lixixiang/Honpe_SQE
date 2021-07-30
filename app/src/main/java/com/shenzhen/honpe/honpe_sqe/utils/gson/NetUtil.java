package com.shenzhen.honpe.honpe_sqe.utils.gson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;

import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: NetUtil
 * Author: asus
 * Date: 2021/3/10 13:41
 * Description:与网络有关的工具
 */
public class NetUtil {
    private static String PREFIX_UNICODE = "\\u";

    public static String ascii2Native(String str) {
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int index = str.indexOf(PREFIX_UNICODE);
        while (index != -1) {
            sb.append(str.substring(begin, index));
            sb.append(ascii2Char(str.substring(index, index + 6)));
            begin = index + 6;
            index = str.indexOf(PREFIX_UNICODE, begin);
        }
        sb.append(str.substring(begin));
        return sb.toString();
    }

    private static char ascii2Char(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
        }
        if (!PREFIX_UNICODE.equals(str.substring(0, 2))) {
            throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        // 将十六进制转为十进制
        int code = Integer.parseInt(tmp, 16) << 8; // 转为高位，后与地位相加
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16); // 与低8为相加
        return (char) code;
    }

    /**
     * 用于上传图片给 web 端
     *
     * @param path
     * @return
     */
    public static String BitMapIconToString(String path) {
        try {
            Bitmap cameraBitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, 0, byteArray.length, Base64.NO_WRAP);
        } catch (IOException e) {
            ToastUtil.getInstance().showToast(e.getMessage());
            return e.getMessage();
        }
    }

    public static String URIIconToString(Uri uri){
        try {
            Bitmap cameraBitmap = BitmapFactory.decodeStream(MyApplication.getContext().getContentResolver().openInputStream(uri));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, 0, byteArray.length, Base64.NO_WRAP);
        } catch (Exception e) {
            ToastUtil.getInstance().showToast(e.getMessage());
            return e.getMessage();
        }
    }

    public static String BitToString(Bitmap bitmap){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, 0, byteArray.length, Base64.NO_WRAP);
        } catch (Exception e) {
            ToastUtil.getInstance().showToast(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * 字符串转义特殊处理
     *
     * @param str 源字符串
     * @param sr  替换成哪个字符
     * @param sd  需要替换的字符
     * @return 目标字符串
     */
    public static String stringReplace(String str, String sr, String sd) {
        String regEx = sr;
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        str = m.replaceAll(sd);
        return str;
    }

    /**
     * 字符串相对路径转 bitMap
     *
     * @return
     */
    public static Bitmap strPathToBitMap(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 得到服务器返回的值
     */
    public static String strContent(String s) throws JSONException {
        JSONObject jsonObject = null;
        jsonObject = new JSONObject(s);
        return jsonObject.getString("value");
    }

    public static void NewVersionRequest(Context context) {
        EasyHttp.post(Constants.URL + "PostRequestforAppVer")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.getInstance().showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("NewVersionRequest", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            Bundle bundle = new Bundle();
                            String ver = object.getString("key");
                            bundle.putInt("key",Integer.parseInt(ver));
                            bundle.putString("value",object.getString("value"));
                            Event<Bundle> event = new Event<Bundle>(FinalClass.UP_VERSION,bundle);
                            EventBusUtil.sendEvent(event);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}













