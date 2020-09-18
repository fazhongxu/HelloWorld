package com.xxl.example.dagger.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.example.dagger.R;
import com.xxl.example.mediator.dagger.IConstantDagger;
import com.xxl.example.origin.ui.BaseActivity;

import javax.inject.Inject;

@Route(path = IConstantDagger.DAGGER_DAGGER)
public class Dagger2Activity extends BaseActivity {

    //region: 成员变量

//    @Inject
//    Dagger2ActivityViewModel mDagger2ActivityViewModel;

    //endregion

    //region: 页面生命周期

    /**
     * 获取布局资源ID
     *
     * @return
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dagger2;
    }

    /**
     * 设置视图
     */
    @Override
    protected void setupLayout() {

    }

    /**
     * 设置数据
     *
     * @param savedInstanceState
     */
    @Override
    protected void setupData(@Nullable Bundle savedInstanceState) {
        //mDagger2ActivityViewModel.requestQueryUserInfo("123");
    }
    //endregion
}
