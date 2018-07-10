package cn.leo.frame.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Looper;
import android.os.MessageQueue;

import cn.leo.frame.network.HttpLoader;
import cn.leo.frame.network.ResultListener;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Leo on 2018/1/4.
 */

public abstract class SuperBasePresenter<T, Y extends BaseViewInterface> implements LifecycleObserver {
    //泛型T为网络请求API接口类,在BasePresenter实现
    private static Object mAPI;
    private static HttpLoader mHttpLoader;
    //泛型Y为具体的Activity或者Fragment,留给具体的presenter实现
    //写法：BasePresenter<T extends LifecycleOwner> extends SuperBasePresenter<PresenterAPI, T>
    public Y mView;

    //生命周期管理
    private CompositeSubscription mCompositeSubscription;

    {
        if (mHttpLoader == null) {
            mHttpLoader = getHttpLoader();
            mAPI = mHttpLoader.create(getAPIClass());
        }
    }

    /**
     * 如果要自定义拦截器等等,重写这个方法
     * 可以用 {@link cn.leo.frame.network.OkHttp3Builder} 构建自定义的 OkHttpClient
     *
     * @return
     */
    public HttpLoader getHttpLoader() {
        return new HttpLoader.Builder(getBaseUrl()).build();
    }

    public abstract String getBaseUrl();

    public T API() {
        return (T) mAPI;
    }

    public abstract Class<T> getAPIClass();

    public SuperBasePresenter(Y view) {
        mView = view;
        ((LifecycleOwner) mView).getLifecycle().addObserver(this);
        mCompositeSubscription = new CompositeSubscription();
    }

    public <O> Subscription executeApi(final Observable<O> observable,
                                       final ResultListener<O> resultListener) {
        Subscription executor = mHttpLoader.executor(observable, resultListener);
        if (executor != null)
            mCompositeSubscription.add(executor);
        return executor;
    }

    /**
     * 空闲时候加载的方法，可以用来初始化或者请求数据
     */
    protected void lazyInitOnce() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        //子类继承此方法可以拿到详细的生命周期
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                lazyInitOnce();
                return false;
            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        //子类可继承此方法进行请求数据
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        unSubscribe();
    }

    public void unSubscribe() {
        mCompositeSubscription.clear();
    }
}
