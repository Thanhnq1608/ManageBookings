package com.fpoly.managebookings.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class User implements Serializable {
    private String id;
    private String role;
    private String fullName;
    private String phone;
    private String email;
    private String userName;
    private String password;
    private String position;
    private String dateOfBirth;
    private String avatar;
    private String status;
    private String address;
    private String createBy;
    private String updateBy;
    private String createdAt;
    private String updatedAt;
    private long v;

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getUpdateBy() {
        return updateBy;
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


}
