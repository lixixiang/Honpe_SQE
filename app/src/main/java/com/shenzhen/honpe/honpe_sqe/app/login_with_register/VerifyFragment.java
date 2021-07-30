package com.shenzhen.honpe.honpe_sqe.app.login_with_register;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.roundview.RoundTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.SharePrefrenceHelper;
import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginProgress.InputUserInfoFragment;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.RegisterBean;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.DemoResHelper;
import com.shenzhen.honpe.honpe_sqe.utils.DemoSpHelper;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.RxPermissionsTool;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.ContentWithSpaceEditText;
import com.shenzhen.honpe.honpe_sqe.widget.DJEditText;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.sms.PrivacyDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnDialogListener;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;
import cn.smssdk.gui.CountryPage;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.START_LOGIN;


/**
 * FileName: VerifyFragment
 * Author: asus
 * Date: 2021/7/12 11:44
 * Description:验证页，包括短信验证和语音验证，默认使用中国区号
 */
public class VerifyFragment extends BaseBackFragment implements TextWatcher {
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.etPhone)
    ContentWithSpaceEditText etPhone;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.tvVerify)
    RoundTextView tvVerify;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_password)
    DJEditText etPassword;
    @BindView(R.id.input_layout)
    TextInputLayout inputLayout;

    private static final String TAG = "VerifyActivity";
    private static final String[] DEFAULT_COUNTRY = new String[]{"中国", "42", "86"};
    private static final int COUNTDOWN = 60;
    private static final String TEMP_CODE = "1319972";
    private static final String KEY_START_TIME = "start_time";

    private String currentId;
    private String currentPrefix;
    private FakeActivity callback;
    private Toast toast;
    private Handler handler;
    private EventHandler eventHandler;
    private int currentSecond;
    private SharePrefrenceHelper helper;
    private PersonEntity personEntity;
    /////////////////////////////////////////////////////////
    RegisterBean bean;
    private TextView tvToast;
    private String type;

    public static VerifyFragment newInstance(String type) {
        VerifyFragment fragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_verify;
    }

    @Override
    protected void initView() {
        personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_CHECK);
        type = getArguments().getString("type");
        tvTitle.setText(type);

        tvBack.setVisibility(View.GONE);
        initToolbarNav(llBack);
        initSMS();
        initListener();
        if (type.contains("忘记")) {
            etPassword.setText("设置新密码");
            tvVerify.setText("设置新密码");
        }

        //默认获取短信和验证按钮不可点击，输入达到规范后，可点击
        tvCode.setEnabled(false);
        tvVerify.setEnabled(false);
        etPassword.setEnabled(false);
        etPassword.addTextChangedListener(this);
        etPhone.addTextChangedListener(this);
        tvCode.addTextChangedListener(this);
        etCode.addTextChangedListener(this);
        //默认使用中国区号
        currentId = DEFAULT_COUNTRY[1];
        currentPrefix = DEFAULT_COUNTRY[2];
        tvCountry.setText(getString(R.string.smssdk_default_country) + " +" + DEFAULT_COUNTRY[2]);
        helper = new SharePrefrenceHelper(mActivity);
        helper.open("sms_sp");
    }

    private void initSMS() {
        if (!DemoSpHelper.getInstance().isPrivacyGranted()) {
            PrivacyDialog privacyDialog = new PrivacyDialog(mActivity, new OnDialogListener() {
                @Override
                public void onAgree() {
                    uploadResult(true);
                    DemoSpHelper.getInstance().setPrivacyGranted(true);
                    goOn();
                }

                @Override
                public void onDisagree() {
                    uploadResult(false);
                    DemoSpHelper.getInstance().setPrivacyGranted(false);
                    Handler handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            System.exit(0);
                            return false;
                        }
                    });
                    handler.sendEmptyMessageDelayed(0, 500);
                }
            });
            privacyDialog.show();
        } else {
            goOn();
        }
    }

    private void uploadResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void aVoid) {
                // Nothing to do
                LatteLogger.d(TAG, "Submit privacy grant result onComplete======" + GsonBuildUtil.GsonToString(aVoid));
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Nothing to do
                LatteLogger.d(TAG, "Submit privacy grant result error");
            }
        });
    }

    /**
     * 可以继续流程，一般是接受隐私条款后
     */
    private void goOn() {
        RxPermissionsTool.with(mActivity)
                .addPermission(Manifest.permission.READ_PHONE_STATE)
                .addPermission(Manifest.permission.RECEIVE_SMS)
                .addPermission(Manifest.permission.READ_CONTACTS)
                .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .initPermission();
        // 初始化短信SDK
    }

    private void initListener() {
        //回调选中的国家
        callback = new FakeActivity() {
            @Override
            public void onResult(HashMap<String, Object> data) {
                if (data != null) {
                    int page = (Integer) data.get("page");
                    if (page == 1) {
                        currentId = (String) data.get("id");
                        String[] country = SMSSDK.getCountry(currentId);
                        if (country != null) {
                            tvCountry.setText(country[0] + " " + "+" + country[1]);
                            currentPrefix = country[1];
                        }
                    }
                }
            }
        };
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (tvCode != null) {
                    if (currentSecond > 0) {
                        tvCode.setText(getString(R.string.smssdk_get_code) + " (" + currentSecond + "s)");
                        tvCode.setEnabled(false);
                        currentSecond--;
                        etCode.requestFocus();
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        tvCode.setText(R.string.smssdk_get_code);
                        tvCode.setEnabled(true);
                    }
                }
            }
        };

        eventHandler = new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                LatteLogger.d("testPersonEntity", result);
                                if (personEntity != null) {
                                    // start(MainFragment.newInstance());
                                    LatteLogger.d("testPersonEntity", GsonBuildUtil.GsonBuilder(personEntity));
                                } else {
                                    personEntity.setUsername(etPhone.getText().toString().replaceAll(" ", ""));
                                    personEntity.setPhone(etPhone.getText().toString().replaceAll(" ", ""));
                                    personEntity.setPassword(etPassword.getText().toString());
                                    personEntity.setPassword(StringUtil.md5(etPassword.getText().toString()));
//                                    start(InputUserInfoFragment.newInstance(bean));
                                    DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_CHECK, personEntity);
                                    LatteLogger.d("testPersonEntity", GsonBuildUtil.GsonBuilder(personEntity));
                                    EventBusUtil.sendEvent(new Event(START_LOGIN));
                                }
                            } else {
                                processError(data);
                            }
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE || event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                currentSecond = COUNTDOWN;
                                handler.sendEmptyMessage(0);
                                helper.putLong(KEY_START_TIME, System.currentTimeMillis());
                            } else {
                                if (data != null && (data instanceof UserInterruptException)) {
                                    // 由于此处是开发者自己决定要中断发送的，因此什么都不用做
                                    return;
                                }
                                processError(data);
                            }
                        }
                    });
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void processError(Object data) {
        int status = 0;
        // 根据服务器返回的网络错误，给toast提示
        try {
            ((Throwable) data).printStackTrace();
            Throwable throwable = (Throwable) data;

            JSONObject object = new JSONObject(
                    throwable.getMessage());
            String des = object.optString("detail");
            status = object.optInt("status");
            if (!TextUtils.isEmpty(des)) {
                showErrorToast(des);
                return;
            }
        } catch (Exception e) {
            Log.w(TAG, "", e);
        }
        // 如果木有找到资源，默认提示
        int resId = DemoResHelper.getStringRes(MyApplication.getContext(),
                "smsdemo_network_error");
        String netErrMsg = MyApplication.getContext().getResources().getString(resId);
        showErrorToast(netErrMsg);
    }

    private void showErrorToast(String text) {
        if (toast == null) {
            toast = new Toast(mActivity);
            View rootView = LayoutInflater.from(mActivity).inflate(R.layout.smssdk_error_toast_layout, null);
            tvToast = rootView.findViewById(R.id.tvToast);
            toast.setView(rootView);
            toast.setGravity(Gravity.CENTER, 0, ResHelper.dipToPx(MyApplication.getContext(), -100));
        }
        tvToast.setText(text);
        toast.show();
    }


    @OnClick({R.id.tvCountry, R.id.tvCode, R.id.tvVerify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCountry:
                //将当前国家带入跳转国家列表
                CountryPage countryPage = new CountryPage();
                countryPage.setCountryId(currentId);
                countryPage.showForResult(mActivity, null, callback);
                break;
            case R.id.tvCode:
                //获取验证码间隔时间小于1分钟，进行toast提示，在当前页面不会有这种情况，但是当点击验证码返回上级页面再进入会产生该情况
                long startTime = helper.getLong(KEY_START_TIME);
                if (System.currentTimeMillis() - startTime < COUNTDOWN * 1000) {
                    showErrorToast(getString(R.string.smssdk_busy_hint));
                    break;
                }
                if (!isNetworkConnected()) {
                    ToastUtil.getInstance().showToast(getString(R.string.smssdk_network_error));
                    break;
                }
                SMSSDK.getVerificationCode(currentPrefix, etPhone.getText().toString().trim());
                hideSoftKeyBoard();
                break;
            case R.id.tvVerify:
                LatteLogger.d("testURL", Constants.URL + "PhoneRegister");
                if (!isNetworkConnected()) {
                    Toast.makeText(mActivity, getString(R.string.smssdk_network_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                LatteLogger.d("ddddddddddddddd", StringUtil.isPhoneLegal(etPhone.getText().toString().replaceAll(" ", "")));
                if (StringUtil.isPhoneLegal(etPhone.getText().toString().replaceAll(" ", ""))) {
                    if (type.contains("注册")) {
                        Register();
                    } else {
                        forgetPassword();
                    }
                } else {
                    ToastUtil.getInstance().showToast("手机号码格式不对！");
                }
                hideSoftInput();
                break;
        }
    }

    private void forgetPassword() {
        LatteLogger.d("testforgetPassword",Constants.TEST_URL + "PhoneModifyPWD" + "\n"+
                "phone  "+ etPhone.getText().toString().replaceAll(" ", "") + "\n"+
                "zone  "+ StringUtil.getStringNum(tvCountry.getText().toString())+ "\n"+
                "code  "+ etCode.getText().toString()+ "\n"+
                "strPWD  "+ StringUtil.md5(etPassword.getText().toString()));

        EasyHttp.post(Constants.TEST_URL + "PhoneModifyPWD")
                .params("phone", etPhone.getText().toString().replaceAll(" ", ""))
                .params("zone",StringUtil.getStringNum(tvCountry.getText().toString()))
                .params("code",  etCode.getText().toString())
                .params("strPWD",StringUtil.md5(etPassword.getText().toString()))
                .retryCount(0)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        LatteLogger.d("testforgetPassword",e.getMessage().toString());
                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("testforgetPassword",s);
                    }
                });
    }

    private void Register() {
        LatteLogger.d("testLog", etPhone.getText().toString().replaceAll(" ", "") + "\n"+
                StringUtil.md5(etPassword.getText().toString())+"\n"+etPassword.getText().toString());

        EasyHttp.post(Constants.URL + "PhoneRegister")
                .params("strPhone", etPhone.getText().toString().replaceAll(" ", ""))
                .params("strPwd", StringUtil.md5(etPassword.getText().toString()))
                .params("comefrom", "Android")
                .retryCount(0)
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
                        LatteLogger.d("ApiException", e.getMessage());
                        ToastUtil.getInstance().showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("Register", s);
                        try {
                            JSONObject o = new JSONObject(s);
                            if (o.getBoolean("key")) {
                                SMSSDK.submitVerificationCode(currentPrefix, etPhone.getText().toString().trim(), etCode.getText().toString());
                                _mActivity.onBackPressed();
                            }
                            ToastUtil.getInstance().showToast(o.getString("value"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //验证码输入6位并且手机大于5位，验证按钮可点击
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (etPhone.getText() != null && etPhone.getText().toString().length() > 5) {
            tvCode.setEnabled(true);
        } else {
            tvCode.setEnabled(false);
        }
        if (etCode.getText().toString().length() > 5) {
            etCode.setFocusable(false);
            etPassword.setEnabled(true);
            etPassword.requestFocus();
        }
        if (etCode.getText().toString().length() == 18) {
            ToastUtil.getInstance().showToast("密码不能超过18位");
        }
        if (etPhone.getText().toString().length() > 5 && etPassword.getText().toString().length() > 5) {
            tvVerify.setEnabled(true);
            tvVerify.getDelegate().setBackgroundColor(R.color.smssdk_text_green);
        } else {
            tvVerify.setEnabled(false);
            tvVerify.getDelegate().setBackgroundColor(R.color.colorPrimaryNormal);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}





















