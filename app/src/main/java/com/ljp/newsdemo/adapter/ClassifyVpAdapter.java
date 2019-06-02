package com.ljp.newsdemo.adapter;


import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ljp.newsdemo.view.NewsFragment;

import java.util.List;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/1 20:34.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class ClassifyVpAdapter extends FragmentPagerAdapter {

    private List<NewsFragment> mShowItems;
    private String[] titleList;


    public ClassifyVpAdapter(FragmentManager fm, List<NewsFragment> showItems, String[] classify) {
        super(fm);
        mShowItems = showItems;
        titleList = classify;
    }

    @Override
    public NewsFragment getItem(int i) {
        if (mShowItems == null) {
            return null;
        }
        return mShowItems.get(i);
    }


    @Override
    public int getCount() {
        return mShowItems == null ? 0 : mShowItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }

}
