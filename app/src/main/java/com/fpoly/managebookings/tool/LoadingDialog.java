package com.fpoly.managebookings.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import com.fpoly.managebookings.R;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity mActivity){
        this.activity = mActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, Color.TRANSPARENT);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading,null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
