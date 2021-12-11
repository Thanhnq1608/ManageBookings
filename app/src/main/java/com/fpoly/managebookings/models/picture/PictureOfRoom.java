package com.fpoly.managebookings.models.picture;

import java.time.OffsetDateTime;

public class PictureOfRoom {

    private String id;
    private int price;
    private String[] picture;
    private String createdAt;
    private String updatedAt;
    private int v;

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String[] getPicture() {
        return picture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getV() {
        return v;
    }
}
