package cn.leo.frame.network;

import android.support.annotation.Nullable;

import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import cn.leo.frame.utils.NetworkUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Leo on 2018/1/4.
 */

public class HttpLoader {

    private Retrofit mRetrofit;
    private CompositeSubscription mCompositeSubscription;

    /**
     * 构建HttpLoader
     */
    public static final class Builder {
        private String mBaseUrl;
        private OkHttpClient mHttpClient = new OkHttp3Builder().build();

        /**
         * 构造build
         *
         * @param baseUrl 必须参数
         */
        public Builder(String baseUrl) {
            this.mBaseUrl = baseUrl;
        }

        /**
         * 请求客户端可以定制，也可以不处理有默认客户端
         *
         * @param client
         * @return
         */
        public Builder client(OkHttpClient client) {
            mHttpClient = client;
            return this;
        }

        public HttpLoader build() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .client(mHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return new HttpLoader(retrofit);
        }

    }

    private HttpLoader(Retrofit retrofit) {
        this.mRetrofit = retrofit;
        //初始化订阅管理器
        mCompositeSubscription = new CompositeSubscription();
    }

    public <T> T create(final Class<T> service) {
        //请求API实例化
        return mRetrofit.create(service);
    }

    /**
     * 集体执行逻辑(如果不想这样用，可以直接用API拿Observable自己写rxjava执行逻辑)
     *
     * @param resultListener 结果监听
     */
    public <T> Subscription executor(@Nullable Object view,
                                     final Observable<T> observable,
                                     final ResultListener<T> resultListener) {
        if (observable == null) {
            return null;
        }
        //检查网络连接
        if (!NetworkUtil.checkNetwork()) {
            if (resultListener != null) {
                resultListener.onFailed("网络连接异常！");
                return null;
            }
        }
        Observable<T> tObservable = observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
        //判断是否可以绑定生命周期
        if (view instanceof ActivityLifecycleProvider) {
            ActivityLifecycleProvider lifecycleProvider = (ActivityLifecycleProvider) view;
            tObservable.compose(lifecycleProvider.<T>bindToLifecycle());
        }
        if (view instanceof FragmentLifecycleProvider) {
            FragmentLifecycleProvider lifecycleProvider = (FragmentLifecycleProvider) view;
            tObservable.compose(lifecycleProvider.<T>bindToLifecycle());
        }
        //执行请求
        Subscription subscribe = tObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultListener<T>() {
                    @Override
                    public void onSuccess(T result) {
                        if (resultListener != null) {
                            resultListener.onSuccess(result);
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        if (resultListener != null) {
                            resultListener.onFailed(msg);
                        }
                    }
                });

        mCompositeSubscription.add(subscribe);//订阅添加到管理
        return subscribe;
    }

    /**
     * 取消所有订阅
     */
    public void cancelAll() {
        mCompositeSubscription.clear();
    }

    /**
     * 取消指定订阅，可以取消请求
     *
     * @param subscription
     */
    public void cancel(Subscription subscription) {
        if (!mCompositeSubscription.isUnsubscribed() && subscription != null) {
            mCompositeSubscription.remove(subscription);
        }
    }

    /**
     * 添加订阅到管理
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        if (subscription != null) {
            mCompositeSubscription.add(subscription);
        }
    }
}
