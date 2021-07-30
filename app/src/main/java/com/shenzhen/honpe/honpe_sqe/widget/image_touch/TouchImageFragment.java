package com.shenzhen.honpe.honpe_sqe.widget.image_touch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.imagepicker.view.PinchImageView;

import butterknife.BindView;

/**
 * FileName: TouchImageFragment
 * Author: asus
 * Date: 2021/3/13 15:23
 * Description:手势放大缩小图片
 */
public class TouchImageFragment extends BaseBackFragment {
    @BindView(R.id.re_back)
    LinearLayout reBack;
    @BindView(R.id.iv_main_play)
    PinchImageView ivMainPlay;
    private Bundle bundle;

    public static TouchImageFragment newInstance(String strBit) {
        TouchImageFragment fragment = new TouchImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("strBit", strBit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_single_pre_image;
    }

    @Override
    protected void initView() {
        initToolbarNav(reBack);
        bundle = getArguments();
        String path = bundle.getString("strBit");
        LatteLogger.d("IMAGE", path);
        Bitmap cameraBitmap = BitmapFactory.decodeFile(path);
        // 给图片设置触摸事件
        ivMainPlay.setImageBitmap(cameraBitmap);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(mActivity).statusBarColor(R.color.tool_bar_color).fitsSystemWindows(true).init();
    }
}








