package com.fpoly.managebookings.api.roomDetail;

import com.fpoly.managebookings.models.RoomDetail;

public interface ResponseCreateRoomInterface {
    void createSucces(RoomDetail roomDetail);
    void createFail(String status);
}
