package com.shenzhen.honpe.honpe_sqe.app.login_with_register;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.shenzhen.honpe.honpe_sqe.MainFragment;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.animation.RxAnimationTool;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.SharedPreferencesUtil;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.AndroidBug5497Workaround;
import com.shenzhen.honpe.honpe_sqe.widget.DJEditText;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.shenzhen.honpe.honpe_sqe.api.AppConfig.TABLE_NAME_LOGIN;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.START_LOGIN;

/**
 * FileName: LoginFragment
 * Author: asus
 * Date: 2021/1/20 15:52
 * Description:登录界面
 */
public class LoginFragment extends BaseBackFragment {
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.logo)
    CircleImageView mLogo;
    @BindView(R.id.service)
    LinearLayout mService;
    @BindView(R.id.et_mobile)
    DJEditText mEtUseAccount;
    @BindView(R.id.et_password)
    DJEditText mEtPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView mIvShowPwd;
    @BindView(R.id.ck_password)
    CheckBox ckPassWord;
    @BindView(R.id.forget_password)
    TextView tvForgetPassWord;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    /**
     * PersonInfo
     **/
    PersonEntity personEntity;
    List<PersonEntity> listdata;
    private int keyHeight = 0; //软件盘弹起后所占高度
    private int screenHeight = 0;//屏幕高度
    private float scale = 0.6f; //logo缩放比例
    private int height = 0;
    private String tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFullScreen(_mActivity)) {
            AndroidBug5497Workaround.assistActivity(_mActivity);
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        personEntity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_CHECK);
        LatteLogger.d("LoginPersonEntity", GsonBuildUtil.GsonBuilder(personEntity));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Utils.setCheckBoxColor(ckPassWord, R.color.colorPrimary);
        }
        if (personEntity != null) {
            ckPassWord.setChecked(personEntity.isCheck());
            if (personEntity.getValue() != null) {
                Glide.with(mActivity).load(personEntity.getValue().getIcons()).error(R.drawable.selector_men).placeholder(R.drawable.selector_men).into(mLogo);
            }
            if (ckPassWord.isChecked()) {
                mEtUseAccount.setText(personEntity.getUsername());
                mEtPassword.setText(personEntity.getPassword());
            } else {
                mEtUseAccount.setText("");
                mEtPassword.setText("");
                mLogo.setImageResource(R.drawable.selector_men);
            }
        } else {
            personEntity = new PersonEntity();
        }
        LatteLogger.d("testPerson", GsonBuildUtil.GsonBuilder(personEntity));
        remPass();
    }

    public boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    public static LoginFragment newInstance(String tag) {
        LoginFragment fragment = new LoginFragment();
        fragment.tag = tag;
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {
        screenHeight = getResources().getDisplayMetrics().heightPixels;//获取屏幕高度
        keyHeight = screenHeight / 3; //弹起高度为屏幕高度的1/3
    }

    private void remPass() {
        ckPassWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ckPassWord.isChecked()) {
                    ckPassWord.setText("记住密码");
                } else {
                    ckPassWord.setText("取消记住");
                }
                personEntity.setCheck(isChecked);
                DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_CHECK, personEntity);
            }
        });
        mEtUseAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (personEntity.getValue().getIcons() !=null) {
//                    if (s.toString().equals(personEntity.getValue().getUsername())) {
//                        Glide.with(mActivity).load(personEntity.getValue().getIcons()).error(R.drawable.selector_men).placeholder(R.drawable.selector_men).into(mLogo);
//                    }
//                } else {
                mLogo.setImageResource(R.drawable.selector_men);
//                }
            }
        });
    }


    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColor(R.color.white).hideBar(BarHide.FLAG_SHOW_BAR).navigationBarColor(R.color.black).keyboardEnable(false).statusBarDarkFont(true).init();
        /**
         * 禁止键盘弹起的时候可以滚动
         */
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    int dist = mContent.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY",
                                0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        RxAnimationTool.zoomIn(mLogo, scale, dist);
                    }
                    mService.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((mContent.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY",
                                mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        RxAnimationTool.zoomOut(mLogo, scale);
                    }
                    mService.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @OnClick({R.id.tv_register, R.id.btn_login, R.id.iv_show_pwd, R.id.forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                //  start(RegFragment.newInstance());
                start(VerifyFragment.newInstance(tvRegister.getText().toString()));
                break;
            case R.id.btn_login:
                if (personEntity.isCheck()) {
                    personEntity.setUsername(mEtUseAccount.getText().toString());
                    personEntity.setPassword(mEtPassword.getText().toString());
                    DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_CHECK, personEntity);
                } else {
                    DBUtils.remove(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_CHECK);
                }
                RequestLog();
                break;
            case R.id.iv_show_pwd:
                if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIvShowPwd.setImageResource(R.mipmap.pass_visuable);
                } else {
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIvShowPwd.setImageResource(R.mipmap.pass_gone);
                }
                String pwd = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;
            case R.id.forget_password:
                start(VerifyFragment.newInstance(tvForgetPassWord.getText().toString()));
                break;
        }
    }

    private void RequestLog() {
        EasyHttp.post(Constants.URL + "login")
                .params("userName", mEtUseAccount.getText().toString())
                .params("userPwd", StringUtil.md5(mEtPassword.getText().toString()))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        ProgressUtils.disLoadView(_mActivity, 1);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        ProgressUtils.disLoadView(_mActivity, 0);
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.getInstance().showToast(e.getMessage().toString());
                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("testLogin", s);
                        try {
                            JSONArray a = new JSONArray(s);
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject o = a.getJSONObject(i);
                                if (o.getString("key").contains("失败"))
                                    ToastUtil.getInstance().showToast(o.getString("value"));
                            }

                            listdata = GsonBuildUtil.create().fromJson(s, new TypeToken<List<PersonEntity>>() {
                            }.getType());

                            if (listdata.get(0).getKey().equals("登陆成功")) {
                                LatteLogger.d("listData", GsonBuildUtil.GsonBuilder(listdata));
                                personEntity = listdata.get(0);
                                personEntity.setCheck(ckPassWord.isChecked());
                                personEntity.setUsername(mEtUseAccount.getText().toString());
                                personEntity.setPassword(mEtPassword.getText().toString());
                                DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_LOGIN, personEntity);
                                Event<PersonEntity> event = new Event<PersonEntity>(FinalClass.BACK_DATA, personEntity);
                                EventBusUtil.sendEvent(event);
                                if (tag.contains("MainActivity")) {
                                    startWithPop(MainFragment.newInstance());
                                } else {
                                    pop();
                                }
                                SharedPreferencesUtil.put(TABLE_NAME_LOGIN, AppConfig.userType, 1);
                                ToastUtil.getInstance().showToast("登录成功！");
                            } else if (listdata.get(0).getKey().equals("登陆失败")) {
                                ToastUtil.getInstance().showToast("用户名或密码错误！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case START_LOGIN:
                personEntity = (PersonEntity) event.getData();
                mEtUseAccount.setText(personEntity.getUsername());
                mEtPassword.setText(personEntity.getPassword());
                break;
        }
    }
}






