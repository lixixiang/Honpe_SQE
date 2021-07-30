package com.shenzhen.honpe.honpe_sqe.widget.dialog.tipsDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * FileName: TipsDialog
 * Author: asus
 * Date: 2021/3/11 22:44
 * Description:
 */
public class TipsDialog extends Dialog {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_sure)
    TextView btnSure;
    @BindView(R.id.loading)
    LinearLayout loading;
    Unbinder unbinder;

    private String title;

    public TipsDialog(@NonNull Context context, String title) {
        super(context, R.style.tips_dialog);
        this.title = title;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tips);
        unbinder = ButterKnife.bind(this);
        tvContent.setText(title);
    }

    public elseWork elseWork;

    public void setOnElseWork(elseWork elseWork){
        this.elseWork = elseWork;
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        if (elseWork != null) {
            elseWork.setOnClick();
        }
        dismiss();
    }

    public interface elseWork{
        void setOnClick();
    }
}
























