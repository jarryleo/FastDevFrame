package cn.leo.fastframe.test;

import cn.leo.fastframe.MainActivity;
import cn.leo.fastframe.base.BasePresenter;
import cn.leo.frame.network.ResultListener;
import cn.leo.frame.utils.ToastUtil;

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
        executeApi(getAPI().getNewsList(
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
