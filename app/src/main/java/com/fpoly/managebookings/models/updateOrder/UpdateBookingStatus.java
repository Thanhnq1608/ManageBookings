package com.fpoly.managebookings.models.updateOrder;

public class UpdateBookingStatus {
    class Data {
        private String bookingStatus;
        private long updatedAt;

        public String getBookingStatus() {
            return bookingStatus;
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