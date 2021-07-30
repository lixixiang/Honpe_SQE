package com.shenzhen.honpe.honpe_sqe.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.shenzhen.honpe.honpe_sqe.MainActivity;
import com.shenzhen.honpe.honpe_sqe.MainFragment;
import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.shenzhen.honpe.honpe_sqe.MyApplication.getContext;


/**
 * @ProjectName: Honpe
 * @CreateDate: 2020/7/6 13:47
 * @Author: 李熙祥
 * @Description: java类作用描述 字符串工具
 */
public class StringUtil {

    /**
     * 复制字符串
     *
     * @param context
     * @param text
     */
    public static void toCopy(Context context, String text) {
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    public static void openBrowser(String targetUrl) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            Toast.makeText(getContext(), targetUrl + " 该链接无法使用浏览器打开。", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri mUri = Uri.parse(targetUrl);
        intent.setData(mUri);
        getContext().startActivity(intent);
    }


    /**
     * 截取部分字分串
     *
     * @param sourceString
     * @param objects
     * @return
     */
    public static String replace(String sourceString, Object[] objects) {
        String temp = sourceString;
        for (int i = 0; i < objects.length; i++) {
            String[] result = (String[]) objects[i];
            Pattern pattern = Pattern.compile(result[0]);
            Matcher matcher = pattern.matcher(temp);
            temp = matcher.replaceAll(result[1]);
        }
        return temp;
    }

    public static String replaceT(String sourceString) {
        String[][] object = {new String[]{"T", " "}};
        String temp = sourceString;
        for (int i = 0; i < object.length; i++) {
            String[] result = (String[]) object[i];
            Pattern pattern = Pattern.compile(result[0]);
            Matcher matcher = pattern.matcher(temp);
            temp = matcher.replaceAll(result[1]);
        }
        return temp;
    }

    /**
     * 去除数组空元素
     *
     * @param strArray
     * @return
     */
    public static String[] removeArrayEmptyTextBackNewArray(String[] strArray) {
        List<String> strList = Arrays.asList(strArray);
        List<String> strListNew = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i) != null && !strList.get(i).equals("")) {
                strListNew.add(strList.get(i));
            }
        }
        String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
        return strNewArray;
    }

    /**
     * 去除数组中重复的元素
     *
     * @param arr
     * @return
     */
    public static String[] ifRepeart(String[] arr) {
        List<String> list = new ArrayList<>();
        for (String str : arr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        String[] strs = list.toArray(new String[list.size()]);
        return strs;
    }


    //1. 循环list中的所有元素然后删除重复
    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    //2. 通过HashSet踢除重复元素
    public static List removeDuplicate2(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    //3. 删除ArrayList中重复元素，保持顺序
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        System.out.println(" remove duplicate " + list);
    }

    //4.把list里的对象遍历一遍，用list.contain()，如果不存在就放入到另外一个list集合中
    public static List removeDuplicate4(List list) {
        List listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    /**
     * 字符串不区分大小写
     */
    public static boolean igonreCaseEquals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    //返回一个网页 没有带前缀
    public static String[] returnImageUrlsFromHtml2(String data) {
        List<String> imageSrcList = new ArrayList<String>();
        //  String htmlCode = returnExampleHtml();
        String htmlCode = data;
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList", "资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList.toArray(new String[imageSrcList.size()]);
    }

    /**
     * 首行缩进的SpannableString
     *
     * @param label       标签信息
     * @param description 描述信息
     * @param dimen       描述信息  R.dimen.label_size
     */
    private SpannableString getSpannableString(Context context, String label, int dimen, String description) {
        SpannableString spannableString = new SpannableString(description);
        int marginSpanSize = (int) (label.length() * context.getResources().getDimension(dimen)
                + Utils.dp2px(context, 6));//文字宽度+ 背景padding4dp+间隔2dp
        LeadingMarginSpan leadingMarginSpan = new LeadingMarginSpan.Standard(marginSpanSize, 0);//仅首行缩进
        spannableString.setSpan(leadingMarginSpan, 0, description.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 改变字符串某一个字的大小
     * @param label  字符
     * @param type  选择哪种类型
     * @return
     */
    public static SpannableString changeStrColor(String label, int type) {
        SpannableString spannableString = new SpannableString(label);
        if (type == 0) {
            spannableString.setSpan(new AbsoluteSizeSpan(20), label.length() - 2, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new AbsoluteSizeSpan(20), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (type == 1) {
            spannableString.setSpan(new AbsoluteSizeSpan(20), 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF000000")), 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new AbsoluteSizeSpan(30), 6, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0006")), 5, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (type == 2) {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0006")), 7, label.length() - 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (type == 3) {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0006")), 5, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 改变文字是否可以被点击或部分文字可以被点击
     * @return
     */
    public static void changeStrClick(String label, ClickableSpan clickableSpan, TextView tv, int start, int end) {
        SpannableStringBuilder style = new SpannableStringBuilder(label);
        //设置部分文字点击事件
        style.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.blue_dark)), start, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(style);
    }

    /**
     * textView 显示文字某几个字改变颜色
     *
     * @param str   需要修改的字符串
     * @param color 改变字符的颜色
     * @param start 开始位置
     * @param end   结束位置
     */
    public static SpannableString changeFontColor(Context context, String str, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color))
                , start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 是否有邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     *java中double类型如果小数点后为零则显示整数否则保留两位小数
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    public static String format2Double(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    /**
     * 帐号
     * @param account
     * @return
     */
    public static boolean isAccount(String account) {
        String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(account);
        return m.matches();
    }

    /**
     * 是否有密码
     *
     * @param pass
     * @return
     */
    public static boolean isPass(String pass) {
        String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(pass);
        return m.matches();
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    //=========================================================================

    /**
     * 检查数组是否包含某个值
     */
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    public static String useList2(String[] arr, String targetValue) {
        boolean b = Arrays.asList(arr).contains(targetValue);
        if (b) {
            return targetValue;
        } else {
            return "没有这个字符";
        }
    }

    //使用Set
    public static boolean useSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(targetValue);
    }

    //使用循环判断
    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    //查找有序数组中是否包含某个值的用法
    public static boolean useArraysBinarySearch(String[] arr, String targetValue) {
        int a = Arrays.binarySearch(arr, targetValue);
        if (a > 0)
            return true;
        else
            return false;
    }

    //=========================================================================
    public static String returnExampleHtml() {

        return "<img src=\"/uploadfile/image/20170628/20170628155301_17333.jpg\" alt=\"\" />&nbsp;<img src=\"/uploadfile/image/20170628/20170628155301_84385.jpg\" alt=\"\" />";
    }

    /**
     * 代码实现editText提示文字
     */
    public static SpannableString editHint(String hint) {
        SpannableString s = new SpannableString(hint);
        return s;
    }

    /**
     * editText hint文字大小
     *
     * @param et
     * @param content
     */
    public static void HintUtil(EditText et, CharSequence content) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(content);
        // 新建 一个属性对象,设置文字的大小,
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);
        //附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置hint
        et.setHint(new SpannableString(ss)); // 一定要进行转换,否则属性会消失
    }

    public static void HintUtil(TextView et, CharSequence content, int size) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(content);
        // 新建 一个属性对象,设置文字的大小,
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        //附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置hint
        et.setHint(new SpannableString(ss)); // 一定要进行转换,否则属性会消失
    }

    public static void HintUtil(EditText et, CharSequence content, int size) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(content);
        // 新建 一个属性对象,设置文字的大小,
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        //附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置hint
        et.setHint(new SpannableString(ss)); // 一定要进行转换,否则属性会消失
    }

    /**
     * 删除集合中所有重复的元素
     *
     * @param list
     */
    public static void DeleteArraySingleStr(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                    list.remove(i);
                }
            }
        }
    }


    /**
     * 删除集合中重复的元素保留维一一个元素
     *
     * @param list
     */
    public static void DeleteArrayRepeatStr(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
    }

    /**
     * 将数据按照名称分组
     *
     * @param list 需要被改变的json
     * @param name 按照name来提取
     * @return
     */
    public static List group(List<Map> list, String name) {
        List<Map> newList = new ArrayList<>();
        /*
         *第一次外循环与内循环完成以name为比较准则的封装
         */
        for (int i = 0; i < list.size(); i++) {
            //用map接收list中一个键值对
            Map<String, Object> dt_i = list.get(i);
            if (!dt_i.containsKey(name))
                continue;
            String date1 = dt_i.get(name).toString();

            Map<String, Object> res = new HashMap<String, Object>();
            res.put(name, date1);

            //用于存放第i次的比较后的结果
            List lst_1 = new ArrayList();

            for (int j = 0; j < list.size(); j++) {
                Map<String, Object> dt_j = list.get(j);
                if (!dt_j.containsKey(name))
                    continue;
                String date2 = dt_j.get(name).toString();

                if (date1 == date2 || date1.equals(date2)) {

                    //深拷贝当前第j条的json数组
                    Map<String, Object> dt1 = new HashMap<String, Object>();
                    dt1.putAll(dt_j);
                    dt1.remove(name);

                    //将数据按照json的形式存储，方便前端解析
                    String dataJson = new Gson().toJson(dt1);
                    lst_1.add(dataJson);
                }

            }
            res.put("data", lst_1);
            newList.add(res);
        }

        /*
         * 将封装后的结果进行去重复
         */
        for (int i = 0; i < newList.size(); i++) {
            Map<String, Object> dt_i = newList.get(i);
            if (!dt_i.containsKey(name))
                continue;
            String date1 = dt_i.get(name).toString();
            for (int j = newList.size() - 1; j > i; j--) {
                Map<String, Object> dt_j = newList.get(j);
                if (!dt_j.containsKey(name))
                    continue;
                String date2 = dt_j.get(name).toString();
                if (date1 == date2 || date1.equals(date2)) {
                    newList.remove(j);
                }
            }
        }
        return newList;
    }

    //判断Java数组是否包含某个值
    public static boolean findStr(String[] args, String str) {
        boolean result = false;
        //第一种：list
        result = Arrays.asList(args).contains(str);
        //第二种: set
        Set<String> sets = new HashSet<>(Arrays.asList(args));
        result = sets.contains(str);
        //第三种：loop
        for (String s : args) {
            if (s.equals(str)) {
                return true;
            }
        }
        //第四种：binarySearch(Arrays的binarySearch方法必须应用于有序数组)
        int res = Arrays.binarySearch(args, str);
        if (res > 0) {
            return true;
        }
        return result;
    }

    public static String md5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 数组转集合
     */
    public static List<String> ArrToList(String[] strArr) {
        return Arrays.asList(strArr);
    }

    public static List<Integer> ArrToList(Integer[] arr) {
        return Arrays.asList(arr);
    }

    /**
     * 集合转数组
     */
    public static String[] ListToArr(List<String> list) {
        return list.toArray(new String[]{});
    }

    /**
     * 去掉字符串最后一位字任
     *
     * @param str
     * @param index 去掉后面几位字符 0
     * @return
     */
    public static String deleteLastStr(String str, int index) {
        return str.substring(0, str.length() - index);
    }

    /**
     * 字符串转Uri
     * @param str
     * @return
     */
    public static Uri StrToUri(String str){
       return Uri.parse(str);
    }

    /**
     * 去掉换行符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 获得字符串最后一位字符
     *
     * @return
     */
    public static String getLastStr(String str) {
        return str.substring(str.length() - 1, str.length());
    }

    /**
     * 没有订单时
     */
    public static void getUnOrder(TextView tvTitle, TextView tvTips, TextView tvAddOrder, String strTitle, String strTips, String strAdd) {
        tvTitle.setText(strTitle);
        tvTips.setText(strTips);
        tvAddOrder.setText(strAdd);
    }


    // 工具类 绘制不同状态的颜色

    /**
     * 对TextView设置不同状态时其文字颜色
     *
     * @param normal
     * @param pressed
     * @param focused
     * @param unable
     * @return
     */
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 位图 转 字符串
     * Bitmap to String
     */
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);
        byte[] arr = bao.toByteArray();
        String result = Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

    /**
     * 字符串 转 位图
     * String to Bitmap
     */
    public static Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * 取字符串内的数字
     */
    public static String getStringNum(String str){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}


