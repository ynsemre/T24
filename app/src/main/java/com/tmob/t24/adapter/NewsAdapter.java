package com.tmob.t24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.tmob.t24.R;
import com.tmob.t24.model.NewsObject;

import java.util.List;

/**
 * Created by yunusemre on 04/08/16.
 */
public class NewsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<NewsObject> newsList;

    public NewsAdapter(Context context, List<NewsObject> newsList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_news_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.pnlLoading = (FrameLayout) convertView.findViewById(R.id.pnl_news_loading);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_item_news_title);
            viewHolder.imgNewsImage = (ImageView)convertView.findViewById(R.id.img_item_news_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsObject newsObject = newsList.get(position);
        String imagePath = "http:" + newsObject.getImages().getList();
        Picasso.with(context).load(imagePath).placeholder(R.drawable.placeholder) .into(viewHolder.imgNewsImage);
        //ImageLoader.getInstance().displayImage(imagePath, viewHolder.imgNewsImage);
        viewHolder.txtTitle.setText(newsObject.getTitle());

        viewHolder.pnlLoading.setVisibility(newsObject.getLoadingVisibility());

        return convertView;
    }

    private class ViewHolder {
        FrameLayout pnlLoading;
        TextView txtTitle;
        ImageView imgNewsImage;
    }
}
