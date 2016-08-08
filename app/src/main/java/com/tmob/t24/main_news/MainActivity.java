package com.tmob.t24.main_news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.tmob.t24.BaseActivity;
import com.tmob.t24.R;
import com.tmob.t24.category_news.CategoryNewsActivity;
import com.tmob.t24.model.Category;
import com.tmob.t24.model.CategoryResult;
import com.tmob.t24.model.NewsObject;
import com.tmob.t24.model.NewsResult;
import com.tmob.t24.news_details.NewsDetailsActivity;
import com.tmob.t24.view.CirclePageIndicator;
import com.tmob.t24.view.CustomViewPager;
import com.tmob.t24.view.NoDefaultSpinner;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private WebServiceRequestAsync requestAsync;
    LastNewsPagerAdapter lastNewsPagerAdapter;
    private NewsAdapter newsAdapter;

    private ArrayList<NewsObject> lastNewsList;
    private ArrayList<NewsObject> newsList;
    private List<Category> categoryList;
    private String[] arrCategories;
    private String lastNewsId;

    ListView newsListView;

    private CirclePageIndicator circlePageIndicator;
    private CustomViewPager lastNewsPager;
    private View headerView;

    private boolean canGetMoreNews = false;
    private final int REFRESHING_PERIOD = 12000;
    private int newsPreLast;
    private int newsPageIndex = 2;
    private int currentLastNewsPage = 0;
    private Handler lastNewsPageHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();

        newsListView = (ListView) findViewById(R.id.news_list_view);
        newsListView.setOnItemClickListener(this);
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

        setHeaderView();
        lastNewsPager.setOnSwipeOutListener(lastNewsPagerSwipeOutListener);

        if (cd.isConnectingToInternet()) {
            getCategoryList();
            getNews(1, false);
            getNews(newsPageIndex, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lastNewsList != null && lastNewsList.size() > 0 && newsList != null && newsList.size() > 0) {
            lastNewsPageHandler.postDelayed(lastNewsPageRunnable, 3000);
            lastNewsPageHandler.postDelayed(refreshNewsRunnable, 12000);
        }
    }

    private void setHeaderView() {
        LayoutInflater layoutInflater = getLayoutInflater();
        headerView = layoutInflater.inflate(R.layout.header_news_list_header, newsListView, false);
        circlePageIndicator = (CirclePageIndicator) headerView.findViewById(R.id.last_news_circle_indicator);
        lastNewsPager = (CustomViewPager) headerView.findViewById(R.id.last_news_view_pager);
        lastNewsPager.addOnPageChangeListener(lastNewsPageChangeListener);
    }

    private void setActionBar() {
        setActionBar(R.layout.actionbar_main_activity);
    }

    private void initializeActionBarComponents() {
        View actionView = getSupportActionBar().getCustomView();
        NoDefaultSpinner spinner = (NoDefaultSpinner) actionView.findViewById(R.id.actionbar_category_choice_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.item_spinner, arrCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(MainActivity.this);
        ImageView imgRefresh = (ImageView) actionView.findViewById(R.id.img_actionbar_news_refresh);
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastNewsPageHandler.removeCallbacks(lastNewsPageRunnable);
                lastNewsPageHandler.removeCallbacks(refreshNewsRunnable);
                getNews(1, true);
                getNews(2, true);
            }
        });
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
                    if (pageIndex == 1 || pageIndex == 10) {
                        if (!isRefreshing) {
                            lastNewsList = newsResult.getData();
                            lastNewsId = lastNewsList.get(0).getId();
                            lastNewsPagerAdapter = new LastNewsPagerAdapter(getSupportFragmentManager());
                            lastNewsPager.setAdapter(lastNewsPagerAdapter);
                            circlePageIndicator.setViewPager(lastNewsPager);
                            newsListView.addHeaderView(headerView, null, false);
                            lastNewsPageHandler.postDelayed(refreshNewsRunnable, REFRESHING_PERIOD);
                        } else {
                            if (!TextUtils.isEmpty(lastNewsId) && !lastNewsId.equals(newsResult.getData().get(0).getId())) {
                                currentLastNewsPage = 0;
                                lastNewsList = newsResult.getData();
                                lastNewsId = lastNewsList.get(0).getId();
                                lastNewsPagerAdapter.notifyDataSetChanged();
                                lastNewsPager.setCurrentItem(0);
                                showToastMessage("Yeni Haberler Var");
                            }
                        }
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
                                lastNewsPageHandler.postDelayed(lastNewsPageRunnable, 3000);
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
                        } else {
                            newsList.clear();
                            newsList = newsResult.getData();
                            if (canGetMoreNews)
                                newsList.get(newsList.size() - 1).setLoadingVisibility(View.VISIBLE);
                            newsAdapter = new NewsAdapter(MainActivity.this, newsList);
                            newsListView.setAdapter(newsAdapter);
                            lastNewsPageHandler.postDelayed(lastNewsPageRunnable, 3000);
                        }
                    }
                } else {

                }
            }
        }
    }

    private void getCategoryList() {
        requestAsync = new WebServiceRequestAsync(this, categoryListResponseListener);
        Bundle categoryBundle = new Bundle();
        categoryBundle.putString("type", "story");
        requestAsync.setParams(categoryBundle);
        requestAsync.execute(WebServiceRequestAsync.GET_CATEGORIES);
    }

    private WebServiceResponseListener categoryListResponseListener = new WebServiceResponseListener() {
        @Override
        public void onResponse(String jsonString) {
            if (!TextUtils.isEmpty(jsonString)) {
                CategoryResult categoryResult = gson.fromJson(jsonString, CategoryResult.class);
                if (categoryResult.getResult()) {
                    categoryList = categoryResult.getData();
                    arrCategories = new String[categoryList.size()];
                    for (int i = 0; i < categoryList.size(); i++) {
                        arrCategories[i] = categoryList.get(i).getAlias();
                    }
                    initializeActionBarComponents();
                }
            }
        }
    };

    private Runnable lastNewsPageRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i("Path", "LASTRUNNABLE");
            if (currentLastNewsPage < 9)
                lastNewsPager.setCurrentItem(currentLastNewsPage + 1, true);
            else
                lastNewsPager.setCurrentItem(0);
            lastNewsPageHandler.postDelayed(lastNewsPageRunnable, 3000);
        }
    };

    private Runnable refreshNewsRunnable = new Runnable() {
        @Override
        public void run() {
            if (cd.isConnectingToInternet())
                getNews(10, true);
            else
                showToastMessage(resources.getString(R.string.AlertDialog_NETWORK_CONNECTION_ERROR));
            lastNewsPageHandler.postDelayed(refreshNewsRunnable, REFRESHING_PERIOD);
        }
    };

    private Bundle createPagerBundle(int position) {
        Bundle pagerBundle = new Bundle();
        String newsTitle = lastNewsList.get(position).getTitle();
        String imageUrl = lastNewsList.get(position).getImages().getPage();
        pagerBundle.putString("newsTitle", newsTitle);
        pagerBundle.putString("imageUrl", imageUrl);
        pagerBundle.putInt("position", position);
        return pagerBundle;
    }

    public void openLastNewsDetails(int position) {
        Intent lastNewsIntent = new Intent(MainActivity.this, NewsDetailsActivity.class);
        lastNewsIntent.putExtra("position", position);
        lastNewsIntent.putParcelableArrayListExtra("categoryNewsIdList", lastNewsList);
        startActivity(lastNewsIntent);
    }

    private class LastNewsPagerAdapter extends FragmentStatePagerAdapter {

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

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent newsListIntent = new Intent(MainActivity.this, NewsDetailsActivity.class);
        newsListIntent.putExtra("position", position - 1);
        newsListIntent.putParcelableArrayListExtra("categoryNewsIdList", newsList);
        startActivity(newsListIntent);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent categoryListIntent = new Intent(MainActivity.this, CategoryNewsActivity.class);
        String categoryId = categoryList.get(position).getId();
        categoryListIntent.putExtra("categoryId", categoryId);
        startActivity(categoryListIntent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onStop() {
        lastNewsPageHandler.removeCallbacks(lastNewsPageRunnable);
        lastNewsPageHandler.removeCallbacks(refreshNewsRunnable);
        super.onStop();
    }
}
