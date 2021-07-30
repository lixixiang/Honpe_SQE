package com.shenzhen.honpe.honpe_sqe.app.e_package.child.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.CompanyEntity;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.utils.BankInfoUtil;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.StringUtil;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;
import com.shenzhen.honpe.honpe_sqe.utils.gson.GsonBuildUtil;
import com.shenzhen.honpe.honpe_sqe.widget.ContentWithSpaceEditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FileName: UserInfoContentAdapter
 * Author: asus
 * Date: 2021/7/20 9:39
 * Description:
 */
public class UserInfoContentAdapter extends BaseQuickAdapter<String, BaseViewHolder>  {
    ImageView ivRightDirectory;
    ContentWithSpaceEditText etContraryBank;
    EditText etContent;
    private int curPos;
    private PersonEntity personEntity = new PersonEntity();
    private CompanyEntity companyEntity = new CompanyEntity();
    private BankInfoUtil mInfoUtil;

    public UserInfoContentAdapter(int position, @Nullable List<String> data) {
        super(R.layout.item_user_info_content, data);
        curPos = position;
        LatteLogger.d("curPos", curPos);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, String s) {
        holder.setText(R.id.tv_title, s);
        ivRightDirectory = holder.getView(R.id.iv_right_directory);
        etContraryBank = holder.getView(R.id.et_contrary_bank);
        etContent = holder.getView(R.id.et_content);
        holder.getView(R.id.re_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.getInstance().showToast(holder.getAdapterPosition());

            }
        });
        if (curPos == 0) {
            switch (holder.getAdapterPosition()) {
                case 0:
                    StringUtil.HintUtil(etContent, "营业执照名称，如西安市xxx高赛克门业加工厂", 12);
                    personEntity.setCompany(etContent.getText().toString());
                    break;
                case 1:
                    StringUtil.HintUtil(etContent, "输入公司邮箱或者自己邮箱用于接收中标消息和发票", 12);
                    personEntity.setEmail(etContent.getText().toString());
                    break;
                case 2:
                    StringUtil.HintUtil(etContent, "输入正在使用的手机号", 12);
                    personEntity.setPhone(etContent.getText().toString());
                    break;
                case 3:
                    StringUtil.HintUtil(etContent, "输入目前所在地址", 12);
                    personEntity.setAdress(etContent.getText().toString());
                    break;
            }
        } else {
            switch (holder.getAdapterPosition()) {
                case 0:
                    StringUtil.HintUtil(etContraryBank, "输入有效的银行卡号用于接收中标款项", 12);
                    etContent.setVisibility(View.GONE);
                    etContraryBank.setVisibility(View.VISIBLE);
                    ivRightDirectory.setVisibility(View.VISIBLE);
                    ivRightDirectory.setImageResource(R.drawable.iv_carmer);

                    ivRightDirectory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.getInstance().showToast("测试");
                        }
                    });
                    etContraryBank.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            companyEntity.setBankNum(etContraryBank.getText().toString().replaceAll(" ",""));
                            notifyDataSetChanged();
                            LatteLogger.d("testCardNum", GsonBuildUtil.GsonToString(companyEntity));
                        }
                    });
                    break;
                case 1:
                    StringUtil.HintUtil(etContent, "自动匹配银行卡", 12);
                    String cardNum = companyEntity.getBankNum();
                        etContent.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(cardNum) && checkBankCard(cardNum)) {
                            mInfoUtil = new BankInfoUtil(cardNum);
                            etContent.setText(mInfoUtil.getBankName());
                        } else {
                            etContent.setText("");
                        }

                    break;
                case 2:
                    StringUtil.HintUtil(etContent, "选择供应商属于哪种加工类型（可多选）", 12);
                    break;
            }
        }
    }


    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}


























