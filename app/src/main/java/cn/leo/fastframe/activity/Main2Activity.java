package cn.leo.fastframe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.leo.fastframe.R;
import cn.leo.fastframe.adapter.TestRVadapter;
import cn.leo.fastframe.bean.TestBean;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TestRVadapter mAdapter;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mBtn = findViewById(R.id.btnDel);
        mRecyclerView = findViewById(R.id.rvTest);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new TestRVadapter();
        mRecyclerView.setAdapter(mAdapter);
        initView();
        initData();
    }

    private void initView() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeData(5);
            }
        });
    }

    private void initData() {
        List<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TestBean e = new TestBean();
            e.id = i;
            e.content = "测试条目" + i;
            list.add(e);
        }
        mAdapter.setData(list);
    }
}
