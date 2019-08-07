package com.xxl.example.web;

import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.example.mediator.web.IConstantWeb;
import com.xxl.example.mediator.web.IWebProvider;

/**
 * @author xxl.
 * @date 2019/08/06.
 */
@Route(path = IConstantWeb.WEB_PROVIDER)
public class WebProviderImpl implements IWebProvider {
    /**
     * 获取用户名称
     *
     * @param uid
     * @return
     */
    @Override
    public String getUserName(@NonNull String uid) {
        return "I am json"+uid;
    }

    /**
     * Do your init work in this method, it well be call when processor has been load.
     *
     * @param context ctx
     */
    @Override
    public void init(Context context) {

    }
}
