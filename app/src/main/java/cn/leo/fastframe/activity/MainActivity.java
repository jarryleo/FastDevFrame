package cn.leo.fastframe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import cn.leo.fastframe.R;
import cn.leo.fastframe.adapter.TestFragmentAdapter;
import cn.leo.fastframe.base.BaseActivity;
import cn.leo.fastframe.test.TestBean;
import cn.leo.fastframe.test.TestPresenter;
import cn.leo.frame.utils.PermissionUtil;
import cn.leo.frame.utils.ToastUtil;
import cn.leo.frame.utils.UIUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TestPresenter mPresenter;
    private ViewPager mVp;
    private Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new TestPresenter(this);
        UIUtil.translucentStatusBar(this);

        mVp = findViewById(R.id.vp_test);

        mBtnTest = findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
        init();
    }

    private void init() {
        mVp.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
    }


    public void onGetNewsListSuccess(TestBean bean) {
        Intent a = new Intent();
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, Main2Activity.class));
    }

    private void test1() {
        PermissionUtil.getInstance(this)
                .request(PermissionUtil.permission.READ_EXTERNAL_STORAGE,
                        PermissionUtil.permission.CAMERA)
                .execute(new PermissionUtil.Result() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.shortToast("申请权限成功");
                    }

                    @Override
                    public void onFailed() {
                        ToastUtil.shortToast("申请权限失败");
                    }
                });
    }
}
