package com.xxl.example.dagger.data.remote;

import androidx.annotation.NonNull;

import com.xxl.example.origin.data.BaseRemoteDataSource;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * dagger模块远程数据源
 *
 * @author xxl.
 * @date 2020/9/18.
 */
public class DaggerRemoteDataSourceImpl extends BaseRemoteDataSource implements DaggerRemoteDataSource {

    //region: 成员变量

    private DaggerRemoteDataService mDaggerRemoteDataService;

    //endregion

    //region: 构造函数

    public DaggerRemoteDataSourceImpl(@NonNull Retrofit retrofit) {
        super(retrofit);
        mDaggerRemoteDataService = retrofit.create(DaggerRemoteDataService.class);
    }


    //endregion

    //region: 用户信息相关

    /**
     * 查询用户信息
     *
     * @param targetUserId 用户ID
     */
    @Override
    public Observable<String> queryUserInfo(@NonNull String targetUserId) {
        return mDaggerRemoteDataService.queryUserInfo(targetUserId);
    }

    //endregion
}