package cn.leo.fastframe.test;

import cn.leo.frame.network.HttpLoader;
import cn.leo.frame.network.ResultListener;

/**
 * Created by Leo on 2018/1/4.
 */

public class Test {
    public void test1() {
        HttpLoader httpLoader = new HttpLoader.Builder("baseUrl").build();
        PresenterAPI presenterAPI = httpLoader.create(PresenterAPI.class);

        httpLoader.executor(presenterAPI.requestNewsPicData(1), new ResultListener<WaybillBean>() {
            @Override
            public void onSuccess(WaybillBean result) {

            }

            @Override
            public void onFailed(String errorMsg) {

            }
        });
    }
}
