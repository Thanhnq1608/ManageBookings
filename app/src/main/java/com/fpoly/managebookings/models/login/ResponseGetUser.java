package com.fpoly.managebookings.models.login;

import com.fpoly.managebookings.models.User;

public class ResponseGetUser {
    private String status;
    private User data;

    public String getStatus() {
        return status;
    }

    public User getData() {
        return data;
    }
}
