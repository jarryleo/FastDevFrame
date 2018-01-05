package cn.leo.fastframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.leo.fastframe.test.TestBean;
import cn.leo.fastframe.test.TestPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestPresenter presenter = new TestPresenter(this);
    }

    public void onGetNewsListSuccess(TestBean bean){

    }
}
