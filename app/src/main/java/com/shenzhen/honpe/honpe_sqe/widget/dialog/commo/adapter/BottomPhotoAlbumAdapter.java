package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.adapter;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: BottomPhotoAlbumAdapter
 * Author: asus
 * Date: 2021/4/3 10:48
 * Description:
 */
public class BottomPhotoAlbumAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    TextView tv;
    LinearLayout llBackground;

    public BottomPhotoAlbumAdapter(@Nullable List<String> data) {
        super(R.layout.css_text, data);
        addChildClickViewIds(R.id.ll_background);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, String s) {
        tv = holder.getView(R.id.tv);
        llBackground = holder.getView(R.id.ll_background);

        llBackground.setBackgroundResource(R.drawable.selector_gird_white_grey_item);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getContext().getResources().getColor(R.color.black));
        tv.setText(s);
        LatteLogger.d("testPos", holder.getAdapterPosition());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (holder.getAdapterPosition() == 2) {
            params.setMargins(30,10,30,10);
            tv.setTextColor(getContext().getResources().getColor(R.color.google_red));
        }else {
            params.setMargins(30,0,30,0);
        }
        llBackground.setLayoutParams(params);
    }
}





















