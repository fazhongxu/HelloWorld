package com.xxl.example;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;

/**
 * 搜索
 *
 * @author xxl.
 * @date 2019/11/01.
 */
public class SearchPresenter {

    private PublishSubject<String> mKeywordPublishSubject = PublishSubject.create();

    public SearchPresenter() {
        initSearch();
    }

    private void initSearch() {
        ConnectableObservable<String> connectableObservable = mKeywordPublishSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String keyword) throws Throwable {
                        return doSearch(keyword);
                    }
                }).publish();

        Disposable disposable = connectableObservable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                Log.e("aaa", "accept: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.e("aaa", "accept: "+throwable.getCause());
            }
        });
        connectableObservable.connect();
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    public void search(@NonNull String keyword) {
        mKeywordPublishSubject.onNext(keyword);
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    public Observable<String> doSearch(@NonNull String keyword) {
        return Observable.just(keyword)
                .delay(100,TimeUnit.MILLISECONDS)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Throwable {
                        return "result-->" + s;
                    }
                });
    }
}
