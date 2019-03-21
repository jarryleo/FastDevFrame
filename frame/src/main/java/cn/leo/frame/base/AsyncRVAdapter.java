package cn.leo.frame.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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
    private OnItemClickListener mOnItemClickListener;
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
     * 异步比对去重，areItemsTheSame相同areContentsTheSame不同的则替换位置
     *
     * @param oldList 原列表
     * @param newList 新列表
     */
    private void asyncAddData(final List<T> oldList, final List<T> newList) {
        System.out.println("oldSize" + oldList.size());
        final AsyncDifferConfig<T> config = new AsyncDifferConfig.Builder<T>(diffCallback).build();
        config.getBackgroundThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                boolean change = false;
                for (T newItem : newList) {
                    boolean flag = false;
                    for (int i = 0; i < oldList.size(); i++) {
                        T oldItem = oldList.get(i);
                        if (!diffCallback.areItemsTheSame(oldItem, newItem)) {
                            continue;
                        }
                        flag = diffCallback.areContentsTheSame(oldItem, newItem);
                        if (!flag) {
                            oldList.set(i, newItem);
                            change = true;
                            flag = true;
                            System.out.println("替换第" + i);
                        }
                        break;
                    }
                    if (!flag) {
                        oldList.add(newItem);
                        change = true;
                        System.out.println("增加1个");
                    }
                }
                if (change) {
                    mDiffer.submitList(oldList);
                    System.out.println("更改列表");
                }
            }
        });
    }

    private void asyncAddData(final List<T> data) {
        final List<T> oldList = getData();
        asyncAddData(oldList, data);
    }

    private void asyncAddData(final T data) {
        final List<T> oldList = getData();
        List<T> newList = new ArrayList<>();
        newList.add(data);
        asyncAddData(oldList, newList);
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
     * @param data 数据集
     */
    public void addData(List<T> data) {
        asyncAddData(data);
    }

    /**
     * 新增单条数据
     *
     * @param data 数据
     */
    public void addData(T data) {
        asyncAddData(data);
    }

    /**
     * 根据索引移除条目
     *
     * @param position 条目索引
     */
    public void removeData(int position) {
        List<T> list = getData();
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
        List<T> list = getData();
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
        ((ViewHolder) holder).setData(getItem(position), position);
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
     */
    protected abstract void bindData(ItemHelper itemHelper, T t);


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        /**
         * 点击条目
         *
         * @param t        条目数据
         * @param position 条目索引
         */
        void onItemClick(T t, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemHelper mItemHelper;
        private T mData;

        private ViewHolder(ViewGroup parent, int layout) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(layout, parent, false));
            mItemHelper = new ItemHelper(itemView);
            mItemHelper.setLayoutResId(layout);
            itemView.setOnClickListener(this);
        }

        public void setData(T t, int position) {
            mData = t;
            mItemHelper.setPosition(position);
            bindData(mItemHelper, t);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mData, mItemHelper.getPosition());
            }
        }
    }

    public class ItemHelper {
        private SparseArray<View> viewCache = new SparseArray<>();
        private View itemView;
        private @LayoutRes
        int layoutResId;
        private int mPosition;

        public ItemHelper(View itemView) {
            this.itemView = itemView;
        }

        public @LayoutRes
        int getLayoutResId() {
            return layoutResId;
        }

        private void setLayoutResId(@LayoutRes int layoutResId) {
            this.layoutResId = layoutResId;
        }

        public int getPosition() {
            return mPosition;
        }

        private void setPosition(int position) {
            mPosition = position;
        }

        public View getItemView() {
            return itemView;
        }

        public final <V extends View> V getViewById(@IdRes int viewId) {
            View v = viewCache.get(viewId);
            V view;
            if (v == null) {
                view = itemView.findViewById(viewId);
                if (view == null) {
                    String entryName = itemView.getResources().getResourceEntryName(viewId);
                    throw new NullPointerException("id :" + entryName + " can not find in this item!");
                }
                viewCache.put(viewId, view);
            } else {
                view = (V) v;
            }
            return view;
        }

        /**
         * 给按钮或文本框设置文字
         *
         * @param viewId 控件id
         * @param text   设置的文字
         */
        public void setText(@IdRes int viewId, CharSequence text) {
            View view = getViewById(viewId);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            } else {
                String entryName = view.getResources().getResourceEntryName(viewId);
                throw new ClassCastException("id :" + entryName + " are not TextView");
            }
        }

        /**
         * 给图片控件设置资源图片
         *
         * @param viewId 图片控件id
         * @param resId  资源id
         */
        public void setImageResource(@IdRes int viewId, @DrawableRes int resId) {
            View view = getViewById(viewId);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(resId);
            } else {
                String entryName = view.getResources().getResourceEntryName(viewId);
                throw new ClassCastException("id :" + entryName + " are not ImageView");
            }
        }

        public void setViewVisble(@IdRes int viewId) {
            View view = getViewById(viewId);
            view.setVisibility(View.VISIBLE);
        }

        public void setViewInvisble(@IdRes int viewId) {
            View view = getViewById(viewId);
            view.setVisibility(View.INVISIBLE);
        }

        public void setViewGone(@IdRes int viewId) {
            View view = getViewById(viewId);
            view.setVisibility(View.GONE);
        }

    }

}
