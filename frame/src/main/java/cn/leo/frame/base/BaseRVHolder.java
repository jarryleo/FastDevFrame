package cn.leo.frame.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Leo on 2017/7/19.
 */

public abstract class BaseRVHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;

    public BaseRVHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public abstract void setData(T t, int position);
}
