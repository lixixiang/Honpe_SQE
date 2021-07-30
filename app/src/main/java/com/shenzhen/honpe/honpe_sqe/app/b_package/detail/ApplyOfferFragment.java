package com.shenzhen.honpe.honpe_sqe.app.b_package.detail;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.DataClass;
import com.shenzhen.honpe.honpe_sqe.app.b_package.bean.OrderHomeBean;
import com.shenzhen.honpe.honpe_sqe.app.b_package.detail.adapter.ApplyOfferAdapter;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * FileName: ApplyOfferFragment
 * Author: asus
 * Date: 2021/1/22 11:54
 * Description:申请报价
 */
public class ApplyOfferFragment extends BaseBackFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_back)
    RelativeLayout llBack;
    @BindView(R.id.toolbar)
    Toolbar llTitleBar;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.iv_order_icon)
    CircleImageView ivOrderIcon;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    @BindView(R.id.tv_applyDate)
    TextView tvApplyDate;
    @BindView(R.id.tv_principal)
    TextView tvPrincipal;
    @BindView(R.id.tv_classify)
    TextView tvClassify;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ApplyOfferAdapter adapter;
    OrderHomeBean bean;
    private PopupWindow mPopWindow;
    EditText etPrice, etRate, etDeliverDate;

    public static ApplyOfferFragment newInstance(OrderHomeBean bean) {
        ApplyOfferFragment fragment = new ApplyOfferFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_apply_offer;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        tvTitle.setText("外发采购报价单");

        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (OrderHomeBean) bundle.getSerializable("key");
            tvApplyDate.setText("发布日期："+bean.getApplyDate());
            tvClassify.setText("跟单负责人："+bean.getClassify());
            tvPrincipal.setText("采购分类："+bean.getPrincipal());
            tvStatus.setText(bean.getStatus() ==0? "未报价":"已报价");
            tvOrderName.setText(bean.getOrderNum());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new ApplyOfferAdapter(DataClass.orderDetailData());
        recyclerView.setAdapter(adapter);

    }
//
//    private void showPopupWindow() {
//        View view = LayoutInflater.from(_mActivity).inflate(R.layout.css_count_window_up_layout, null);
//        mPopWindow = new PopupWindow(view,
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        mPopWindow.setContentView(view);
//        etPrice = view.findViewById(R.id.et_price);
//        etRate = view.findViewById(R.id.et_rate);
//        etDeliverDate = view.findViewById(R.id.et_deliver_date);
//       // mPopWindow.setAnimationStyle(R.style.MenuAnim);
//        mPopWindow.showAtLocation(llTitleBar, Gravity.TOP, 0, 150);
//    }
}






