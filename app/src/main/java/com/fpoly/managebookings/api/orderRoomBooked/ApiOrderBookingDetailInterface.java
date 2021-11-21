package com.fpoly.managebookings.api.orderRoomBooked;

import com.fpoly.managebookings.models.RoomDetail;

import java.util.ArrayList;

public interface ApiOrderBookingDetailInterface {
    void deleteOrder(String statusDelete);
    void changeBookingStatus(String updateStatus);
}
