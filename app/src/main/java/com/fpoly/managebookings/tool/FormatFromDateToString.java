package com.fpoly.managebookings.tool;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class FormatFromDateToString {


    public String formatToDate(String date){
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

    public String formatToHour(String time){
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
}
