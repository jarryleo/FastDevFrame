package cn.leo.fastframe;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.leo.fastframe.adapter.TestFragmentAdapter;
import cn.leo.fastframe.test.TestBean;
import cn.leo.fastframe.test.TestPresenter;
import cn.leo.frame.utils.UIUtil;

public class MainActivity extends AppCompatActivity {

    private TestPresenter mPresenter;
    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new TestPresenter(this);
        UIUtil.translucentStatusBar(this);

        mVp = findViewById(R.id.vp_test);
        init();
    }

    private void init() {
        mVp.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
    }


    public void onGetNewsListSuccess(TestBean bean) {

    }
}
