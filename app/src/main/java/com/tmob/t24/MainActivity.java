package com.tmob.t24;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.tmob.t24.model.NewsObject;
import com.tmob.t24.model.NewsResult;
import com.tmob.t24.utils.BaseActivity;
import com.tmob.t24.view.CirclePageIndicator;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends BaseActivity {

    private WebServiceRequestAsync requestAsync;

    LastNewsPagerAdapter lastNewsPagerAdapter;
    //NewsAdapter adapter;
    private List<NewsObject> lastNewsList;
    private List<NewsObject> newsList;

    ListView newsListView;

    private CirclePageIndicator circlePageIndicator;
    private ViewPager lastNewsPager;
    private View headerView;

    private boolean canGetMoreNews = false;
    private int newsPreLast;
    private int newsPageIndex = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListView = (ListView) findViewById(R.id.news_list_view);

        LayoutInflater layoutInflater = getLayoutInflater();
        headerView = layoutInflater.inflate(R.layout.header_news_list_header, newsListView, false);
        circlePageIndicator = (CirclePageIndicator) headerView.findViewById(R.id.last_news_circle_indicator);
        lastNewsPager = (ViewPager) headerView.findViewById(R.id.last_news_view_pager);

        if (cd.isConnectingToInternet()) {
            getNews(1);
        }
    }

    private void getNews(int pageIndex) {
        requestAsync = new WebServiceRequestAsync(this, new NewsResponseListener(pageIndex));
        Bundle newsBundle = new Bundle();
        newsBundle.putString("paging", String.valueOf(pageIndex));
        requestAsync.setParams(newsBundle);
        requestAsync.showDialog(true);
        requestAsync.execute(WebServiceRequestAsync.GET_STORIES_LIST);
    }

    private class NewsResponseListener implements WebServiceResponseListener {

        private int pageIndex;

        public NewsResponseListener(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        @Override
        public void onResponse(String jsonString) {
            if (!TextUtils.isEmpty(jsonString)) {
                NewsResult newsResult = gson.fromJson(jsonString, NewsResult.class);
                if (newsResult.getResult()) {
                    if (pageIndex == 1) {
                        lastNewsList = newsResult.getData();
/*                        lastNewsPagerAdapter = new LastNewsPagerAdapter(getSupportFragmentManager());
                        lastNewsPager.setAdapter(lastNewsPagerAdapter);
                        circlePageIndicator.setViewPager(lastNewsPager);
                        newsListView.addHeaderView(headerView, null, false);*/
                    } else {

                    }
                } else {

                }
            }
        }
    }

    private class LastNewsPagerAdapter extends FragmentPagerAdapter {

        public LastNewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LastNewsFragment lastNewsFragment = new LastNewsFragment();
/*            Bundle bundle = pagerIntent(position);
            lastNewsFragment.setArguments(bundle);*/
            return lastNewsFragment;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
