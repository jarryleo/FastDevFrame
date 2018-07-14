package cn.leo.fastframe.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import cn.leo.fastframe.test.TestFragment;
import cn.leo.frame.base.SuperBaseFragment;
import cn.leo.frame.base.BaseFragmentVPAdapter;

/**
 * Created by Leo on 2018/2/1.
 */

public class TestFragmentAdapter extends BaseFragmentVPAdapter {
    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment createFragment(int position) {
        return new TestFragment();
    }

    @Override
    protected void initFragment(SuperBaseFragment fragment, int position) {
        Bundle data = new Bundle();
        data.putString("title", "第" + position + "个");
        fragment.setArguments(data);
    }

    @Override
    protected int getFragmentCount() {
        return 5;
    }
}
