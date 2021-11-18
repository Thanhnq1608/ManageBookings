package com.fpoly.managebookings.models;

import java.time.OffsetDateTime;
import java.util.Date;

public class OrderRoomBooked {
    private String _id;
    private String fullName;
    private long phone;
    private String timeBooking;
    private long bookingStatus;
    private String createdAt;
    private String updatedAt;
    private long __v;

    public OrderRoomBooked() {
    }

    public OrderRoomBooked(String _id, String fullName, long phone, String timeBooking, long bookingStatus, String createdAt, String updatedAt, long __v) {
        this._id = _id;
        this.fullName = fullName;
        this.phone = phone;
        this.timeBooking = timeBooking;
        this.bookingStatus = bookingStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long get__v() {
        return __v;
    }

    public void set__v(long __v) {
        this.__v = __v;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public void setTimeBooking(String timeBooking) {
        this.timeBooking = timeBooking;
    }

    public long getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(long bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
