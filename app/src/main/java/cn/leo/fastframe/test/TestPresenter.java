package cn.leo.fastframe.test;

import cn.leo.fastframe.MainActivity;
import cn.leo.fastframe.base.BasePresenter;
import cn.leo.frame.network.ResultListener;

/**
 * Created by Leo on 2018/1/5.
 */

public class TestPresenter extends BasePresenter<MainActivity> {

    public TestPresenter(MainActivity view) {
        super(view);
    }

    public void testHttp() {
        executor(mAPI.getNewsList(1, 20), new ResultListener<TestBean>() {
            @Override
            public void onSuccess(TestBean result) {
                mView.onGetNewsListSuccess(result);
            }

            @Override
            public void onFailed(String msg) {

            }
        });
    }
}
