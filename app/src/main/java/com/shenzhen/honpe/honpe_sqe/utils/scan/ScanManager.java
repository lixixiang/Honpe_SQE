package com.shenzhen.honpe.honpe_sqe.utils.scan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;

import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.bertsir.zbar.view.ScanLineView;

import static com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment.TAG;

/**
 * FileName: ScanManager
 * Author: asus
 * Date: 2021/3/28 10:14
 * Description:
 */
public class ScanManager {
    public static void startScan(Activity activity, int scan_type, int scan_view_type) {
        int screen = QrConfig.SCREEN_SENSOR;
        int line_style = ScanLineView.style_line;
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("将二维码/条形码放入框内，即可自动扫描")//扫描框下文字
                .setShowDes(true)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setNeedCrop(true)//是否从相册选择后裁剪图片
                .setCornerColor(MyApplication.getContext().getResources().getColor(R.color.green))//设置扫描框颜色
                .setLineColor(MyApplication.getContext().getResources().getColor(R.color.green))//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(scan_type)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(scan_view_type)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_EAN13)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setDingPath(R.raw.qrcode)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText(MyApplication.getContext().getString(R.string.scan))//设置Tilte文字
                .setTitleSize(Utils.getFontSize(50))
                .setContentSize(Utils.getFontSize(40))
                .setTitleBackgroudColor(Color.parseColor("#262020"))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .setShowZoom(true)//是否开始滑块的缩放
                .setAutoZoom(true)//是否开启自动缩放(实验性功能，不建议使用)
                .setFingerZoom(false)//是否开始双指缩放
                .setDoubleEngine(false)//是否开启双引擎识别(仅对识别二维码有效，并且开启后只识别框内功能将失效)
                .setScreenOrientation(screen)//设置屏幕方式
                .setOpenAlbumText("选择要识别的图片")//打开相册的文字
                .setLooperScan(false)//是否连续扫描二维码
                .setLooperWaitTime(5000)//连续扫描间隔时间
                .setScanLineStyle(line_style)//扫描线样式
                .setAutoLight(false)//自动灯光
                .setShowVibrator(true)//是否震动提醒
                .create();

        QrManager.getInstance().init(qrConfig).startScan(activity,result ->{
            if (result.getContent().contains("http")) {
                Uri content = Uri.parse(result.getContent());
                Intent intent = new Intent(Intent.ACTION_VIEW,content);
                activity.startActivity(intent);
            }else {
                LatteLogger.e(TAG, "onScanSuccess: " + result);
                ToastUtil.getInstance().showToast("内容：" + result.getContent()
                        + "  类型：" + result.getType());
            }
        });
    }
}
