package com.saike.tzh.homepage.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 主页
 * 参考资料:  http://android.662p.com/thread-6137-1-1.html
 */
public class MainActivity_drawlayout_toolbar extends BaseActivity {
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
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private List<Fragment> fragments = new ArrayList<>();
    private BroadcastReceiver netStateReceiver;
    private long exitTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawlayout);
        ButterKnife.bind( this ) ;
        EventBus.getDefault().register(this);
        initView();
        inittoolbar();
        checkNetworkStatus();
    }

    private void inittoolbar() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, new Toolbar(this), R.string.app_name,
                R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                Logutils.printD(TAG,"onDrawerClosed");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                Logutils.printD(TAG,"onDrawerOpened");
            }
        };
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
//        setDrawerLeftEdgeSize(this, mDrawerLayout, 0.5f);
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
        //替换内容布局
        replaceFragment(R.id.frame_container, fragments.get(0));
        //替换侧滑菜单布局
        replaceFragment(R.id.drawer_container, fragments.get(2));
    }

    protected void checkNetworkStatus() {
        netStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(
                        ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetWorkUtil.isNetWorkConnected(MainActivity_drawlayout_toolbar.this)) {
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
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.closeDrawers();//关闭
                }else{
                    mDrawerLayout.openDrawer(Gravity.LEFT);//开启
                }

                break;
            case R.id.iv_message:
                Toast.makeText(this, "iv_message", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    /**
     * 抽屉滑动范围控制
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage 占全屏的份额0~1
     */
    private void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            // find ViewDragHelper and set it accessible
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            // find edgesize and set is accessible
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            // set new edgesize
            // Point displaySize = new Point();
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            Log.e("NoSuchFieldException", e.getMessage().toString());
        } catch (IllegalArgumentException e) {
            Log.e("IllegalArgumentException", e.getMessage().toString());
        } catch (IllegalAccessException e) {
            Log.e("IllegalAccessException", e.getMessage().toString());
        }
    }
}
