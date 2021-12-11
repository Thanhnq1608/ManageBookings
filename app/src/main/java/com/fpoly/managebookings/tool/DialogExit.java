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
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogExit {

    public void exit(Activity context) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.dialog_custom_message);
        dialog.setCancelable(false);

        TextView tvTitleDialog;
        TextView tvMessageDialog;
        TextView btnYesDialog;
        TextView btnNoDialog;

        btnYesDialog = (TextView) dialog.findViewById(R.id.btn_yes_dialog);
        btnNoDialog = (TextView) dialog.findViewById(R.id.btn_no_dialog);
        tvTitleDialog = dialog.findViewById(R.id.tvTitleDialog);
        tvMessageDialog = dialog.findViewById(R.id.tv_message_dialog);

        btnYesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finishAffinity();
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
