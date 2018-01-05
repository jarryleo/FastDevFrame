package cn.leo.fastframe.base;

import android.arch.lifecycle.LifecycleOwner;

import cn.leo.fastframe.net.NetConfig;
import cn.leo.fastframe.net.PresenterAPI;
import cn.leo.frame.base.SuperBasePresenter;

/**
 * Created by Leo on 2018/1/4.
 */

public class BasePresenter extends SuperBasePresenter<PresenterAPI> {


    @Override
    public String getBaseUrl() {
        return NetConfig.BASE_URL;
    }

    @Override
    public Class<PresenterAPI> getAPIClass() {
        return PresenterAPI.class;
    }

    public BasePresenter(LifecycleOwner view) {
        super(view);
    }

}
