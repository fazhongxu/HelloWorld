package com.xxl.example.mediator.web;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xxl on 19/5/22.
 * <p>
 * Description
 **/
public class MediatorWeb {
    /**
     * 打开 WebView
     * @param url
     */
    public static void startWeb(String url)  {
        ARouter.getInstance().build(IConstantWeb.WEB_WEBVIEW)
                .withString(IConstantWeb.KEY_URL,url)
                .navigation();
    }

    /**
     * 获取web模块的 provider
     * @return
     */
    public static IWebProvider getWebProvider() {
       return (IWebProvider) ARouter.getInstance()
               .build(IConstantWeb.WEB_PROVIDER)
               .navigation();
    }

    /**
     * 获取用户名称
     * @return
     */
    public static String getUserName() {
        return getWebProvider().getUserName("001");
    }
}
