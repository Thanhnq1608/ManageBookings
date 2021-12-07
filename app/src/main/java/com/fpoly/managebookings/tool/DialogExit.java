package com.fpoly.managebookings.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.views.orderBookingDetail.OrderBookingDetailActivity;

public class DialogExit {
    public void exit(Activity context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.exit);
        alertDialogBuilder.setIcon(R.drawable.ic_exit);

        // set dialog message
        alertDialogBuilder
                .setMessage(context.getString(R.string.question_exit)+"?")
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
