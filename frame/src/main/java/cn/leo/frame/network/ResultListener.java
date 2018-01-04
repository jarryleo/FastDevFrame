package cn.leo.frame.network;/**
 * Created by ww on 2016/11/14.
 */


import rx.Observer;

/**
 * Created by leo at 2016/11/14
 */
public abstract class ResultListener<T> implements Observer<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e.getMessage());
    }

    @Override
    public void onNext(T result) {
        onSuccess(result);
    }

    public abstract void onSuccess(T result);

    public abstract void onFailed(String errorMsg);
}
