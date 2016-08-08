package com.tmob.t24.news_details;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmob.t24.R;
import com.tmob.t24.model.NewsDetailsResult;
import com.tmob.t24.model.NewsObject;
import com.tmob.t24.BaseActivity;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

import java.util.ArrayList;

public class NewsDetailsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private WebServiceRequestAsync requestAsync;
    private NewsDetailsPagerAdapter newsDetailsPagerAdapter;

    private ArrayList<NewsObject> newsDetailsPagerList;

    private ViewPager newsDetailsViewPager;
    private TextView txtNewsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        setActivonBar();

        newsDetailsViewPager = (ViewPager) findViewById(R.id.news_details_view_pager);

        int choosenNewsPosition = getIntent().getExtras().getInt("position");
        newsDetailsPagerList = getIntent().getParcelableArrayListExtra("categoryNewsIdList");

        newsDetailsPagerAdapter = new NewsDetailsPagerAdapter(this, newsDetailsPagerList);
        newsDetailsViewPager.addOnPageChangeListener(this);
        newsDetailsViewPager.setAdapter(newsDetailsPagerAdapter);
        newsDetailsViewPager.setCurrentItem(choosenNewsPosition);
        if (newsDetailsPagerList != null && newsDetailsPagerList.size() > 0)
            newsDetailsViewPager.setOffscreenPageLimit(newsDetailsPagerList.size() - 1);

        if (choosenNewsPosition == 0) {
            getNewsDetailsResponse(newsDetailsPagerList.get(choosenNewsPosition).getId(), choosenNewsPosition);
            txtNewsCount.setText(choosenNewsPosition + 1 + " / " + newsDetailsPagerList.size());
        }
    }

    private void setActivonBar() {
        setActionBar(R.layout.actionbar_category_news_activity);

        View actionView = getSupportActionBar().getCustomView();
        LinearLayout pnlBack = (LinearLayout) actionView.findViewById(R.id.pnl_category_back);
        pnlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtNewsCount = (TextView) actionView.findViewById(R.id.txt_actionbar_category_title);
    }

    private void getNewsDetailsResponse(String newsId, int position) {
        requestAsync = new WebServiceRequestAsync(this, new NewsDetailsResponseListener(position));
        Bundle newsDetailsBundle = new Bundle();
        newsDetailsBundle.putString("story", newsId);
        requestAsync.setParams(newsDetailsBundle);
        requestAsync.execute(WebServiceRequestAsync.GET_CATEGORY_STORY_DETAILS);
    }

    private class NewsDetailsResponseListener implements WebServiceResponseListener {

        private int position;

        public NewsDetailsResponseListener(int position) {
            this.position = position;
        }

        @Override
        public void onResponse(String jsonString) {
            if (!TextUtils.isEmpty(jsonString)) {
                NewsDetailsResult newsDetailsResult = gson.fromJson(jsonString, NewsDetailsResult.class);
                if (newsDetailsResult.getResult()) {
                    NewsObject tempNewsObject = newsDetailsResult.getData();
                    newsDetailsPagerList.set(position, tempNewsObject);
                    newsDetailsPagerAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        txtNewsCount.setText(position + 1 + " / " + newsDetailsPagerList.size());
        NewsObject newsObject = newsDetailsPagerList.get(position);
        if (TextUtils.isEmpty(newsObject.getText())) {
            getNewsDetailsResponse(newsObject.getId(), position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
