package com.tmob.t24.category_news;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmob.t24.R;
import com.tmob.t24.model.NewsObject;

import java.util.List;

/**
 * Created by yunusemre on 04/08/16.
 */
public class CategoryNewsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<NewsObject> newsObjectList;

    public CategoryNewsAdapter(Context context, List<NewsObject> newsObjectList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.newsObjectList = newsObjectList;
    }

    @Override
    public int getCount() {
        return newsObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_category_news, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.pnlLoading = (FrameLayout) convertView.findViewById(R.id.pnl_category_news_loading);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_category_news_title);
            viewHolder.imgNewsImage = (ImageView)convertView.findViewById(R.id.img_category_news_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsObject newsObject = newsObjectList.get(position);
        String imagePath = "http:" + newsObject.getImages().getPage();
        Picasso.with(context).load(imagePath).placeholder(R.drawable.placeholder) .into(viewHolder.imgNewsImage);
        viewHolder.txtTitle.setText(Html.fromHtml(newsObject.getTitle()));

        viewHolder.pnlLoading.setVisibility(newsObject.getLoadingVisibility());

        return convertView;
    }

    private class ViewHolder {
        FrameLayout pnlLoading;
        TextView txtTitle;
        ImageView imgNewsImage;
    }
}
