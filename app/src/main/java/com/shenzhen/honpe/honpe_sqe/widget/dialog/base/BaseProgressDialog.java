package com.shenzhen.honpe.honpe_sqe.widget.dialog.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.reflect.TypeToken;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.AppConfig;
import com.shenzhen.honpe.honpe_sqe.api.Constants;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.utils.DBUtils;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.ProgressUtils;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.BaseProgressAdapter;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.adapter.bean.ProgressBean;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.MyDialog;
import com.shenzhen.honpe.honpe_sqe.widget.dialog.commo.listener.CommodityPresenterInf;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * FileName: BaseProgressDialog
 * Author: asus
 * Date: 2021/3/22 20:22
 * Description:物料完成进度
 */
public class BaseProgressDialog implements CommodityPresenterInf, View.OnClickListener {

    /*** 引用上下文*/
    private Context mContext;
    /*** 弹窗布局*/
    private View contentView;
    /*** 弹窗*/
    public MyDialog mBottomSheetDialog;
    private RecyclerView mRecyclerView;
    private BaseProgressAdapter adapter;
    private UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO bean;
    PersonEntity entity;
    private List<ProgressTime> progressTimes = new ArrayList<>();
    Button applySucceed, applyNormal;

    public BaseProgressDialog(Context mContext, UnQuotedBean.OfferModelDTO.申请单明细报价记录DTO bean) {
        this.mContext = mContext;
        this.bean = bean;
    }

    /**
     * 显示窗口
     */
    @Override
    public void showDialog() {
        entity = (PersonEntity) DBUtils.getSerializableEntity(AppConfig.TABLE_NAME_LOGIN, AppConfig.APP_LOGIN);
        mBottomSheetDialog = new MyDialog(mContext, R.style.GoodDialog);
        //设置退出速度
        mBottomSheetDialog.outDuration(200);
        mBottomSheetDialog.inDuration(200);
        //设置铺满
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        //解析视图
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_progress_dialog, null);
        mBottomSheetDialog.setContentView(contentView);
        mRecyclerView = contentView.findViewById(R.id.recyclerView);
        ImageView ivClose = contentView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        applyNormal = contentView.findViewById(R.id.apply_normal);
        applySucceed = contentView.findViewById(R.id.apply_succeed);
        applySucceed.setText("登记进度");
        applySucceed.setOnClickListener(this);
        applyNormal.setVisibility(View.GONE);
        applySucceed.setVisibility(View.VISIBLE);
        getStringData();
        mBottomSheetDialog.show();

    }

    private void getStringData() {
        EasyHttp.post(Constants.URL + "speedList")
                .retryCount(0)
                .params("strCode", bean.get申请单号())
                .params("strSID", entity.getValue().getSid())
                .params("strLingJianCode", bean.get零件编码())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        ProgressUtils.disLoadView(mContext, 1);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        ProgressUtils.disLoadView(mContext, 0);
                    }

                    @Override
                    public void onError(ApiException e) {
                        ProgressUtils.disLoadView(mContext, 0);
                        ToastUtil.getInstance().showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        List<ProgressBean> listProgress = GsonBuildUtil.create().fromJson(s, new TypeToken<List<ProgressBean>>() {
                        }.getType());
                        for (int i = 0; i < listProgress.size(); i++) {
                            ProgressBean bean = listProgress.get(i);
                            if (!TextUtils.isEmpty(bean.get进度1())) {
                                ProgressTime pt = new ProgressTime();
                                pt.setStrProgress(bean.get进度1());
                                pt.setStrDate(bean.get进度时间1());
                                pt.setJindu("进度1");
                                progressTimes.add(pt);
                            }
                            if (!TextUtils.isEmpty(bean.get进度2())) {
                                ProgressTime pt = new ProgressTime();
                                pt.setStrProgress(bean.get进度2());
                                pt.setStrDate(bean.get进度时间2());
                                pt.setJindu("进度2");
                                progressTimes.add(pt);
                            }
                            if (!TextUtils.isEmpty(bean.get进度3())) {
                                ProgressTime pt = new ProgressTime();
                                pt.setStrProgress(bean.get进度3());
                                pt.setStrDate(bean.get进度时间3());
                                pt.setJindu("进度3");
                                progressTimes.add(pt);
                            }
                            if (!TextUtils.isEmpty(bean.get进度4())) {
                                ProgressTime pt = new ProgressTime();
                                pt.setStrProgress(bean.get进度4());
                                pt.setStrDate(bean.get进度时间4());
                                pt.setJindu("进度4");
                                progressTimes.add(pt);
                            }
                            if (!TextUtils.isEmpty(bean.get进度5())) {
                                ProgressTime pt = new ProgressTime();
                                pt.setStrProgress(bean.get进度5());
                                pt.setStrDate(bean.get进度时间5());
                                pt.setJindu("进度5");
                                progressTimes.add(pt);
                            }
                        }
                        LatteLogger.d("testgetStringData", GsonBuildUtil.GsonBuilder(listProgress) + "\n" + GsonBuildUtil.GsonBuilder(progressTimes));
                        Utils.reverse(progressTimes);
                        adapter = new BaseProgressAdapter(progressTimes);
                        mRecyclerView.setAdapter(adapter);
                        if (progressTimes.size() >= 5) {
                            applySucceed.setBackgroundResource(R.drawable.shape_grey_radius5_pl5t2);
                        }

                        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(@NonNull BaseQuickAdapter baseAdapter, @NonNull View view, int position) {
                                BaseEditDialog dialog = new BaseEditDialog(mContext, "修改最新进度",progressTimes.get(position).getStrProgress());
                                dialog.showDialog();
                                dialog.setOnClickData(new BaseEditDialog.InputData() {
                                    @Override
                                    public void setOnClickData(String strProgress) {
                                        EasyHttp.post(Constants.URL + "AddSpeed")
                                                .params("strCode", bean.get申请单号())
                                                .params("strSID", entity.getValue().getSid())
                                                .params("strLingJian", bean.get零件编码())
                                                .params("strBaoJiaCode", bean.get报价单号())
                                                .params("strAddJindu", "进度" + (progressTimes.size()))
                                                .params("strContext", strProgress)
                                                .execute(new SimpleCallBack<String>() {
                                                    @Override
                                                    public void onStart() {
                                                        super.onStart();
                                                        ProgressUtils.disLoadView(mContext, 1);
                                                    }

                                                    @Override
                                                    public void onCompleted() {
                                                        super.onCompleted();
                                                        ProgressUtils.disLoadView(mContext, 0);
                                                    }

                                                    @Override
                                                    public void onError(ApiException e) {
                                                        ProgressUtils.disLoadView(mContext, 0);
                                                        ToastUtil.getInstance().showToast(e.getMessage());
                                                    }

                                                    @Override
                                                    public void onSuccess(String s) {
                                                        try {
                                                            JSONObject object = new JSONObject(s);
                                                            if (object.getBoolean("key")) {
                                                                progressTimes.get(position).setStrProgress(strProgress);
                                                                adapter.upData(progressTimes);
                                                                if (position == 0) {
                                                                    if (setProgressContent != null) {
                                                                        setProgressContent.setOnClick(strProgress);
                                                                    }
                                                                }
                                                                ToastUtil.getInstance().showToast(object.getString("value"));
                                                            } else {
                                                                ToastUtil.getInstance().showToast(object.getString("value"));
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                    }
                                });
                            }
                        });
                    }
                });
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_succeed:
                BaseEditDialog dialog = new BaseEditDialog(mContext, "登记最新进度","");
                if (progressTimes.size() >= 5) {
                    ToastUtil.getInstance().showToast("最多只能添加五条进度");
                } else {
                    dialog.showDialog();
                }

                dialog.setOnClickData(new BaseEditDialog.InputData() {
                    @Override
                    public void setOnClickData(String strProgress) {
                        ProgressTime progressTime = new ProgressTime();
                        progressTime.setStrProgress(strProgress);
                        progressTime.setStrDate(sdf.format(new Date()));
                        progressTime.setJindu("进度" + (progressTimes.size() + 1));

                        Collections.sort(progressTimes, new Comparator<ProgressTime>() {
                            @Override
                            public int compare(ProgressTime o1, ProgressTime o2) {
                                return o1.getJindu().compareTo(o2.getJindu());
                            }
                        });
                        progressTimes.add(progressTime);
                        LatteLogger.d("testSize", GsonBuildUtil.GsonBuilder(progressTimes.size()));

                        Utils.reverse(progressTimes);
                        adapter.upData(progressTimes);

                        EasyHttp.post(Constants.URL + "AddSpeed")
                                .retryCount(0)
                                .params("strCode", bean.get申请单号())
                                .params("strSID", entity.getValue().getSid())
                                .params("strLingJian", bean.get零件编码())
                                .params("strBaoJiaCode", bean.get报价单号())
                                .params("strAddJindu", "进度" + (progressTimes.size()))
                                .params("strContext", strProgress)
                                .execute(new SimpleCallBack<String>() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        ProgressUtils.disLoadView(mContext, 1);
                                    }

                                    @Override
                                    public void onCompleted() {
                                        super.onCompleted();
                                        ProgressUtils.disLoadView(mContext, 0);
                                    }

                                    @Override
                                    public void onError(ApiException e) {
                                        ProgressUtils.disLoadView(mContext, 0);
                                        ToastUtil.getInstance().showToast(e.getMessage());
                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        try {
                                            JSONObject object = new JSONObject(s);
                                            if (object.getBoolean("key")) {
                                                if (setProgressContent != null) {
                                                    setProgressContent.setOnClick(strProgress);
                                                    ToastUtil.getInstance().showToast(object.getString("value"));
                                                }
                                            } else {
                                                ToastUtil.getInstance().showToast(object.getString("value"));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                    }
                });
                break;
            case R.id.iv_close:
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    public SetProgressContent setProgressContent;

    public void setOnClickListener(SetProgressContent listener) {
        this.setProgressContent = listener;
    }

    public interface SetProgressContent {
        void setOnClick(String content);
    }

    public class ProgressTime {
        private String strProgress;
        private String strDate;
        private String jindu;

        public String getJindu() {
            return jindu;
        }

        public void setJindu(String jindu) {
            this.jindu = jindu;
        }

        public String getStrProgress() {
            return strProgress;
        }

        public void setStrProgress(String strProgress) {
            this.strProgress = strProgress;
        }

        public String getStrDate() {
            return strDate;
        }

        public void setStrDate(String strDate) {
            this.strDate = strDate;
        }
    }

}





