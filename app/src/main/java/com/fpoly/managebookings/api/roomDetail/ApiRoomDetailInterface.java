package com.fpoly.managebookings.api.roomDetail;

import com.fpoly.managebookings.models.RoomDetail;

import java.util.ArrayList;

public interface ApiRoomDetailInterface {
    void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails);
    void getRoomById(ArrayList<RoomDetail> roomDetails);
    void getAllRoomByIdBooking(ArrayList<RoomDetail> roomDetails);
    void updateWhileRemoveOrder(String message);
}
