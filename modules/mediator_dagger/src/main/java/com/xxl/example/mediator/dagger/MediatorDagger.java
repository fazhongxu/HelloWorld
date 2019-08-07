package com.xxl.example.mediator.dagger;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * dagger 模块中间件
 *
 *  @author xxl.
 * @date 2019/08/07.
 */
public class MediatorDagger {
    /**
     * 打开dagger页面
     */
    public static void startDagger() {
        ARouter.getInstance()
                .build(IConstantDagger.DAGGER_DAGGER)
                .navigation();
    }
}
