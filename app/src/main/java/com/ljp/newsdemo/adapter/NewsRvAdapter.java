package com.ljp.newsdemo.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ljp.newsdemo.R;

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

@SuppressLint("RecyclerView")
public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.NewsViewHolder> {

    private Context mContext;
    private List<String> mShowItems;

    public NewsRvAdapter(Context context, List<String> showItems) {
        mContext = context;
        mShowItems = showItems;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_news_item, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder newsViewHolder, final int i) {
        String title = mShowItems.get(i);
        if (null != title) {
            newsViewHolder.mTvTitle.setText(title);

            newsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "position = " + i, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mShowItems == null ? 0 : mShowItems.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final View itemView;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            this.itemView = itemView;
        }
    }

}
