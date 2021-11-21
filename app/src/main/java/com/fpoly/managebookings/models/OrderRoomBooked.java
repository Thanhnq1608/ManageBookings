package com.fpoly.managebookings.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

public class OrderRoomBooked implements Serializable {
    private String _id;
    private String fullName;
    private String phone;
    private String timeBookingStart;
    private String timeBookingEnd;
    private int advanceDeposit;
    private int bookingStatus;
    private int totalRoomRate;
    private String createdAt;
    private String updatedAt;
    private long __v;

    public OrderRoomBooked() {
    }

    public OrderRoomBooked(String _id, String fullName, String phone, String timeBookingStart, String timeBookingEnd, int advanceDeposit, int bookingStatus, int totalRoomRate, String createdAt, String updatedAt, long __v) {
        this._id = _id;
        this.fullName = fullName;
        this.phone = phone;
        this.timeBookingStart = timeBookingStart;
        this.timeBookingEnd = timeBookingEnd;
        this.advanceDeposit = advanceDeposit;
        this.bookingStatus = bookingStatus;
        this.totalRoomRate = totalRoomRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    public String getTimeBookingStart() {
        return timeBookingStart;
    }

    public void setTimeBookingStart(String timeBookingStart) {
        this.timeBookingStart = timeBookingStart;
    }

    public int getAdvanceDeposit() {
        return advanceDeposit;
    }

    public void setAdvanceDeposit(int advanceDeposit) {
        this.advanceDeposit = advanceDeposit;
    }

    public int getTotalRoomRate() {
        return totalRoomRate;
    }

    public void setTotalRoomRate(int totalRoomRate) {
        this.totalRoomRate = totalRoomRate;
    }

    public String get_id() {
        return _id;
    }

    public long get__v() {
        return __v;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeBookingEnd() {
        return timeBookingEnd;
    }

    public void setTimeBookingEnd(String timeBookingEnd) {
        this.timeBookingEnd = timeBookingEnd;
    }

    public int getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
