package com.fpoly.managebookings.views.orderBookingDetail;

import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookingDetailInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;

public class OrderBookingDetailPresenter implements ApiOrderBookingDetailInterface {
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private OrderBookingDetailInterface mOrderBookingDetailInterface;

    public OrderBookingDetailPresenter(OrderBookingDetailInterface mOrderBookingDetailInterface) {
        this.mOrderBookingDetailInterface = mOrderBookingDetailInterface;
    }

    void getTotal(int payment, int advanceDeposit) {
            double total = payment + payment * 0.05 - advanceDeposit;
            mOrderBookingDetailInterface.getTotal((int)(total));
    }

    void onClickCancel(String id){
        mApiOrderRoomBooked.deleteOrder(id);
    }

    void onClickCofirm(String id,int bookingStatus){
        mApiOrderRoomBooked.changeBookingStatus(id,bookingStatus);
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
