package cn.leo.fastframe.adapter;

import android.view.View;
import android.widget.Toast;

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
    protected void bindData(ItemHelper helper, final TestBean data) {
        helper.setText(R.id.tv_test, data.content);
        helper.subscribeClick(R.id.tv_test);
    }

    @Override
    protected void onClickObserve(View view, TestBean data) {
        if (view.getId() == R.id.tv_test) {
            Toast.makeText(view.getContext(), "点击了：条目内 " + data.content, Toast.LENGTH_SHORT).show();
        }
    }
}
