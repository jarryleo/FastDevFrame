package cn.leo.fastframe.test;

import android.arch.lifecycle.LifecycleOwner;

import cn.leo.fastframe.base.BasePresenter;
import cn.leo.frame.network.ResultListener;

/**
 * Created by Leo on 2018/1/5.
 */

public class TestPresenter extends BasePresenter {
    public TestPresenter(LifecycleOwner view) {
        super(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        testHttp();
    }

    public void testHttp() {
        executor(mAPI.requestNewsPicData(1), new ResultListener<TestBean>() {
            @Override
            public void onSuccess(TestBean result) {

            }

            @Override
            public void onFailed(String msg) {

            }
        });
    }
}
