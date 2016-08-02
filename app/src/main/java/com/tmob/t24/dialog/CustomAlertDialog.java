package com.tmob.t24.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tmob.t24.R;

public class CustomAlertDialog {
    public static void showMessage(Context context, String msg) {
        showMessage(context, null, msg, true, null, null, null, null);
    }

    public static void showMessage(Context context, String msg, int dismissTime) {
        showMessage(context, null, msg, true, null, null, null, null, dismissTime);
    }

    public static void showMessage(Context context, String title, String msg,
                                   boolean cancelable, String positiveBtn, Handler positiveBtnHandler) {
        showMessage(context, title, msg, cancelable, positiveBtn, null,
                positiveBtnHandler, null);
    }

    public static void showMessage(Context context, String title, String msg,
                                   boolean cancelable, String positiveBtn, String negativeBtn,
                                   final Handler positiveBtnHandler, final Handler negativeBtnHandler) {

        showMessage(context, title, msg, cancelable, positiveBtn, negativeBtn, positiveBtnHandler, negativeBtnHandler, 0);
    }


    public static void showMessage(Context context, String title, String msg,
                                   boolean cancelable, String positiveBtn, String negativeBtn,
                                   final Handler positiveBtnHandler, final Handler negativeBtnHandler, int dismissTime) {

        showMessage(context, title, msg, cancelable, positiveBtn, negativeBtn, positiveBtnHandler, negativeBtnHandler, dismissTime, null, null);
    }

    public static void showMessage(Context context, String title, String msg,
                                   boolean cancelable, String positiveBtn, String negativeBtn,
                                   final Handler positiveBtnHandler, final Handler negativeBtnHandler, int dismissTime, String neutralBtn, final Handler neutralBtnHandler) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(cancelable);

        if (title == null) {
            builder.setTitle(context.getResources().getString(
                    R.string.app_name));
        } else {
            builder.setTitle(title);
        }

        builder.setMessage(msg);

        if (positiveBtn == null)
            positiveBtn = context.getResources().getString(
                    R.string.AlertDialog_OKButton);

        builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (positiveBtnHandler != null) {
                    positiveBtnHandler.sendEmptyMessage(0);
                }
            }
        });

        if (negativeBtn != null) {
            builder.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (negativeBtnHandler != null) {
                        negativeBtnHandler.sendEmptyMessage(0);
                    }
                }
            });
        }

        if (neutralBtn != null) {
            builder.setNeutralButton(neutralBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (neutralBtnHandler != null) {
                        neutralBtnHandler.sendEmptyMessage(0);
                    }
                }
            });
        }

        final AlertDialog dialog = builder.show();
        int dividerId = dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(dividerId);
        if (divider != null)
            divider.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        int textViewId = dialog.getContext().getResources()
                .getIdentifier("android:id/alertTitle", null, null);
        TextView tv = (TextView) dialog.findViewById(textViewId);
        tv.setTextColor(context.getResources().getColor(
                R.color.colorPrimary));

        Button pstvBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pstvBtn.setBackgroundResource(R.drawable.clickable_item_bg);

        Button ngtvBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        ngtvBtn.setBackgroundResource(R.drawable.clickable_item_bg);

        Button ntrlBtn = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        ntrlBtn.setBackgroundResource(R.drawable.clickable_item_bg);
    }
}
