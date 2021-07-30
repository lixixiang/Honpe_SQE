package com.shenzhen.honpe.honpe_sqe.utils;

import com.shenzhen.honpe.honpe_sqe.bean.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * FileName: EventBusUtil
 * Author: asus
 * Date: 2021/1/18 17:57
 * Description:事件分发工具
 */
public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }
}

