package com.fpoly.managebookings.api.user;

import com.fpoly.managebookings.models.User;

public interface ApiLoginUserInterface {
    void loginSuccess(String token, User user,String status);
    void loginFail(String status);
}
