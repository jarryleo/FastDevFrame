package cn.leo.fastframe.test;

import cn.leo.fastframe.activity.MainActivity;
import cn.leo.fastframe.base.BasePresenter;
import cn.leo.frame.network.ResultListener;

/**
 * Created by Leo on 2018/1/5.
 */

public class TestPresenter extends BasePresenter<MainActivity> {

    public TestPresenter(MainActivity view) {
        super(view);
    }

    @Override
    protected void lazyInitOnce() {
        testHttp();
        //ToastUtil.shortToast("空闲加载数据");
    }

    public void testHttp() {
        executeApi(API().getNewsList(
                "",
                1,
                "json",
                1,
                20),
                new ResultListener<TestBean>() {
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
