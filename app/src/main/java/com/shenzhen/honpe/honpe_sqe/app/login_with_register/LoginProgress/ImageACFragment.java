package com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginProgress;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.RxPermissionsTool;
import com.shenzhen.honpe.honpe_sqe.utils.SharedPreferencesUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.NetUtil;
import com.shenzhen.honpe.honpe_sqe.widget.camera.CameraActivity;
import com.shenzhen.honpe.imagepicker.utils.PermissionUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenzhen.honpe.honpe_sqe.api.AppConfig.TABLE_NAME_LOGIN;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CAMERA_UPLOAD_CODE;

/**
 * FileName: ImageACFragment
 * Author: asus
 * Date: 2021/7/14 8:52
 * Description: 拍摄图片认证
 */
public class ImageACFragment extends BaseBackFragment {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_camera_business)
    TextView tvCameraBusiness;
    @BindView(R.id.iv_camera_business)
    ImageView ivCameraBusiness;
    @BindView(R.id.tips_camera_business)
    TextView tipsCameraBusiness;
    @BindView(R.id.tv_camera_organization)
    TextView tvCameraOrganization;
    @BindView(R.id.iv_camera_organization)
    ImageView ivCameraOrganization;
    @BindView(R.id.tips_camera_organization)
    TextView tipsCameraOrganization;
    @BindView(R.id.tv_camera_tax_certificate)
    TextView tvCameraTaxCertificate;
    @BindView(R.id.iv_camera_tax_certificate)
    ImageView ivCameraTaxCertificate;
    @BindView(R.id.tips_camera_tax_certificate)
    TextView tipsCameraTaxCertificate;
    @BindView(R.id.tvVerify)
    RoundTextView tvVerify;

    RegisterBean bean;

    public static ImageACFragment newInstance(RegisterBean bean) {
        ImageACFragment fragment = new ImageACFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_image_ac;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        tvTitle.setText("上传执照");
        bean = (RegisterBean) getArguments().getSerializable("bean");

        LatteLogger.d("testBean", GsonBuildUtil.GsonBuilder(bean));
    }

    @OnClick({R.id.fl_camera_business,R.id.fl_organize,R.id.fl_license, R.id.tvVerify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_camera_business:
                takePhoto(CameraActivity.TYPE_COMPANY_LANDSCAPE);
                break;
            case R.id.fl_organize:
                takePhoto(CameraActivity.TYPE_ORGANIZATION_LANDSCAPE);
                break;
            case R.id.fl_license:
                takePhoto(CameraActivity.TYPE_TAX_CERTIFICATE);
                break;
            case R.id.tvVerify:
                submitData();
                SharedPreferencesUtil.put(TABLE_NAME_LOGIN, AppConfig.userType,1);
                break;
        }
    }
    PersonEntity personEntity = new PersonEntity();
    CompanyEntity companyEntity = new CompanyEntity();
    private void submitData() {
        personEntity.setAdress(bean.getAddress());
        personEntity.setCompany(bean.getCompanyName());
        personEntity.setEmail(bean.getEmail());
        personEntity.setName(bean.getName());
        personEntity.setUsername(bean.getAccount());
        personEntity.setPhone(bean.getPhone());
        personEntity.setUserpassword(bean.getPassword());
        personEntity.setComefrom(bean.getComefrom());

        companyEntity.setBank(bean.getContraryBank());
        companyEntity.setBankNum(bean.getContraryAccount());
        companyEntity.setCompany(bean.getCompanyName());
        companyEntity.setLicense(bean.getPhotoLicense());
        companyEntity.setOrganize(bean.getPhotoOrganize());
        companyEntity.setTax(bean.getPhotoTax());
        companyEntity.setIntroduction("");
        companyEntity.setNullify(0);
        companyEntity.setOrgCode(bean.getOrganizingCode());
        companyEntity.setTaxcode(bean.getTaxID());
        companyEntity.setWftype(bean.getSupplyStrId());

        LatteLogger.d("personEntity",GsonBuildUtil.GsonBuilder(personEntity));
        LatteLogger.d("companyEntity",GsonBuildUtil.GsonBuilder(companyEntity));

        EasyHttp.post(Constants.URL + "Register")
                .retryCount(0)
                .params("entity",GsonBuildUtil.GsonToString(companyEntity))
                .params("entityUser",GsonBuildUtil.GsonToString(personEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        if (s.contains("成功")) {
                            DBUtils.saveSerializableEntity(AppConfig.TABLE_NAME_CHECK,AppConfig.APP_CHECK,personEntity);
                            mActivity.onBackPressed();
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
    }

    String strBusiness,strOrganization,strTaxCertificate;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case CAMERA_UPLOAD_CODE:
                Bundle bundle = (Bundle) event.getData();
                String path = bundle.getString("path");
                int requestCode = bundle.getInt("requestCode");
                switch (requestCode) {
                    case CameraActivity.TYPE_COMPANY_LANDSCAPE:
                        strBusiness =  NetUtil.BitMapIconToString(path);
                        ivCameraBusiness.setImageBitmap(NetUtil.strPathToBitMap(path));
                        bean.setPhotoLicense(strBusiness);
                        tvCameraBusiness.setVisibility(View.GONE);
                        break;
                    case CameraActivity.TYPE_ORGANIZATION_LANDSCAPE:
                        strOrganization =  NetUtil.BitMapIconToString(path);
                        ivCameraOrganization.setImageBitmap(NetUtil.strPathToBitMap(path));
                        bean.setPhotoOrganize(strOrganization);
                        tvCameraOrganization.setVisibility(View.GONE);
                        break;
                    case CameraActivity.TYPE_TAX_CERTIFICATE:
                        strTaxCertificate =  NetUtil.BitMapIconToString(path);
                        ivCameraTaxCertificate.setImageBitmap(NetUtil.strPathToBitMap(path));
                        bean.setPhotoTax(strTaxCertificate);
                        tvCameraTaxCertificate.setVisibility(View.GONE);
                        break;
                }
                break;
        }
    }
}




















