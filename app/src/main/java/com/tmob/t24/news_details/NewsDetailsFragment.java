package com.tmob.t24.news_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmob.t24.BaseFragment;
import com.tmob.t24.R;
import com.tmob.t24.webservice.WebServiceRequestAsync;
import com.tmob.t24.webservice.WebServiceResponseListener;

public class NewsDetailsFragment extends BaseFragment {

    WebServiceRequestAsync requestAsync;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        TextView txtTest = (TextView) view.findViewById(R.id.txt_news_detials_test);
        return view;
    }

    private void getNewsDetailsInfo(String newsId) {
        requestAsync = new WebServiceRequestAsync(getActivity(), newsDetailsResponseListener);
    }

    private WebServiceResponseListener newsDetailsResponseListener = new WebServiceResponseListener() {
        @Override
        public void onResponse(String jsonString) {

        }
    };

}
