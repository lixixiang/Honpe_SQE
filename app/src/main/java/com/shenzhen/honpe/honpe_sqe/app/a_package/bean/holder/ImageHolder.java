package com.shenzhen.honpe.honpe_sqe.app.a_package.bean.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: ImageHolder
 * Author: asus
 * Date: 2021/1/27 10:43
 * Description:
 */
public class ImageHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.imageView = (ImageView) view;
    }
}
