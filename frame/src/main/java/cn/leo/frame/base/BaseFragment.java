package cn.leo.frame.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Leo on 2018/2/1.
 */

public abstract class BaseFragment extends Fragment {
    private boolean firstVisible = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(inflater, container, savedInstanceState);
        if (view == null) {
            TextView textView = new TextView(inflater.getContext());
            textView.setText("fragment not create view");
            return textView;
        }
        return view;
    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        //此方法只有activity对应的方法调用才会走，fragment隐藏显示不会走
        if (getUserVisibleHint()) {
            //getUserVisibleHint()方法获取对用户的可见状态
            onVisibilityChangedToUser(true, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //此方法只有activity对应的方法调用才会走，fragment隐藏显示不会走
        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(false, false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            onVisibilityChangedToUser(isVisibleToUser, true);
        }
    }

    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     *
     * @param isVisibleToUser                      true：用户能看见当前Fragment；false：用户看不见当前Fragment
     * @param isHappenedInSetUserVisibleHintMethod true：本次回调发生在setUserVisibleHintMethod方法里；false：发生在onResume或onPause方法里
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser,
                                          boolean isHappenedInSetUserVisibleHintMethod) {
        if (isVisibleToUser) {
            if (firstVisible) {
                lazyInit();
                firstVisible = false;
            }
            onVisible(!isHappenedInSetUserVisibleHintMethod);
        } else {
            onHide(!isHappenedInSetUserVisibleHintMethod);
        }
    }

    /**
     * fragment 第一次可见，可以进行数据的延迟加载
     */
    public void lazyInit() {

    }

    /**
     * fragment 可见
     * 参数表示是activity的onResume导致的可见为true
     */
    public void onVisible(boolean onResume) {

    }

    /**
     * fragment 不可见
     * 参数表示是activity的onPause导致的不可见为true
     */
    public void onHide(boolean onPause) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("first", firstVisible);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            firstVisible = savedInstanceState.getBoolean("first", firstVisible);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            firstVisible = savedInstanceState.getBoolean("first", firstVisible);
        }
        super.onCreate(savedInstanceState);
    }

    public void startActivity(Class<Activity> activityClass) {
        if (!isAdded()) return;
        super.startActivity(new Intent(getActivity(), activityClass));
    }
}
