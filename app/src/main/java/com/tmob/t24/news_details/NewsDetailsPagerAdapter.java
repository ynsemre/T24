package com.tmob.t24.news_details;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmob.t24.R;
import com.tmob.t24.model.NewsObject;

import java.util.ArrayList;

/**
 * Created by yunusemre on 05/08/16.
 */
public class NewsDetailsPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<NewsObject> newsDetailsPagerList;

    public NewsDetailsPagerAdapter(Context context, ArrayList<NewsObject> newsDetailsPagerList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.newsDetailsPagerList = newsDetailsPagerList;
    }

    @Override
    public int getCount() {
        return newsDetailsPagerList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        NewsObject newsObject = newsDetailsPagerList.get(position);

        View newsBodyView = layoutInflater.inflate(R.layout.item_news_details_pager, null);
        TextView txtNewsTitle = (TextView) newsBodyView.findViewById(R.id.txt_news_details_title);
        WebView newsTextWebView = (WebView) newsBodyView.findViewById(R.id.item_news_details_text_webview);
        //TextView txtNewsText = (TextView) newsBodyView.findViewById(R.id.txt_item_news_details_text);
        ImageView imgNewsImage = (ImageView) newsBodyView.findViewById(R.id.img_item_news_details_image);
        FrameLayout loadingProgressBarContainer = (FrameLayout) newsBodyView.findViewById(R.id.pnl_news_details_progressbar_container);

        if (!TextUtils.isEmpty(newsObject.getText())) {
            txtNewsTitle.setText(Html.fromHtml(newsObject.getTitle()));
            String imagePath = "http:" + newsObject.getImages().getPage();
            Picasso.with(context).load(imagePath).into(imgNewsImage);
            newsTextWebView.getSettings().setJavaScriptEnabled(true);
            newsTextWebView.loadDataWithBaseURL("", newsObject.getText(), "text/html", "UTF-8", "");
            //txtNewsText.setText(Html.fromHtml(newsObject.getText()));
            loadingProgressBarContainer.setVisibility(View.GONE);
        }

        container.addView(newsBodyView);
        return newsBodyView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
