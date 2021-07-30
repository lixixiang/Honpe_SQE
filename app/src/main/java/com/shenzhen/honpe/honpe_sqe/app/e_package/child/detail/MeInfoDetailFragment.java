package com.shenzhen.honpe.honpe_sqe.app.e_package.child.detail;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * FileName: MeInfoDetailFragment
 * Author: asus
 * Date: 2021/1/19 15:33
 * Description:
 */
public class MeInfoDetailFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;

    private String title;
    private int position;

    PersonEntity entity;

    public static MeInfoDetailFragment newInstance(Bundle bundle) {
        MeInfoDetailFragment fragment = new MeInfoDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me_info_detail;
    }

    @Override
    protected void initView() {
        entity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            position = bundle.getInt("position");
            tvTitle.setText(title);
            et.setText(bundle.getString("content"));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(Utils.dp2px(_mActivity, 20));
        params.gravity = Gravity.CENTER_VERTICAL;
        tvBack.setLayoutParams(params);

        initToolbarNav(llBack);

        ivBack.setVisibility(View.GONE);
        tvBack.setText("取消");
        tvEnd.setVisibility(View.VISIBLE);
        tvEnd.setText("完成");

    }

    @OnClick(R.id.tv_end)
    public void onClick() {
        if (title.equals("姓名")) {
            entity.getValue().setName(et.getText().toString());
        } else if (title.equals("邮箱")) {
            if (StringUtil.isEmail(et.getText().toString())) {
                entity.getValue().setEmail(et.getText().toString());
            } else {
                ToastUtil.getInstance().showToast("邮箱错误！");
            }
        } else if (title.equals("取系电话")) {
            if (StringUtil.isPhoneLegal(et.getText().toString())) {
                entity.getValue().setPhone(et.getText().toString());
            } else {
                ToastUtil.getInstance().showToast("取系电话格式不对！");
            }
        } else if (title.equals("地址")) {
            entity.getValue().setAddress(et.getText().toString());
        } else if (title.equals("公司名称")) {
            entity.getValue().setCompany(et.getText().toString());
        }
        requestMobPersonInfo(entity);

//        Bundle bundle = new Bundle();
//        bundle.putString("result", et.getText().toString());
//        bundle.putInt("position",position);
//        Event<Bundle> event = new Event<Bundle>(BACK_DATA, bundle);
//        EventBusUtil.sendEvent(event);

    }

    private void requestMobPersonInfo(PersonEntity entity) {
        LatteLogger.d("RegisterInfoUpdate", GsonBuildUtil.GsonBuilder(entity.getValue()));
        EasyHttp.post(Constants.URL + "RegisterInfoUpdate")
                .retryCount(0)
                .params("entityUser", GsonBuildUtil.GsonToString(entity.getValue()))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.getInstance().showToast(e.getMessage().toString());
                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("testObject", s);
                        try {
                            JSONObject o = new JSONObject(s);
                            if (o.getString("value").contains("成功")) {
                                Event<PersonEntity> event = new Event<PersonEntity>(FinalClass.BACK_DATA, entity);
                                EventBusUtil.sendEvent(event);
                            }
                            ToastUtil.getInstance().showToast(o.getString("value"));
                            hideSoftInput();
                            _mActivity.onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}












