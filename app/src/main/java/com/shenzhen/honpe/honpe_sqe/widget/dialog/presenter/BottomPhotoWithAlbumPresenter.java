package com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.adapter.BottomPhotoAlbumAdapter;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.CommodityPresenterInf;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * FileName: BottomPhotoWithAlbumPresenter
 * Author: asus
 * Date: 2021/4/3 10:31
 * Description:相册或照相底部弹框
 */
public class BottomPhotoWithAlbumPresenter implements CommodityPresenterInf {
    /*** 引用上下文*/
    private Activity mContext;
    /*** 弹窗*/
    public MyDialog mBottomSheetDialog;
    /*** 弹窗布局*/
    private View contentView;

    RecyclerView recyclerView;
    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private BottomPhotoAlbumAdapter adapter;
    SupportFragment fragment;
    String PERMISSION_STORAGE_MSG = "请授予权限，否则影响部分使用功能";

    public BottomPhotoWithAlbumPresenter(Activity mContext, SupportFragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
    }

    /**
     * 显示窗口
     */
    @Override
    public void showDialog() {
        mBottomSheetDialog = new MyDialog(mContext, R.style.GoodDialog);
        mBottomSheetDialog.outDuration(200);
        mBottomSheetDialog.inDuration(200);
        //设置铺满
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        //解析视图
        contentView = LayoutInflater.from(mContext).inflate(R.layout.css_recyclerview, null);
        mBottomSheetDialog.setContentView(contentView);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BottomPhotoAlbumAdapter(getData());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Intent takePhotoIntent;
                switch (position) {
                    case 0:
                         takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {
                                mContext.startActivityForResult(takePhotoIntent,FinalClass.START_CAMERA);
                            } else {
                               EasyPermissions.requestPermissions(mContext,PERMISSION_STORAGE_MSG,FinalClass.START_CAMERA,permissions);
                            }
                        break;
                    case 1:
                        takePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        takePhotoIntent.setType("image/*");
                        mContext.startActivityForResult(takePhotoIntent,FinalClass.START_ALBUM);
                        break;
                    case 2:
                        break;
                }
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("相册");
        list.add("取消");
        return list;
    }
}























