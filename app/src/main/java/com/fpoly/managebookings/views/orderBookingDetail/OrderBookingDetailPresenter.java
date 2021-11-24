package com.fpoly.managebookings.views.orderBookingDetail;

import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookingDetailInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;

import java.util.ArrayList;

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
        switch (bookingStatus){
            case 0:
                mApiOrderRoomBooked.changeBookingStatus(id,1);
            case 1:
                mApiOrderRoomBooked.changeBookingStatus(id,2);
            case 2:
                mApiOrderRoomBooked.changeBookingStatus(id,3);
        }
    }

    void updateRoomDetail(ArrayList<RoomDetail> list){
        for (int i=0;i<list.size();i++){
            mApiRoomDetail.updateWhileRemoveOrder(list.get(i).getId());
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
}
