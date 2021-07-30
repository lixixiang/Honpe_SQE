package com.shenzhen.honpe.honpe_sqe.widget.dialog.tipsDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.flyco.roundview.RoundTextView;
import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.api.FinalClass;
import com.shenzhen.honpe.honpe_sqe.app.login_with_register.bean.PersonEntity;
import com.shenzhen.honpe.honpe_sqe.bean.Event;
import com.shenzhen.honpe.honpe_sqe.utils.EventBusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * FileName: SelectorTypeDialog
 * Author: asus
 * Date: 2021/7/19 16:24
 * Description:
 */
public class SelectorTypeDialog extends Dialog {
    @BindView(R.id.tv_person)
    RoundTextView tvPerson;
    @BindView(R.id.tv_company)
    RoundTextView tvCompany;
    Unbinder unbinder;

    public SelectorTypeDialog(@NonNull Context context) {
        super(context, R.style.tips_dialog);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_selector);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_person, R.id.tv_company,R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_person:
                if (personInfo != null) {
                    personInfo.strType(tvPerson.getText().toString());
                }
                break;
            case R.id.tv_company:
                if (personInfo != null) {
                    personInfo.strType(tvCompany.getText().toString());
                }
                break;
            case R.id.tv_back:
                EventBusUtil.sendEvent(new Event(FinalClass.BACK_DATA));
                break;
        }
        dismiss();
    }

    personInfo personInfo;

    public void setOnSelectorPerson(personInfo personInfo){
        this.personInfo = personInfo;
    }

    public interface personInfo{
        void strType(String type);
    }

}




































