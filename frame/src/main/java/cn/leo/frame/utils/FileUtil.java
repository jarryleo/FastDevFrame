package cn.leo.frame.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Leo on 2018/1/4.
 */

public class FileUtil extends BaseUtil {

    /**
     * 获取外部缓存路径
     *
     * @return
     */
    public static File getExternalCacheDir() {
        return mContext.getExternalCacheDir();
    }

    /**
     * 获取内部缓存路径
     *
     * @return
     */
    public static File getCacheDir() {
        return mContext.getCacheDir();
    }

    /**
     * 获取内部缓存路径
     *
     * @return
     */
    public static File getFileDir() {
        return mContext.getFilesDir();
    }

    /**
     * 获取下载目录
     *
     * @return
     */
    public static File getDownloadDir() {
        return mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 获取图片目录
     *
     * @return
     */
    public static File getPicDir() {
        return mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

}
