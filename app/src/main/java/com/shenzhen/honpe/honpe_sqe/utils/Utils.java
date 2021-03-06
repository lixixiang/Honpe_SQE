package com.shenzhen.honpe.honpe_sqe.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jessyan.autosize.utils.ScreenUtils;

import static com.shenzhen.honpe.honpe_sqe.MyApplication.getContext;


/**
 * created by lxx at 2019/11/12 10:59
 * ??????:
 */
public class Utils {
    private static Map<String, BaseBackFragment> fragmentList = new HashMap<>();
    private static InputMethodManager imm;

    /**
     * ??????Class??????Fragment
     *
     * @param clazz the Fragment of create
     * @return
     */
    public static BaseBackFragment createFragment(Class<?> clazz, boolean isObtain) {
        BaseBackFragment resultFragment = null;
        String className = clazz.getName();
        if (fragmentList.containsKey(className)) {
            resultFragment = fragmentList.get(className);
        } else {
            try {
                try {
                    resultFragment = (BaseBackFragment) Class.forName(className).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (isObtain)
                fragmentList.put(className, resultFragment);
        }

        return resultFragment;
    }

    public static BaseBackFragment createFragment(Class<?> clazz) {
        return createFragment(clazz, true);
    }


    /**
     * ??????edit hint ??????
     *
     * @param edit
     * @param hintSize
     * @param content  ?????????????????????
     */
    public static void setEditTextHint(EditText edit, int hintSize, String content) {
        //??????hint ??????
        SpannableString ss = new SpannableString(content);
        AbsoluteSizeSpan as = new AbsoluteSizeSpan(hintSize, true);//?????????????????? true???????????????sp
        ss.setSpan(as, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edit.setHint(new SpannableString(ss));
    }
    /**
     * ??????edit hint ??????
     *
     * @param edit
     * @param hintSize
     * @param content  ?????????????????????
     */
    public static void setEditTextHint(TextView edit, int hintSize, String content) {
        //??????hint ??????
        SpannableString ss = new SpannableString(content);
        AbsoluteSizeSpan as = new AbsoluteSizeSpan(hintSize, true);//?????????????????? true???????????????sp
        ss.setSpan(as, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edit.setHint(new SpannableString(ss));
    }

    /**
     * ??????radioButton ???????????????????????????
     *
     * @param context  ?????????
     * @param button   ??????
     * @param icon     ??????
     * @param left     ????????????
     * @param top      ????????????
     * @param right    ????????????
     * @param bottom   ????????????
     * @param position ??????????????? 0 ??? 1 ??? 2 ??? 3 ???
     */
    public static void setRadioIconSize(Context context, RadioButton button, int icon, int left, int top, int right, int bottom, int position) {
        //???????????????????????????????????????
        Drawable drawable_news = context.getResources().getDrawable(icon);
        //?????????????????????????????????????????????????????? ltrb??????????????????
        drawable_news.setBounds(left, top, right, bottom);
        //????????????????????????????????????
        if (position == 0) {
            button.setCompoundDrawables(drawable_news, null, null, null);
        } else if (position == 1) {
            button.setCompoundDrawables(null, drawable_news, null, null);
        } else if (position == 2) {
            button.setCompoundDrawables(null, null, drawable_news, null);
        } else if (position == 3) {
            button.setCompoundDrawables(null, null, null, drawable_news);
        }
    }

    /**
     * dp???dip
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5F);
    }

    /**
     * ???px????????????dip???dp??????????????????????????????
     *
     * @param pxValue ???DisplayMetrics????????????density???
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * ???px????????????sp??????????????????????????????
     *
     * @param pxValue ???DisplayMetrics????????????scaledDensity???
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * ???sp????????????px??????????????????????????????
     *
     * @param spValue ???DisplayMetrics????????????scaledDensity???
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * ?????????????????????
     *
     * @param context
     * @return
     */
    public static String getPhoneSize(final Context context) {
        DisplayMetrics dm;
        dm = context.getResources().getDisplayMetrics();

       /* int densityDPI = dm.densityDpi; // ??????????????????????????????120/160/240/320???
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;*/
        int screenWidth = dm.widthPixels; // ???????????????????????????480px???
        int screenHeight = dm.heightPixels; // ???????????????????????????800px???

        return screenWidth + "*" + screenHeight;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public static float getPhoneWith() {
        DisplayMetrics outsize = getContext().getResources().getDisplayMetrics();
        return outsize.widthPixels;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public static float getPhoneHeight() {
        DisplayMetrics outsize = getContext().getResources().getDisplayMetrics();
        return outsize.heightPixels;
    }


    /**
     * ????????????????????????
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * ???????????????
     *
     * @return
     */
    public static int getWindowHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * ?????????????????????????????? yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(System.currentTimeMillis());
    }

    //????????????
    public static String long2String(long time) {
        //????????????
        int sec = (int) time / 1000;
        int min = sec / 60;    //??????
        sec = sec % 60;        //???
        if (min < 10) {    //?????????0
            if (sec < 10) {    //??????0
                return "0" + min + ":0" + sec;
            } else {
                return "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {    //??????0
                return min + ":0" + sec;
            } else {
                return min + ":" + sec;
            }
        }
    }

    /**
     * ???????????????????????????
     *
     * @param ms
     * @return
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "d");
        }
        if (hour > 0) {
            sb.append(hour + "h");
        }
        if (minute > 0) {
            sb.append(minute + "???");
        }
        if (second > 0) {
            sb.append(second + "???");
        }
        return sb.toString();
    }

    /**
     * ???????????????
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {//???????????????(???????????????)
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ?????????????????????
     *
     * @param context
     * @param prefName
     * @param value
     */
    public static void putAppPrefInt(Context context, String prefName, int value) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt(prefName, value);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param context
     * @return
     */
    public static String getVersionDes(Context context) {//???????????????
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ???????????????SDCard
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static void showSoftInput(Context context, View view) {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isShowSoftInput(Context context) {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //??????????????????
        return imm.isActive();//true ??????
    }

    public static void hideSoftKeyBoard(Activity activity) {
        View localView = activity.getCurrentFocus();
        if (imm == null) {
            imm = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (imm != null)) {
            imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    //file???uri:URI uri = file.toURI();
//uri???file:file = new File(new URI(uri.toString()));

    /**
     * file ??? uri
     */
    public static URI FileToUri(File file) {
        return file.toURI();
    }

    /**
     * uri ??? file
     */
    public static File UriToFile(Uri uri) {
        File FileToUri = null;
        try {
            FileToUri = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return FileToUri;
    }

    /**
     * bitmap ??? uri ????????????
     */
    public static Uri BitmapToUri(Context context, Bitmap bitmap) {
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));
        return uri;
    }

    public static Bitmap UriToBitmap(Context context, Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ImageView ?????? bitmap
     */
    public static Bitmap ImageViewToBitmap(ImageView iv){
        return ((BitmapDrawable) iv.getDrawable()).getBitmap();
    }

    /**
     * String?????????Bitmap
     */
    public static Bitmap stringToBitmap(String string) {
        // ?????????????????????Bitmap??????
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Bitmap?????????String
     */

    public static String bitmapToString(Bitmap bitmap){
        //???Bitmap??????????????????
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string=Base64.encodeToString(bytes,Base64.DEFAULT);
        return string;
    }

    /**
     * @param array
     * @return ???????????????
     */
    public static String[] ListToString(List<?> array) {
        return array.toArray(new String[array.size()]);
    }

    /**
     * ??????????????????
     *
     * @param child
     * @return
     */
    public static float getRealHeight(View child) {
        LinearLayout llayout = (LinearLayout) child;
        ViewGroup.LayoutParams params = llayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, params.width);
        int heightSpec;
        if (params.height > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        llayout.measure(widthSpec, heightSpec);
        return llayout.getMeasuredHeight();//???????????????????????????????????????????????????getMeasuredWidth()
    }

    public static float getRealHeight2(View child) {
        FrameLayout llayout = (FrameLayout) child;
        ViewGroup.LayoutParams params = llayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, params.width);
        int heightSpec;
        if (params.height > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        llayout.measure(widthSpec, heightSpec);
        return llayout.getMeasuredHeight();//???????????????????????????????????????????????????getMeasuredWidth()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ???????????????
     */
    public static void screenImageView(ImageView icon) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        icon.setLayoutParams(params);
    }

    /**
     * ???????????????2
     */
    public static void screenImageView(ImageView icon, int dimen) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimen, getContext().getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        icon.setLayoutParams(params);
    }

    /**
     * ?????????TotalQueryActivity
     */
//    public static void start(Activity cls, int index, String msg) {
//        Intent intent = new Intent(getContext(), TotalQueryActivity.class);
//        intent.putExtra("tag", index);
//        intent.putExtra("title", msg);
//        cls.startActivity(intent);
//    }


//    public static void start(Activity cls, Bundle bundle) {
//        Intent intent = new Intent(getContext(), TotalQueryActivity.class);
//        intent.putExtras(bundle);
//        cls.startActivity(intent);
//    }


    /**
     * ??????
     */
    public static void reverse(List<?> list) {
        Collections.reverse(list);
    }



    public static void removeParent(View v) {
        //  ???????????? ???????????????????????????
        ViewParent parent = v.getParent();
        //??????????????? ?????????  ?????????????????? ??????ViewGoup
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(v);
        }
    }

    public static Integer[] getWidthAndHeight(Window window) {
        if (window == null) {
            return null;
        }
        Integer[] integer = new Integer[2];
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        } else {
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        integer[0] = dm.widthPixels;
        integer[1] = dm.heightPixels;
        return integer;
    }

    /**
     * ?????????????????????
     *
     * @param
     * @return
     */
    public static boolean checkPackInfo(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * TextView Size ????????????
     */
    public static void TextSize(TextView tv, int size) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getFontSize(size));
    }

    /**
     * ????????????
     *
     * @param textSize
     * @return
     */
    public static int getFontSize(int textSize) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        int rate = (int) (textSize * (float) screenHeight / 1920);
        return rate;
    }

    /**
     * ?????????????????????????????????
     *
     * @param et
     * @param click
     * @return
     */
    public static void editIsEnable(EditText et, boolean click) {
        et.setFocusable(click);
        et.setFocusableInTouchMode(click);
        et.setEnabled(click);
    }

    public static int getNavigationBarHeight(Context context) {
        boolean mInPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar((Activity) context)) {
                String key;
                if (mInPortrait) {
                    key = "navigation_bar_height";
                } else {
                    key = "navigation_bar_height_landscape";
                }
                return getInternalDimensionSize(context, key);
            }
        }
        return result;
    }

    private static boolean hasNavBar(Activity activity) {
        //??????????????????????????????????????????,????????????????????????false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(activity.getContentResolver(), "force_fsg_nav_bar", 0) != 0) {
                return false;
            }
        }
        //????????????????????????????????????????????????????????????????????????
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics);
        }

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    private static int getInternalDimensionSize(Context context, String key) {
        int result = 0;
        try {
            int resourceId = context.getResources().getIdentifier(key, "dimen", "android");
            if (resourceId > 0) {
                result = Math.round(context.getResources().getDimensionPixelSize(resourceId) *
                        Resources.getSystem().getDisplayMetrics().density /
                        context.getResources().getDisplayMetrics().density);
            }
        } catch (Resources.NotFoundException ignored) {
            return 0;
        }
        return result;
    }


    /**
     * ?????????????????????????????????  isHeight=true?????????????????????????????????isHeight=false??????????????????????????????
     * @param view
     * @param isHeight
     * @return
     */
    public static int getViewHeight(View view, boolean isHeight){
        int result;
        if(view==null)return 0;
        if(isHeight){
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(h,0);
            result =view.getMeasuredHeight();
        }else{
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            view.measure(0,w);
            result =view.getMeasuredWidth();
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setCheckBoxColor(CheckBox ck,int color){
        ck.setButtonTintList(ColorStateList.valueOf(MyApplication.getContext().getResources().getColor(color)));
    }
}


