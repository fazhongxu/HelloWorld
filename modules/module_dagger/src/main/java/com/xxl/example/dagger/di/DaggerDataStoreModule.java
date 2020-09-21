package com.xxl.example.dagger.di;

import androidx.annotation.NonNull;

import com.xxl.example.dagger.data.local.DaggerLocalDataSource;
import com.xxl.example.dagger.data.local.DaggerLocalDataSourceImpl;
import com.xxl.example.dagger.data.remote.DaggerRemoteDataSource;
import com.xxl.example.dagger.data.remote.DaggerRemoteDataSourceImpl;
import com.xxl.example.dagger.data.repository.DaggerRepository;
import com.xxl.example.dagger.data.repository.DaggerRepositoryImpl;
import com.xxl.example.origin.anotation.ForRetorfit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module
public class DaggerDataStoreModule {

    @Singleton
    @Provides
    DaggerLocalDataSource provideDaggerLocalDataSource() {
        return new DaggerLocalDataSourceImpl();
    }

    @Singleton
    @Provides
    DaggerRemoteDataSource provideDaggerRemoteDataSource(@ForRetorfit Retrofit retrofit) {
        return new DaggerRemoteDataSourceImpl(retrofit);
    }

    @Singleton
    @Provides
    DaggerRepository provideDaggerRepositoryImpl(@NonNull final DaggerRemoteDataSource daggerRemoteDataSource,
                                                 @NonNull final DaggerLocalDataSource daggerLocalDataSource) {
        return new DaggerRepositoryImpl(daggerRemoteDataSource, daggerLocalDataSource);
    }

}