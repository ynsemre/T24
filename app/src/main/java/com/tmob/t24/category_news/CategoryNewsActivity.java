package com.tmob.t24.category_news;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tmob.t24.BaseActivity;
import com.tmob.t24.R;
import com.tmob.t24.model.NewsObject;
import com.tmob.t24.model.NewsResult;
import com.tmob.t24.news_details.NewsDetailsActivity;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

import java.util.ArrayList;

public class CategoryNewsActivity extends BaseActivity {

    private WebServiceRequestAsync requestAsync;
    private CategoryNewsAdapter categoryNewsAdapter;

    private ListView categoryNewsListView;
    private TextView txtCategoryAlias;

    private ArrayList<NewsObject> categoryNewsList;
    private String choosenCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);

        setActionBar();

        Bundle categoryBundle = getIntent().getExtras();
        choosenCategoryId = categoryBundle.getString("categoryId");
        //choosenCategoryId = "19";

        categoryNewsListView = (ListView) findViewById(R.id.category_news_list_view);
        categoryNewsListView.setOnItemClickListener(categoryNewsListItemClickListener);

        if (cd.isConnectingToInternet())
            getCategoryStoriesListResponse(choosenCategoryId);
    }

    private void setActionBar() {
        setActionBar(R.layout.actionbar_category_news_activity);

        View actionView = getSupportActionBar().getCustomView();
        LinearLayout pnlBack = (LinearLayout) actionView.findViewById(R.id.pnl_category_back);
        pnlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtCategoryAlias = (TextView) actionView.findViewById(R.id.txt_actionbar_category_title);
    }

    private void getCategoryStoriesListResponse(String categoryId) {
        requestAsync = new WebServiceRequestAsync(this, categoryStoriesResponseListener);
        Bundle categoryStoriesBundle = new Bundle();
        categoryStoriesBundle.putString("category", categoryId);
        requestAsync.setParams(categoryStoriesBundle);
        requestAsync.showDialog(true);
        requestAsync.execute(WebServiceRequestAsync.GET_CATEGORY_STORIES_LIST);
    }

    private WebServiceResponseListener categoryStoriesResponseListener = new WebServiceResponseListener() {
        @Override
        public void onResponse(String jsonString) {
            if (!TextUtils.isEmpty(jsonString)) {
                NewsResult categoryNewsResult = gson.fromJson(jsonString, NewsResult.class);
                if (categoryNewsResult.getResult()) {
                    if (categoryNewsResult.getData() != null && categoryNewsResult.getData().size() > 0) {
                        categoryNewsList = categoryNewsResult.getData();
                        String categoryName = categoryNewsList.get(0).getCategory().getName();
                        txtCategoryAlias.setText(categoryName);
                        categoryNewsAdapter = new CategoryNewsAdapter(CategoryNewsActivity.this, categoryNewsList);
                        categoryNewsListView.setAdapter(categoryNewsAdapter);
                    }
                }
            }
        }
    };

    private AdapterView.OnItemClickListener categoryNewsListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent choosenNewsIntent = new Intent(CategoryNewsActivity.this, NewsDetailsActivity.class);
            choosenNewsIntent.putExtra("position", position);
            choosenNewsIntent.putParcelableArrayListExtra("categoryNewsIdList", categoryNewsList);
            startActivity(choosenNewsIntent);
        }
    };
}
