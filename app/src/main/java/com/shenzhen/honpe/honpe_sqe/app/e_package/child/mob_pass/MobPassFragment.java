package com.shenzhen.honpe.honpe_sqe.app.e_package.child.mob_pass;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.MOB_PASS;

/**
 * @ProjectName: Honpe
 * @CreateDate: 2020/7/7 11:29
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class MobPassFragment extends BaseBackFragment implements TextWatcher {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.css_title)
    TextView cssTitle;
    @BindView(R.id.css_et)
    EditText cssEt;
    @BindView(R.id.css_title2)
    TextView cssTitle2;
    @BindView(R.id.css_et2)
    EditText cssEt2;
    @BindView(R.id.css_title3)
    TextView cssTitle3;
    @BindView(R.id.css_et3)
    EditText cssEt3;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.view_line3)
    View viewLine3;


    private String[] hints = {"填不写原密码", "填写新密码", "再次填写确认"};

    private PersonEntity entity;

    public static final MobPassFragment newInstance() {
        MobPassFragment fragment = new MobPassFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.mob_pass;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        entity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_CHECK, AppConfig.APP_CHECK);
        LatteLogger.d("entity", GsonBuildUtil.GsonBuilder(entity));
        initToolbarNav(llBack);
        tvTitle.setText("修改密码");
        StringUtil.HintUtil(cssEt, hints[0]);
        StringUtil.HintUtil(cssEt2, hints[1]);
        StringUtil.HintUtil(cssEt3, hints[2]);
        cssEt.setHintTextColor(getResources().getColor(R.color.grey_home));
        cssEt2.setHintTextColor(getResources().getColor(R.color.grey_home));
        cssEt3.setHintTextColor(getResources().getColor(R.color.grey_home));
        cssEt.addTextChangedListener(this);
        cssEt2.addTextChangedListener(this);
        cssEt3.addTextChangedListener(this);
        if (entity != null) {
            cssEt.setText(entity.getPassword());
        }
        cssEt.setEnabled(false);
        cssEt.setBackgroundResource(R.color.grey_home);

        SpannableString spannableString = new SpannableString("密码必须是8-16位 数字/字母/字符两种组合");
        spannableString.setSpan(new ForegroundColorSpan
                (getResources().getColor(R.color.blue)), 5, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTips.setText(spannableString);
        btnSure.setEnabled(false);
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (cssEt2.getText().toString().equals(cssEt3.getText().toString())&&!"".equals(cssEt2.getText().toString())|| !TextUtils.isEmpty(cssEt2.getText().toString())) {
            btnSure.setEnabled(true);
        } else {
            btnSure.setEnabled(false);
        }
    }


    @Override
    public void afterTextChanged(Editable s) {
    }


    @OnClick({R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                if (cssEt2.getText().toString().equals(cssEt3.getText().toString())) {
                    MobUploadHeadImage(cssEt3.getText().toString());
                }else {
                    ToastUtil.getInstance().showToast("两次密码输入不一致！");
                }
                break;
        }
    }

    private void MobUploadHeadImage(String password) {
        entity.getValue().setUserpassword(StringUtil.md5(password));
        entity.setPassword(password);
        EasyHttp.post(Constants.URL+"RegisterInfoUpdate")
                .params("entityUser",GsonBuildUtil.GsonToString(entity.getValue()))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("MobUploadHeadImage",s);
                        try {
                            JSONObject object = new JSONObject(s);
                            ToastUtil.getInstance().showToast(object.getString("value"));
                            if (object.getString("key").contains("成功")) {
                                Event<PersonEntity> event = new Event<PersonEntity>(MOB_PASS,entity);
                                EventBusUtil.sendEvent(event);
                            }
                            hideSoftKeyBoard();
                            _mActivity.onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
