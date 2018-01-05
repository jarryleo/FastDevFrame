package cn.leo.frame.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import cn.leo.frame.network.HttpLoader;
import cn.leo.frame.network.ResultListener;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Leo on 2018/1/4.
 */

public abstract class SuperBasePresenter<T> implements LifecycleObserver {
    public T mAPI;
    private HttpLoader mHttpLoader;

    public LifecycleOwner mView;
    //生命周期管理
    private CompositeSubscription mCompositeSubscription;

    {
        mHttpLoader = new HttpLoader.Builder(getBaseUrl()).build();
        mAPI = mHttpLoader.create(getAPIClass());
    }

    public abstract String getBaseUrl();

    public abstract Class<T> getAPIClass();


    public SuperBasePresenter(LifecycleOwner view) {
        mView = view;
        mView.getLifecycle().addObserver(this);
        mCompositeSubscription = new CompositeSubscription();
    }

    public <O> Subscription executor(final Observable<O> observable,
                                     final ResultListener<O> resultListener) {
        Subscription executor = mHttpLoader.executor(observable, resultListener);
        mCompositeSubscription.add(executor);
        return executor;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        unSubscribe();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        //子类可继承此方法进行请求数据
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        //子类继承此方法可以拿到详细的生命周期
    }

    public void unSubscribe() {
        mCompositeSubscription.clear();
    }
}
