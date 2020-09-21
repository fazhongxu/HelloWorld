package com.xxl.example.origin.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author xxl.
 * @date 2020/9/21.
 */
public class BaseViewModel extends AndroidViewModel {

    //region: 成员变量

    CompositeDisposable mDisposable = new CompositeDisposable();

    //endregion

    //region: 构造函数

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}