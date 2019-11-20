package com.xxl.example.mediator.web;


import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by xxl on 19/5/22.
 * <p>
 * Description
 **/
public interface IWebProvider extends IProvider {
    /**
     * 获取用户名称
     * @param uid
     * @return
     */
    String getUserName(String uid);
}
