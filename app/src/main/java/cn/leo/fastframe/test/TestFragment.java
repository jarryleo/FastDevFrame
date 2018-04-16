package cn.leo.fastframe.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.leo.frame.base.BaseFragment;
import cn.leo.frame.utils.Logger;
import cn.leo.frame.utils.ToastUtil;

/**
 * Created by Leo on 2018/1/5.
 */

public class TestFragment extends BaseFragment {
    private String mTitle;


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("title");
    }

    @Override
    public void onVisible(boolean onResume) {
        super.onVisible(onResume);
        Logger.i("fragment---可见" + mTitle + onResume);
    }

    @Override
    public void onHide(boolean onPause) {
        super.onHide(onPause);
        Logger.i("fragment---不可见" + mTitle + onPause);
    }

    @Override
    public void lazyInit() {
        super.lazyInit();
        Logger.i("fragment---第一次可见" + mTitle);
        ToastUtil.shortToast("fragment---第一次可见" + mTitle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
