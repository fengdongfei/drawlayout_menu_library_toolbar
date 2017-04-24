package com.saike.tzh.account.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saike.tzh.base.BaseActivity;
import com.saike.tzh.homepage.ui.MainActivity_drawlayout_toolbar;
import com.saike.tzh.homepage.ui.MainActivity_menudrawer_toolbar;
import com.saike.tzh.R;
import com.saike.tzh.account.present.LoginInterface;
import com.saike.tzh.account.present.LoginPresent;
import com.saike.tzh.utils.Logutils;
import com.saike.tzh.widget.KeyboardListenRelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录界面
 * Created by admin on 2017/4/21.
 */

public class LoginActivity extends BaseActivity implements LoginInterface.resultdefine {
    String TAG=getClass().getName();
    private LoginPresent loginimpl;
    @BindView(R.id.root_my)
    KeyboardListenRelativeLayout root_my;
    @BindView(R.id.input_password)
    EditText passwordEditText;
    @BindView(R.id.pswd_switch)
    ImageView  pswdSwitchImageView;
    @BindView(R.id.input_account)
    EditText input_account;
    @BindView(R.id.account_switch)
    ImageView account_switch;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_checkphone)
    TextView  tv_checkphone;


    private boolean isPlain=false;
    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String temp = editable.toString();
            if(TextUtils.isEmpty(temp)){
                account_switch.setVisibility(View.GONE);
            }else{
                account_switch.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {

        }

    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind( this ) ;
        root_my.setOnKeyboardStateChangedListener(new KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener() {
            public void onKeyboardStateChanged(int state) {
                switch (state) {
                    case KeyboardListenRelativeLayout.KEYBOARD_STATE_HIDE:
                        Logutils.printD(TAG, state+"");
                        break;
                    case KeyboardListenRelativeLayout.KEYBOARD_STATE_SHOW:
                        Logutils.printD(TAG, state+"");
                        break;
                    default:
                        break;
                }
            }
        },this);
        input_account.addTextChangedListener(mTextWatcher);
        initView();
    }

    private void initView() {
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pswdSwitchImageView.setOnClickListener(noDoubleListener);
        account_switch.setOnClickListener(noDoubleListener);
        btn_login.setOnClickListener(noDoubleListener);
        tv_checkphone.setOnClickListener(noDoubleListener);
    }

    public void login(View view) {
        loginimpl=new LoginPresent(this);
        loginimpl.setdata(input_account.getText().toString(),passwordEditText.getText().toString());
        loginimpl.login();
    }

    public void tv_help(View view){
        Toast.makeText(this, "帮助", Toast.LENGTH_SHORT).show();
    }

    public void forgetpassword(View view){
        Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onError(String msg) {
        Logutils.printD(TAG,"onError"+" "+msg);
    }

    @Override
    public void success(String msg) {
        Logutils.printD(TAG,"success"+" "+msg);
    }

    @Override
    public void noHaveThisCount(String msg) {
        Logutils.printD(TAG,"noHaveThisCount"+" "+msg);
    }

    @Override
    public void onUserNameError(String msg) {
        Logutils.printD(TAG,"onUserNameError"+" "+msg);
    }

    @Override
    public void onUserPwdError(String msg) {
        Logutils.printD(TAG,"onUserPwdError"+" "+msg);
    }

    @Override
    public void relogin(String msg) {
        Logutils.printD(TAG,"relogin"+" "+msg);
    }

    /**
     * 密码明文匿文切换按钮
     */
    private void switchPwd() {
        isPlain = !isPlain;
        if(isPlain){
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            pswdSwitchImageView.setImageDrawable(this.getResources().getDrawable(R.drawable.eye_on));
            passwordEditText.setSelection(passwordEditText.length());
        }
        else{
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pswdSwitchImageView.setImageDrawable(this.getResources().getDrawable(R.drawable.eye_off));
            passwordEditText.setSelection(passwordEditText.length());
        }
    }

    @Override
    public void NoDoubleClick(View var1) {
        switch (var1.getId()){
            case R.id.account_switch:
                input_account.setText("");
                break;

            case R.id.pswd_switch:
                switchPwd();
                break;
            case R.id.btn_login:
                startActivity(new Intent(this, MainActivity_menudrawer_toolbar.class));//add toolbar
                finish();
                break;
            case R.id.tv_checkphone:
                startActivity(new Intent(this, MainActivity_drawlayout_toolbar.class));// no toolbar
                finish();
                break;
        }
    }
}
