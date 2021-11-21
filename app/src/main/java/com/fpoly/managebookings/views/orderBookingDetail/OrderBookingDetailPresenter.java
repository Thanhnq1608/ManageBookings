package com.fpoly.managebookings.views.orderBookingDetail;

import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookingDetailInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBookedWaiting;

import java.util.ArrayList;

public class OrderBookingDetailPresenter implements ApiOrderBookingDetailInterface {
    private ApiOrderRoomBookedWaiting mApiOrderRoomBookedWaiting = new ApiOrderRoomBookedWaiting(this);
    private OrderBookingDetailInterface mOrderBookingDetailInterface;

    public OrderBookingDetailPresenter(OrderBookingDetailInterface mOrderBookingDetailInterface) {
        this.mOrderBookingDetailInterface = mOrderBookingDetailInterface;
    }

    void getTotal(int payment, int advanceDeposit) {
            double total = payment + payment * 0.05 - advanceDeposit;
            mOrderBookingDetailInterface.getTotal((int)(total));
    }

    void onClickCancel(String id){
        mApiOrderRoomBookedWaiting.deleteOrder(id);
    }

    void onClickCofirm(String id,int bookingStatus){
        mApiOrderRoomBookedWaiting.changeBookingStatus(id,bookingStatus);
    }

    @Override
    public void deleteOrder(String statusDelete) {
        mOrderBookingDetailInterface.onCancelRoom(statusDelete);
    }

    @Override
    public void changeBookingStatus(String updateStatus) {
        mOrderBookingDetailInterface.onConfirmSuccess(updateStatus);
    }
}
