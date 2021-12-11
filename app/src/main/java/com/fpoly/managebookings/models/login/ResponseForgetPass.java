package com.fpoly.managebookings.models.login;

import com.fpoly.managebookings.models.User;

public class ResponseForgetPass {
    private String message;
    private User data;

    public String getStatus() {
        return message;
    }

    public User getData() {
        return data;
    }
}
