package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.guid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.shenzhen.honpe.honpe_sqe.MainActivity;

import static com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment.TOUCH_TIME;
import static com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment.WAIT_TIME;

/**
 * FileName: WelcomeActivitiy
 * Author: asus
 * Date: 2021/3/1 11:15
 * Description:
 */
public class WelcomeActivity extends AppCompatActivity {
    public static final int SKIN_GUIDE = 0x001;
    public static final int SKINP_MAIN = 0x002;

    private SharedPreferences sharedPreferences;
    private Thread mThread;
    //判断是否第一次打开该应用
    private boolean flag;

    //此handler用于处理界面的变换（跳转activity）
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SKIN_GUIDE:
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    finish();
                    break;
                case SKINP_MAIN:
                    long nowTime = System.currentTimeMillis();
                    if (nowTime - TOUCH_TIME > WAIT_TIME) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        TOUCH_TIME = nowTime;
                        finish();
                    } else {
                        //  ToastUtils.getInstance().showToast("不要重复点击，手机会爆的呢-^-");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        /**
         * context.getgetSharedPreferences(String name, int mode) 获取sharedPreference对象
         * name是sharedPreference生成的xml文件的名字
         */
        sharedPreferences = getSharedPreferences("FirstIn",MODE_PRIVATE);
        mThread = new Thread(runnable);
        mThread.start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                //getBoolean(String key, boolean defValue) 获取键isFirst的值，若无此键则获取默认值（第一次打开程序的时候没有isFirst这个键）
                flag = sharedPreferences.getBoolean("isFirst", true);
                Message msg = handler.obtainMessage();
                if(flag){
                    //Editor对象用于修改sharedpreference对象,修改完后必须提交事务，才能完成修改（参考数据库的事务处理）
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirst", false);
                    editor.commit();
                    msg.what = SKIN_GUIDE;
                }else{
                    msg.what = SKINP_MAIN;
                }
                //休眠2s后，将信息发给handler，由handler来跳转activity
                Thread.sleep(1000);
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}




