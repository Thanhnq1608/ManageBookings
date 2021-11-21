package com.fpoly.managebookings.api.orderRoomBooked;

import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;

public interface ApiOrderBookedInterface {
    void getOrderWaiting(ArrayList<OrderRoomBooked> list);
    void getOrderCompleted(ArrayList<OrderRoomBooked> list);
    void getOrderConfirmed(ArrayList<OrderRoomBooked> list);
    void getOrderOccupied(ArrayList<OrderRoomBooked> list);
}
