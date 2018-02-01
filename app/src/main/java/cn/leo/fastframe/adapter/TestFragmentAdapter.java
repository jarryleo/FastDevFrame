package cn.leo.fastframe.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import cn.leo.fastframe.test.TestFragment;
import cn.leo.frame.base.BaseFragmentVPAdapter;

/**
 * Created by Leo on 2018/2/1.
 */

public class TestFragmentAdapter extends BaseFragmentVPAdapter {
    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TestFragment testFragment = new TestFragment();
        Bundle data = new Bundle();
        data.putString("title", "第" + position + "个");
        testFragment.setArguments(data);
        return testFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
