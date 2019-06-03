package com.ljp.newsdemo.view;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ljp.newsdemo.R;
import com.ljp.newsdemo.adapter.NewsRvAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/1 20:42.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    //分类id
    private static final String classifyIdKey = "classifyIdKey";
    private RecyclerView mRecyclerView;
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;
    private NewsRvAdapter mNewsRvAdapter;
    private String mClassify;

    public static NewsFragment getInstance(String classifyId) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(classifyIdKey, classifyId);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        Bundle bundle = getArguments();
        mClassify = bundle.getString(classifyIdKey);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRefreshLayout = view.findViewById(R.id.swipeRefresh);
        mRefreshLayout.setOnRefreshListener(this);
        initData();

        return view;
    }


    private void initData() {
        List<String > newsList = new ArrayList<>();
        //制造假数据
        for (int i = 0; i < 20; i++) {
            newsList.add(i + "   我是  " + mClassify + "  类型的新闻标题 ");
        }
        //设置adapter
        mNewsRvAdapter = new NewsRvAdapter(mContext, newsList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mNewsRvAdapter);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String > newsList = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    newsList.add(i + "   我是  刷新后的 " + mClassify + "  类型的新闻标题 ");
                }
                //设置adapter
                mNewsRvAdapter.setDataList(newsList);
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
            }
        }, 1500);
    }

    public void setRvScrollTop() {
        if (null != mRecyclerView) {
            mRecyclerView.scrollToPosition(0);
        }
    }
}
