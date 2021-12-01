package com.fpoly.managebookings.models.updateOrder;

public class UpdateTotalRoomRate {
    class Data {
        private int totalRoomRate;
        private long updatedAt;

        public int getBookingStatus() {
            return totalRoomRate;
        }
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