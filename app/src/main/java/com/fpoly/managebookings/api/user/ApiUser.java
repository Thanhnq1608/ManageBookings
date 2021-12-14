package com.fpoly.managebookings.api.user;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.models.login.Data;
import com.fpoly.managebookings.models.login.ResponseForgetPass;
import com.fpoly.managebookings.models.login.ResponseLogin;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiUser {
    private ApiLoginUserInterface mApiLoginUserInterface;
    private ApiForgetPassInterface mApiForgetPassInterface;
    private GetUserInterface mGetUserInterface;

    public ApiUser() {
    }

    public ApiUser(GetUserInterface mGetUserInterface) {
        this.mGetUserInterface = mGetUserInterface;
    }

    public ApiUser(ApiForgetPassInterface mApiForgetPassInterface) {
        this.mApiForgetPassInterface = mApiForgetPassInterface;
    }

    public ApiUser(ApiLoginUserInterface mApiLoginUserInterface) {
        this.mApiLoginUserInterface = mApiLoginUserInterface;
    }

    public void login(String phone, String password) {
        ApiService.apiService.login(phone, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin responseLogin = response.body();
                    Data data = responseLogin.getData();
                    mApiLoginUserInterface.loginSuccess(data.getToken(), data.getUser(), responseLogin.getStatus());
                    Log.e("token", "" + data.getToken());
                } else if (response.code() == 400) {
                    mApiForgetPassInterface.changePassFail();
                    Log.e("Exception 400", "" + response.code() + " " + response.message());
                } else {
                    Log.e("Login Exception", "" + response.code() + " " + response.message());

                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("Login Error", "" + t.getMessage());
            }
        });
    }

    public void forgetPassword(String phone, String password) {
        ApiService.apiService.forgetPassword(phone, password).enqueue(new Callback<ResponseForgetPass>() {
            @Override
            public void onResponse(Call<ResponseForgetPass> call, Response<ResponseForgetPass> response) {
                if (response.isSuccessful()) {
                    ResponseForgetPass responseForgetPass = response.body();
                    User data = responseForgetPass.getData();
                    mApiForgetPassInterface.changePassSuccess();
                    Log.e("token", "" + responseForgetPass.getStatus());
                } else if (response.code() == 400) {
                    mApiLoginUserInterface.loginFail(response.message());
                    Log.e("Exception 400", "" + response.code() + " " + response.message());
                } else {
                    Log.e("Login Exception", "" + response.code() + " " + response.message());

                }
            }

            @Override
            public void onFailure(Call<ResponseForgetPass> call, Throwable t) {
                Log.e("Login Error", "" + t.getMessage());
            }
        });
    }

    public void updateTokenId(String token, JsonObject tokenId) {
        ApiService.apiService.updateTokenId("Bearer "+token, tokenId).enqueue(new Callback<ResponseForgetPass>() {
            @Override
            public void onResponse(Call<ResponseForgetPass> call, Response<ResponseForgetPass> response) {
                if (response.isSuccessful()) {
                    ResponseForgetPass responseForgetPass = response.body();
                    Log.e("token", "" + responseForgetPass.getStatus());
                } else if (response.code() == 400) {
                    mApiLoginUserInterface.loginFail(response.message());
                    Log.e("Exception 400", "" + response.code() + " " + response.message());
                } else {
                    Log.e("Login Exception", "" + response.code() + " " + response.message());

                }
            }

            @Override
            public void onFailure(Call<ResponseForgetPass> call, Throwable t) {
                Log.e("Login Error", "" + t.getMessage());
            }
        });
    }

    public void getUserByPhone(String phone) {
        ApiService.apiService.getUserByPhone(phone).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User data = response.body();
                    mGetUserInterface.getUserByPhone(data);
                    Log.e("user", "" + data);
                } else {
                    mGetUserInterface.getUserByPhone(null);
                    Log.e("User Exception", "" + response.code() + " " + response.message());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("User Error", "" + t.getMessage());
            }
        });
    }


}
