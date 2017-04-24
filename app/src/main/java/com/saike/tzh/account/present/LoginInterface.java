package com.saike.tzh.account.present;

public class LoginInterface {

    public interface dologin{
        void login();
    }

    public interface resultdefine {
        void onError(String msg);
        void success(String msg);
        void noHaveThisCount(String msg);
        void onUserNameError(String msg);
        void onUserPwdError(String msg);
        void relogin(String msg);
    }
}
