package com.xxl.example.floating;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 悬浮窗服务
 *
 * @author xxl.
 * @date 2019/11/20.
 */
public class FloatingService extends Service {

    //region: 成员变量

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mLayoutParams;

    /**
     * 屏幕宽度的一半
     */
    private float mHalfScreenWidth;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 悬浮窗圆角背景
     */
    private Drawable mBackgroundRadiusDrawable;

    /**
     * 悬浮窗左边圆角背景
     */
    private Drawable mBackgroundLeftHalfRadiusDrawable;

    /**
     * 悬浮窗右边圆角背景
     */
    private Drawable mBackgroundRightHalfRadiusDrawable;

    /**
     * 悬浮窗内View
     */
    private View mFloatingWidowView;

    /**
     * 会话信息
     */
    private List<ConversationInfo> mConversationInfoList;

    /**
     * 会话信息key
     */
    public static final String PARAM_KEY_CONVERSATION_INFO = "param_key_conversation_info";

    /**
     * 悬浮窗操作事件监听
     */
    private FloatingWidowOperateListener mFloatingWidowOperateListener;

    /**
     * 计时任务，用于监听App是否在后台运行，在后台运行时，隐藏悬浮窗，否则显示
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            mHandler.sendEmptyMessageDelayed(0, 600);
            if (isForegroundRunning() && canDrawOverlays()) {
                showFloatingView();
            } else {
                closeFloatingView();
            }
        }
    };
    //endregion

    //region: 生命周期

    @Override
    public void onCreate() {
        super.onCreate();
        initFloatingWindow();
        mConversationInfoList = new ArrayList<>();
        mHandler.sendEmptyMessageDelayed(0, 600);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ConversationInfo conversationInfo = null;
        if (intent.getExtras() != null) {
            conversationInfo = (ConversationInfo) intent.getExtras().getSerializable(PARAM_KEY_CONVERSATION_INFO);
        }
        createFloatingWindow(conversationInfo);
        return new FloatingWindowBinder(conversationInfo);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //createFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }
    //endregion

    //region: FloatingWindowBinder

    /**
     * 悬浮窗Binder通讯
     */
    public class FloatingWindowBinder extends Binder {

        public FloatingWindowBinder(ConversationInfo conversationInfo) {
            if (conversationInfo != null) {
                addConversationInfo(conversationInfo);
            }
        }

        public void changeFloatingIcon(Drawable targetDrawable) {
            changeFloatingBackground(targetDrawable);
        }

        public synchronized void addConversationInfo(ConversationInfo conversationInfo) {
            if (!mConversationInfoList.contains(conversationInfo)) {
                mConversationInfoList.add(conversationInfo);
            }
        }

        public synchronized void removeConversationInfo(ConversationInfo conversationInfo) {
            if (mConversationInfoList.contains(conversationInfo)) {
                mConversationInfoList.remove(conversationInfo);
            }
        }

        public List<ConversationInfo> getConversationInfos() {
            return mConversationInfoList;
        }

        public void setFloatingWidowOparaterListener(FloatingWidowOperateListener floatingWidowOperateListener) {
            mFloatingWidowOperateListener = floatingWidowOperateListener;
        }
    }

    //endregion

    //region: 悬浮窗初始化

    /**
     * 初始化悬浮窗窗口
     */
    private void initFloatingWindow() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mScreenWidth = getScreenWidth();
        mHalfScreenWidth = getScreenWidth() / 2;
        mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.x = mScreenWidth;
        mLayoutParams.y = 200;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBackgroundRadiusDrawable = getResources().getDrawable(R.drawable.shape_floating_window_radius, getTheme());
            mBackgroundLeftHalfRadiusDrawable = getResources().getDrawable(R.drawable.shape_floating_window_left_half_radius, getTheme());
            mBackgroundRightHalfRadiusDrawable = getResources().getDrawable(R.drawable.shape_floating_window_right_half_radius, getTheme());
        } else {
            mBackgroundRadiusDrawable = getResources().getDrawable(R.drawable.shape_floating_window_radius);
            mBackgroundLeftHalfRadiusDrawable = getResources().getDrawable(R.drawable.shape_floating_window_left_half_radius);
            mBackgroundRightHalfRadiusDrawable = getResources().getDrawable(R.drawable.shape_floating_window_right_half_radius);
        }
    }

    /**
     * 创建悬浮窗
     */
    private void createFloatingWindow(ConversationInfo conversationInfo) {
        if (canDrawOverlays()) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            if (mFloatingWidowView == null) {
                mFloatingWidowView = layoutInflater.inflate(R.layout.ui_floating_window, null);
            } else {
                mWindowManager.removeView(mFloatingWidowView);
            }
            if (conversationInfo != null) {
                Log.e("aa", "createFloatingWindow: " + conversationInfo.getNickname());
            }
            mFloatingWidowView.setVisibility(View.VISIBLE);
            mFloatingWidowView.setOnTouchListener(new FloatingOnTouchListener());
            mWindowManager.addView(mFloatingWidowView, mLayoutParams);
        }
    }

    //endregion

    //region: 悬浮窗控制方法

    /**
     * 关闭悬浮窗
     */
    private void closeFloatingView() {
        if (mFloatingWidowView != null && mFloatingWidowView.getVisibility() == View.VISIBLE) {
            mFloatingWidowView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 展示悬浮窗
     */
    private void showFloatingView() {
        if (mFloatingWidowView == null || mFloatingWidowView.getVisibility() == View.VISIBLE) {
            return;
        }
        mFloatingWidowView.setVisibility(View.VISIBLE);
    }

    /**
     * 改变悬浮窗的背景
     *
     * @param targetDrawable
     */
    private void changeFloatingBackground(Drawable targetDrawable) {
        if (mFloatingWidowView == null || targetDrawable == null) {
            return;
        }
        mFloatingWidowView.setBackground(targetDrawable);
    }
    //endregion

    //region: 事件监听

    /**
     * 悬浮窗触摸事件监听
     */
    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private long currentTimeMillis;
        private long xMoveDistance;
        private long yMoveDistance;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(final View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    xMoveDistance = 0;
                    yMoveDistance = 0;

                    currentTimeMillis = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    xMoveDistance += Math.abs(movedX);
                    yMoveDistance += Math.abs(movedY);
                    x = nowX;
                    y = nowY;
                    mLayoutParams.x = mLayoutParams.x + movedX;
                    mLayoutParams.y = mLayoutParams.y + movedY;
                    mWindowManager.updateViewLayout(view, mLayoutParams);
                    //拖拽时背景设置
                    if (mLayoutParams.x > 0 || mLayoutParams.x < mScreenWidth) {
                        view.setBackground(mBackgroundRadiusDrawable);
                    }

                    long movingTime = System.currentTimeMillis() - currentTimeMillis;
                    boolean isDrag = movingTime > 200 && (xMoveDistance > 20 || yMoveDistance > 20);
                    if (isDrag) {
                        mFloatingWidowOperateListener.onDragging();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    int upX = (int) event.getRawX();
                    int upY = (int) event.getRawY();
                    int viewWidth = view.getWidth();

                    long moveTime = System.currentTimeMillis() - currentTimeMillis;
                    boolean isClick = moveTime < 200 && (xMoveDistance < 20 || yMoveDistance < 20);
                    if (isClick) {
                        if (mFloatingWidowOperateListener != null) {
                            mFloatingWidowOperateListener.onClick();
                        }
                    } else {
                        if (mFloatingWidowOperateListener != null) {
                            mFloatingWidowOperateListener.onDrag();
                        }
                    }
                    int currentUpX = upX;
                    if (mLayoutParams.x + viewWidth / 2 > mHalfScreenWidth) {
                        upX = mScreenWidth - view.getWidth();
                    } else {
                        upX = 0;
                    }
                    x = upX;
                    y = upY;

                    //动画设置
                    ValueAnimator valueAnimator;
                    if (upX <= 0) {
                        valueAnimator = ValueAnimator.ofFloat(currentUpX - viewWidth, 0);
                    } else {
                        valueAnimator = ValueAnimator.ofFloat(currentUpX, mScreenWidth - viewWidth);
                    }
                    valueAnimator.setInterpolator(new AccelerateInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedValue = (float) animation.getAnimatedValue();
                            mLayoutParams.x = (int) animatedValue;
                            mWindowManager.updateViewLayout(view, mLayoutParams);
                        }
                    });
                    //停止拖拽时背景设置
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (mLayoutParams.x <= 0) {
                                view.setBackground(mBackgroundRightHalfRadiusDrawable);
                            } else {
                                view.setBackground(mBackgroundLeftHalfRadiusDrawable);
                            }
                        }
                    });
                    valueAnimator.setDuration(100);
                    valueAnimator.start();
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    //endregion

    //region: 辅助方法

    /**
     * 是否有悬浮窗权限
     *
     * @return
     */
    private boolean canDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        }
        return false;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    private int getScreenWidth() {
        Display defaultDisplay = mWindowManager.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    /**
     * 当前应用是否处于前台运行
     *
     * @returne
     */
    private boolean isForegroundRunning() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(getPackageName())
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
    //endregion

}
