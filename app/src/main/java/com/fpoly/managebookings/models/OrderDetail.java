package com.fpoly.managebookings.models;

public class OrderDetail {
    private String _id;
    private String idBooking;
    private String idRoom;
    private String createAt;
    private String updateAt;
    private long __v;

    public OrderDetail() {
    }

    public OrderDetail(String idBooking, String idRoom) {
        this.idBooking = idBooking;
        this.idRoom = idRoom;
    }

    public String get_id() {
        return _id;
    }


    public String getIdBooking() {
        return idBooking;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
         return updateAt;
    }

    public long get__v() {
        return __v;
    }
}
