package com.fpoly.managebookings.views.createOrder;

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
    public void onClickCreate(OrderRoomBooked orderRoomBooked) throws ParseException {
        if (checkInforOrder(orderRoomBooked)){
            mApiOrderRoomBooked.createOrder(orderRoomBooked);
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

        if (orderRoomBooked.getPhone().trim().isEmpty() || orderRoomBooked.getEmail().trim().isEmpty() || orderRoomBooked.getFullName().trim().isEmpty() || orderRoomBooked.getTimeBookingStart().isEmpty() || orderRoomBooked.getTimeBookingEnd().isEmpty()) {
            mCreateOrderInterface.emptyData("User information cannot be empty!");
            return false;
        }else if (timeStart.compareTo(currentTime) <= 0){
            Log.e("now===",timeStart +"==="+currentTime);
            mCreateOrderInterface.checkTimeBooking("Booking time must be after current date!");
            return false;
        }else if(timeStart.compareTo(timeEnd) >= 0){
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
