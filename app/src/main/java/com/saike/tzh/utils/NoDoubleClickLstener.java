package com.saike.tzh.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by admin on 2017/4/22.
 */

public class NoDoubleClickLstener extends CustomClickListener {
    private  Context context;

    public NoDoubleClickLstener(Context context) {
        this.context=context;
    }

    @Override
    protected void onNoDoubleClick(View v) {
        OnClickListener Listener=(OnClickListener)context;
        Listener.NoDoubleClick(v);
    }

    public interface OnClickListener {
        void NoDoubleClick(View var1);
    }
}
