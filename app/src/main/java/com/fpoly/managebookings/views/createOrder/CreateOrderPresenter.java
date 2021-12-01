package com.fpoly.managebookings.views.createOrder;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.tool.Formater;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.http.Body;

public class CreateOrderPresenter implements ApiOrderBookedInterface {
    private CreateOrderInterface mCreateOrderInterface;
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);

    public CreateOrderPresenter(CreateOrderInterface mCreateOrderInterface) {
        this.mCreateOrderInterface = mCreateOrderInterface;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickCreate(OrderRoomBooked orderRoomBooked, Context context) throws ParseException {
        if (checkInforOrder(orderRoomBooked)){
            mApiOrderRoomBooked.createOrder(orderRoomBooked,context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkInforOrder(OrderRoomBooked orderRoomBooked) throws ParseException {
        Date currentTime = Calendar.getInstance().getTime();
        Log.e("currentTime",""+currentTime);
        Date timeEnd,timeStart;
        if (orderRoomBooked.getTimeBookingStart().isEmpty() || orderRoomBooked.getTimeBookingEnd().isEmpty()){
             timeStart = currentTime;
             timeEnd = currentTime;
        }else {
             timeStart = Formater.formatToDateTime(orderRoomBooked.getTimeBookingStart());
             timeEnd = Formater.formatToDateTime(orderRoomBooked.getTimeBookingEnd());
        }

        if (orderRoomBooked.getPhone().trim().isEmpty() || orderRoomBooked.getEmail().trim().isEmpty() || orderRoomBooked.getFullName().trim().isEmpty()) {
            mCreateOrderInterface.emptyData("User information cannot be empty!");
            return false;
        }else if ( orderRoomBooked.getTimeBookingStart().isEmpty() || orderRoomBooked.getTimeBookingEnd().isEmpty()){
            mCreateOrderInterface.emptyData("You do not select time booking!");
            return false;
        }
        else if(timeStart.compareTo(timeEnd) >= 0){
            mCreateOrderInterface.checkTimeBooking("Booking end time must be after start date!");
            return false;
        }else return true;
    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void responseCreateOrder(OrderRoomBooked orderRoomBooked) {
        mCreateOrderInterface.createSuccess(orderRoomBooked);
    }
}
