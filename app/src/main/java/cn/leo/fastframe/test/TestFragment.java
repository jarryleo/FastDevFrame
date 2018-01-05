package cn.leo.fastframe.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Leo on 2018/1/5.
 */

public class TestFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestPresenter testPresenter = new TestPresenter(this);
        testPresenter.testHttp();
    }
}
