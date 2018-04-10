package cn.leo.fastframe.holder;

import android.content.Context;
import android.view.View;

import cn.leo.fastframe.test.TestBean;
import cn.leo.frame.base.BaseHolder;

/**
 * Created by Leo on 2018/2/28.
 */

public class TestHolder extends BaseHolder<TestBean> {
    @Override
    protected View onCreateView(Context context, int position) {
        return null;
    }

    @Override
    public void bindView(TestBean data, int position) {

    }
}
