package com.xxl.example;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 悬浮窗服务
 *
 * @author xxl.
 * @date 2019/11/20.
 */
public class FloatingService extends Service {

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

    @Override
    public void onCreate() {
        super.onCreate();
        initFloatingWindow();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

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
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
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
     * 展示悬浮窗
     */
    private void showFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View view = layoutInflater.inflate(R.layout.ui_floating_window, null);
                view.setOnTouchListener(new FloatingOnTouchListener());
                mWindowManager.addView(view, mLayoutParams);
            }
        }
    }

    /**
     * 悬浮窗触摸事件监听
     */
    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private long currentTimeMillis;
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(final View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();

                    currentTimeMillis = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mLayoutParams.x = mLayoutParams.x + movedX;
                    mLayoutParams.y = mLayoutParams.y + movedY;
                    mWindowManager.updateViewLayout(view, mLayoutParams);
                    //拖拽时背景设置
                    if (mLayoutParams.x > 0 || mLayoutParams.x < mScreenWidth) {
                        view.setBackground(mBackgroundRadiusDrawable);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    int upX = (int) event.getRawX();
                    int upY = (int) event.getRawY();
                    long moveTime = System.currentTimeMillis() - currentTimeMillis;
                    if (moveTime < 200) {
                        Toast.makeText(FloatingService.this, "点击", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(FloatingService.this, "拖拽", Toast.LENGTH_SHORT).show();
                    }
                    int currentUpX = upX;
                    if (upX > mHalfScreenWidth) {
                        upX = mScreenWidth - view.getWidth();
                    } else {
                        upX = 0;
                    }
                    x = upX;
                    y = upY;

                    //动画设置
                    ValueAnimator valueAnimator;
                    int viewWidth = view.getWidth();
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
                    valueAnimator.setDuration(200);
                    valueAnimator.start();
                    break;
                default:
                    break;
            }
            return false;
        }
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

}
