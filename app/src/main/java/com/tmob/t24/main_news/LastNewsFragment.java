package com.tmob.t24.main_news;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.tmob.t24.BaseFragment;
import com.tmob.t24.R;

public class LastNewsFragment extends BaseFragment {

    private LinearLayout pnlLastNewsContainer;
    private ImageView imgLastNewsImage;
    private TextView txtLastNewsHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_last_news, container, false);

        pnlLastNewsContainer = (LinearLayout) v.findViewById(R.id.pnl_news_manset_container);
        imgLastNewsImage = (ImageView) v.findViewById(R.id.img_last_news);
        txtLastNewsHeader = (TextView) v.findViewById(R.id.txt_last_news_title);

        Bundle bundle = getArguments();
        String newsTitle = bundle.getString("newsTitle");
        String imageUrl = bundle.getString("imageUrl");
        final int position = bundle.getInt("position");
        imageUrl = "http:" + imageUrl;
        setLastNewsInfo(newsTitle, imageUrl);

        pnlLastNewsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openLastNewsDetails(position);
            }
        });

        return v;
    }

    private void setLastNewsInfo(String newsTitle, String imageUrl) {
        txtLastNewsHeader.setText(Html.fromHtml(newsTitle));
        Picasso.with(getActivity()).load(imageUrl).placeholder(R.drawable.placeholder) .into(imgLastNewsImage);
    }

}
