package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.homebtn.logistercs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: Honpe
 * @CreateDate: 2020/7/17 13:51
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterPermissionGranted {
    int value();
}
