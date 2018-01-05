package cn.leo.fastframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.leo.fastframe.test.TestBean;
import cn.leo.fastframe.test.TestPresenter;
import cn.leo.frame.utils.UIUtil;

public class MainActivity extends AppCompatActivity {

    private TestPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new TestPresenter(this);
        UIUtil.translucentStatusBar(this);
    }

    public void onGetNewsListSuccess(TestBean bean) {

    }
}
