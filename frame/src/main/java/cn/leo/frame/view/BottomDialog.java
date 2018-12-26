package cn.leo.frame.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import cn.leo.frame.R;

/**
 * Created by Leo on 2018/1/4.
 */

public abstract class BottomDialog extends Dialog {

    private final Context mContext;

    public BottomDialog(@NonNull Context context) {
        super(context, R.style.BottomDialog);
        setContentView(getContentViewResId());
        ButterKnife.bind(this);
        mContext = context;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        Window win = getWindow();
        win.getDecorView()
                .setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        show();
    }

    /**
     * 获取底部dialog的布局id
     *
     * @return 布局id
     * @return 布局id
     */
    protected abstract @LayoutRes
    int getContentViewResId();
}
