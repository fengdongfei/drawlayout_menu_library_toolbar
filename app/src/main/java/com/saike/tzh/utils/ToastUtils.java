package com.saike.tzh.utils;

import android.app.Activity;
import android.view.Gravity;

import com.saike.tzh.R;


/**
 * Created by admin on 2017/2/21.
 */

public  class ToastUtils {
    public static void showAppMsg(int id, Activity context, CharSequence msg) {
        final AppMsg.Style style;
        // 选择显示的样式
        switch (id) {
            // 警告
            case 1:
                style = AppMsg.STYLE_ALERT;
                break;
            // 确认
            case 2:
                style = AppMsg.STYLE_CONFIRM;
                break;
            // 信息
            case 3:
                style = AppMsg.STYLE_INFO;
                break;
            // 自定义
            case 4:
                style = new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.yellw_01);
                break;

            default:
                return;
        }

        // 创建AppMsg对象，并定义显示的信息和样式
        AppMsg appMsg = AppMsg.makeText(context, msg, style);
        // 设置Toast显示的位置,默认显示在屏幕的最上方
//        if (((CheckBox) (findViewById(R.id.bottom))).isChecked()) {
//            appMsg.setLayoutGravity(Gravity.BOTTOM);
//        }
        appMsg.setLayoutGravity(Gravity.BOTTOM);
        appMsg.show();
    }
}
