package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs.bean.LogisticsInfoBean;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FileName: MessListAdapter
 * Author: asus
 * Date: 2021/3/28 11:15
 * Description:快递显示适配器
 */
public class MessListAdapter extends BaseAdapter {
    private List<LogisticsInfoBean.TracesBean> allContent;
    private Context context;
    private LayoutInflater layoutInflater;

    public MessListAdapter(Context context, List<LogisticsInfoBean.TracesBean> allContent) {
        this.allContent = allContent;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allContent.size();
    }

    @Override
    public Object getItem(int position) {
        return allContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_logistics_info, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogisticsInfoBean.TracesBean bean = allContent.get(position);
        holder.tvStatus.setText(bean.getAcceptStation());
        holder.tvTime.setText(bean.getAcceptTime());

        if (position == 0) {
            //绿色的圆点
            FrameLayout.LayoutParams pointParams = new FrameLayout.LayoutParams(
                    Utils.dp2px(context,20), Utils.dp2px(context,20));
            pointParams.gravity = Gravity.CENTER_HORIZONTAL;
            pointParams.setMargins(0, 30, 0, 0);
            holder.ivStatus.setLayoutParams(pointParams);

            holder.ivStatus.setImageResource(R.drawable.shape_circle_logistics_green);
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.green));
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));

        } else {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    Utils.dp2px(context,10), Utils.dp2px(context,10));
            lp.setMargins(0, 30, 0, 0);
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            holder.ivStatus.setLayoutParams(lp);
            holder.ivStatus.setImageResource(R.drawable.shape_circle_logistics_gray);
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.grey));
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.grey));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
