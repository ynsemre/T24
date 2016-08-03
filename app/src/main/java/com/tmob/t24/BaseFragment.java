package com.tmob.t24;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.tmob.t24.dialog.CustomAlertDialog;
import com.tmob.t24.utils.BaseActivity;
import com.tmob.t24.utils.ConnectionDetector;

public class BaseFragment extends Fragment {

    public BaseActivity mActivity;
    public Resources resources;
    public Gson gson;

    public ConnectionDetector cd;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();
        gson = new Gson();

        cd = new ConnectionDetector(mActivity);
    }

    public void showMessage(String msg) {
        CustomAlertDialog.showMessage(mActivity, resources.getString(R.string.app_name), msg, false, resources.getString(R.string.AlertDialog_OKButton), null);
    }

    public void showConnectionError() {
        CustomAlertDialog.showMessage(getActivity(), resources.getString(R.string.AlertDialog_NETWORK_CONNECTION_ERROR));
    }
}
