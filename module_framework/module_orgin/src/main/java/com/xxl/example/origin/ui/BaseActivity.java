package com.xxl.example.origin.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
public abstract class BaseActivity extends AppCompatActivity {

    //region: 页面生命周期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isNeedInject()) {
            AndroidInjection.inject(this);
        }
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        setupData(savedInstanceState);
        setupLayout();
    }

    /**
     * 是否需要依赖注入
     *
     * @return
     */
    protected boolean isNeedInject() {
        return true;
    }

    //endregion

    //region: 抽象方法

    /**
     * 获取布局资源ID
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 设置视图
     */
    protected abstract void setupLayout();

    /**
     * 设置数据
     */
    protected abstract void setupData(@Nullable Bundle savedInstanceState);

    //endregion

}