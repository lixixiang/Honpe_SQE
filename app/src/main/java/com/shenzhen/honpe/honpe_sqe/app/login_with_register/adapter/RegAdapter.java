package com.shenzhen.honpe.honpe_sqe.app.login_with_register.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.RegisterBean;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.Utils;
import com.shenzhen.honpe.honpe_sqe.widget.DJEditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: RegAdapter
 * Author: asus
 * Date: 2021/3/8 17:25
 * Description:
 */
public class RegAdapter extends BaseQuickAdapter<RegisterBean, BaseViewHolder> {
    TextView tvTitle;
    DJEditText etContent;
    int etFocusPos = -1;
    List<RegisterBean> data;
    //edittext里的文字内容集合
    SparseArray<String> etTextAry = new SparseArray();
    Activity activity;

    public RegAdapter(Activity activity, @Nullable List<RegisterBean> data) {
        super(R.layout.item_register, data);
        this.data = data;
        this.activity = activity;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, RegisterBean bean) {
        holder.setIsRecyclable(false);
        tvTitle = holder.getView(R.id.item_title);
        etContent = holder.getView(R.id.et_content);
        tvTitle.setText(bean.getTitle());
        etContent.setText(etTextAry.get(holder.getAdapterPosition()));
        Utils.setEditTextHint(etContent, 14, bean.getHint());

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etTextAry.put(etFocusPos, s.toString());//更新list的数据,防止rv滑动的时候重新绘制,数据还是之前的
                for (int i = 0; i < etTextAry.size(); i++) {
                    String content = etTextAry.valueAt(i);
                    int tKey = etTextAry.keyAt(i);
                    switch (tKey) {
                        case 0:
                            data.get(tKey).setAccount(content);
                            break;
                        case 1:
                            data.get(tKey).setName(content);
                            break;
                        case 2:
                            data.get(tKey).setPassword(content);
                            break;
                        case 3:
                            data.get(tKey).setEmail(content);
                            break;
                        case 4:
                            data.get(tKey).setPhone(content);
                            break;
                        case 5:
                            data.get(tKey).setCompanyName(content);
                            break;
                        case 6:
                            data.get(tKey).setCompanySimpleName(content);
                            break;
                        case 7:
                            data.get(tKey).setOrganizingCode(content);
                            break;
                        case 8:
                            data.get(tKey).setTaxID(content);
                            break;
                        case 9:
                            data.get(tKey).setContraryBank(content);
                            break;
                        case 10:
                            data.get(tKey).setContraryAccount(content);
                            break;
                        case 11:
                            data.get(tKey).setSupplyClassification(content);
                            break;
                        case 12:
                            data.get(tKey).setUploadBusiness(content);
                            break;
                        case 13:
                            data.get(tKey).setUploadOrganization(content);
                            break;
                        case 14:
                            data.get(tKey).setUploadTaxCertificate(content);
                            break;
                    }
                }

                LatteLogger.d("testRegister", GsonBuildUtil.GsonBuilder(bean));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etFocusPos = holder.getAdapterPosition();
                }
                LatteLogger.d("testEtPos", etFocusPos);
            }
        });
    }
}





