package com.xxl.example.dagger.data.repository;

import androidx.annotation.NonNull;

import io.reactivex.Observable;

/**
 * dagger模块数据源
 *
 * @author xxl.
 * @date 2020/9/18.
 */
public interface DaggerRepository {

    //region: 用户信息相关

    /**
     * 查询用户信息
     *
     * @param targetUserId 用户ID
     */
    Observable<String> queryUserInfo(@NonNull String targetUserId);

    //endregion
}