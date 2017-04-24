package com.saike.tzh.account.present;

/**
 * Created by admin on 2017/4/21.
 */

public class LoginPresent implements LoginInterface.dologin {
    private LoginInterface.resultdefine resultcode;
    private int types;

    public LoginPresent(LoginInterface.resultdefine define) {
        this.resultcode =define;
    }

    @Override
    public void login() {
        //TODO 知心网络访问,获取code值

        switch (types){
            case 0:
                resultcode.onError("msg: 网络访问失败");
                break;
            case 1:
                resultcode.onUserNameError("msg: 用户名错误");
                break;
            case 2:
                resultcode.onUserPwdError("msg: 密码错误");
                break;
            case 3:
                resultcode.relogin("msg: 重新登录");
                break;
            case 4:
                resultcode.relogin("msg: 登录成功");
                break;
            case 5:
                resultcode.relogin("msg: 此账号不存在");
                break;
        }
    }

    public void setdata(String name, String password) {
        
    }
}
