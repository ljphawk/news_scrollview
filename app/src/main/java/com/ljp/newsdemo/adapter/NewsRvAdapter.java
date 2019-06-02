package com.ljp.newsdemo.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ljp.newsdemo.R;
import com.ljp.newsdemo.bean.NewsInfoBean;

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
public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.NewsViewHolder> {

    private Context mContext;
    private List<NewsInfoBean> mShowItems;

    public NewsRvAdapter(Context context, List<NewsInfoBean> showItems) {
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
        NewsInfoBean newsInfoBean = mShowItems.get(i);
        if (null != newsInfoBean) {
            newsViewHolder.mTvTitle.setText(newsInfoBean.getTitle());
            newsViewHolder.mTvContent.setText(newsInfoBean.getContent());

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
        private final TextView mTvContent;
        private final View itemView;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_content);
            this.itemView = itemView;
        }
    }

}
