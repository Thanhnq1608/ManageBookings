package com.fpoly.managebookings.api.orderRoomBooked;

import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;

public interface ApiOrderBookedInterface {
    void getOrderWaiting(ArrayList<OrderRoomBooked> list);
}
