package com.xxl.example.mediator.media;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author xxl
 * @date 19/6/12.
 * <p>
 * Description  音频 中间件
 **/
public class MediatorMedia {
    /**
     * 跳转到音频界面
     */
    public static void startMedia() {
        ARouter.getInstance()
                .build(IConstantMedia.MEDIA_PATH)
                .navigation();
    }
}
