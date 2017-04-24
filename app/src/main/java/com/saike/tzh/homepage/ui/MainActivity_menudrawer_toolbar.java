package com.saike.tzh.homepage.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.saike.tzh.R;
import com.saike.tzh.account.event.NetWorkEvent;
import com.saike.tzh.base.BaseActivity;
import com.saike.tzh.utils.Logutils;
import com.saike.tzh.utils.NetWorkUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 主页
 * 参考资料:  http://android.662p.com/thread-6137-1-1.html
 */
public class MainActivity_menudrawer_toolbar extends BaseActivity {
    String TAG=getClass().getName();
    private static final float SELECT_TITLE_TEXT_SIZT = 20;
    private static final float UNSELECT_TITLE_TEXT_SIZT = 16;
    @BindView(R.id.im_toggle)
    ImageView im_toggle;
    @BindView(R.id.rb_tzh)
    RadioButton rb_tzh;
    @BindView(R.id.rb_mine)
    RadioButton rb_mine;
    @BindView(R.id.frame_container)
    FrameLayout frame_container;
    @BindView(R.id.iv_message)
    ImageView  iv_message;
    @BindView(R.id.v_toolbar)
    Toolbar mToolbar;
    private List<Fragment> fragments = new ArrayList<>();
    private BroadcastReceiver netStateReceiver;
    private long exitTime;
    private SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        ButterKnife.bind( this ) ;
        EventBus.getDefault().register(this);
        initView();
        inittoolbar();
        checkNetworkStatus();
        initmenu();
    }

    private void inittoolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //隐藏toolbar的自带左边小箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void initmenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMenu(R.layout.left_menu_frame);
        //侧拉栏目所在方向
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // 设置渐入渐出效果的值
//        slidingMenu.setFadeDegree(0.35f);
        //设置菜单占屏幕的比例
        slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 2);
        //设置滑动时菜单的是否淡入淡出
        slidingMenu.setFadeEnabled(true);
//设置淡入淡出的比例
        slidingMenu.setFadeDegree(0.4f);
//设置滑动时拖拽效果
        slidingMenu.setBehindScrollScale(0);
        //设置侧拉条目的宽度
//		slidingMenu.setBehindWidth(10);
        //设置一个分割线
//        slidingMenu.setShadowDrawable(R.drawable.shadow);
        //给分割线设置宽度
//        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        //侧拉栏目的触摸范围
		/*
		 * SlidingMenu.TOUCHMODE_FULLSCREEN 全屏拖拽
		 * SlidingMenu.TOUCHMODE_MARGIN  边缘拖拽
		 * SlidingMenu.TOUCHMODE_NONE  不能拖拽
		 */
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置右侧侧栏目
//		slidingMenu.setSecondaryMenu(R.layout.right_menu_frame);
        //右侧侧拉栏目设置相应的分割线
//		slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
        replaceFragment(R.id.frame_container, fragments.get(0));
        //替换侧滑菜单布局
        replaceFragment(R.id.menu, fragments.get(2));
        slidingMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
                                            @Override
                                            public void onClosed() {
                                                Logutils.printD(TAG,"setOnClosedListener");
                                            }
                                        }
        );
        slidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                Logutils.printD(TAG,"setOnOpenListener");
            }
        });
    }

    protected void initView() {
        rb_tzh.setOnClickListener(noDoubleListener);
        rb_mine.setOnClickListener(noDoubleListener);
        im_toggle.setOnClickListener(noDoubleListener);
        iv_message.setOnClickListener(noDoubleListener);
        fragments.add( new MainFragment());
        fragments.add(new MineFragment());
        fragments.add( new MenuFragment());
        rb_tzh.setTextSize(SELECT_TITLE_TEXT_SIZT);
        rb_mine.setTextSize(UNSELECT_TITLE_TEXT_SIZT);

    }

    protected void checkNetworkStatus() {
        netStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(
                        ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetWorkUtil.isNetWorkConnected(MainActivity_menudrawer_toolbar.this)) {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.AVAILABLE));
                    } else {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.UNAVAILABLE));
                    }
                }
            }
        };

        registerReceiver(netStateReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(NetWorkEvent event) {

//        if (event.getType() == NetWorkEvent.UNAVAILABLE) {
//
//            if (noNetWorkDialog == null) {
//                noNetWorkDialog = new MaterialDialog.Builder(MainActivity_menudrawer_toolbar.this)
//                        .title("无网络连接")
//                        .content("去开启网络?")
//                        .positiveText("是")
//                        .backgroundColor(getResources().getColor(JDApplication.COLOR_OF_DIALOG))
//                        .contentColor(JDApplication.COLOR_OF_DIALOG_CONTENT)
//                        .positiveColor(JDApplication.COLOR_OF_DIALOG_CONTENT)
//                        .negativeColor(JDApplication.COLOR_OF_DIALOG_CONTENT)
//                        .titleColor(JDApplication.COLOR_OF_DIALOG_CONTENT)
//                        .negativeText("否")
//                        .callback(new MaterialDialog.ButtonCallback() {
//                            @Override
//                            public void onPositive(MaterialDialog dialog) {
//                                Intent intent = new Intent(
//                                        Settings.ACTION_WIRELESS_SETTINGS);
//                                startActivity(intent);
//                            }
//
//                            @Override
//                            public void onNegative(MaterialDialog dialog) {
//                            }
//                        })
//                        .cancelable(false)
//                        .build();
//            }
//            if (!noNetWorkDialog.isShowing()) {
//                noNetWorkDialog.show();
//            }
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netStateReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




    @Override
    public void NoDoubleClick(View var1) {
        super.NoDoubleClick(var1);
        switch (var1.getId()){
            case R.id.rb_mine:
                rb_mine.setChecked(true);
                rb_mine.setTextSize(SELECT_TITLE_TEXT_SIZT);
                rb_tzh.setTextSize(UNSELECT_TITLE_TEXT_SIZT);
                replaceFragment(R.id.frame_container, fragments.get(1));
                break;
            case R.id.rb_tzh:
                rb_tzh.setChecked(true);
                rb_tzh.setTextSize(SELECT_TITLE_TEXT_SIZT);
                rb_mine.setTextSize(UNSELECT_TITLE_TEXT_SIZT);
                replaceFragment(R.id.frame_container, fragments.get(0));
                break;
            case R.id.im_toggle:
                    slidingMenu.toggle();
                break;
            case R.id.iv_message:
                Toast.makeText(this, "iv_message", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
