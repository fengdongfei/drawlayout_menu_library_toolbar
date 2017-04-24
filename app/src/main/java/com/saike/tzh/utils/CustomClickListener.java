package com.saike.tzh.utils;

import android.view.View;

import java.util.Calendar;

/**
 * 防止连续点击的自定义点击事件
 * Created by Chexiangjia-MAC on 17/4/21.
 */

abstract class CustomClickListener implements View.OnClickListener {


    private static final int MIN_CLICK_DELAY_TIME = 1000;    // 设置1秒内只能点击一次

    private long lastClickTime = 0;

    private int id = -1;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int mId = v.getId();
        if (id != mId) {
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);

}
