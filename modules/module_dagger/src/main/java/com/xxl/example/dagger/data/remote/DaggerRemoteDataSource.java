package com.xxl.example.dagger.data.remote;

import androidx.annotation.NonNull;

import io.reactivex.Observable;

/**
 * dagger模块远程数据源
 *
 * @author xxl.
 * @date 2020/9/18.
 */
public interface DaggerRemoteDataSource {

    //region: 用户信息相关

    /**
     * 查询用户信息
     *
     * @param targetUserId 用户ID
     */
    Observable<String> queryUserInfo(@NonNull String targetUserId);

    //endregion
}
