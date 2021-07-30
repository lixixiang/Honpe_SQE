package com.shenzhen.honpe.honpe_sqe.app.e_package.child.mob;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.shenzhen.honpe.honpe_sqe.utils.gson.NetUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.presenter.BottomPhotoWithAlbumPresenter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.BACK_DATA;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CAMERA_UPLOAD_CODE;

/**
 * FileName: MobIconFragment
 * Author: asus
 * Date: 2021/1/20 10:57
 * Description:修改头像
 */
public class MobIconFragment extends BaseBackFragment {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_circle)
    CircleImageView ivCircle;
    @BindView(R.id.btn_sure)
    Button btnSure;
    private String cropTempPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "cropHeadIcon.jpg";
    private PersonEntity entity;

    public static MobIconFragment newInstance(Bundle b) {
        MobIconFragment fragment = new MobIconFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mob_icon;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        Bundle b = getArguments();
        tvTitle.setText(b.getString("title"));
        entity = (PersonEntity) b.getSerializable("person");
        // entity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        LatteLogger.d("testEntity", GsonBuildUtil.GsonBuilder(entity));
        Glide.with(getContext()).load(entity.getValue().getIcons()).placeholder(R.drawable.selector_men).into(ivCircle);
    }

    @OnClick({R.id.iv_circle, R.id.btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_circle:
                BottomPhotoWithAlbumPresenter presenter = new BottomPhotoWithAlbumPresenter(mActivity, findFragment(MobIconFragment.class));
                presenter.showDialog();
                break;
            case R.id.btn_sure:
                UpdateIcon();
                break;
        }
    }

    private void UpdateIcon() {
        Bitmap bit = Utils.ImageViewToBitmap(ivCircle);
        EasyHttp.post(Constants.URL + "UploadHeadImage")
                .params("strSID", entity.getValue().getSid())
                .params("fileBytes", NetUtil.BitToString(bit))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        LatteLogger.d("UploadHeadImage", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            if ("1".equals(object.getString("key"))) {
                                entity.getValue().setIcons(object.getString("value"));
                                DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN, entity);
                                Event<PersonEntity> event = new Event<PersonEntity>(BACK_DATA, entity);
                                EventBusUtil.sendEvent(event);
                                ToastUtil.getInstance().showToast("图片修改成功！");
                            } else {
                                ToastUtil.getInstance().showToast("图片修改失败！");
                            }
                            _mActivity.onBackPressed();
                        } catch (JSONException e) {
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
            case CAMERA_UPLOAD_CODE:
                Bundle bundle = (Bundle) event.getData();
                switch (bundle.getInt("requestCode")) {
                    case FinalClass.START_CAMERA:
                        String CAMERA = bundle.getString("bit");
                        Bitmap bitmap = StringUtil.StringToBitMap(CAMERA);
                        Glide.with(mActivity).load(bitmap).placeholder(R.drawable.health_guide_men_selected).into(ivCircle);
                        break;
                    case FinalClass.START_ALBUM:
                        String ALBUM = bundle.getString("bit");
                        Glide.with(mActivity).load(StringUtil.StrToUri(ALBUM)).placeholder(R.drawable.health_guide_men_selected).into(ivCircle);
                        break;
                }
                btnSure.setEnabled(true);
                break;
        }
    }
}














