package com.ljp.newsdemo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ljp.newsdemo.R;
import com.ljp.newsdemo.adapter.ClassifyVpAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private NewsViewPager mViewPager;
    private NewsScrollView mNewScrollview;
    private Context mContext;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initViews();
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mTvContent = findViewById(R.id.tv_content);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mNewScrollview = findViewById(R.id.news_scrollview);

        mTvContent.post(new Runnable() {
            @Override
            public void run() {
                mNewScrollview.setMaxScrollViewRange(mTvContent.getMeasuredHeight());
                initData();
                initListener();
            }
        });
    }

    private void initData() {
        //初始化vpAdapter 关联tablayout
        String[] classify = {"推荐", "视频", "热点", "上海", "娱乐", "体育", "星座", "汽车"};
        List<NewsFragment> vpDataList = new ArrayList<>();
        for (int i = 0; i < classify.length; i++) {
            NewsFragment newsFragment = NewsFragment.getInstance(classify[i]);
            vpDataList.add(newsFragment);
        }
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        ClassifyVpAdapter classifyVpAdapter = new ClassifyVpAdapter(getSupportFragmentManager(), vpDataList, classify);
        mViewPager.setAdapter(classifyVpAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        //初始化时把tablayout移除屏幕外
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.setTranslationY(-mTabLayout.getMeasuredHeight());
            }
        });
    }

    private void initListener() {
        mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了文字", Toast.LENGTH_SHORT).show();
            }
        });
        mNewScrollview.setOnScrollListener(new NewsScrollView.OnScrollListener() {
            @Override
            public void onScrollChange(final float y, int maxRange, boolean isAnim) {
                //计算tabLayout要移动的距离
                int height = mTabLayout.getHeight();
                //计算倍率
                float power = height * 1f / maxRange;
                //计算tablayout的滚动距离
                float tabMoveY = -height + power * Math.abs(y);
                //透明度的计算0—255  假设最多透明到100f
                float alphaPower = 1f / maxRange;
                float alpha = 1 - (alphaPower * Math.abs(y));
                Log.d("===", "onScrollChange: alpha = " + alpha);
                if (isAnim) {
                    AnimListener animListener = new AnimListener();
                    ViewCompat.animate(mViewPager).translationY(y).setDuration(500).start();
                    ViewCompat.animate(mTabLayout).translationY(tabMoveY).setDuration(500).setListener(animListener).start();
                    if (y == 0) {
                        //还原了
                        ViewCompat.animate(mTvContent).alpha(alpha).alphaBy(1).setDuration(500).start();
                    } else {
                        //合并了
                        ViewCompat.animate(mTvContent).alpha(alpha).alphaBy(0).setDuration(500).start();
                    }
                } else {
                    mViewPager.setTranslationY(y);
                    mTabLayout.setTranslationY(tabMoveY);
                    mTvContent.setAlpha(alpha);
                }
            }
        });
    }

    //恢复layout的动画
    private void restoreLayoutAnim() {
        ViewCompat.animate(mViewPager).translationY(0).setDuration(500).start();
        ViewCompat.animate(mTabLayout).translationY(-mTabLayout.getHeight()).setDuration(500).start();
        ViewCompat.animate(mTvContent).alpha(0).alphaBy(1).setDuration(500).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mNewScrollview.getIsMerge()) {
                mNewScrollview.scrollTo(0,0);
                //恢复原来的样子
                restoreLayoutAnim();
                mNewScrollview.setMergeState(false);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 可以在这个类里边 进行一些状态的变更
     *
     * 比如动画执行完以后才能点击
     * 或者动画执行完以后才能再次按返回
     */
    private class AnimListener implements ViewPropertyAnimatorListener {

        @Override
        public void onAnimationStart(View view) {

        }

        @Override
        public void onAnimationEnd(View view) {

        }

        @Override
        public void onAnimationCancel(View view) {

        }
    }
}
