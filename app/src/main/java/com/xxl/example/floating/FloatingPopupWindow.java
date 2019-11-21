package com.xxl.example.floating;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xxl.example.R;

import static android.widget.ListPopupWindow.MATCH_PARENT;

/**
 * 悬浮窗弹窗
 * @author xxl.
 * @date 2019/11/21.
 */
public class FloatingPopupWindow extends PopupWindow {

    private Activity mActivity;

    public FloatingPopupWindow(Activity activity) {
        super(MATCH_PARENT, MATCH_PARENT);
        mActivity = activity;
        setTouchable(true);
        setOutsideTouchable(true);
        setUpLayout();
    }

    private void setUpLayout() {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View view = layoutInflater.inflate(R.layout.ui_user_popup, null);
        LinearLayout llRootView = view.findViewById(R.id.ll_root);
        llRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(view);
    }

    public void show() {
        Window window = mActivity.getWindow();
        View decorView = window.getDecorView();
        showAtLocation(decorView, Gravity.CENTER,0,0);
    }

}
