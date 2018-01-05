package cn.leo.frame.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2018/1/5.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    public List<T> mList = new ArrayList<>();

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            holder = getViewHolder(position);
            holder.inflateView(parent.getContext(), position);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        T data = mList.get(position);
        holder.bindView(data);
        return holder.getView();
    }


    //由子类选择ViewHolder
    protected abstract BaseHolder getViewHolder(int data);
}
