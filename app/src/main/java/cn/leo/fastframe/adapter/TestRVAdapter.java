package cn.leo.fastframe.adapter;

import cn.leo.fastframe.R;
import cn.leo.fastframe.bean.TestBean;
import cn.leo.frame.base.AsyncRVAdapter;

/**
 * @author : Jarry Leo
 * @date : 2019/3/20 14:44
 */
public class TestRVAdapter extends AsyncRVAdapter<TestBean> {

    @Override
    protected int getItemLayout(int position) {
        return R.layout.item_test_rv;
    }

    @Override
    protected void bindData(ItemHelper helper, TestBean data) {
        helper.setText(R.id.tv_test, data.content);
    }

}
