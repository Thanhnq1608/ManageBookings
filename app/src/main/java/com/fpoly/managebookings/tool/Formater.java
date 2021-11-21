package com.fpoly.managebookings.tool;


import static android.provider.Settings.Secure.getString;

import static androidx.core.content.res.TypedArrayUtils.getText;

import android.content.res.Resources;
import android.widget.Switch;

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
    static String getFormatMoney(int price){
        DecimalFormat dFormat = new DecimalFormat();
        String formattedString = dFormat.format(price);
        return formattedString + " VND";
    }

    static String getBookingStatus(int bookingStatus){
        String temp = "";
        switch (bookingStatus){
            case 0:
                temp = "Not Confirmed Yet";
                break;
            case 1:
                temp = "Confirmed";
                break;
            case 2:
                temp = "Occupied";
                break;
            case 3:
                temp = "Check-out";
                break;
        }
        return temp;
    }
}
