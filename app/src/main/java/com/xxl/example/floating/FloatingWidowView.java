package com.xxl.example.floating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 悬浮窗自定义View
 *
 * @author xxl.
 * @date 2019/11/21.
 */
public class FloatingWidowView extends ConstraintLayout {

    public FloatingWidowView(Context context) {
        this(context, null);
    }

    public FloatingWidowView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FloatingWidowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //监听返回键和HOME建
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                || event.getKeyCode() == KeyEvent.KEYCODE_SETTINGS) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(this);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    OnClickListener mOnClickListener;

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        super.setOnClickListener(onClickListener);
    }
}
