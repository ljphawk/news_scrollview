package com.ljp.newsdemo.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljp.newsdemo.R;
import com.ljp.newsdemo.adapter.NewsRvAdapter;
import com.ljp.newsdemo.bean.NewsInfoBean;

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
public class NewsFragment extends Fragment {
    //分类id
    private static final String classifyIdKey = "classifyIdKey";
    private RecyclerView mRecyclerView;
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;

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
        String classify = bundle.getString(classifyIdKey);
        mRecyclerView = view.findViewById(R.id.recyclerView);
//        mRefreshLayout = view.findViewById(R.id.swipeRefresh);
//        mRefreshLayout.setOnRefreshListener(this);
        initData(classify);

        return view;
    }


    private void initData(String classify) {
        List<NewsInfoBean> newsList = new ArrayList<>();
        //制造假数据
        for (int i = 0; i < 20; i++) {
            NewsInfoBean newsInfoBean = new NewsInfoBean();
            newsInfoBean.setTitle(i + "   我是  " + classify + "  类型的新闻标题 ");
            newsInfoBean.setContent("我是新闻内容");
            newsList.add(newsInfoBean);
        }
        //设置adapter
        NewsRvAdapter newsRvAdapter = new NewsRvAdapter(mContext, newsList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(newsRvAdapter);
    }


//    @Override
//    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRefreshLayout.setRefreshing(false);
//            }
//        }, 1500);
//    }
}
