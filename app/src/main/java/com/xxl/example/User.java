package com.xxl.example;

import android.app.Application;

/**
 * Created by xxl on 19/4/25.
 * <p>
 * Description
 **/
public class User {
    private String nick;
    private String password;

    private Application mApplication;

    public User(Application application) {
        mApplication = application;
    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Application getApplication() {
        return mApplication;
    }
}
