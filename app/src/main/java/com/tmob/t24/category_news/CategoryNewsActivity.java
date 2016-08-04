package com.tmob.t24.category_news;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tmob.t24.R;
import com.tmob.t24.model.NewsObject;
import com.tmob.t24.model.NewsResult;
import com.tmob.t24.utils.BaseActivity;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

import java.util.List;

public class CategoryNewsActivity extends BaseActivity {

    private WebServiceRequestAsync requestAsync;
    private CategoryNewsAdapter categoryNewsAdapter;

    private ListView categoryNewsListView;

    private List<NewsObject> categoryNewsList;
    private String choosenCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);

        Bundle categoryBundle = getIntent().getExtras();
        //choosenCategoryId = categoryBundle.getString("categoryId");
        choosenCategoryId = "19";

        categoryNewsListView = (ListView) findViewById(R.id.category_news_list_view);
        categoryNewsListView.setOnItemClickListener(categoryNewsListItemClickListener);

        if (cd.isConnectingToInternet())
            getCategoryStoriesListResponse(choosenCategoryId);
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

        }
    };
}
