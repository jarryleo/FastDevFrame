package cn.leo.frame.network;/**
 * Created by ww on 2016/11/14.
 */


import cn.leo.frame.utils.ToastUtil;
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
        e.printStackTrace();
        onError();
    }

    @Override
    public void onNext(T result) {
        onSuccess(result);
    }

    public abstract void onSuccess(T result);

    public abstract void onFailed(String msg);

    public void onError() {
        ToastUtil.shortToast("网络异常");
    }
}
