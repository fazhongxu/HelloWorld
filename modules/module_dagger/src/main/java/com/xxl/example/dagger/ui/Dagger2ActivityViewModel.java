package com.xxl.example.dagger.ui;

import android.util.Log;

import androidx.annotation.NonNull;

import com.xxl.example.dagger.data.repository.DaggerRepository;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
public class Dagger2ActivityViewModel {

    //region: 成员变量

    /**
     * dagger模块数据源
     */
    private DaggerRepository mDaggerRepository;

    //endregion

    //region: 构造函数

   // @Inject
    public Dagger2ActivityViewModel(@NonNull DaggerRepository daggerRepository) {
        mDaggerRepository = daggerRepository;
    }

    //endregion

    //region: 用户信息模块

    public void requestQueryUserInfo(@NonNull final String targetUserId) {

        Disposable disposable = mDaggerRepository.queryUserInfo(targetUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(s -> {
                    Log.e("aaa", "requestQueryUserInfo: throwable" +s);
                },throwable -> {
                    Log.e("aaa", "requestQueryUserInfo: throwable" +throwable.getMessage());
                });
    }

    //endregion
}