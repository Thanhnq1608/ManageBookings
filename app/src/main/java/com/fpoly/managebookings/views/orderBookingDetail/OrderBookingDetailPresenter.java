package com.fpoly.managebookings.views.orderBookingDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.orderDetail.ApiOrderDetail;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookingDetailInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.api.sendNotifyFirebase.ApiSendNotifyWithFirebase;
import com.fpoly.managebookings.api.user.ApiUser;
import com.fpoly.managebookings.api.user.GetUserInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.tool.DialogPayment;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderConfirmedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderOccupiedActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class OrderBookingDetailPresenter implements ApiOrderBookingDetailInterface, ApiRoomDetailInterface, GetUserInterface {
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private OrderBookingDetailInterface mOrderBookingDetailInterface;
    private ApiUser apiUser = new ApiUser(this);
    private ApiSendNotifyWithFirebase apiSendNotifyWithFirebase = new ApiSendNotifyWithFirebase();
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this);

    public OrderBookingDetailPresenter(OrderBookingDetailInterface mOrderBookingDetailInterface) {
        this.mOrderBookingDetailInterface = mOrderBookingDetailInterface;
    }

    void getTotal(int payment, int advanceDeposit) {
        double total = payment + payment * 0.05 - advanceDeposit;
        mOrderBookingDetailInterface.getTotal((int) (total));
    }

    void onClickCancel(OrderRoomBooked orderRoomBooked) {
        apiUser.getUserByPhone(orderRoomBooked.getPhone());
        mApiOrderRoomBooked.deleteOrder(orderRoomBooked.get_id());
    }

    void onClickCofirm(OrderRoomBooked orderRoomBooked, ArrayList<RoomDetail> list, Activity context) {
        switch (orderRoomBooked.getBookingStatus()) {
            case 0:
                mApiOrderRoomBooked.changeBookingStatus(orderRoomBooked.get_id(), 1);
                context.startActivity(new Intent(context, ListOrderConfirmedActivity.class));
                break;
            case 1:
                mApiOrderRoomBooked.changeBookingStatus(orderRoomBooked.get_id(), 2);
                mApiRoomDetail.updateWhileRemoveOrCheck(orderRoomBooked.get_id(),2,orderRoomBooked.get_id());
                context.startActivity(new Intent(context, ListOrderOccupiedActivity.class));
                break;
        }
    }

    @Override
    public void deleteOrder(String statusDelete) {
        mOrderBookingDetailInterface.onCancelRoom(statusDelete);
    }

    @Override
    public void changeBookingStatus(String updateStatus) {
        mOrderBookingDetailInterface.onConfirmSuccess(updateStatus);
    }

    @Override
    public void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void getRoomById(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void getAllRoomByIdBooking(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void updateWhileRemoveOrder(String message) {

    }

    private void sendNotification(String message, String tokenTo){
        JsonObject payload = new JsonObject();
//        payload.addProperty("to", "dOkCu5PISgS62M0SCZfT3q:APA91bGhCT6NLXl-iFyFMFln63Pg23LdUx0O4MsYh1uJBGsWzrU6r-6tZKeRNPmx2b7Nl9AtD364lbmv5yLFzdeHNdPcm04wadUipbUKNPRymAYkAUdD9TirzXBKtCsuyPzH1NgZzmdu");
        payload.addProperty("to",tokenTo);
        // compose data payload here
        JsonObject data = new JsonObject();
        data.addProperty("title", "Hotel Booking");
        data.addProperty("message", message);
        // add data payload
        payload.add("data", data);
        apiSendNotifyWithFirebase.sendNotify(payload);
    }

    @Override
    public void getUserByPhone(User user) {
        if (user != null){
            sendNotification("Your booking has been deleted",user.getTokenId());
        }
    }
}
