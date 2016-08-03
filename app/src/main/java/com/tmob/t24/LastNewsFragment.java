package com.tmob.t24;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class LastNewsFragment extends BaseFragment {

    private ImageView imgLastNewsImage;
    private TextView txtLastNewsHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_last_news, container, false);

        imgLastNewsImage = (ImageView) v.findViewById(R.id.img_last_news);
        txtLastNewsHeader = (TextView) v.findViewById(R.id.txt_last_news_title);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        String newsTitle = bundle.getString("newsTitle");
        String imageUrl = bundle.getString("imageUrl");
    }

    private void setLastNewsInfo(String newsTitle, String imageUrl) {
        txtLastNewsHeader.setText(newsTitle);
        ImageLoader.getInstance().displayImage(imageUrl, imgLastNewsImage);
    }

}
