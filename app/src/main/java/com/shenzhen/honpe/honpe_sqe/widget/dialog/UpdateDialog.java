package com.shenzhen.honpe.honpe_sqe.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.widget.NumberProgressBar;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.base.RxDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.math.BigDecimal;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.REQUEST_CODE_APP_INSTALL;


/**
 * FileName: UpdateDialog
 * Author: asus
 * Date: 2020/8/16 13:00
 * Description: 检测更新对话框
 */
public class UpdateDialog extends RxDialog implements View.OnClickListener {

    TextView tvTitle,tvDes,tvProgressNumBer,tvProgressPercent;
    Button btnVersionDialogCommit;
    NumberProgressBar numberProgressBar;
    LinearLayout llDownload;
    ImageView icUpdateCancel;
    Disposable disposable; //网络请求会返回Disposable对象，方便取消网络请求
    Bundle bundle;
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;
    private long oldBytesRead;

    public UpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }


    public UpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public UpdateDialog(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
        initView();
    }


    /**
     * @param context
     * @param alpha   透明度 0.0f--1f(不透明)
     * @param gravity 方向(Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT)
     */
    public UpdateDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sure_false, null);
        tvTitle = view.findViewById(R.id.tv_title);
        tvDes = view.findViewById(R.id.tv_des);
        tvProgressNumBer = view.findViewById(R.id.tv_progress_number);
        tvProgressPercent = view.findViewById(R.id.tv_progress_percent);
        btnVersionDialogCommit = view.findViewById(R.id.btn_version_dialog_commit);
        numberProgressBar = view.findViewById(R.id.number_progress_bar);
        llDownload = view.findViewById(R.id.ll_download);
        icUpdateCancel = view.findViewById(R.id.ic_update_cancel);
        btnVersionDialogCommit.setOnClickListener(this);
        icUpdateCancel.setOnClickListener(this);
        String ver = bundle.getString("value");
        tvTitle.setText("检测到新版本号:"+ver);
        tvDes.setText(ver);
        setContentView(view);
    }

    private void getRequestDownload() {
        disposable = EasyHttp.downLoad(Constants.DownLoad)
                .savePath(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/test/")
                .saveName("honpe.apk")
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void onStart() {
                        HttpLog.i("======" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.getInstance().showToast(e.getMessage());
//                        dismiss();
                        HttpLog.i("======" + e.getMessage());
                    }

                    @Override
                    public void update(final long bytesRead, final long contentLength, final boolean done) {
                        final int progress = (int) (bytesRead * 100 / contentLength);
                        double dProgress = (double) bytesRead / (double) (1024 * 1024);
                        double dMax = (double) contentLength / (double) (1024 * 1024);
                        initFormats();
                        if (mProgressNumberFormat != null) {
                            String format = mProgressNumberFormat;
                            tvProgressNumBer.setText(String.format(format, dProgress, dMax));
                        } else {
                            tvProgressNumBer.setText("");
                        }
                        if (oldBytesRead != 0) {
                            long NetWorkSpeek = bytesRead - oldBytesRead;
                            double newSpeek = (double) NetWorkSpeek / (double) (1024);
                            BigDecimal bg = new BigDecimal(newSpeek);
                            newSpeek = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            LatteLogger.d("NetWorkSpeek", NetWorkSpeek + "  " + bytesRead + "  " + oldBytesRead + "  " + newSpeek);
                            tvProgressPercent.setText(String.valueOf(newSpeek) + "KB/s");
                        }
                        oldBytesRead = bytesRead;
                        LatteLogger.d("progress", progress + "    " + String.valueOf(contentLength) + "   " + done);
                        numberProgressBar.setProgress((int) progress);
                    }

                    @Override
                    public void onComplete(String path) {
                        LatteLogger.d("path", path);
                        Event<String> event = new Event<String>(REQUEST_CODE_APP_INSTALL, path);
                        EventBusUtil.sendEvent(event);
                        dismiss();
                    }
                });
    }

    private void initFormats() {
        mProgressNumberFormat = "%1.2fM/%2.2fM";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_version_dialog_commit:
                llDownload.setVisibility(View.VISIBLE);
                btnVersionDialogCommit.setVisibility(View.GONE);
                getRequestDownload();
                break;
            case R.id.ic_update_cancel: //取消网络请求
                EasyHttp.cancelSubscription(disposable);
                dismiss();
                break;
        }
    }
}






