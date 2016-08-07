package com.tmob.t24;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tmob.t24.dialog.CustomAlertDialog;
import com.tmob.t24.utils.ConnectionDetector;
import com.tmob.t24.utils.SharedPreference;

public class BaseActivity extends AppCompatActivity {

    //private WebServiceRequestAsync requestAsync;
    public SharedPreference sharedPreference;

    public Resources resources;
    Context context;
    public Gson gson;
    public ConnectionDetector cd;
    public android.support.v7.app.ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreference = SharedPreference.getInstance(this);

        resources = getResources();
        gson = new Gson();
        context = this;
        cd = new ConnectionDetector(context);
        actionBar = getSupportActionBar();
    }

    public void setActionBar(int viewResId) {
        actionBar.show();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setCustomView(viewResId);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void showMessage(String msg) {
        try {
            CustomAlertDialog.showMessage(this, resources.getString(R.string.app_name), msg, false, resources.getString(R.string.AlertDialog_OKButton), null);
        } catch (Exception e) {

        }
    }

    public void showToastMessage(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
