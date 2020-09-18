package com.xxl.example.origin.data;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
public class BaseRemoteDataSource {

    //region: 成员变量

    protected Retrofit mRetrofit;

    //endregion

    //region: 构造函数

    public BaseRemoteDataSource(@NonNull final Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }

    //endregion

}