package com.fpoly.managebookings.tool;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.managebookings.R;

public class FixSizeForToast {
    private Context context;

    public FixSizeForToast(Context context) {
        this.context = context;
    }

    public void fixSizeToast(String message){
        SpannableStringBuilder biggerText = new SpannableStringBuilder(message);
        biggerText.setSpan(new RelativeSizeSpan(2.0f), 0, message.length(), 0);
        Toast.makeText(context, biggerText, Toast.LENGTH_SHORT).show();
    }
}
