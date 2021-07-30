package com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.JsonObject;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.FlowTagLayout;
import com.shenzhen.honpe.honpe_sqe.widget.VirtualKeyboardView;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.ModelOneEntity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.OfferListEntity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.entity.TagInfo;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.OnTagSelectListener;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter.BaseTimePickPresenter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.SUCCESS_FAIL;


/**
 * FileName: BottomOrderAdapter
 * Author: asus
 * Date: 2021/3/24 20:02
 * Description:
 */
public class BottomOrderAdapter extends BaseQuickAdapter<UnQuotedBean, BaseViewHolder> implements TextWatcher, AdapterView.OnItemClickListener, View.OnClickListener {
    List<UnQuotedBean> data;
    RecyclerView mRecyclerView;
    BaseDetailAdapter adapter;
    FlowTagLayout flClearingForm, flGoodsMethod;
    ProperyTagAdapter moneyAdapter, goodsAdapter;
    List<TagInfo> clearingFormMethod, GoodsMethod;
    private ArrayList<Map<String, String>> valueList;
    /***指定某一个位置的数据**/
    private TextView tvWeekDate, tvSure, textAmount, tvAmount, tvPriceRate, tvActualAmount, tvStartDate, tvEndDate;
    public MyDialog mPriceDialog, mBottomSheetDialog;
    private int curPos;
    private Button apply_normal, apply_succeed;
    private PersonEntity personEntity;
    List<OfferListEntity> ListOfferEntity = new ArrayList<>();
    private String strState;
    private LinearLayout ll_start_end_date;

    public BottomOrderAdapter(@Nullable List<UnQuotedBean> data, List<TagInfo> clearingFormMethod, List<TagInfo> GoodsMethod, MyDialog mBottomSheetDialog) {
        super(R.layout.item_bottom_order, data);
        this.data = data;
        this.clearingFormMethod = clearingFormMethod;
        this.GoodsMethod = GoodsMethod;
        this.mBottomSheetDialog = mBottomSheetDialog;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, UnQuotedBean bean) {
        mRecyclerView = holder.getView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseDetailAdapter(bean.getOfferModel().get申请单明细报价记录());
        mRecyclerView.setAdapter(adapter);
        moneyAdapter = new ProperyTagAdapter(getContext(), clearingFormMethod);
        flClearingForm = holder.getView(R.id.rl_clearing_form_method);
        flClearingForm.setAdapter(moneyAdapter);
        moneyAdapter.notifyDataSetChanged();
        flClearingForm.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        for (TagInfo tagInfo : clearingFormMethod) {
            if (tagInfo.getText().equals(bean.get结算方式())) {
                tagInfo.setSelect(true);
            }
        }
        flClearingForm.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                bean.set结算方式(clearingFormMethod.get(selectedList.get(0)).getText());
                data.set(0, bean);
                UpData(data);
            }
        });
        for (TagInfo tagInfo : GoodsMethod) {
            if (tagInfo.getText().equals(bean.get送货方式())) {
                tagInfo.setSelect(true);
            }
        }
        goodsAdapter = new ProperyTagAdapter(getContext(), GoodsMethod);
        flGoodsMethod = holder.getView(R.id.rl_goods_method);
        flGoodsMethod.setAdapter(goodsAdapter);
        goodsAdapter.notifyDataSetChanged();
        flGoodsMethod.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flGoodsMethod.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                bean.set送货方式(GoodsMethod.get(selectedList.get(0)).getText());
                data.set(0, bean);
                UpData(data);
            }
        });

        double sum = 0, rate = 0, actualAmount = 0;
        for (int i = 0; i < bean.getOfferModel().get申请单明细报价记录().size(); i++) {
            UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO entity = bean.getOfferModel().get申请单明细报价记录().get(i);
            sum = sum + entity.get单价() * entity.get数量();
            rate = rate + sum * entity.get税率();
            actualAmount = sum - rate;
            bean.set报价金额(String.format("%.2f", sum));
            bean.set总税额(String.format("%.2f", rate));
            bean.set总净额(String.format("%.2f", actualAmount));
        }
        tvAmount = holder.getView(R.id.tv_amount);
        tvPriceRate = holder.getView(R.id.tv_price_rate);
        tvActualAmount = holder.getView(R.id.tv_actual_amount);

        if (actualAmount == 0) {
            tvAmount.setText("");
            tvPriceRate.setText("");
            tvActualAmount.setText("");
        } else {
            tvAmount.setText(StringUtil.changeStrColor("¥" + bean.get报价金额(), 0));
            tvPriceRate.setText(StringUtil.changeStrColor("¥" + bean.get总税额(), 0));
            tvActualAmount.setText(StringUtil.changeStrColor("¥" + bean.get总净额(), 0));
        }

        tvWeekDate = holder.getView(R.id.tv_week_date);

        tvWeekDate.setText(bean.get制作周期() == 0 ? "" : bean.get制作周期() + "");
        tvWeekDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPriceDialog(holder);
                mPriceDialog.show();
            }
        });
        tvStartDate = holder.getView(R.id.tv_start_date);
        tvEndDate = holder.getView(R.id.tv_end_date);
        ll_start_end_date = holder.getView(R.id.ll_start_end_date);
        tvStartDate.setText(bean.get报价有效开始时间());
        tvEndDate.setText(bean.get报价有效结束时间());
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        ll_start_end_date.setOnClickListener(this);
        apply_normal = holder.getView(R.id.apply_normal);
        apply_succeed = holder.getView(R.id.apply_succeed);
        apply_normal.setOnClickListener(this);
        apply_succeed.setOnClickListener(this);
        if (bean.getRowid() == 0) {
            apply_normal.setText("提交报价");
            apply_succeed.setText("提交报价");
            strState = "";
        } else {
            apply_normal.setText("修改报价");
            apply_succeed.setText("修改报价");
            strState = String.valueOf(bean.get中标状态());
        }
        if (!TextUtils.isEmpty(bean.get报价金额()) && bean.get制作周期() != 0
                && !TextUtils.isEmpty(bean.get报价有效开始时间()) && !TextUtils.isEmpty(bean.get报价有效结束时间())
                && !TextUtils.isEmpty(bean.get结算方式()) && !TextUtils.isEmpty(bean.get送货方式())) {
            apply_normal.setVisibility(View.GONE);
            apply_succeed.setVisibility(View.VISIBLE);
        } else {
            apply_normal.setVisibility(View.VISIBLE);
            apply_succeed.setVisibility(View.GONE);
        }
        Log.d("getTime", GsonBuildUtil.GsonBuilder(bean));

    }

    /**
     * 更新数据
     ***/
    public void UpData(List<UnQuotedBean> entities) {
        if (entities != null) {
            this.data = entities;
            notifyDataSetChanged();
        }
    }

    private void ShowPriceDialog(BaseViewHolder holder) {
        curPos = holder.getAdapterPosition();
        mPriceDialog = new MyDialog(getContext(), R.style.GoodDialog);
        mPriceDialog.outDuration(200);
        mPriceDialog.inDuration(200);
        mPriceDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        View priceView = LayoutInflater.from(getContext()).inflate(R.layout.pop_enter_pass, null);
        mPriceDialog.setContentView(priceView);
        VirtualKeyboardView pwdView = mPriceDialog.findViewById(R.id.pwd_view);
        textAmount = mPriceDialog.findViewById(R.id.textAmount);
        StringUtil.HintUtil(textAmount, "按数字键输入完成订单需要花费天数",14);
        tvSure = mPriceDialog.findViewById(R.id.tv_sure);
        valueList = pwdView.getValueList();
        GridView gridView = pwdView.getGridView();
        textAmount.addTextChangedListener(this);
        gridView.setOnItemClickListener(this);
        tvSure.setOnClickListener(this);
        pwdView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPriceDialog.dismiss();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            tvSure.setVisibility(View.VISIBLE);
        } else {
            tvSure.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 11 && position != 9) {
            String amount = textAmount.getText().toString().trim();
            amount = amount + valueList.get(position).get("name");
            textAmount.setText(amount);
        } else {
            if (position == 9) {
                String amount = textAmount.getText().toString().trim();
                if (!amount.contains(".")) {
                    amount = amount + valueList.get(position).get("name");
                    textAmount.setText(amount);
                }
            }
            if (position == 11) {
                String amount = textAmount.getText().toString().trim();
                if (amount.length() > 0) {
                    amount = amount.substring(0, amount.length() - 1);
                    textAmount.setText(amount);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                data.get(0).set制作周期(Integer.parseInt(textAmount.getText().toString()));
                tvWeekDate.setText(textAmount.getText().toString());
                mPriceDialog.dismiss();
                break;
            case R.id.tv_start_date:
            case R.id.tv_end_date:
                BaseTimePickPresenter pickPresenter = new BaseTimePickPresenter(getContext(),"bottom");
                pickPresenter.showDialog();
                pickPresenter.setOnDateClickListener(new BaseTimePickPresenter.OnDateClickListener() {
                    @Override
                    public void setOnDate(String startDate, String endDate) {
                        if (DateUtil.hourMinuteBetween(format, startDate, startDate, endDate)) {
                            data.get(0).set报价有效开始时间(startDate);
                            data.get(0).set报价有效结束时间(endDate);
                            tvStartDate.setText(startDate);
                            tvEndDate.setText(endDate);
                        } else {
                            ToastUtil.getInstance().showToast("开始时间不能大于结束时间");
                        }
                    }
                });
                break;
            case R.id.apply_normal:
                ToastUtil.getInstance().showToast("请填写完整的信息才能提交报价！");
                break;
            case R.id.apply_succeed:
                getRequestNet(data.get(0));
                break;
        }
    }

    private void getRequestNet(UnQuotedBean bean) {
        personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        if (ListOfferEntity != null) {
            ListOfferEntity.clear();
        }
        for (int i = 0; i < bean.getOfferModel().get申请单明细报价记录().size(); i++) {
            OfferListEntity listEntity = new OfferListEntity();
            UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO entity = bean.getOfferModel().get申请单明细报价记录().get(i);
            listEntity.setRowid(bean.getOfferModel().get申请单明细报价记录().get(i).getRowid()); //修改的时候要
            listEntity.set报价单价(entity.get单价());
            listEntity.set税率(entity.get税率());
            listEntity.set零件编码(entity.get零件编码());
            listEntity.set备注(entity.get备注());
            listEntity.set数量(entity.get数量());
            ListOfferEntity.add(listEntity);
        }
        ModelOneEntity modelOne = new ModelOneEntity();
        modelOne.setRowid(bean.getRowid());
        modelOne.set报价单号(bean.get报价单号());
        modelOne.set申请单号(bean.get申请单号());
        modelOne.set供应商名称(personEntity.getValue().getCompany());
        modelOne.set报价日期(format.format(new Date()));
        modelOne.set供应商代码(personEntity.getValue().getSid());
        modelOne.set报价人(personEntity.getValue().getName());
        modelOne.set报价金额(bean.get报价金额());
        modelOne.set报价类型(1);
        modelOne.set制作周期(bean.get制作周期());
        modelOne.set报价有效开始时间(bean.get报价有效开始时间());
        modelOne.set报价有效结束时间(bean.get报价有效结束时间());
        modelOne.set结算方式(bean.get结算方式());
        modelOne.set送货方式(bean.get送货方式());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("list", GsonBuildUtil.GsonToString(ListOfferEntity));
        jsonObject.addProperty("modelOne", GsonBuildUtil.GsonToString(modelOne));
        jsonObject.addProperty("strSID", personEntity.getValue().getSid());
        jsonObject.addProperty("strCode", bean.get申请单号());
        jsonObject.addProperty("strState", strState);
        getOffer(jsonObject);
        LatteLogger.d("testJsonData", GsonBuildUtil.GsonBuilder(jsonObject));
    }

    private void getOffer(JsonObject jsonObject) {
        EasyHttp.post(Constants.URL + "Offer")
                .retryCount(0)
                .params("jsonData", GsonBuildUtil.GsonToString(jsonObject))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.getInstance().showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("onSuccess", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            Event<String> event = new Event<String>(SUCCESS_FAIL, object.getString("value"));
                            EventBusUtil.sendEvent(event);
                            mBottomSheetDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
}
