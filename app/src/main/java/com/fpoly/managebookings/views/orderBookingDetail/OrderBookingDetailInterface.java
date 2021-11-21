package com.fpoly.managebookings.views.orderBookingDetail;

public interface OrderBookingDetailInterface {
    void getTotal(int total);
    void onConfirmSuccess(String updateStatus);
    void onCancelRoom(String cancelStatus);
}
