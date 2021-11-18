package com.fpoly.managebookings.api.orderRoomBooked;

import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;
import java.util.Collection;

public interface GetOrderBookedInterface {
    void getOrderWaiting(ArrayList<OrderRoomBooked> list);
}
