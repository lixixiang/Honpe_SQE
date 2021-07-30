package com.shenzhen.honpe.honpe_sqe.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;

/**
 * FileName: ToastUtil
 * Author: asus
 * Date: 2021/1/20 11:36
 * Description:
 */
public class ToastUtil {
    private volatile static ToastUtil newInstance = null;

    private ToastUtil() {
    }

    private Toast toast;
    private TextView mTvToast;

    public static ToastUtil getInstance() {
        if (newInstance == null) {
            synchronized (ToastUtil.class) {
                if (newInstance == null) {
                    newInstance = new ToastUtil();
                }
            }
        }
        return newInstance;
    }

    public void showToast(String text) {
        if (toast == null) {
            toast = new Toast(MyApplication.getContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            View root = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.layout_custom_toast, null);
            mTvToast = root.findViewById(R.id.toast_text);
            mTvToast.setText(text);
            toast.setView(root);
        }
        mTvToast.setText(text);
        toast.show();
    }

    public void showLongToast(String text) {
        if (toast == null) {
            toast = new Toast(MyApplication.getContext());
            toast.setDuration(Toast.LENGTH_LONG);
            View root = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.layout_custom_toast, null);
            mTvToast = root.findViewById(R.id.toast_text);
            mTvToast.setText(text);
            toast.setView(root);
        }
        mTvToast.setText(text);
        toast.show();
    }

    public void showToast(int stringId){
        showToast(stringId+"");
    }
}


