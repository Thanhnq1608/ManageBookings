package com.fpoly.managebookings.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class RoomDetail implements Serializable {
    private String _id;
    private String idRoom;
    private String roomName;
    private int maximumNumberOfPeople;
    private int roomStatus;
    private int idKindOfRoom;
    private int roomPrice;
    private String createdAt;
    private String updatedAt;
    private long v;
    private String idBooking;


    public RoomDetail() {
    }

    public String getId() {
        return _id;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getMaximumNumberOfPeople() {
        return maximumNumberOfPeople;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getIdKindOfRoom() {
        return idKindOfRoom;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public long getV() {
        return v;
    }

    public String getIdBooking() {
        return idBooking;
    }

    public String getIdRoom() {
        return idRoom;
    }
}
