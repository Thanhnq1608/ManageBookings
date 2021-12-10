package com.fpoly.managebookings.api.sendNotifyFirebase;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.api.FCMService;
import com.fpoly.managebookings.models.firebase.DataSendMessFirebase;
import com.fpoly.managebookings.models.firebase.ResponseSendMessage;
import com.fpoly.managebookings.models.login.Data;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiSendNotifyWithFirebase {

    public void sendNotify(JsonObject jsonObject){
        FCMService.fCMService.sendNotificationForUser(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    Log.e(ApiSendNotifyWithFirebase.class.getName(),"Thành công");
                }else {
                    Log.e(ApiSendNotifyWithFirebase.class.getName(),"Thất bại"+response.code()+response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(ApiSendNotifyWithFirebase.class.getName(),"Lỗi rồi"+t.getMessage());
            }
        });
    }
}
