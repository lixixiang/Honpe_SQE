package com.shenzhen.honpe.honpe_sqe.app.e_package.downloadqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;

import butterknife.BindView;
import butterknife.OnLongClick;
import cn.bertsir.zbar.utils.QRUtils;

/**
 * FileName: DownloadQRCodeFragment
 * Author: asus
 * Date: 2021/3/28 17:54
 * Description:
 */
public class DownloadQRCodeFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout homeBack;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_version_num)
    TextView tvVersionNum;
    /**
     * 生成的二维码图片存储的URI
     */
    private Uri imageFileUri;
    private Bitmap qrCode;

    public static DownloadQRCodeFragment newInstance() {
        DownloadQRCodeFragment fragment = new DownloadQRCodeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_download_qr;
    }

    @Override
    protected void initView() {
        initToolbarNav(homeBack);
        title.setText("关于红品app");

        tvVersionNum.setText("当前版本号:V" + Utils.getVersionDes(_mActivity));
        qrCode = QRUtils.getInstance().createQRCodeAddLogo(Constants.DownLoad, 300, 300,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_honpe));
        ivQrCode.setImageBitmap(qrCode);
    }

    @OnLongClick(R.id.iv_qr_code)
    boolean img_code() {
        imageFileUri = Utils.BitmapToUri(getContext(), qrCode);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageFileUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
        return true;
    }
}
