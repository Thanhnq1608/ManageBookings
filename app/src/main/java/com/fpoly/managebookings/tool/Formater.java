package com.fpoly.managebookings.tool;


import static android.provider.Settings.Secure.getString;

import static androidx.core.content.res.TypedArrayUtils.getNamedColor;
import static androidx.core.content.res.TypedArrayUtils.getText;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.fpoly.managebookings.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public interface Formater {

    static String formatToDateTime(String date){
        Date temp = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            temp = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(temp);
    }
    static String formatToDate(String date){
        Date temp = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            temp = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(temp);
    }

    static String formatToHour(String time){
        Date temp = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            temp = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(temp);
    }

    static String getKindOfRoom(int id){
        String temp = "";
        switch (id){
            case 0:
                temp = "Single Room";
                break;
            case 1:
                temp = "Double Room";
                break;
            case 2:
                temp = "VIP Single Room";
                break;
            case 3:
                temp = "VIP Double Room";
                break;
        }
        return temp;
    }

    static void setButtonWithBookingStatus(int bookingStatus, Button btn,Button btnRemove){
        switch (bookingStatus){
            case 0:
                btn.setText(R.string.confirm);
                break;
            case 1:
                btn.setText(R.string.check_in);
                break;
            case 2:
                btn.setText(R.string.check_out);
                break;
            case 3:
                btn.setVisibility(View.INVISIBLE);
                btnRemove.setVisibility(View.INVISIBLE);
                break;
        }
    }

    static String getFormatMoney(int price){
        DecimalFormat dFormat = new DecimalFormat();
        String formattedString = dFormat.format(price);
        return formattedString + " VND";
    }

    @SuppressLint("ResourceAsColor")
    static void setStatusForOrder(int bookingStatus, TextView status){
        switch (bookingStatus){
            case 0:
                status.setText(R.string.choXacNhan);
                status.setTextColor(0xFFF10A0A);
                break;
            case 1:
                status.setText(R.string.confirmed);
                status.setTextColor(0xFF28B237);
                break;
            case 2:
                status.setText(R.string.occupied);
                status.setTextColor(0xFFF10A0A);
                break;
            case 3:
                status.setText(R.string.completed);
                status.setTextColor(0XFF28B237);
                break;
        }
    }
}
