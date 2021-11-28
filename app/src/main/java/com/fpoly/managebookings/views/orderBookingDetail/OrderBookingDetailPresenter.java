package com.fpoly.managebookings.views.orderBookingDetail;

import android.content.Context;
import android.content.Intent;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.orderDetail.ApiOrderDetail;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookingDetailInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderConfirmedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderOccupiedActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;

import java.util.ArrayList;

public class OrderBookingDetailPresenter implements ApiOrderBookingDetailInterface, ApiRoomDetailInterface {
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private OrderBookingDetailInterface mOrderBookingDetailInterface;
    private ApiOrderDetail mApiOrderDetail = new ApiOrderDetail();
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this);

    public OrderBookingDetailPresenter(OrderBookingDetailInterface mOrderBookingDetailInterface) {
        this.mOrderBookingDetailInterface = mOrderBookingDetailInterface;
    }

    void getTotal(int payment, int advanceDeposit) {
        double total = payment + payment * 0.05 - advanceDeposit;
        mOrderBookingDetailInterface.getTotal((int) (total));
    }

    void onClickCancel(String id) {
        mApiOrderRoomBooked.deleteOrder(id);
    }

    void onClickCofirm(OrderRoomBooked orderRoomBooked, ArrayList<RoomDetail> list, Context context) {
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
            case 2:
                mApiOrderRoomBooked.changeBookingStatus(orderRoomBooked.get_id(), 3);
                mApiOrderDetail.createOrderDetail(list, orderRoomBooked.get_id());
                mApiRoomDetail.updateWhileRemoveOrCheck(orderRoomBooked.get_id(),0,"");
                context.startActivity(new Intent(context, ListOrdersCompletedActivity.class));
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
}
