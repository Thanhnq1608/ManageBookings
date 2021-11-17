package com.fpoly.managebookings.models;

import java.util.Date;

public class OrderRoomBooked {
    private String fullname;
    private int phone;
    private String timeBooking;
    private int bookingStatus;
    private int totalRoomRate;

    public OrderRoomBooked() {
    }

    public OrderRoomBooked(String fullname, int phone, String timeBooking, int bookingStatus, int totalRoomRate) {
        this.fullname = fullname;
        this.phone = phone;
        this.timeBooking = timeBooking;
        this.bookingStatus = bookingStatus;
        this.totalRoomRate = totalRoomRate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public void setTimeBooking(String timeBooking) {
        this.timeBooking = timeBooking;
    }

    public int getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getTotalRoomRate() {
        return totalRoomRate;
    }

    public void setTotalRoomRate(int totalRoomRate) {
        this.totalRoomRate = totalRoomRate;
    }
}
