package com.shenzhen.honpe.honpe_sqe.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * @ProjectName: Honpe
 * @CreateDate: 2020/7/6 12:30
 * @Author: 李熙祥
 * @Description: java类作用描述 IOS进度工具
 */
public class ProgressUtils {
    private static KProgressHUD hud ;

    /**
     * 显示加载中
     * status 0 表示  不显示
     * **/

    public static void disLoadView(Context context, int status){
        if(hud != null && status == 0){
            hud.dismiss();
        }else if(status ==1){
            hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setLabel("正在加载...");
            try{
                hud.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
