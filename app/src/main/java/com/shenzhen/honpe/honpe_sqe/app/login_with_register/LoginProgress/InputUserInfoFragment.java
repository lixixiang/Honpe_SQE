package com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginProgress;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.CompanyEntity;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.RegisterBean;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.DJEditText;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.tipsDialog.TipsDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.BACK_DATA;

/**
 * FileName: InputUserData
 * Author: asus
 * Date: 2021/7/13 11:02
 * Description: 填写用户资料界面 姓名、密码、邮箱
 */
public class InputUserInfoFragment extends BaseBackFragment implements TextWatcher {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    DJEditText etName;
    @BindView(R.id.tvVerify)
    RoundTextView tvVerify;

    private PersonEntity bean;

    public static InputUserInfoFragment newInstance(PersonEntity bean) {
        InputUserInfoFragment inputUserData = new InputUserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        inputUserData.setArguments(bundle);
        return inputUserData;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_input_user_info;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        tvTitle.setText("填写昵称");
        tvVerify.setEnabled(false);
        bean = (PersonEntity) getArguments().getSerializable("bean");
        etName.addTextChangedListener(this);
    }

    @OnClick(R.id.tvVerify)
    public void onClick() {
        if (etName.getText().toString().length() > 0) {

            bean.getValue().setName(etName.getText().toString());
            DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN, bean);

            CompanyEntity companyEntity = new CompanyEntity("","","","","",
                    "","","",0,"","","");
            LatteLogger.d("testddddd",GsonBuildUtil.GsonBuilder(companyEntity));
            LatteLogger.d("testddddd",GsonBuildUtil.GsonBuilder(bean));
            EasyHttp.post(Constants.URL + "PhoneAuthentication")
                    .params("entity",GsonBuildUtil.GsonToString(companyEntity))
                    .params("entityUser",GsonBuildUtil.GsonToString(bean))
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {
                            LatteLogger.d("testInfo", e.getMessage());
                        }

                        @Override
                        public void onSuccess(String s) {
                            hideSoftKeyBoard();
                            LatteLogger.d("testInfo", GsonBuildUtil.GsonBuilder(bean)+"\n"+ s);
                            Event<PersonEntity> event = new Event<>(BACK_DATA, bean);
                            EventBusUtil.sendEvent(event);
                            mActivity.onBackPressed();
                        }
                    });
          //  start(CompanyInfoFragment.newInstance(bean));
        }else {
            TipsDialog tipsDialog = new TipsDialog(mActivity,"请填写完昵称再来点击哦~");
            tipsDialog.show();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etName.getText().toString().length() > 0) {
            tvVerify.setEnabled(true);
        } else {
            tvVerify.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
























