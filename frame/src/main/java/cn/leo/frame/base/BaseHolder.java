package cn.leo.frame.base;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseHolder<T> {

    protected Context mContext;
    protected View mView;

    public void inflateView(Context context, int position) {
        mContext = context;
        mView = onCreateView(context, position);
        ButterKnife.bind(this, mView);
        mView.setTag(this);
    }

    protected abstract View onCreateView(Context context, int position);

    public abstract void bindView(T data, int position);

    public View getView() {
        return mView;
    }


}
