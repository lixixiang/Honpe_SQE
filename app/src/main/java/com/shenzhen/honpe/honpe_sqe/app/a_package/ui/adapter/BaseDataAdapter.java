package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.adapter;

import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.BaseProgressAdapter;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.base.BaseEditDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.base.BaseProgressDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: BaseDataAdapter
 * Author: asus
 * Date: 2021/2/26 9:35
 * Description:
 */
public class BaseDataAdapter extends BaseQuickAdapter<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO, BaseViewHolder> {
    private List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> data;
    private String title,label;
    private TextView itemTvProgress;
    LinearLayout llNote;
    ClickableSpan clickableSpan;

    public BaseDataAdapter(@Nullable List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> data, String title) {
        super(R.layout.item_child_node, data);
        this.data = data;
        this.title = title;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO bean) {
        holder.setText(R.id.item_material, bean.get物品名称());
        holder.setText(R.id.item_size, bean.get规格());
        holder.setText(R.id.item_texture, bean.get材质());
        holder.setText(R.id.item_unit, bean.get数量() + "");
        holder.setText(R.id.item_note, bean.get备注());
        llNote = holder.getView(R.id.ll_note);
        itemTvProgress = holder.getView(R.id.item_tv_progress);
        if ("出货查询".equals(title)||"退货记录".equals(title)) {
            if ("".equals(bean.getJindu())) {
                itemTvProgress.setVisibility(View.GONE);
            } else {
                label ="登记进度：" + bean.getJindu();
                StringUtil.changeStrClick(label,clickableSpan,itemTvProgress,5,label.length());
            }
        }else {
            itemTvProgress.setVisibility(View.VISIBLE);
            if ("".equals(bean.getJindu())) { // 没有进度 来登记进度
                label = "登记进度";
                StringUtil.changeStrClick(label, clickableSpan, itemTvProgress, 0, 4);
            } else {
                label = "登记进度：" + bean.getJindu();
                StringUtil.changeStrClick(label, clickableSpan, itemTvProgress, 5, label.length());
            }
        }

        itemTvProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(bean.getJindu())) {
                    BaseEditDialog dialog = new BaseEditDialog(getContext(),"登记最新进度","");
                    dialog.showDialog();
                    dialog.setOnClickData(new BaseEditDialog.InputData() {
                        @Override
                        public void setOnClickData(String strProgress) {
                            if ("出货查询".equals(title)||"退货记录".equals(title)) {

                            }else {
                                LatteLogger.d("testProgressData",strProgress);
                                bean.setJindu(strProgress);
                                data.set(holder.getAdapterPosition()-1, bean);
                                submitProgress(bean);
                                upData(data);
                            }
                        }
                    });
                }else {
                    BaseProgressDialog dialog = new BaseProgressDialog(getContext(),bean);
                    dialog.showDialog();
                    dialog.setOnClickListener(new BaseProgressDialog.SetProgressContent() {
                        @Override
                        public void setOnClick(String content) {
                            bean.setJindu(content);
                            data.set(holder.getAdapterPosition() -1, bean);
                            upData(data);
                        }
                    });
                }
            }
        });

        if (!"待报价".equals(title)) {
            holder.setGone(R.id.re_price_tax, false);

            holder.setText(R.id.item_price, StringUtil.formatDouble(Double.valueOf(bean.get单价())));
            holder.setText(R.id.item_tax_rate, String.format("%.0f", bean.get税率() * 100) + "%");
            if ("".equals(bean.get备注()) || " ".equals(bean.get备注())) {
                llNote.setVisibility(View.GONE);
            } else {
                llNote.setVisibility(View.VISIBLE);
                holder.setText(R.id.item_note, bean.get备注());
            }
        } else {
            holder.setGone(R.id.re_price_tax, true);
        }
    }

    private void submitProgress(UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO bean) {
        PersonEntity entity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN,AppConfig.APP_LOGIN);
        EasyHttp.post(Constants.URL + "AddSpeed")
                .retryCount(0)
                .params("strCode", bean.get申请单号())
                .params("strSID", entity.getValue().getSid())
                .params("strLingJian", bean.get零件编码())
                .params("strBaoJiaCode",bean.get报价单号())
                .params("strAddJindu","进度1")
                .params("strContext",bean.getJindu())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (object.getBoolean("key")) {
                                ToastUtil.getInstance().showToast(object.getString("value"));
                            }else {
                                ToastUtil.getInstance().showToast(object.getString("value"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void upData(List<UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
