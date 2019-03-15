package cn.leo.fastframe.base;

import cn.leo.fastframe.net.NetConfig;
import cn.leo.fastframe.net.PresenterAPI;
import cn.leo.frame.base.BaseViewInterface;
import cn.leo.frame.base.SuperBasePresenter;

/**
 * Created by Leo on 2018/1/4.
 */

public class BasePresenter<T extends BaseViewInterface>
        extends SuperBasePresenter<PresenterAPI, T> {

    public BasePresenter(T view) {
        super(view);
    }

    @Override
    public String getBaseUrl() {
        return NetConfig.BASE_URL;
    }

    @Override
    public Class<PresenterAPI> getAPIClass() {
        return PresenterAPI.class;
    }
}
