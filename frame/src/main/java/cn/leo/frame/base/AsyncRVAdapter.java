package cn.leo.frame.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jarry Leo
 * @date : 2019/3/19 16:09
 */
public abstract class AsyncRVAdapter<T> extends RecyclerView.Adapter {
    private AsyncListDiffer<T> mDiffer;
    private DiffUtil.ItemCallback<T> diffCallback = new DiffUtil.ItemCallback<T>() {

        @Override
        public boolean areItemsTheSame(T oldItem, T newItem) {
            //是不是同一个item，不是则加到列表末尾
            return AsyncRVAdapter.this.areItemsTheSame(oldItem, newItem);
        }

        @Override
        public boolean areContentsTheSame(T oldItem, T newItem) {
            //如果是同一个item，判断内容是不是相同，不相同则替换成新的
            return AsyncRVAdapter.this.areContentsTheSame(oldItem, newItem);
        }


    };

    public AsyncRVAdapter() {
        mDiffer = new AsyncListDiffer<>(this, diffCallback);
    }

    /**
     * 设置新的数据集
     *
     * @param data 数据
     */
    public void setData(List<T> data) {
        mDiffer.submitList(data);
    }

    /**
     * 新增数据集
     *
     * @param data 数据
     */
    public void addData(List<T> data) {
        List<T> list = new ArrayList<>(mDiffer.getCurrentList());
        list.addAll(data);
        mDiffer.submitList(list);
    }

    /**
     * 根据索引移除条目
     *
     * @param position 条目索引
     */
    public void removeData(int position) {
        List<T> list = new ArrayList<>(mDiffer.getCurrentList());
        list.remove(position);
        mDiffer.submitList(list);
    }

    /**
     * 根据对象移除条目
     * 只需要对象满足下面2个条件即可删除
     *
     * @param t 条目对象
     * @see AsyncRVAdapter#areItemsTheSame(Object, Object)
     * @see AsyncRVAdapter#areContentsTheSame(Object, Object)
     */
    public void removeData(T t) {
        List<T> list = new ArrayList<>(mDiffer.getCurrentList());
        list.remove(t);
        mDiffer.submitList(list);
    }

    /**
     * 获取当前数据集
     *
     * @return 返回一个可修改的数据集，修改数据后通过
     * @see AsyncRVAdapter#setData(List)
     * 可以刷新列表
     */
    public List<T> getData() {
        return new ArrayList<>(mDiffer.getCurrentList());
    }

    /**
     * 获取索引位置对应的条目
     *
     * @param position 索引
     * @return 条目
     */
    public T getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    /**
     * 判断条目是不是相同
     *
     * @param oldItem 旧条目
     * @param newItem 新条目
     * @return 是否相同
     */
    protected abstract boolean areItemsTheSame(T oldItem, T newItem);

    /**
     * 判断内容是不是相同，
     * 如果条目相同，内容不同则替换条目
     *
     * @param oldItem 旧条目
     * @param newItem 新条目
     * @return 是否相同
     */
    protected abstract boolean areContentsTheSame(T oldItem, T newItem);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setData(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayout(position);
    }

    /**
     * 获取条目类型的布局
     *
     * @param position 索引
     * @return 布局id
     */
    protected abstract @LayoutRes
    int getItemLayout(int position);

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    /**
     * 给条目绑定数据
     *
     * @param itemHelper 条目帮助类
     * @param t          对应数据
     * @param layout     条目对应的布局，多布局的时候使用
     */
    protected abstract void bindData(ItemHelper itemHelper, T t, @LayoutRes int layout);

    public class ViewHolder extends RecyclerView.ViewHolder {
        private @LayoutRes
        int layoutRes;
        private final ItemHelper mItemHelper;

        private ViewHolder(ViewGroup parent, int layout) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(layout, parent, false));
            layoutRes = layout;
            mItemHelper = new ItemHelper(itemView);
        }

        public void setData(T t) {
            bindData(mItemHelper, t, layoutRes);
        }
    }

    public class ItemHelper {
        private View itemView;

        public ItemHelper(View itemView) {
            this.itemView = itemView;
        }

        public View getView() {
            return itemView;
        }

        public final <V extends View> V getViewById(@IdRes int viewId) {
            return itemView.findViewById(viewId);
        }

        /**
         * 给按钮或文本框设置文字
         *
         * @param viewId 控件id
         * @param text   设置的文字
         */
        public void setText(@IdRes int viewId, CharSequence text) {
            View view = itemView.findViewById(viewId);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            } else {
                String entryName = view.getResources().getResourceEntryName(viewId);
                throw new ClassCastException("id :" + entryName + " are not TextView");
            }
        }

        public void setImageResource(@IdRes int viewId, @DrawableRes int resId) {
            View view = itemView.findViewById(viewId);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(resId);
            } else {
                String entryName = view.getResources().getResourceEntryName(viewId);
                throw new ClassCastException("id :" + entryName + " are not ImageView");
            }
        }

    }

}
