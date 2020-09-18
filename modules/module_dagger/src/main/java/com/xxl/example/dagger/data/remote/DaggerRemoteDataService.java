package com.xxl.example.dagger.data.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
public interface DaggerRemoteDataService {

    //region: 用户信息相关

    /**
     * 查询用户信息
     *
     * @param targetUserId 用户ID
     */
    @GET("xxx")
    Observable<String> queryUserInfo(@Query("xxx") String targetUserId);

    //endregion
}