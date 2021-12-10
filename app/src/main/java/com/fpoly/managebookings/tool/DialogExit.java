package com.fpoly.managebookings.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.views.orderBookingDetail.OrderBookingDetailActivity;

public class DialogExit {
    private Dialog dialog;

    public void exit(Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();
        View rootLayout = inflater.inflate(R.layout.dialog_custom_message,null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setView(rootLayout);

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.setCancelable(false);

        TextView tvTitleDialog;
        TextView tvMessageDialog;
        TextView btnYesDialog;
        TextView btnNoDialog;

        btnYesDialog = (TextView) rootLayout.findViewById(R.id.btn_yes_dialog);
        btnNoDialog = (TextView) rootLayout.findViewById(R.id.btn_no_dialog);

        btnYesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });

        btnNoDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
