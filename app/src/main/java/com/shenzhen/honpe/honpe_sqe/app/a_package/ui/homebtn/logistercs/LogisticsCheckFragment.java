package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs.bean.LogisticsInfoBean;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.utils.scan.HandleData;
import com.shenzhen.honpe.honpe_sqe.widget.BaseListView;
import com.shenzhen.honpe.honpe_sqe.widget.KdniaoTrackQueryAPI;

import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.bertsir.zbar.view.ScanLineView;
import pub.devrel.easypermissions.EasyPermissions;

import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.RC_CAMERA;
import static com.shenzhen.honpe.honpe_sqe.api.FinalClass.START_CAMERA_RESULT;

/**
 * FileName: LogisticsCheckFragment
 * Author: asus
 * Date: 2021/3/28 11:00
 * Description:
 */
public class LogisticsCheckFragment extends BaseBackFragment {
    @BindView(R.id.ll_back)
    LinearLayout homeBack;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_end)
    TextView tvSure;
    @BindView(R.id.et_express_num)
    EditText etExpressNum;
    @BindView(R.id.bt_qrcode)
    Button btQrcode;
    @BindView(R.id.layout_search_express)
    RelativeLayout layoutSearchExpress;
    @BindView(R.id.lv_express_data)
    ListView lvExpressData;
    @BindView(R.id.tv_search_tip)
    TextView tvSearchTip;
    @BindView(R.id.lv_search_result)
    BaseListView lvSearchResult;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.sv_search_list)
    ScrollView svSearchList;

    private String title;
    private boolean canVis = true; //判断当前历史记录能不能显示

    private HandleData handleData;  //处理搜索记录

    private static final int START_CAMER = 1;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    private String StrScanResult;

    public static LogisticsCheckFragment newInstance(String title) {
        LogisticsCheckFragment fragment = new LogisticsCheckFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_logistics_check;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mTitle.setText(title);
        initToolbarNav(homeBack);
        handleData = new HandleData(_mActivity);
        StringUtil.HintUtil(etExpressNum, getResources().getString(R.string.input_express_no));
        tvSure.setVisibility(View.VISIBLE);
        tvSure.setText(getString(R.string.search));
        etExpressNum.setSelection(etExpressNum.getText().toString().trim().length());
        //编缉框焦点事件
        etExpressNum.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && canVis) {
                if (handleData.queryData("", lvSearchResult) == 0) {
                    //   disView(0);
                } else {
                    //  disView(START_CAMER);
                }
            } else {//隐藏键盘，隐藏列表
                closeKeybord(etExpressNum);
                //  disView(0);
            }
        });

        etExpressNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (handleData.queryData("", lvSearchResult) == 0) {
                    //    disView(0);
                } else if (canVis) {
                    //    disView(START_CAMER);
                }

                if (s.toString().trim().length() == 0) {
                    tvSearchTip.setText(getString(R.string.search));
                } else {
                    tvSearchTip.setText(getString(R.string.search_result));
                }
                String tepName = etExpressNum.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                handleData.queryData(tepName, lvSearchResult);
            }
        });

        //点击listView 收缩
        lvExpressData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                disView(0);
            }
        });

        /*历史记录点击事件*/
        lvSearchResult.setOnItemClickListener((parent, view, position, id) -> {
            canVis = false;
            TextView textView = view.findViewById(R.id.tv);
            String name = textView.getText().toString();
            checkExpress(name);
        });
    }

    @Override
    protected void initView() {

    }

    /**
     * 执行查询
     */
    private void checkExpress(String str) {
        disView(0);
        handleData.insertData(str);
        closeKeybord(etExpressNum);
        etExpressNum.setText(str);
        loadExpress(str);
    }

    /**
     * 线程解析快递
     *
     * @param pid
     */
    private void loadExpress(String pid) {
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
        try {
            api.getOrderTracesByJson(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case START_CAMERA_RESULT:
                //   disView(0);
                String startCR = (String) event.getData();
                LogisticsInfoBean bean = GsonBuildUtil.fromJson(startCR, LogisticsInfoBean.class);
                Collections.reverse(bean.getTraces());
                MessListAdapter adapter = new MessListAdapter(_mActivity, bean.getTraces());
                lvExpressData.setAdapter(adapter);
                lvExpressData.setVisibility(View.VISIBLE);
                LatteLogger.d("startCR", startCR);
                break;
        }
    }

    /**
     * 关闭软键盘
     */
    private void closeKeybord(EditText etExpressNum) {
        hideSoftKeyBoard();
    }

    /**
     * 历史记录的显示隐藏动画
     **/
    private void disView(int mode) {
        if (mode == 0 && svSearchList.getVisibility() == View.VISIBLE) {
            svSearchList.setVisibility(View.GONE);
            svSearchList.setAnimation(AnimationUtils.loadAnimation(_mActivity, R.anim.scale_top_out));
        } else if (mode == 1 && svSearchList.getVisibility() == View.GONE) {
            svSearchList.setVisibility(View.VISIBLE);
            svSearchList.setAnimation(AnimationUtils.loadAnimation(_mActivity, R.anim.scale_top_in));
        }
    }

    @OnClick({R.id.tv_end, R.id.tv_clear, R.id.bt_qrcode, R.id.et_express_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_end:
                String str = etExpressNum.getText().toString().trim();
                if (str.equals("")) {
                    ToastUtil.getInstance().showToast("快递单号不能空噢！");
                    return;
                }
                canVis = false;
                checkExpress(str);
                break;
            case R.id.tv_clear:
                handleData.deleteData();
                handleData.queryData("", lvSearchResult);
                disView(0);
                break;
            case R.id.bt_qrcode:
                checkCameraPermissions();
                break;
            case R.id.et_express_num:
                if (handleData.queryData("", lvSearchResult) == 0) {
                    disView(0);
                } else {
                    disView(START_CAMER);
                }
                break;
        }
    }

    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(_mActivity, perms)) {//有权限
            disView(0);
            startScan(_mActivity, QrConfig.TYPE_ALL, QrConfig.SCANVIEW_TYPE_QRCODE);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App需要用到读写权限",
                    RC_CAMERA, perms);
        }
    }

    public void startScan(Activity activity, int scan_type, int scan_view_type) {
        int screen = QrConfig.SCREEN_SENSOR;
        int line_style = ScanLineView.style_line;
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("将二维码/条形码放入框内，即可自动扫描")//扫描框下文字
                .setShowDes(true)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setNeedCrop(true)//是否从相册选择后裁剪图片
                .setCornerColor(MyApplication.getContext().getResources().getColor(R.color.green))//设置扫描框颜色
                .setLineColor(MyApplication.getContext().getResources().getColor(R.color.green))//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(scan_type)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(scan_view_type)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_EAN13)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setDingPath(R.raw.qrcode)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText(MyApplication.getContext().getString(R.string.scan))//设置Tilte文字
                .setTitleSize(Utils.getFontSize(50))
                .setContentSize(Utils.getFontSize(40))
                .setTitleBackgroudColor(Color.parseColor("#262020"))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .setShowZoom(true)//是否开始滑块的缩放
                .setAutoZoom(true)//是否开启自动缩放(实验性功能，不建议使用)
                .setFingerZoom(false)//是否开始双指缩放
                .setDoubleEngine(false)//是否开启双引擎识别(仅对识别二维码有效，并且开启后只识别框内功能将失效)
                .setScreenOrientation(screen)//设置屏幕方式
                .setOpenAlbumText("选择要识别的图片")//打开相册的文字
                .setLooperScan(false)//是否连续扫描二维码
                .setLooperWaitTime(5000)//连续扫描间隔时间
                .setScanLineStyle(line_style)//扫描线样式
                .setAutoLight(false)//自动灯光
                .setShowVibrator(true)//是否震动提醒
                .create();
        QrManager.getInstance().init(qrConfig).startScan(activity, result -> {
            disView(0);
            checkExpress(result.getContent());
        });
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(false).fitsSystemWindows(true).statusBarDarkFont(false).statusBarColor(R.color.colorPrimary).navigationBarColor(R.color.colorPrimary).init();
    }
}



