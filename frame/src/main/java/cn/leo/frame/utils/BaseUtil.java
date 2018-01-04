package cn.leo.frame.utils;

import android.app.Application;

/**
 * Created by pc on 2017/3/11.
 */

public class BaseUtil {

    protected static Application mContext;

    public static void init(Application context) {
        mContext = context;
    }
}
