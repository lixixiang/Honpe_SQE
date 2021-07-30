package com.shenzhen.honpe.honpe_sqe.app.a_package;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.GridSpanSizeLookup;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shenzhen.honpe.honpe_sqe.MainFragment;
import com.shenzhen.honpe.honpe_sqe.MyApplication;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.DataClass;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.api.MenuConfig;
import com.shenzhen.honpe.honpe_sqe.app.a_package.adapter.HomeAdapter;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.MenuEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.bean.QuickMultipleEntity;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.childBanner.ChildBannerFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.contact_way.ContactWayFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.feedback.FeedBackFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs.LogisticsCheckFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.news.NewsFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.AFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position2.QuotedFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position3.ProgressManagerFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position4.ShipmentQueryFragment;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position5.ExchangeRecordFragment;
import com.shenzhen.honpe.honpe_sqe.base.BaseMainFragment;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.NetUtil;
import com.shenzhen.honpe.honpe_sqe.utils.scan.ScanManager;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.UpdateDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;

import static com.mob.tools.utils.DeviceHelper.getApplication;

/**
 * FileName: HomeFragment
 * Author: asus
 * Date: 2021/1/25 10:36
 * Description:
 */
public class HomeFragment extends BaseMainFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_order)
    ImageView ivOrder;
    @BindView(R.id.iv_Logistics)
    ImageView ivLogistics;
    @BindView(R.id.iv_way)
    ImageView ivWay;

    private  MyApplication appContext;
    private  HomeAdapter adapter;
    private  List<QuickMultipleEntity> mList;
    private List<MenuEntity> indexDataList = new ArrayList<>();
    private List<MenuEntity> indexDataAll = new ArrayList<>();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        appContext = (MyApplication) getApplication();
        NetUtil.NewVersionRequest(_mActivity);//检测版本是否一致
        GridLayoutManager manager = new GridLayoutManager(_mActivity, 5);
        initSubMenu();
        recyclerView.setLayoutManager(manager);

        adapter.setGridSpanSizeLookup(new GridSpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int viewType, int position) {
                return DataClass.getMultipleItemData(indexDataList).get(position).getSpanSize();
            }
        });
        getHeadView();
        initImageView();
        adapter.addFooterView(getFootView());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_icon_text:
                        if (position == 2) {
                            ((MainFragment) getParentFragment()).startBrotherFragment(QuotedFragment.newInstance(DataClass.getMultipleItemData(indexDataList).get(position)));
                        } else if (position == 3) {//加工进度
                            ((MainFragment) getParentFragment()).startBrotherFragment(ProgressManagerFragment.newInstance(DataClass.getMultipleItemData(indexDataList).get(position)));
                        } else if (position == 4) {
                            ((MainFragment) getParentFragment()).startBrotherFragment(ShipmentQueryFragment.newInstance(DataClass.getMultipleItemData(indexDataList).get(position)));
                        } else if (position == 5) {
                            ((MainFragment) getParentFragment()).startBrotherFragment(ExchangeRecordFragment.newInstance(DataClass.getMultipleItemData(indexDataList).get(position)));
                        } else {
                            ((MainFragment) getParentFragment()).startBrotherFragment(AFragment.newInstance(DataClass.getMultipleItemData(indexDataList).get(position)));
                        }
                        break;
                    case R.id.ll_new:
                        ((MainFragment) getParentFragment()).startBrotherFragment(NewsFragment.newInstance(mList.get(position).getNewsContent()));
                        break;
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(mActivity).resumeRequests();
                } else {
                    Glide.with(mActivity).pauseRequests();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                if (totalDy < 0) {
                    totalDy = 0;
                }
            }
        });
    }

    /**
     * 子菜单列表初始化
     */
    private void initSubMenu() {
        //初始化
        JsonParser parser = new JsonParser();
        String strByJson = GsonBuildUtil.getCityJson(_mActivity, "menulist");
        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
        Gson gson = new Gson();
        //加强for循环遍历JsonArray
        for (JsonElement indexArr : jsonArray) {
            //使用GSON，直接转成Bean对象
            MenuEntity menuEntity = gson.fromJson(indexArr, MenuEntity.class);
            indexDataAll.add(menuEntity);
        }
        LatteLogger.d("testDDDDD",GsonBuildUtil.GsonBuilder(indexDataAll));
        appContext.saveObject((Serializable) indexDataAll, MenuConfig.KEY_All);

        List<MenuEntity> indexDataUser = (List<MenuEntity>) appContext.readObject(MenuConfig.KEY_USER);
        if (indexDataUser == null || indexDataUser.size() == 0) {
            appContext.saveObject((Serializable) indexDataAll,MenuConfig.KEY_USER);
        }

        indexDataList = (List<MenuEntity>) appContext.readObject(MenuConfig.KEY_USER);
        MenuEntity allMenuEntity = new MenuEntity();
        allMenuEntity.setIco("ic_home_all");
        allMenuEntity.setId("all");
        allMenuEntity.setTitle("全部");
        allMenuEntity.setEnable(true);

        indexDataList.add(allMenuEntity);
        mList = DataClass.getMultipleItemData(indexDataList);
        adapter = new HomeAdapter(mList);
    }

    private void initImageView() {
        ivScan.setColorFilter(getResources().getColor(R.color.white));
        ivOrder.setColorFilter(getResources().getColor(R.color.white));
        ivLogistics.setColorFilter(getResources().getColor(R.color.white));
        ivWay.setColorFilter(getResources().getColor(R.color.white));
    }

    private View getFootView() {
        View view = getLayoutInflater().inflate(R.layout.item_foot_view, recyclerView, false);
        return view;
    }

    private void getHeadView() {

    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        switch (event.getCode()) {
            case FinalClass.ENTER_SERVICE_CENTER:
                QuickMultipleEntity entity = (QuickMultipleEntity) event.getData();
                LatteLogger.d("testPos", GsonBuildUtil.GsonBuilder(entity));
                ((MainFragment) getParentFragment()).startBrotherFragment(ChildBannerFragment.newInstance(entity));
                break;
            case FinalClass.UP_VERSION:
                Bundle bundle = (Bundle) event.getData();
                int curVersion = Utils.getVersionCode(_mActivity);
                LatteLogger.d("testBundle",GsonBuildUtil.GsonBuilder(bundle)+"   "+curVersion);

                if (curVersion < bundle.getInt("key")) {
                    UpdateDialog dialog = new UpdateDialog(_mActivity, bundle);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                }
                break;
            case FinalClass.REQUEST_CODE_APP_INSTALL:
                String apkFile = (String) event.getData();
                LatteLogger.d("apkFile", apkFile);
                installApk(apkFile);
                break;
        }
    }

    private void installApk(String apkFile) {
        File apk = new File(apkFile);
        LatteLogger.d("apkFile", apk + "");
        Uri uri = Uri.fromFile(apk);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(_mActivity, getContext().getPackageName() + ".fileProvider", apk);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        startActivity(intent);
        Process.killProcess(Process.myPid());
    }

    @OnClick({R.id.ll_scan, R.id.ll_order, R.id.ll_Logistics, R.id.ll_way})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_scan:
                ScanManager.startScan(_mActivity, QrConfig.TYPE_QRCODE, QrConfig.SCANVIEW_TYPE_QRCODE);
                break;
            case R.id.ll_order:
                ((MainFragment) getParentFragment()).startBrotherFragment(FeedBackFragment.newInstance(getString(R.string.order)));
                break;
            case R.id.ll_Logistics:
                if (Utils.checkPackInfo(_mActivity)) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse("alipays://platformapi/startapp?appId=20000754"));
                    intent.putExtra("", "");//这里Intent当然也可传递参数,但是一般情况下都会放到上面的URL中进行传递
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    ((MainFragment) getParentFragment()).startBrotherFragment(LogisticsCheckFragment.newInstance(getString(R.string.Logistics)));
                }
                break;
            case R.id.ll_way:
                ((MainFragment) getParentFragment()).startBrotherFragment(ContactWayFragment.newInstance(getString(R.string.contact_way)));
                break;
        }
    }
}























