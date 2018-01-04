package cn.leo.frame;

import android.app.Application;
import android.os.Looper;
import android.os.MessageQueue;

import cn.leo.frame.utils.FileUtil;
import cn.leo.frame.utils.Logger;
import cn.leo.frame.utils.NetworkUtil;
import cn.leo.frame.utils.SPUtil;
import cn.leo.frame.utils.ToastUtil;
import cn.leo.frame.utils.UIUtil;

/**
 * Created by Leo on 2018/1/4.
 */

public class FastFrame {
    public static void init(final Application application) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                lazyInit(application);
                return false;
            }
        });
        /**
         * 初始化工具类
         */
        //初始化吐司
        ToastUtil.init(application);
        //初始化UI
        UIUtil.init(application);
        //初始化SP
        SPUtil.init(application);
        //初始化文件
        FileUtil.init(application);
        //初始化网络
        NetworkUtil.init(application);
    }

    private static void lazyInit(Application application) {
        //初始化日志器
        Logger.init(application);
    }
}
