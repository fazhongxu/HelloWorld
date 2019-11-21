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

    private FloatingService.FloatingWindowBinder mFloatingWindowBinder;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mFloatingWindowBinder = (FloatingService.FloatingWindowBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public FloatingService.FloatingWindowBinder getFloatingWindowBinder() {
        return mFloatingWindowBinder;
    }
}
