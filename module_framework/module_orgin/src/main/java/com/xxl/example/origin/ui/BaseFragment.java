package com.xxl.example.origin.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dagger.android.support.AndroidSupportInjection;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
public abstract class BaseFragment extends Fragment {

    //region: 页面生命周期

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (isNeedInject()) {
            AndroidSupportInjection.inject(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        final View rootView = inflater.inflate(getLayoutResId(), parent);
        setupData();
        setupLayout(rootView);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
     *
     * @param rootView
     */
    protected abstract void setupLayout(@NonNull final View rootView);

    /**
     * 设置数据
     */
    protected abstract void setupData();

    //endregion
}