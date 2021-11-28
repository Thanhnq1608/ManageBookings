package com.fpoly.managebookings.views.createOrder;

import com.fpoly.managebookings.models.OrderRoomBooked;

public interface CreateOrderInterface {
    void emptyData(String alert);

    void checkTimeBooking(String alert);

    void createSuccess(OrderRoomBooked orderRoomBooked);
}
