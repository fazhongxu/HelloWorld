package com.xxl.example.floating;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 悬浮窗服务连接
 *
 * @author xxl.
 * @date 2019/11/21.
 */
public class FloatingWindowServiceConnection implements ServiceConnection {
    //region: 成员变量

    private FloatingService.FloatingWindowBinder mFloatingWindowBinder;

    private FloatingWidowOperateListener mFloatingWidowOperateListener;

    //endregion

    //region: 生命周期

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mFloatingWindowBinder = (FloatingService.FloatingWindowBinder) service;
        mFloatingWindowBinder.setFloatingWidowOparaterListener(mFloatingWidowOperateListener);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    //endregion

    //region: 提供方法

    /**
     * 设置悬浮窗操作事件监听
     *
     * @param floatingWidowOperateListener
     */
    public void setFloatingWidowOperateListener(FloatingWidowOperateListener floatingWidowOperateListener) {
        mFloatingWidowOperateListener = floatingWidowOperateListener;
    }

    /**
     * 获取悬浮窗的Binder
     *
     * @return
     */
    public FloatingService.FloatingWindowBinder getFloatingWindowBinder() {
        return mFloatingWindowBinder;
    }

    //endregion
}
