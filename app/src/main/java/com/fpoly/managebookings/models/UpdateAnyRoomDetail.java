package com.fpoly.managebookings.models;

public class UpdateAnyRoomDetail {
    class Data{
        private int roomStatus;
        private String idBooking;
    }
    private String message;
    private Data data;

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }
}
