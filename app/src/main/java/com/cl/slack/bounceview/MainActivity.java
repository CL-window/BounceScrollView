package com.cl.slack.bounceview;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView,mRecycleView2,mHorizontalRecyclerView;
//    private BounceScrollView mScrollView;

    private List<String> testDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

//        mScrollView = (BounceScrollView) findViewById(R.id.id_scrollView);
//        mScrollView.setCallBack(new ScrollCallback() {
//
//            @Override
//            public void callback() {
//                Toast.makeText(MainActivity.this, "------", Toast.LENGTH_SHORT).show();
//            }
//        });
        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.id_listView);
        mHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mHorizontalRecyclerView.setAdapter(new TestAdapter());

//        mRecyclerView = (RecyclerView) findViewById(R.id.id_listView2);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(new TestAdapter());

        mRecycleView2 = (RecyclerView) findViewById(R.id.id_listView3);
        mRecycleView2.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView2.setAdapter(new TestAdapter());

        if (Build.VERSION.SDK_INT >= 9) {
            mHorizontalRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    private void initData() {
        testDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            testDatas.add("test_" + (char) i);
        }
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TestViewHolder holder = new TestViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.test_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(TestViewHolder holder, int position) {
            holder.bindView(holder,position);
        }

        @Override
        public int getItemCount() {
            return testDatas.size();
        }

        class TestViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public TestViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }

            private void bindView(TestViewHolder holder, int position) {
                holder.tv.setText(testDatas.get(position));
            }

        }
    }


}
