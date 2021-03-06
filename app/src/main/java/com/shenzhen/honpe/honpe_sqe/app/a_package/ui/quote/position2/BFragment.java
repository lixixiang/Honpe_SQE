package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.adapter.BaseOrderAdapter;
import com.shenzhen.honpe.honpe_sqe.app.a_package.ui.quote.position1.bean.UnQuotedBean;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.utils.DateUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * FileName: AFragment
 * Author: asus
 * Date: 2021/1/28 9:34
 * Description:
 */
public class BFragment extends BaseBackFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.re_date_layout)
    RelativeLayout reDateLayout;
    @BindView(R.id.item_1)
    TextView item1;
    @BindView(R.id.item_2)
    TextView item2;
    @BindView(R.id.item_3)
    TextView item3;
    @BindView(R.id.item_4)
    TextView item4;
    @BindView(R.id.tv_last_month)
    TextView tvLastMonth;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_next_month)
    TextView tvNextMonth;
    @BindView(R.id.tv_tag_new)
    TextView tvTagNew;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.iv_date_down)
     ImageView ivDateDown;

//    @BindView(R.id.type)
//    TextView type;
//    @BindView(R.id.num)
//    TextView num;
//    @BindView(R.id.deliver)
//    TextView deliver;
//    @BindView(R.id.down)
//    ImageView down;
//    @BindView(R.id.expand)
//    LinearLayout expandBar;
//    @BindView(R.id.part_num)
//    TextView partNum;
//    @BindView(R.id.order_num)
//    TextView orderNum;

            ArrayList<UnQuotedBean> listData;
    BaseOrderAdapter orderAdapter;
    private String title;
    //item??????????????????
    private int mSuspensionBarHeight;
    //??????item??????
    private int mCurrentPosition = 0;
    SimpleDateFormat myDay = new SimpleDateFormat("dd");
    boolean isOpen = false;


    public static BFragment newInstance(Bundle bundle) {
        BFragment fragment = new BFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.a_fragment;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        Bundle bundle = getArguments();
        listData = (ArrayList<UnQuotedBean>) bundle.getSerializable("list");
        title = bundle.getString("title");
        item4.setText("????????????");
        ivDateDown.setColorFilter(getResources().getColor(R.color.white));
        initData();
    }

    @Override
    protected void initView() {
        mToolbar.setVisibility(View.GONE);
        reDateLayout.setVisibility(View.GONE);
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        Utils.reverse(listData);
        orderAdapter = new BaseOrderAdapter(listData, title);
        mRecyclerView.setAdapter(orderAdapter);
        mRecyclerView.setHasFixedSize(true);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                //????????????????????????
//                mSuspensionBarHeight = expandBar.getHeight();
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                //?????????????????????????????????
//                //???????????????item
//                View view = manager.findViewByPosition(mCurrentPosition + 1);
//                if (view != null) {
//                    if (view.getTop() <= mSuspensionBarHeight) {
//                        //??????????????????????????????
//                        expandBar.setY(-(mSuspensionBarHeight - view.getTop()));
//                    } else {
//                        //????????????????????????
//                        expandBar.setY(0);
//                    }
//                    if (mCurrentPosition != manager.findFirstVisibleItemPosition()) {
//                        mCurrentPosition = manager.findFirstVisibleItemPosition();
//                        expandBar.setY(0);
//                        updateSuspensionBar();
//                    }
//                }
//            }
//        });
//        updateSuspensionBar();
    }

    /**
     * ?????????????????????
     */
    private void updateSuspensionBar() {
        if (listData.size() > 0) {
//            expandBar.setBackgroundResource(R.color.white);
//            LatteLogger.d("Nest", GsonBuildUtil.GsonBuilder(listData.get(mCurrentPosition)));
//            orderNum.setText(listData.get(mCurrentPosition).get????????????());
//            type.setText(listData.get(mCurrentPosition).get????????????());
//            num.setText(listData.get(mCurrentPosition).get????????????() + "");
//            deliver.setText(StringUtil.formatDouble(Double.parseDouble(listData.get(mCurrentPosition).get????????????())));
//            partNum.setText(myDay.format(DateUtil.setDate(listData.get(mCurrentPosition).get????????????())) + "/" +
//                    DateUtil.dateToWeek(DateUtil.getStringOfDay((DateUtil.setDate(listData.get(mCurrentPosition).get????????????())))).replace("??????", ""));
//            expandBar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (isOpen) {
//                        down.setImageResource(R.mipmap.ic_up);
//                    }else {
//                        down.setImageResource(R.mipmap.ic_down);
//                    }
//                    isOpen = !isOpen;
//                    listData.get(mCurrentPosition).setOpen(isOpen);
//                    orderAdapter.addNewsData(listData);
//                }
//            });
        }
    }
}


















