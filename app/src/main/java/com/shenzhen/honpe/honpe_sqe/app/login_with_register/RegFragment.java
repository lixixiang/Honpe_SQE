package com.shenzhen.honpe.honpe_sqe.app.login_with_register;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.adapter.SupplyClassificationAdapter;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.CompanyEntity;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.RegisterBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.SupplyBean;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.gson.NetUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.RxPermissionsTool;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.widget.CustomPopWindow;
import com.shenzhen.honpe.honpe_sqe.widget.DJEditText;
import com.shenzhen.honpe.honpe_sqe.widget.MyScrollView;
import com.shenzhen.honpe.honpe_sqe.widget.camera.CameraActivity;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.tipsDialog.TipsDialog;
import com.shenzhen.honpe.honpe_sqe.widget.image_touch.TouchImageFragment;
import com.shenzhen.honpe.imagepicker.utils.PermissionUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CAMERA_UPLOAD_CODE;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CHECK_EVENT;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.REGISTER_DATA;

/**
 * FileName: RegFragment
 * Author: asus
 * Date: 2021/3/8 17:22
 * Description:
 */
public class RegFragment extends BaseBackFragment implements TextWatcher {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_account)
    DJEditText etAccount;
    @BindView(R.id.et_name)
    DJEditText etName;
    @BindView(R.id.et_password)
    DJEditText etPassword;
    @BindView(R.id.et_email)
    DJEditText etEmail;
    @BindView(R.id.et_phone)
    DJEditText etPhone;
    @BindView(R.id.et_company_name)
    DJEditText etCompanyName;
    @BindView(R.id.et_company_simple_name)
    DJEditText etCompanySimpleName;
    @BindView(R.id.et_organizingCode)
    DJEditText etOrganizingCode;
    @BindView(R.id.et_tax_ID)
    DJEditText etTaxID;
    @BindView(R.id.et_contrary_bank)
    DJEditText etContraryBank;
    @BindView(R.id.et_contrary_account)
    DJEditText etContraryAccount;
    @BindView(R.id.et_supply_classification)
    TextView etSupplyClassification;
    @BindView(R.id.apply_normal)
    Button applyNormal;
    @BindView(R.id.apply_succeed)
    Button applySucceed;
    @BindView(R.id.apply_relative)
    RelativeLayout applyRelative;
    @BindView(R.id.scrollview)
    MyScrollView mScrollView;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.iv_upload_business)
    ImageView ivUploadBusiness;
    @BindView(R.id.iv_upload_organization)
    ImageView ivUploadOrganization;
    @BindView(R.id.iv_upload_tax_certificate)
    ImageView ivUploadTaxCertificate;
    @BindView(R.id.iv_camera_business)
    ImageView ivCameraBusiness;
    @BindView(R.id.iv_camera_organization)
    ImageView ivCameraOrganization;
    @BindView(R.id.iv_camera_tax_certificate)
    ImageView ivCameraTaxCertificate;

    private static MyApplication appContext;
    private String strBusiness, strOrganization, strTaxCertificate;
    List<SupplyBean> supplyBeanList = new ArrayList<>();
    List<String> supplyStrList = new ArrayList<>();
    List<String> supplyStrId = new ArrayList<>();
    SupplyClassificationAdapter adapter;
    TipsDialog tipsDialog;
    public String[] hints = {"请输入6-20位英文字符，数字", "请输入姓名", "密码为6-18位数字和字母组合", "请输入邮箱", "请输入手机号码或者电话号码", "请输入公司名称", "请输入公司简称"
            , "请输入组织机构代码", "请输入税务登记号", "请输入对公银行", "请选择对公帐号", "请选择", "请选择", "请选择", "请选择"};

    public static RegFragment newInstance() {
        RegFragment fragment = new RegFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.css_register_fragment;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        tvTitle.setText("新用户注册");
        initScrollEvent();
        addTextWatcherEvent();
        initEditHint();
        initListener();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        appContext = (MyApplication) _mActivity.getApplication();
    }

    private void initEditHint() {
        Utils.setEditTextHint(etAccount, 14, hints[0]);
        Utils.setEditTextHint(etName, 14, hints[1]);
        Utils.setEditTextHint(etPassword, 14, hints[2]);
        Utils.setEditTextHint(etEmail, 14, hints[3]);
        Utils.setEditTextHint(etPhone, 14, hints[4]);
        Utils.setEditTextHint(etCompanyName, 14, hints[5]);
        Utils.setEditTextHint(etCompanySimpleName, 14, hints[6]);
        Utils.setEditTextHint(etOrganizingCode, 14, hints[7]);
        Utils.setEditTextHint(etTaxID, 14, hints[8]);
        Utils.setEditTextHint(etContraryBank, 14, hints[9]);
        Utils.setEditTextHint(etSupplyClassification, 14, hints[10]);
        Utils.setEditTextHint(etAddress, 14, "请输入详细地址");
        Utils.setEditTextHint(etContraryAccount, 14, "请输入对公帐号");
    }

    private void addTextWatcherEvent() {
        etAccount.addTextChangedListener(this);
        etName.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etPhone.addTextChangedListener(this);
        etCompanyName.addTextChangedListener(this);
        etCompanySimpleName.addTextChangedListener(this);
        etOrganizingCode.addTextChangedListener(this);
        etTaxID.addTextChangedListener(this);
        etContraryBank.addTextChangedListener(this);
        etSupplyClassification.addTextChangedListener(this);
        etAddress.addTextChangedListener(this);
        etContraryAccount.addTextChangedListener(this);
    }

    private void initScrollEvent() {
        mScrollView.setOnScrollChanged(new MyScrollView.OnScrollChanged() {
            @Override
            public void onScroll(int x, int y, int oldx, int oldy) {

            }

            @Override
            public void onTouch(boolean isDown) {
                if (!isDown) {
                    hideSoftInput();
                }
            }
        });
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.toolbar).keyboardEnable(true).statusBarDarkFont(false).statusBarColor(R.color.colorPrimary)
                .navigationBarColor(R.color.grey_c9).init();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        Event<RegisterBean> event = new Event<RegisterBean>(REGISTER_DATA);
        EventBusUtil.sendEvent(event);
    }

    @OnClick({R.id.iv_camera_business, R.id.iv_camera_organization, R.id.iv_camera_tax_certificate, R.id.iv_upload_business, R.id.iv_upload_organization, R.id.iv_upload_tax_certificate, R.id.et_address, R.id.apply_normal, R.id.apply_succeed, R.id.et_supply_classification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_normal:
                break;
            case R.id.apply_succeed:
                submitData();
                break;
            case R.id.iv_camera_business:
                takePhoto(CameraActivity.TYPE_COMPANY_LANDSCAPE);
                break;
            case R.id.iv_camera_organization:
                takePhoto(CameraActivity.TYPE_ORGANIZATION_LANDSCAPE);
                break;
            case R.id.iv_camera_tax_certificate:
                takePhoto(CameraActivity.TYPE_TAX_CERTIFICATE);
                break;
            case R.id.iv_upload_business:
                start(TouchImageFragment.newInstance(strBusiness));
                break;
            case R.id.iv_upload_organization:
                start(TouchImageFragment.newInstance(strOrganization));
                break;
            case R.id.iv_upload_tax_certificate:
                start(TouchImageFragment.newInstance(strTaxCertificate));
                break;
            case R.id.et_supply_classification:
                showPopListView();
                break;
            case R.id.et_address:

                break;
        }
    }

    PersonEntity personEntity = new PersonEntity();
    CompanyEntity companyEntity = new CompanyEntity();

    private void submitData() {
        personEntity.setAdress(etAddress.getText().toString());
        personEntity.setCompany(etCompanyName.getText().toString());
        personEntity.setEmail(etEmail.getText().toString());
        personEntity.setName(etName.getText().toString());
        personEntity.setUsername(etAccount.getText().toString());
        personEntity.setPhone(etPhone.getText().toString());
        personEntity.setUserpassword(StringUtil.md5(etPassword.getText().toString()));
        personEntity.setPassword(etPassword.getText().toString());
        personEntity.setComefrom("android");

        companyEntity.setBank(etContraryBank.getText().toString());
        companyEntity.setBankNum(etContraryAccount.getText().toString());
        companyEntity.setCompany(etCompanyName.getText().toString());
        companyEntity.setLicense(strBusiness);
        companyEntity.setOrganize(strOrganization);
        companyEntity.setTax(strTaxCertificate);
        companyEntity.setIntroduction("");
        companyEntity.setNullify(0);
        companyEntity.setOrgCode(etOrganizingCode.getText().toString());
        companyEntity.setTaxcode(etTaxID.getText().toString());
        companyEntity.setWftype(StringUtil.replaceBlank(GsonBuildUtil.GsonToString(supplyStrId))
                .replace("[", "").replace("]", "").replaceAll("\"", ""));

        EasyHttp.post(Constants.URL+ "Register")
                .retryCount(0)
                .params("entity", GsonBuildUtil.GsonToString(companyEntity))
                .params("entityUser", GsonBuildUtil.GsonToString(personEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("testRegister", s);
                        if (s.contains("成功")) {
                            DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_CHECK,AppConfig.APP_CHECK,personEntity);
                            mActivity.onBackPressed();
                        }
                    }
                });
    }

    private void initRequest() {
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

    private void showPopListView() {
        initRequest();
    }

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
                etSupplyClassification.setText(GsonBuildUtil.GsonBuilder(supplyStrList).replace("[", "").replace("]", "").replaceAll("\"", ""));
                break;
            case CAMERA_UPLOAD_CODE:
                Bundle bundle = (Bundle) event.getData();
                String path = bundle.getString("path");
                int requestCode = bundle.getInt("requestCode");
                switch (requestCode) {
                    case CameraActivity.TYPE_COMPANY_LANDSCAPE:
                        strBusiness =  NetUtil.BitMapIconToString(path);
                        ivUploadBusiness.setImageBitmap(NetUtil.strPathToBitMap(path));
                        break;
                    case CameraActivity.TYPE_ORGANIZATION_LANDSCAPE:
                        strOrganization =  NetUtil.BitMapIconToString(path);
                        ivUploadOrganization.setImageBitmap(NetUtil.strPathToBitMap(path));
                        break;
                    case CameraActivity.TYPE_TAX_CERTIFICATE:
                        strTaxCertificate =  NetUtil.BitMapIconToString(path);
                        ivUploadTaxCertificate.setImageBitmap(NetUtil.strPathToBitMap(path));
                        break;
                }
                break;
            case REGISTER_DATA:
                if (!"".equals(etAccount.getText().toString()) && !"".equals(etName.getText().toString()) && !"".equals(etPassword.getText().toString()) &&
                        !"".equals(etEmail.getText().toString()) && !"".equals(etPassword.getText().toString()) && !"".equals(etPhone.getText().toString()) &&
                        !"".equals(etCompanyName.getText().toString()) && !"".equals(etCompanySimpleName.getText().toString()) && !"".equals(etOrganizingCode.getText().toString()) &&
                        !"".equals(etTaxID.getText().toString()) && !"".equals(etContraryBank.getText().toString()) && !"".equals(etSupplyClassification.getText().toString()) &&
                        !"".equals(strBusiness) && !"".equals(strOrganization) && !"".equals(strTaxCertificate) && !"".equals(etContraryAccount.getText().toString())) {
                    applySucceed.setVisibility(View.VISIBLE);
                    applyNormal.setVisibility(View.GONE);
                } else {
                    applySucceed.setVisibility(View.GONE);
                    applyNormal.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void initRequestCompanyName(String name) {
        EasyHttp.post(Constants.URL + "CompanyExists")
                .retryCount(0)
                .params("CompanyName", name)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        if (s.contains("该公司已经注册无需再进行注册")) {
                            etCompanyName.setText("");
                            tipsDialog = new TipsDialog(mActivity, s.replaceAll("\"", ""));
                            tipsDialog.show();
                        }
                    }
                });
    }

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
        LatteLogger.d("testPermisstion", PermissionUtil.checkPermission(mActivity));
    }

    private void initListener() {
        etPassword.setOnFocusChangeListener(new FocusChangeListener());
        etEmail.setOnFocusChangeListener(new FocusChangeListener());
        etPhone.setOnFocusChangeListener(new FocusChangeListener());
        etCompanyName.setOnFocusChangeListener(new FocusChangeListener());
        etAccount.setOnFocusChangeListener(new FocusChangeListener());
    }

    boolean isFocus = false;//当editText第一次点击时无效

    private class FocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (isFocus) {
                switch (v.getId()) {
                    case R.id.et_account:
                        if (!StringUtil.isAccount(etAccount.getText().toString())) {
                            etAccount.setText("");
                            tipsDialog = new TipsDialog(mActivity, hints[0]);
                            tipsDialog.show();
                        }
                        break;
                    case R.id.et_password:
                        if (!StringUtil.isPass(etPassword.getText().toString())) {
                            etPassword.setText("");
                            tipsDialog = new TipsDialog(mActivity, "密码为6-18位数字和字母组合");
                            tipsDialog.show();
                        }
                        break;
                    case R.id.et_email:
                        if (!StringUtil.isEmail(etEmail.getText().toString())) {
                            tipsDialog = new TipsDialog(mActivity, "请正确填写邮箱\n如:18888888@qq.com");
                            tipsDialog.show();
                            etEmail.setText("");
                        }
                        break;
                    case R.id.et_phone:
                        if (!StringUtil.isPhoneLegal(etPhone.getText().toString())) {
                            tipsDialog = new TipsDialog(mActivity, "手机号输入有误，请重新输入");
                            tipsDialog.show();
                            etPhone.setText("");
                        }
                        break;
                    case R.id.et_company_name:
                        initRequestCompanyName(etCompanyName.getText().toString());
                        break;
                }
            }
            isFocus = hasFocus;
        }
    }
}


