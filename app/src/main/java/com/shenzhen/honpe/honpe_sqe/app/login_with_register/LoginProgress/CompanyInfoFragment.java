package com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginProgress;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.adapter.SupplyClassificationAdapter;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.RegisterBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.SupplyBean;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.BankInfoUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.RxPermissionsTool;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.NetUtil;
import com.shenzhen.honpe.honpe_sqe.widget.ContentWithSpaceEditText;
import com.shenzhen.honpe.honpe_sqe.widget.CustomPopWindow;
import com.shenzhen.honpe.honpe_sqe.widget.DJEditText;
import com.shenzhen.honpe.honpe_sqe.widget.camera.CameraActivity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.tipsDialog.TipsDialog;
import com.shenzhen.honpe.imagepicker.utils.PermissionUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CAMERA_UPLOAD_CODE;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CHECK_EVENT;

/**
 * FileName: ComplayInfoFragment
 * Author: asus
 * Date: 2021/7/13 13:50
 * Description:
 */
public class CompanyInfoFragment extends BaseBackFragment implements TextWatcher {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tvVerify)
    RoundTextView tvVerify;
    @BindView(R.id.et_company_name)
    DJEditText etCompanyName;  //公司名称
    @BindView(R.id.et_company_address)
    DJEditText etCompanyAddress; //联系地址
    @BindView(R.id.et_organizingCode)
    DJEditText etOrganizingCode; //组织机构
    @BindView(R.id.et_tax_ID)
    DJEditText etTaxID;       //税务登记代码
    @BindView(R.id.et_contrary_bank)
    ContentWithSpaceEditText etContraryBank; //对公银行
    @BindView(R.id.et_supply_classification)
    TextView etSupplyClassification;//供应分类（可多选）
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.et_bank)
    EditText etBank;
    private RegisterBean bean;
    private String strBusiness;
    List<SupplyBean> supplyBeanList = new ArrayList<>();
    List<String> supplyStrList = new ArrayList<>();
    List<String> supplyStrId = new ArrayList<>();
    private BankInfoUtil mInfoUtil;

    public static CompanyInfoFragment newInstance(RegisterBean bean) {
        CompanyInfoFragment fragment = new CompanyInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_company_info;
    }

    @Override
    protected void initView() {
        bean = (RegisterBean) getArguments().getSerializable("bean");
        initToolbarNav(llBack);
        tvTitle.setText("公司信息");
        ivRight.setColorFilter(getResources().getColor(R.color.white));
        etCompanyName.addTextChangedListener(this);
        etContraryBank.addTextChangedListener(this);
    }

    @OnClick({R.id.iv_scan, R.id.tvVerify, R.id.et_supply_classification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_supply_classification:
                showPopListView();
                break;
            case R.id.iv_scan:
                takePhoto(CameraActivity.TYPE_BANK);
                break;
            case R.id.tvVerify:
                start(ImageACFragment.newInstance(bean));
                if (etCompanyName.getText().toString().length() > 0 &&
                        etCompanyAddress.getText().toString().length() > 0 &&
                        etContraryBank.getText().toString().length() > 0 &&
                        etOrganizingCode.getText().toString().length() > 0 &&
                        etTaxID.getText().toString().length() > 0 &&
                        etSupplyClassification.getText().toString().length() > 0) {
                    bean.setCompanyName(etCompanyName.getText().toString());
                    bean.setCompanySimpleName(etCompanyName.getText().toString());
                    bean.setAddress(etCompanyAddress.getText().toString());
                    bean.setContraryBank(etBank.getText().toString());
                    bean.setContraryAccount(etContraryBank.getText().toString().replaceAll(" ",""));
                    bean.setOrganizingCode(etOrganizingCode.getText().toString());
                    bean.setTaxID(etTaxID.getText().toString());

                    bean.setComefrom("android");

                } else {
                    TipsDialog tipsDialog = new TipsDialog(mActivity, "公司信息需要全部填写才能进入下一步！");
                    tipsDialog.show();
                }
                break;
        }
    }

    String access_token, expires_in;

    private void takePhoto(int type) {
        if (PermissionUtil.checkPermission(mActivity)) {
            CameraActivity.openCertificateCamera(mActivity, type);
        } else {
            RxPermissionsTool.
                    with(_mActivity).
                    addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
                    addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                    addPermission(Manifest.permission.CAMERA).
                    initPermission();
        }
    }

    SupplyClassificationAdapter adapter;

    private void showPopListView() {
        EasyHttp.post(Constants.URL + "Outsourcing")
                .retryCount(0)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        ProgressUtils.disLoadView(mActivity, 1);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        ProgressUtils.disLoadView(mActivity, 0);
                    }

                    @Override
                    public void onError(ApiException e) {
                        ProgressUtils.disLoadView(mActivity, 0);
                        ToastUtil.getInstance().showToast(e.getMessage().toString());
                    }

                    @Override
                    public void onSuccess(String s) {
                        ProgressUtils.disLoadView(mActivity, 0);
                        Gson gson = new Gson();
                        if (!"".equals(etSupplyClassification.getText().toString()) && supplyBeanList != null) {
                            adapter = new SupplyClassificationAdapter(supplyBeanList);
                            LatteLogger.d("CHECK_EVENT", GsonBuildUtil.GsonBuilder(supplyBeanList));
                        } else {
                            supplyBeanList = gson.fromJson(NetUtil.ascii2Native(s), new TypeToken<List<SupplyBean>>() {
                            }.getType());
                            adapter = new SupplyClassificationAdapter(supplyBeanList);
                        }

                        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_register_supply_classification, null);
                        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
                        GridLayoutManager manager = new GridLayoutManager(_mActivity, 3);
                        mRecyclerView.setLayoutManager(manager);
                        mRecyclerView.setAdapter(adapter);
                        int h = mActivity.getWindowManager().getDefaultDisplay().getHeight();

                        new CustomPopWindow.PopupWindowBuilder(mActivity)
                                .setView(view)
                                .setFocusable(true)
                                .setOutsideTouchable(true)
                                .size(ViewGroup.LayoutParams.MATCH_PARENT, 3 * h / 5)
                                .create()
                                .showAsDropDown(toolbar, 0, 0);
                    }
                });
    }

    private void initRequestCompanyName() {
        EasyHttp.post(Constants.URL + "CompanyExists")
                .retryCount(0)
                .params("CompanyName", etCompanyName.getText().toString())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        if (s.contains("该公司已经注册无需再进行注册")) {
                            etCompanyName.setText("");
                            TipsDialog tipsDialog = new TipsDialog(mActivity, s.replaceAll("\"", ""));
                            tipsDialog.show();
                        }
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
        if (etCompanyName.getText().toString().length() > 0) {
            initRequestCompanyName();
        }
        String cardNum = etContraryBank.getText().toString().replace(" ", "");
        if (!TextUtils.isEmpty(cardNum) && checkBankCard(cardNum)) {
            mInfoUtil = new BankInfoUtil(cardNum);
            etBank.setText(mInfoUtil.getBankName());
        } else {
            etBank.setText("");
        }
    }

    private KProgressHUD hud;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case CHECK_EVENT:
                supplyBeanList = (List<SupplyBean>) event.getData();
                supplyStrList.clear();
                supplyStrId.clear();
                for (int i = 0; i < supplyBeanList.size(); i++) {
                    if (supplyBeanList.get(i).isCheck()) {
                        supplyStrList.add(supplyBeanList.get(i).get分类名称());
                        supplyStrId.add(supplyBeanList.get(i).get分类编码());
                    }
                }
                bean.setSupplyStrId(StringUtil.replaceBlank(GsonBuildUtil.GsonToString(supplyStrId))
                        .replace("[", "").replace("]", "").replaceAll(" ", "").replaceAll("\n", "").replaceAll("\"", ""));
                bean.setSupplyClassification(GsonBuildUtil.GsonBuilder(GsonBuildUtil.GsonBuilder(supplyStrList).replace("[", "")
                        .replace("]", "").replaceAll(" ", "").replaceAll("\n", "")
                        .replaceAll("\"", "")));
                etSupplyClassification.setText(bean.getSupplyClassification());
                break;
            case CAMERA_UPLOAD_CODE:
                Bundle bundle = (Bundle) event.getData();
                String path = bundle.getString("path");
                int requestCode = bundle.getInt("requestCode");
                switch (requestCode) {
                    case CameraActivity.TYPE_BANK:
                        strBusiness = NetUtil.BitMapIconToString(path);
                        String getAccessTokenUrl = Constants.authHost + "grant_type=client_credentials" + "&client_id=" + Constants.API_KEY +
                                "&client_secret=" + Constants.SECRET_KEY;
                        EasyHttp.get(getAccessTokenUrl).execute(new SimpleCallBack<String>() {
                            @Override
                            public void onError(ApiException e) {

                            }

                            @Override
                            public void onSuccess(String s) {
                                LatteLogger.d("takePhoto", s);
                                try {
                                    JSONObject o = new JSONObject(s);
                                    access_token = o.getString("access_token");
                                    expires_in = o.getString("expires_in");

                                    EasyHttp.post(Constants.BANK_CARD + access_token)
                                            .params("image", strBusiness)
                                            .params("detect_direction", true + "")
                                            .execute(new SimpleCallBack<String>() {
                                                @Override
                                                public void onStart() {
                                                    super.onStart();
                                                    hud = KProgressHUD.create(mActivity)
                                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                            .setCancellable(true)
                                                            .setLabel("正在加载...");
                                                }

                                                @Override
                                                public void onCompleted() {
                                                    super.onCompleted();
                                                    hud.dismiss();
                                                }

                                                @Override
                                                public void onError(ApiException e) {

                                                }

                                                @Override
                                                public void onSuccess(String s) {
                                                    LatteLogger.d("testBANK_CARD", s);
                                                    try {
                                                        JSONObject o = new JSONObject(s);
                                                        String bandNum = o.getJSONObject("result").getString("bank_card_number");//6214 8378 9164 6972
                                                        String bankName = o.getJSONObject("result").getString("bank_name"); //招商银行
                                                        int bankType = o.getJSONObject("result").getInt("bank_card_type");
                                                        if (bankType == 1) { //1为获取成功 0为获取失败
                                                            etContraryBank.setText(bandNum);
                                                            bean.setContraryBank(bankName);
                                                            etBank.setHint(bankName);
                                                        } else {
                                                            ToastUtil.getInstance().showToast("银行卡没有获取成功，建议手动编缉！");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        break;
                }
                break;
        }
    }

    /**
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhn 校验算法获得校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}





























