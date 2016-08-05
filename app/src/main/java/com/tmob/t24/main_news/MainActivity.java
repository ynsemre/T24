package com.tmob.t24.main_news;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.tmob.t24.R;
import com.tmob.t24.model.NewsObject;
import com.tmob.t24.model.NewsResult;
import com.tmob.t24.utils.BaseActivity;
import com.tmob.t24.view.CirclePageIndicator;
import com.tmob.t24.view.CustomViewPager;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private WebServiceRequestAsync requestAsync;
    LastNewsPagerAdapter lastNewsPagerAdapter;
    private NewsAdapter newsAdapter;

    private ArrayList<NewsObject> lastNewsList;
    private ArrayList<NewsObject> newsList;

    ListView newsListView;

    private CirclePageIndicator circlePageIndicator;
    private CustomViewPager lastNewsPager;
    private View headerView;

    private boolean canGetMoreNews = false;
    private int newsPreLast;
    private int newsPageIndex = 2;
    private int currentLastNewsPage = 0;
    private Handler lastNewsPageHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListView = (ListView) findViewById(R.id.news_list_view);
        newsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (canGetMoreNews) {
                    final int lastItem = firstVisibleItem + visibleItemCount;
                    if (lastItem == totalItemCount) {
                        if (newsPreLast != lastItem) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    newsPreLast = lastItem;
                                    getNews(newsPageIndex + 1, false);
                                }
                            });
                        }
                    }
                }
            }
        });

        LayoutInflater layoutInflater = getLayoutInflater();
        headerView = layoutInflater.inflate(R.layout.header_news_list_header, newsListView, false);
        circlePageIndicator = (CirclePageIndicator) headerView.findViewById(R.id.last_news_circle_indicator);
        lastNewsPager = (CustomViewPager) headerView.findViewById(R.id.last_news_view_pager);
        lastNewsPager.addOnPageChangeListener(lastNewsPageChangeListener);
        lastNewsPager.setOnSwipeOutListener(lastNewsPagerSwipeOutListener);

        if (cd.isConnectingToInternet()) {
            getNews(1, false);
            getNews(newsPageIndex, false);
        }
    }

    private void getNews(int pageIndex, boolean isRefreshing) {
        requestAsync = new WebServiceRequestAsync(this, new NewsResponseListener(pageIndex, isRefreshing));
        Bundle newsBundle = new Bundle();
        newsBundle.putString("paging", String.valueOf(pageIndex));
        requestAsync.setParams(newsBundle);
        if (pageIndex <= 2)
            requestAsync.showDialog(true);
        requestAsync.execute(WebServiceRequestAsync.GET_STORIES_LIST);
    }

    private class NewsResponseListener implements WebServiceResponseListener {

        private int pageIndex;
        private boolean isRefreshing;

        public NewsResponseListener(int pageIndex, boolean isRefreshing) {
            this.pageIndex = pageIndex;
            this.isRefreshing = isRefreshing;
        }

        @Override
        public void onResponse(String jsonString) {
            if (!TextUtils.isEmpty(jsonString)) {
                NewsResult newsResult = gson.fromJson(jsonString, NewsResult.class);
                if (newsResult.getResult()) {
                    if (pageIndex == 1) {
                        lastNewsList = newsResult.getData();
                        lastNewsPagerAdapter = new LastNewsPagerAdapter(getSupportFragmentManager());
                        lastNewsPager.setAdapter(lastNewsPagerAdapter);
                        circlePageIndicator.setViewPager(lastNewsPager);
                        newsListView.addHeaderView(headerView, null, false);
                    } else {
                        int totalPages = newsResult.getPaging().getPages();
                        if (pageIndex < totalPages) {
                            newsPageIndex = pageIndex;
                            canGetMoreNews = true;
                        }
                        if (!isRefreshing) {
                            if (pageIndex == 2) {
                                newsList = newsResult.getData();
                                if (canGetMoreNews)
                                    newsList.get(newsList.size() - 1).setLoadingVisibility(View.VISIBLE);
                                newsAdapter = new NewsAdapter(MainActivity.this, newsList);
                                newsListView.setAdapter(newsAdapter);
                                //lastNewsPageHandler.postDelayed(lastNewsPageRunnable, 3000);
                            } else {
                                newsList.get(newsList.size() - 1).setLoadingVisibility(View.GONE);
                                newsList.addAll(newsResult.getData());
                                if (canGetMoreNews)
                                    newsList.get(newsList.size() - 1).setLoadingVisibility(View.VISIBLE);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        newsAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                } else {

                }
            }
        }
    }

    private Runnable lastNewsPageRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentLastNewsPage < 9)
                lastNewsPager.setCurrentItem(currentLastNewsPage + 1, true);
            else
                lastNewsPager.setCurrentItem(0);
            //lastNewsPageHandler.postDelayed(lastNewsPageRunnable, 3000);
        }
    };

    private Bundle createPagerBundle(int position) {
        Bundle pagerBundle = new Bundle();
        String newsTitle = lastNewsList.get(position).getTitle();
        String imageUrl = lastNewsList.get(position).getImages().getPage();
        pagerBundle.putString("newsTitle", newsTitle);
        pagerBundle.putString("imageUrl", imageUrl);
        return pagerBundle;
    }

    private class LastNewsPagerAdapter extends FragmentPagerAdapter {

        public LastNewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LastNewsFragment lastNewsFragment = new LastNewsFragment();
            Bundle bundle = createPagerBundle(position);
            lastNewsFragment.setArguments(bundle);
            return lastNewsFragment;
        }

        @Override
        public int getCount() {
            return lastNewsList.size();
        }
    }

    private CustomViewPager.OnSwipeOutListener lastNewsPagerSwipeOutListener = new CustomViewPager.OnSwipeOutListener() {
        @Override
        public void onSwipeOutAtStart() {

        }

        @Override
        public void onSwipeOutAtEnd() {
            lastNewsPager.setCurrentItem(0);
        }
    };

    private ViewPager.OnPageChangeListener lastNewsPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentLastNewsPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
