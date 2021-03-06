package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.JsonObject;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.NetUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.base.BaseEditDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter.CommodityPresenter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * FileName: BaseOrderAdapter
 * Author: asus
 * Date: 2021/2/1 15:08
 * Description:
 */
public class BaseOrderAdapter extends BaseQuickAdapter<UnQuotedBean, BaseViewHolder> {
    RecyclerView childRecyclerView;
    BaseDataAdapter baseChildAdapter;
    LinearLayout llExpand;
    List<UnQuotedBean> data;
    String[][] object = {new String[]{"T", " "}};
    String[][] object2 = {new String[]{"??????", ""}};
    private String curDay, weekday;
    SimpleDateFormat myDay = new SimpleDateFormat("dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat md = new SimpleDateFormat("MM-dd");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    CommodityPresenter presenter;
    private String title, ymdTime, strDeliveryDate;
    Date start;
    PersonEntity personEntity;
    ImageView ivArrow;
    private boolean isOpen = true;
    private String strCancel;
    private JsonObject jsonObject = new JsonObject();
    private SpannableString msg;
    private BaseViewHolder holder;

    public BaseOrderAdapter(@Nullable List<UnQuotedBean> data, String title) {
        super(R.layout.item_root_node, data);
        this.data = data;
        this.title = title;
        personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, UnQuotedBean bean) {
        this.holder = holder;
        if ("??????".equals(title) || "?????????".equals(title) || "?????????".equals(title) || "?????????".equals(title)) {
            ymdTime = StringUtil.replace(bean.get????????????(), object);
            holder.setText(R.id.item_type, bean.get????????????());
            holder.setText(R.id.item_num, bean.get????????????() + "");
            holder.setText(R.id.item_deliver, StringUtil.formatDouble(Double.parseDouble(bean.get????????????())));
        } else if ("????????????".equals(title)) {
            ymdTime = StringUtil.replace(bean.get????????????(), object);
            holder.setText(R.id.item_deliver, md.format(DateUtil.setDate(ymd, bean.get??????????????????())));
        } else if ("????????????".equals(title)) {
            ymdTime = StringUtil.replace(bean.get????????????(), object);
            holder.setText(R.id.item_type, bean.get????????????());
            holder.setText(R.id.item_num, bean.get????????????() + "");
            holder.setText(R.id.item_deliver, Integer.parseInt(bean.get????????????()) == 0 ? "?????????" : "?????????");
        } else if ("????????????".equals(title)) {
            ymdTime = StringUtil.replace(bean.get????????????(), object);
            holder.setText(R.id.item_type, bean.get????????????());
            holder.setText(R.id.item_num, bean.get????????????() + "");
            holder.setText(R.id.item_deliver, Integer.parseInt(bean.get????????????()) == 0 ? "?????????" : "?????????");
        } else if ("?????????".equals(title)) {
            ymdTime = StringUtil.replace(bean.get????????????(), object);
            holder.setText(R.id.item_type, bean.get????????????());
            holder.setText(R.id.item_num, bean.get??????() + "");
            holder.setText(R.id.item_deliver, md.format(DateUtil.setDate(bean.get??????????????????())));
        }
        start = DateUtil.setDate(ymdTime);
        curDay = myDay.format(start);
        weekday = curDay + "/" + StringUtil.replace(DateUtil.dateToWeek(DateUtil.getStringOfDay(start)), object2);
        holder.setText(R.id.item_part_num, weekday);
        holder.setText(R.id.item_order_num, bean.get????????????());


        llExpand = holder.getView(R.id.ll_expand);
        ivArrow = holder.getView(R.id.item_down);
        ivArrow.setColorFilter(Color.BLACK);

        ivArrow.setImageResource(bean.isOpen() ? R.mipmap.ic_down : R.mipmap.ic_up);

        llExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isOpen()) {
                    bean.setOpen(false);
                } else {
                    bean.setOpen(true);
                }
                data.set(holder.getAdapterPosition(), bean);
                addNewsData(data);
            }
        });
        childRecyclerView = holder.getView(R.id.order_recyclerView);
        childRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        childRecyclerView.setVisibility(bean.isOpen() ? View.VISIBLE : View.GONE);
        childRecyclerView.setHasFixedSize(true);
        baseChildAdapter = new BaseDataAdapter(bean.getOfferModel().get???????????????????????????(), title);
        baseChildAdapter.addHeaderView(getHeaderView());
        baseChildAdapter.addFooterView(getFootView(bean, holder));
        childRecyclerView.setAdapter(baseChildAdapter);
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.recylcer_head_layout, childRecyclerView, false);
        return view;
    }

    TextView itemTvStatus, tvDesc, tvContacts, tvEvent;

    private View getFootView(UnQuotedBean bean, BaseViewHolder holder) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_root_foot_node, childRecyclerView, false);
        TextView tvDisagree = view.findViewById(R.id.tv_disagree);
        TextView tvAgree = view.findViewById(R.id.tv_agree);
        TextView itemTotalCash = view.findViewById(R.id.item_total_cash);
        TextView itemHeadRate = view.findViewById(R.id.tv_head_rate);
        TextView itemActualCash = view.findViewById(R.id.item_actual_cash);
        RelativeLayout llRote = view.findViewById(R.id.re_rote);
        LinearLayout llToQuote = view.findViewById(R.id.ll_to_quote);
        LinearLayout llValidity = view.findViewById(R.id.ll_validity);
        TextView itemStartTime = view.findViewById(R.id.item_start_time);
        TextView itemEndTime = view.findViewById(R.id.item_end_time);
        TextView itemOrderWeek = view.findViewById(R.id.item_week_num);
        RelativeLayout reStyle = view.findViewById(R.id.re_style);
        TextView itemClearingWay = view.findViewById(R.id.item_clearing_way);
        TextView itemShippingWay = view.findViewById(R.id.item_shipping_way);
        LinearLayout reStatus = view.findViewById(R.id.re_status);
        LinearLayout ll_rate_total_cash = view.findViewById(R.id.ll_rate_total_cash);
        itemTvStatus = view.findViewById(R.id.item_tv_status);
        tvContacts = view.findViewById(R.id.tv_contacts);
        tvDesc = view.findViewById(R.id.tv_desc);
        tvEvent = view.findViewById(R.id.tv_event);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        if (bean.getOfferModel().get???????????????() != null && !"".equals(bean.getOfferModel().get???????????????())) {
            tvContacts.setText(getContext().getString(R.string.str_company_address) + "\n" + "????????????" + bean.getOfferModel().get???????????????() + "(0755)3393138");
        }
        if (bean.getOfferModel().get?????????????????????() != null && !"".equals(bean.getOfferModel().get?????????????????????())) {
            tvDesc.setText(bean.getOfferModel().get?????????????????????());
        }
        if (bean.getOfferModel().get??????????????????() != null && !"".equals(bean.getOfferModel().get?????????????????????())) {
            tvEvent.setText(bean.getOfferModel().get??????????????????());
        }

        tvEmail.setText("????????????E-mail???" + personEntity.getValue().getEmail());
        reStatus.setVisibility(View.GONE);

        if (bean.get????????????() == 0) { //?????????
            llToQuote.setVisibility(View.GONE);
            tvDisagree.setText("??????");
            tvAgree.setText("??????");
        } else if (bean.get????????????() > 0) { //?????????????????????
            llToQuote.setVisibility(View.VISIBLE);
            tvDisagree.setText("??????");
            tvAgree.setText("??????");
            reStatus.setVisibility(View.VISIBLE);
            if (bean.get????????????() == -1) {//???????????? -1 ??? ??????????????????
                tvAgree.setEnabled(false);
                tvDisagree.setEnabled(false);
                tvDisagree.setBackgroundResource(R.drawable.shape_rectangle_grey_5_lr15_tb5);
                tvAgree.setBackgroundResource(R.drawable.shape_rectangle_grey_5_lr15_tb5);
                itemTvStatus.setText("?????????");
                itemTvStatus.setTextColor(getContext().getResources().getColor(R.color.red));
            } else if (bean.get????????????() == 1) {//1?????????????????????
                tvAgree.setEnabled(false);
                tvDisagree.setEnabled(false);
                tvDisagree.setBackgroundResource(R.drawable.shape_rectangle_grey_5_lr15_tb5);
                tvAgree.setBackgroundResource(R.drawable.shape_rectangle_grey_5_lr15_tb5);
                itemTvStatus.setText("?????????");
                itemTvStatus.setTextColor(getContext().getResources().getColor(R.color.black));
            } else if (bean.get????????????() == 0 && !"????????????".equals(bean.get????????????())) { //???????????? ?????????,?????????????????????
                tvAgree.setEnabled(true);
                tvDisagree.setEnabled(true);
                tvDisagree.setBackgroundResource(R.drawable.selector_btn_red_grey);
                tvAgree.setBackgroundResource(R.drawable.selector_btn_green_grey);
                itemTvStatus.setText("?????????");
                itemTvStatus.setTextColor(getContext().getResources().getColor(R.color.orange));
            }
        } else if (bean.get????????????() == -1) { //?????????
            llToQuote.setVisibility(View.VISIBLE);
            tvDisagree.setVisibility(View.GONE);
            tvAgree.setVisibility(View.GONE);
        }

        if (bean.get????????????() > 0 && !"".equals(bean.get????????????????????????())) {
            llValidity.setVisibility(View.VISIBLE);
            reStyle.setVisibility(View.VISIBLE);
        } else {
            llValidity.setVisibility(View.GONE);
            reStyle.setVisibility(View.GONE);
        }

        if ("?????????".equals(title)) {
            ll_rate_total_cash.setVisibility(View.GONE);
            tvDisagree.setText("??????");
            tvAgree.setText("??????");
        }else {
            ll_rate_total_cash.setVisibility(View.VISIBLE);
        }

        if ("????????????".equals(title) || "????????????".equals(title) || "?????????".equals(title) || "????????????".equals(title) ) {
            llToQuote.setVisibility(View.VISIBLE);
            reStyle.setVisibility(View.GONE);
            reStatus.setVisibility(View.GONE);
            tvDisagree.setVisibility(View.GONE);
            tvAgree.setVisibility(View.GONE);
        }

        itemStartTime.setText(bean.get????????????????????????());
        itemEndTime.setText(bean.get????????????????????????());
        itemOrderWeek.setText(String.valueOf(bean.get????????????()));
        double itemSum = 0, itemRate = 0, itemNetValue = 0;
        for (int i = 0; i < bean.getOfferModel().get???????????????????????????().size(); i++) {
            UnQuotedBean.OfferModelDTO.???????????????????????????DTO entity = bean.getOfferModel().get???????????????????????????().get(i);
            itemSum = itemSum + entity.get??????() * entity.get??????();
            itemRate = itemSum * entity.get??????();
            itemNetValue = itemSum - itemRate;
        }

        itemTotalCash.setText(StringUtil.changeStrColor("???????????????" + StringUtil.formatDouble(itemSum), 3));
        itemHeadRate.setText(StringUtil.formatDouble(itemRate));
        itemActualCash.setText(StringUtil.changeStrColor("???????????????" + StringUtil.formatDouble(itemNetValue), 3));


        if (bean.get????????????() == null || bean.get????????????() == null || "".equals(bean.get????????????()) || "".equals(bean.get????????????())) {
            reStyle.setVisibility(View.GONE);
        } else {
            reStyle.setVisibility(View.VISIBLE);
            itemClearingWay.setText(bean.get????????????());
            itemShippingWay.setText(bean.get????????????());
        }

        tvDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals("?????????")) {
                    strCancel = "Refuse";
                    msg = StringUtil.changeStrColor("?????????????????????" + bean.get????????????() + "??????????", 2);
                    dialogCancel(bean);
                } else if ("?????????".contains(title)) {
                    strCancel = "OfferedCanel";
                    String label = "???????????????" + bean.get????????????() + "??????????";
                    msg = StringUtil.changeFontColor(getContext(), label, R.color.google_red, 5, label.length() - 4);
                    dialogCancel(bean);
                } else if (("?????????").equals(title)) {
                    BaseEditDialog dialog = new BaseEditDialog(getContext(), "??????????????????", "");
                    dialog.showDialog();
                    dialog.setOnClickData(new BaseEditDialog.InputData() {
                        @Override
                        public void setOnClickData(String strProgress) {
                            EasyHttp.post(Constants.URL + "confirmAcceptance")
                                    .params("strCode", bean.get????????????())
                                    .params("strBaoJiaID", bean.get????????????())
                                    .params("strspeed", strProgress)
                                    .params("strSID", personEntity.getValue().getSid())
                                    .params("strAcceptanc", "-1")
                                    .execute(new SimpleCallBack<String>() {
                                        @Override
                                        public void onError(ApiException e) {
                                            ToastUtil.getInstance().showToast(e.getMessage());
                                        }

                                        @Override
                                        public void onSuccess(String s) {
                                            try {
                                                if (NetUtil.strContent(s).contains("??????")) {
                                                    getBtnBackColor(tvAgree, tvDisagree);
                                                    itemTvStatus.setText("????????????");
                                                    itemTvStatus.setTextColor(getContext().getResources().getColor(R.color.red));
                                                    addNewsData(data);
                                                }
                                                ToastUtil.getInstance().showToast(NetUtil.strContent(s));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        }
                    });
                }
            }
        });

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.get????????????() == 1) {
                    EasyHttp.post(Constants.URL + "confirmAcceptance")
                            .params("strCode", bean.get????????????())
                            .params("strBaoJiaID", bean.get????????????())
                            .params("strspeed", "")
                            .params("strSID", personEntity.getValue().getSid())
                            .params("strAcceptanc", "1")
                            .execute(new SimpleCallBack<String>() {
                                @Override
                                public void onError(ApiException e) {
                                    ToastUtil.getInstance().showToast(e.getMessage());
                                }

                                @Override
                                public void onSuccess(String s) {
                                    try {
                                        if (NetUtil.strContent(s).contains("??????")) {
                                            getBtnBackColor(tvAgree, tvDisagree);
                                            itemTvStatus.setText("?????????");
                                            itemTvStatus.setTextColor(getContext().getResources().getColor(R.color.black));
                                            addNewsData(data);
                                        }
                                        ToastUtil.getInstance().showToast(NetUtil.strContent(s));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    presenter = new CommodityPresenter(getContext(), bean);
                    presenter.showDialog();
                    presenter.setOnClickFinishOrder(new CommodityPresenter.FinishOrder() {
                        @Override
                        public void setOrder(UnQuotedBean quotedBean) {
                            if (title == null) { //?????????
                                data.remove(bean);
                            } else {
                                data.set(holder.getAdapterPosition(), quotedBean);
                            }
                            addNewsData(data);
                        }
                    });
                }
            }
        });
        return view;
    }

    private void dialogCancel(UnQuotedBean bean) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("????????????")
                .setMessage(msg)
                .setNegativeButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (title.equals("?????????")) {
                            EasyHttp.post(Constants.URL + "Refuse")
                                    .params("strCode", bean.get????????????())
                                    .params("strSID", personEntity.getValue().getSid())
                                    .execute(new SimpleCallBack<String>() {
                                        @Override
                                        public void onStart() {
                                            super.onStart();
                                        }

                                        @Override
                                        public void onCompleted() {
                                            super.onCompleted();
                                        }

                                        @Override
                                        public void onError(ApiException e) {
                                            ToastUtil.getInstance().showToast(e.getMessage());
                                        }

                                        @Override
                                        public void onSuccess(String s) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                jsonObject.getString("value");
                                                if ("??????????????????".equals(jsonObject.getString("value"))) {
                                                    data.remove(bean);
                                                    Event<List<UnQuotedBean>> event = new Event<List<UnQuotedBean>>(FinalClass.QUOTED_UPDATE_DATA, data);
                                                    EventBusUtil.sendEvent(event);
                                                    addNewsData(data);
                                                }
                                                ToastUtil.getInstance().showToast(jsonObject.getString("value"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        } else {
                            EasyHttp.post(Constants.URL + "OfferedCanel")
                                    .params("strCompany", personEntity.getValue().getCompany())
                                    .params("strBaoJiaID", bean.get????????????())
                                    .params("strReason", "?????????")
                                    .params("strSID", personEntity.getValue().getSid())
                                    .execute(new SimpleCallBack<String>() {
                                        @Override
                                        public void onStart() {
                                            super.onStart();
                                        }

                                        @Override
                                        public void onCompleted() {
                                            super.onCompleted();
                                        }

                                        @Override
                                        public void onError(ApiException e) {
                                            ToastUtil.getInstance().showToast(e.getMessage());
                                        }

                                        @Override
                                        public void onSuccess(String s) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                if ("??????????????????".equals(jsonObject.getString("value"))) {
                                                    data.remove(bean);
                                                    Event<List<UnQuotedBean>> event = new Event<List<UnQuotedBean>>(FinalClass.QUOTED_UPDATE_DATA, data);
                                                    EventBusUtil.sendEvent(event);
                                                    addNewsData(data);
                                                }
                                                ToastUtil.getInstance().showToast(jsonObject.getString("value"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        }
                        dialog.dismiss();
                    }
                }).show();
        alertDialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        alertDialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
    }

    private void getBtnBackColor(TextView tvAgree, TextView tvDisagree) {
        tvAgree.setBackgroundResource(R.drawable.shape_rectangle_grey_5_lr15_tb5);
        tvDisagree.setBackgroundResource(R.drawable.shape_rectangle_grey_5_lr15_tb5);
        tvAgree.setEnabled(false);
        tvDisagree.setEnabled(false);
    }

    public void addNewsData(List<UnQuotedBean> nodes) {
        if (nodes != null) {
            data = nodes;
            notifyDataSetChanged();
        }
    }
}

