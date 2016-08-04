package com.tmob.t24.webservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;

public class WebServiceRequestAsync extends AsyncTask<Integer, Void, String> {

    public static final int GET_STORIES_LIST = 0;
    public static final int GET_CATEGORY_STORIES_LIST = 1;

    private final String REQUEST_PAGING = "paging";
    private final String REQUEST_CATEGORY = "category";

    ProgressDialog serviceProgress;

    boolean showDialog = false, isCancelable = true;
    public static WebServiceRequestAsync webServiceRequestAsync;
    WebServiceResponseListener responseListener;
    Activity activity;
    Bundle bundle;
    String msg = "Lütfen Bekleyiniz...";
    int method;

    public WebServiceRequestAsync(Activity activity) {
        this.activity = activity;
        webServiceRequestAsync = WebServiceRequestAsync.this;
    }

    public WebServiceRequestAsync(Activity activity, WebServiceResponseListener responseListener) {
        this.activity = activity;
        this.responseListener = responseListener;
        webServiceRequestAsync = WebServiceRequestAsync.this;
        //msg = activity.getResources().getString(R.string.loading);
    }

    public void showDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public void setCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void setParams(Bundle bundle) {
        this.bundle = bundle;
    }

    public void cancelRequest() {
        webServiceRequestAsync.cancel(true);
    }

    @Override
    protected void onPreExecute() {
        if (showDialog) {
            if (serviceProgress != null && !serviceProgress.isShowing()) {

            }
            try {
                serviceProgress = new ProgressDialog(activity);
                serviceProgress = ProgressDialog.show(activity, null, msg, true);
                serviceProgress.setCancelable(true);
                serviceProgress.setCanceledOnTouchOutside(false);
                serviceProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        //
                    }
                });
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected String doInBackground(Integer... methods) {

        method = methods[0];

        switch (method) {
            case GET_STORIES_LIST:
                return sendGetStoriesListRequest();
            case GET_CATEGORY_STORIES_LIST:
                return sendGetCategorySoriesListRequest();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if (serviceProgress != null && serviceProgress.isShowing()) {
                serviceProgress.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            this.serviceProgress = null;
        }
        handleResult(result);
    }

    public void handleResult(String result) {

        if (responseListener != null)
            responseListener.onResponse(result);
    }

    public String sendGetStoriesListRequest() {
        try {
            ArrayList<ParcelableNameValuePair> params = new ArrayList<ParcelableNameValuePair>();
            params.add(new ParcelableNameValuePair(REQUEST_PAGING, bundle.getString(REQUEST_PAGING)));
            return ServiceClient.SendHttpGet(ServiceModel.GET_STORIES_URL, params);
        } catch (Exception e) {

        }
        return null;
    }

    public String sendGetCategorySoriesListRequest() {
        try {
            ArrayList<ParcelableNameValuePair> params = new ArrayList<ParcelableNameValuePair>();
            params.add(new ParcelableNameValuePair(REQUEST_CATEGORY, bundle.getString(REQUEST_CATEGORY)));
            return ServiceClient.SendHttpGet(ServiceModel.GET_STORIES_URL, params);
        } catch (Exception e) {

        }
        return null;
    }

}
