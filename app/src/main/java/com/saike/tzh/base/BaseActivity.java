package com.saike.tzh.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.saike.tzh.R;
import com.saike.tzh.utils.NoDoubleClickLstener;
import com.saike.tzh.utils.StatusBarUtil;
import com.saike.tzh.widget.EssentialMethod;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/22.
 */

public class BaseActivity extends AppCompatActivity implements NoDoubleClickLstener.OnClickListener,EssentialMethod {

    public static String TAG = "BaseActivity";
    public NoDoubleClickLstener noDoubleListener;
    public   EssentialMethod methor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        methor = (EssentialMethod)this;
        methor.getDoubleClickListener();
        methor.getTag();
        methor.setStatusBar();
    }



    /**
     * 得到没有连点的点击事件
     * @param var1
     */
    @Override
    public void NoDoubleClick(View var1) {

    }

    /**
     * 获取log标示类名Tag
     */
    @Override
    public void getTag() {
        TAG= getClass().getName();
    }
    /**
     * 获取防止连点的listener
     */
    @Override
    public void getDoubleClickListener() {
        noDoubleListener= new NoDoubleClickLstener(this);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setStatusBarTranslucent(this, false);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_none, R.anim.trans_center_2_right);
    }

    /**
     * 替换fragement
     * @param id_content
     * @param fragment
     */
    public void replaceFragment(int id_content, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id_content, fragment);
        transaction.commit();
    }
}
