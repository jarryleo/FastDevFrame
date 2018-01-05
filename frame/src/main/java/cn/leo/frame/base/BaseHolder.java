package cn.leo.frame.base;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseHolder<T> {

    protected Context mContext;
    protected int mPosition;
    protected View mView;

    public void inflateView(Context context, int position) {
        mContext = context;
        mPosition = position;
        mView = onCreateView(context);
        ButterKnife.bind(this, mView);
        mView.setTag(this);
    }

    protected abstract View onCreateView(Context context);

    public abstract void bindView(T data);

    public View getView() {
        return mView;
    }


}
