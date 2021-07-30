package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.TagInfo;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.OnInitSelectedPosition;

import java.util.List;

/**
 * FileName: ProperyTagAdapter
 * Author: asus
 * Date: 2021/2/21 16:06
 * Description:
 */
public class ProperyTagAdapter extends BaseAdapter implements OnInitSelectedPosition {

    private Context mContext;
    private List<TagInfo> mDataList;

    public ProperyTagAdapter(Context mContext, List<TagInfo> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        TagInfo tagInfo = mDataList.get(position);
        textView.setEnabled(false);
        textView.setFocusable(false);
        textView.setClickable(false);
        textView.setText(tagInfo.getText());
        view.setTag(tagInfo);
        return view;
    }


    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return true;
        }
        return false;
    }

}
