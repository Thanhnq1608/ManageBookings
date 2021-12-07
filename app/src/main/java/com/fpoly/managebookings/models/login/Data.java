package com.fpoly.managebookings.models.login;

import com.fpoly.managebookings.models.User;

public class Data {
    private User user;
    private String token;

    public Data() {
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
