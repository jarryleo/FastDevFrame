package cn.leo.fastframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.leo.fastframe.test.TestPresenter;
import cn.leo.frame.base.SuperBaseActivity;

public class MainActivity extends SuperBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestPresenter presenter = new TestPresenter(this);
    }

}
