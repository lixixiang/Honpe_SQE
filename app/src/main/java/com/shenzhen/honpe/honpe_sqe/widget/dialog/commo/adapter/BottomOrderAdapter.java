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
    /***??????????????????????????????**/
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
        adapter = new BaseDetailAdapter(bean.getOfferModel().get???????????????????????????());
        mRecyclerView.setAdapter(adapter);
        moneyAdapter = new ProperyTagAdapter(getContext(), clearingFormMethod);
        flClearingForm = holder.getView(R.id.rl_clearing_form_method);
        flClearingForm.setAdapter(moneyAdapter);
        moneyAdapter.notifyDataSetChanged();
        flClearingForm.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        for (TagInfo tagInfo : clearingFormMethod) {
            if (tagInfo.getText().equals(bean.get????????????())) {
                tagInfo.setSelect(true);
            }
        }
        flClearingForm.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                bean.set????????????(clearingFormMethod.get(selectedList.get(0)).getText());
                data.set(0, bean);
                UpData(data);
            }
        });
        for (TagInfo tagInfo : GoodsMethod) {
            if (tagInfo.getText().equals(bean.get????????????())) {
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
                bean.set????????????(GoodsMethod.get(selectedList.get(0)).getText());
                data.set(0, bean);
                UpData(data);
            }
        });

        double sum = 0, rate = 0, actualAmount = 0;
        for (int i = 0; i < bean.getOfferModel().get???????????????????????????().size(); i++) {
            UnQuotedBean.OfferModelDTO.???????????????????????????DTO entity = bean.getOfferModel().get???????????????????????????().get(i);
            sum = sum + entity.get??????() * entity.get??????();
            rate = rate + sum * entity.get??????();
            actualAmount = sum - rate;
            bean.set????????????(String.format("%.2f", sum));
            bean.set?????????(String.format("%.2f", rate));
            bean.set?????????(String.format("%.2f", actualAmount));
        }
        tvAmount = holder.getView(R.id.tv_amount);
        tvPriceRate = holder.getView(R.id.tv_price_rate);
        tvActualAmount = holder.getView(R.id.tv_actual_amount);

        if (actualAmount == 0) {
            tvAmount.setText("");
            tvPriceRate.setText("");
            tvActualAmount.setText("");
        } else {
            tvAmount.setText(StringUtil.changeStrColor("??" + bean.get????????????(), 0));
            tvPriceRate.setText(StringUtil.changeStrColor("??" + bean.get?????????(), 0));
            tvActualAmount.setText(StringUtil.changeStrColor("??" + bean.get?????????(), 0));
        }

        tvWeekDate = holder.getView(R.id.tv_week_date);

        tvWeekDate.setText(bean.get????????????() == 0 ? "" : bean.get????????????() + "");
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
        tvStartDate.setText(bean.get????????????????????????());
        tvEndDate.setText(bean.get????????????????????????());
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        ll_start_end_date.setOnClickListener(this);
        apply_normal = holder.getView(R.id.apply_normal);
        apply_succeed = holder.getView(R.id.apply_succeed);
        apply_normal.setOnClickListener(this);
        apply_succeed.setOnClickListener(this);
        if (bean.getRowid() == 0) {
            apply_normal.setText("????????????");
            apply_succeed.setText("????????????");
            strState = "";
        } else {
            apply_normal.setText("????????????");
            apply_succeed.setText("????????????");
            strState = String.valueOf(bean.get????????????());
        }
        if (!TextUtils.isEmpty(bean.get????????????()) && bean.get????????????() != 0
                && !TextUtils.isEmpty(bean.get????????????????????????()) && !TextUtils.isEmpty(bean.get????????????????????????())
                && !TextUtils.isEmpty(bean.get????????????()) && !TextUtils.isEmpty(bean.get????????????())) {
            apply_normal.setVisibility(View.GONE);
            apply_succeed.setVisibility(View.VISIBLE);
        } else {
            apply_normal.setVisibility(View.VISIBLE);
            apply_succeed.setVisibility(View.GONE);
        }
        Log.d("getTime", GsonBuildUtil.GsonBuilder(bean));

    }

    /**
     * ????????????
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
        StringUtil.HintUtil(textAmount, "????????????????????????????????????????????????",14);
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
                data.get(0).set????????????(Integer.parseInt(textAmount.getText().toString()));
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
                            data.get(0).set????????????????????????(startDate);
                            data.get(0).set????????????????????????(endDate);
                            tvStartDate.setText(startDate);
                            tvEndDate.setText(endDate);
                        } else {
                            ToastUtil.getInstance().showToast("????????????????????????????????????");
                        }
                    }
                });
                break;
            case R.id.apply_normal:
                ToastUtil.getInstance().showToast("?????????????????????????????????????????????");
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
        for (int i = 0; i < bean.getOfferModel().get???????????????????????????().size(); i++) {
            OfferListEntity listEntity = new OfferListEntity();
            UnQuotedBean.OfferModelDTO.???????????????????????????DTO entity = bean.getOfferModel().get???????????????????????????().get(i);
            listEntity.setRowid(bean.getOfferModel().get???????????????????????????().get(i).getRowid()); //??????????????????
            listEntity.set????????????(entity.get??????());
            listEntity.set??????(entity.get??????());
            listEntity.set????????????(entity.get????????????());
            listEntity.set??????(entity.get??????());
            listEntity.set??????(entity.get??????());
            ListOfferEntity.add(listEntity);
        }
        ModelOneEntity modelOne = new ModelOneEntity();
        modelOne.setRowid(bean.getRowid());
        modelOne.set????????????(bean.get????????????());
        modelOne.set????????????(bean.get????????????());
        modelOne.set???????????????(personEntity.getValue().getCompany());
        modelOne.set????????????(format.format(new Date()));
        modelOne.set???????????????(personEntity.getValue().getSid());
        modelOne.set?????????(personEntity.getValue().getName());
        modelOne.set????????????(bean.get????????????());
        modelOne.set????????????(1);
        modelOne.set????????????(bean.get????????????());
        modelOne.set????????????????????????(bean.get????????????????????????());
        modelOne.set????????????????????????(bean.get????????????????????????());
        modelOne.set????????????(bean.get????????????());
        modelOne.set????????????(bean.get????????????());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("list", GsonBuildUtil.GsonToString(ListOfferEntity));
        jsonObject.addProperty("modelOne", GsonBuildUtil.GsonToString(modelOne));
        jsonObject.addProperty("strSID", personEntity.getValue().getSid());
        jsonObject.addProperty("strCode", bean.get????????????());
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
