package com.shenzhen.honpe.honpe_sqe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.LoginFragment;
import com.shenzhen.honpe.honpe_sqe.base.BaseActivity;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.SharedPreferencesUtil;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.camera.CameraActivity;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.shenzhen.honpe.honpe_sqe.api.AppConfig.TABLE_NAME_LOGIN;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.CAMERA_UPLOAD_CODE;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    String path;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
    }

    @Override
    public int getLayoutId() {
        return R.layout.css_framelayout;
    }

    @Override
    public void initView() {
        int type = (int) SharedPreferencesUtil.get(TABLE_NAME_LOGIN, AppConfig.userType, -1); //用户是不是第一次使用
        if (type == -1) {
            loadRootFragment(R.id.fl_main_fragment_container, LoginFragment.newInstance("MainActivity"));
        } else {
            loadRootFragment(R.id.fl_main_fragment_container, MainFragment.newInstance());
        }
    }


    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限申请")
                    .setRationale("应用程序运行缺少必要的权限，请前往设置页面打开")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(FinalClass.START_CAMERA)
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == CameraActivity.TYPE_COMPANY_LANDSCAPE) {
                path = CameraActivity.getResult(data);
                bundle.putString("path", path);
            } else if (requestCode == CameraActivity.TYPE_ORGANIZATION_LANDSCAPE) {
                path = CameraActivity.getResult(data);
                bundle.putString("path", path);
            } else if (requestCode == CameraActivity.TYPE_TAX_CERTIFICATE) {
                path = CameraActivity.getResult(data);
                bundle.putString("path", path);
            } else if (requestCode == FinalClass.START_ALBUM) {
                Uri imageUri = data.getData();
                bundle.putString("bit", imageUri.toString());
            } else if (requestCode == FinalClass.START_CAMERA) {
                Bitmap photo = data.getParcelableExtra("data");
                bundle.putString("bit", StringUtil.BitMapToString(photo) );
            } else if (requestCode == CameraActivity.TYPE_BANK) {
                path = CameraActivity.getResult(data);
                bundle.putString("path", path);
            }
            bundle.putInt("requestCode", requestCode);
            Event<Bundle> event = new Event<Bundle>(CAMERA_UPLOAD_CODE, bundle);
            EventBusUtil.sendEvent(event);
            LatteLogger.d("testPath", GsonBuildUtil.GsonBuilder(bundle));
        }
    }
}






