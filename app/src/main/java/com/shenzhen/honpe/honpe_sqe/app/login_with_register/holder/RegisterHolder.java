package com.shenzhen.honpe.honpe_sqe.app.login_with_register.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenzhen.honpe.honpe_sqe.R;

import org.jetbrains.annotations.NotNull;

/**
 * FileName: RegisterHolder
 * Author: asus
 * Date: 2021/3/9 12:09
 * Description:
 */
public class RegisterHolder extends BaseViewHolder {
    public TextView tvTitle;
    public EditText etContent;
    public RegisterHolder(@NotNull View view) {
        super(view);
         tvTitle = view.findViewById(R.id.item_title);
         etContent = view.findViewById(R.id.et_content);
    }
}
