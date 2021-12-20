package com.fpoly.managebookings.views.orderBookingDetail;

public interface OrderBookingDetailInterface {
    void getTotal(int total, int paymentAmount);
    void onConfirmSuccess(String updateStatus);
    void onCancelRoom(String cancelStatus);
}
