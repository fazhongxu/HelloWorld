package com.xxl.example.dagger.data.repository;

import androidx.annotation.NonNull;

import com.xxl.example.dagger.data.local.DaggerLocalDataSource;
import com.xxl.example.dagger.data.remote.DaggerRemoteDataSource;

import io.reactivex.Observable;

/**
 * dagger模块数据源
 *
 * @author xxl.
 * @date 2020/9/18.
 */
public class DaggerRepositoryImpl implements DaggerRepository {

    //region: 成员变量

    /**
     * dagger模块远程数据源
     */
    private final DaggerRemoteDataSource mDaggerRemoteDataSource;

    /**
     * dagger模块本地数据源
     */
    private final DaggerLocalDataSource mDaggerLocalDataSource;

    //endregion

    //region: 构造函数

    public DaggerRepositoryImpl(@NonNull DaggerRemoteDataSource daggerRemoteDataSource,
                                @NonNull DaggerLocalDataSource daggerLocalDataSource) {
        mDaggerRemoteDataSource = daggerRemoteDataSource;
        mDaggerLocalDataSource = daggerLocalDataSource;
    }

    //endregion

    //region: 用户信息相关

    /**
     * 查询用户信息
     *
     * @param targetUserId 用户ID
     */
    @Override
    public Observable<String> queryUserInfo(@NonNull final String targetUserId) {
        return mDaggerRemoteDataSource.queryUserInfo(targetUserId);
    }

    //endregion
}