package cn.leo.fastframe.app;

import android.app.Application;

import cn.leo.frame.FastFrame;

/**
 * Created by Leo on 2018/1/5.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FastFrame.init(this);
    }
}
