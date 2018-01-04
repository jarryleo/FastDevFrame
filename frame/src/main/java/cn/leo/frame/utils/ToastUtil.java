package cn.leo.frame.utils;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast;


    public static void init(Application application) {
        if (toast == null) {
            toast = Toast.makeText(application, "", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 强大的吐司，能够连续弹的吐司并且能在子线程弹吐司
     *
     * @param text
     */
    private static void showToast(final String text, final Boolean isLong) {
        if (toast == null) {
            throw new NullPointerException("未初始化快速开发框架");
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {//线程切换
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showToast(text, isLong);
                }
            });
            return;
        }
        toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.show();//同一个吐司对象不会出现一个接一个弹
    }

    public static void shortToast(String text) {
        showToast(text, false);
    }

    public static void longToast(String text) {
        showToast(text, true);
    }
}
