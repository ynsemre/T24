package com.tmob.t24.news_details;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.tmob.t24.R;
import com.tmob.t24.model.NewsObject;
import com.tmob.t24.utils.BaseActivity;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

import java.util.ArrayList;

public class NewsDetailsActivity extends BaseActivity {

    private WebServiceRequestAsync requestAsync;
    private NewsDetailsPagerAdapter newsDetailsPagerAdapter;

    private ArrayList<NewsObject> categoryNewsIdList;

    private ViewPager newsDetailsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        newsDetailsViewPager = (ViewPager) findViewById(R.id.news_details_view_pager);

        int choosenNewsPosition = getIntent().getExtras().getInt("position");
        categoryNewsIdList = getIntent().getParcelableArrayListExtra("categoryNewsIdList");
    }

    private void getNewsDetailsResponse(String newsId, int position) {

    }

    private class NewsDetailsResponseListener implements WebServiceResponseListener {

        private int position;

        public NewsDetailsResponseListener(int position) {
            this.position = position;
        }

        @Override
        public void onResponse(String jsonString) {

        }
    }
}
