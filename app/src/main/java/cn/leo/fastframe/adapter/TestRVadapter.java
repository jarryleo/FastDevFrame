package cn.leo.fastframe.adapter;

import cn.leo.fastframe.R;
import cn.leo.fastframe.bean.TestBean;
import cn.leo.frame.base.AsyncRVAdapter;

/**
 * @author : Jarry Leo
 * @date : 2019/3/20 14:44
 */
public class TestRVadapter extends AsyncRVAdapter<TestBean> {
    @Override
    protected boolean areItemsTheSame(TestBean oldItem, TestBean newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    protected boolean areContentsTheSame(TestBean oldItem, TestBean newItem) {
        return oldItem.content.equals(newItem.content);
    }

    @Override
    protected int getItemLayout(int position) {
        return R.layout.item_test_rv;
    }

    @Override
    protected void bindData(ItemHelper helper, TestBean data) {
        helper.setText(R.id.tv_test, data.content);
    }

}
