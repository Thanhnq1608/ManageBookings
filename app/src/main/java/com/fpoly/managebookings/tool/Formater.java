package com.fpoly.managebookings.tool;


import static android.provider.Settings.Secure.getString;


import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.fpoly.managebookings.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public interface Formater {

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String formatDateTimeToString(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.forLanguageTag("vi"));
//        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
//        format.setTimeZone(TimeZone.getTimeZone(zoneId));
        String temp = format.format(format.parse(date));
        return temp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String formatDateToStringForCreateAt(String date) throws ParseException {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.forLanguageTag("vi"));
        SimpleDateFormat output = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.forLanguageTag("vi"));
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        output.setTimeZone(TimeZone.getTimeZone(zoneId));
        Date temp = input.parse(date);
        String dateTime = output.format(temp);
        Log.e("date", "" + dateTime);
        return dateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static Date formatToDateTime(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.forLanguageTag("vi"));
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        format.setTimeZone(TimeZone.getTimeZone(zoneId));
        Date temp = format.parse(date);
        return temp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static Date formatDateTimeToStringGMT(String date) throws ParseException {
        SimpleDateFormat input = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.forLanguageTag("vi"));
//        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        output.setTimeZone(TimeZone.getTimeZone(zoneId));
        Date temp = input.parse(date);
        return output.parse(temp.toString());
    }

    static String formatStringToPhone(String phone) {
        String temp = "+84" + phone.substring(1, phone.length());
        Log.e("Phone", "" + temp);
        return temp;
    }

    static String getKindOfRoom(int id) {
        String temp = "";
        switch (id) {
            case 0:
                temp = "Standard Room";
                break;
            case 1:
                temp = "Superior Room";
                break;
            case 2:
                temp = "Deluxe Room";
                break;
            case 3:
                temp = "Suite Room";
                break;
        }
        return temp;
    }

    static String getRoomStatus(int id) {
        String temp = "";
        switch (id) {
            case 0:
                temp = "Empty";
                break;
            case 1:
                temp = "Reserved";
                break;
            case 2:
                temp = "Occupied";
                break;
        }
        return temp;
    }

    static void setButtonWithBookingStatus(int bookingStatus, Button btn, Button btnRemove) {
        switch (bookingStatus) {
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

    static String getFormatMoney(int price) {
        DecimalFormat dFormat = new DecimalFormat();
        String formattedString = dFormat.format(price);
        return formattedString + " VND";
    }

    @SuppressLint("ResourceAsColor")
    static void setStatusForOrder(int bookingStatus, TextView status) {
        switch (bookingStatus) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String getUsedTDate(String timeStart, String timeEnd) throws ParseException {
        Date start = formatToDateTime(timeStart);
        Date end = formatToDateTime(timeEnd);

        long distanceTime = end.getTime() - start.getTime();
        Log.e("time",""+distanceTime);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long usedDate = distanceTime / daysInMilli;
        Log.e("time",""+usedDate);
        distanceTime = distanceTime % daysInMilli;
        Log.e("time",""+distanceTime);

        long usedHour = distanceTime / hoursInMilli;
        Log.e("time",""+usedHour);

        return usedDate+","+usedHour;
    }
}
