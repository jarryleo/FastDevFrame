package cn.leo.frame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class SPUtil extends BaseUtil {

    private final static String name = "config";
    private final static int mode = Context.MODE_PRIVATE;

    /**
     * 保存首选项
     *
     * @param key
     * @param value
     */
    public static void saveBoolean(String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static void saveInt(String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static void saveString(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }


    /**
     * 获取首选项
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(name, mode);
        return sp.getBoolean(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(name, mode);
        return sp.getInt(key, defValue);
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(name, mode);
        return sp.getString(key, defValue);
    }

}
