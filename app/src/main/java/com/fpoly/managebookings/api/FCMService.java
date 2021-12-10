package com.fpoly.managebookings.api;

import com.fpoly.managebookings.models.firebase.DataSendMessFirebase;
import com.fpoly.managebookings.models.firebase.ResponseSendMessage;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FCMService {
    FCMService fCMService = new Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FCMService.class);

    //FireBase Notification

    @Headers({"Authorization:key=AAAABHkXnhQ:APA91bF-GoTXyLgDY7m2OS8Wc_BkYOh3MNF-WqpRzDaMEyXk0lliEgICd47_JvsRKQSVsBHgZ_MMQQM6KyyOt0oie09-1k7mg_2-6ZyT90WG8W0UQIcLCjB0u4rjBWCRChdMvSaLFqhw",
                "Content-Type:application/json"})
    @POST("fcm/send")
    Call<JsonObject> sendNotificationForUser(@Body JsonObject data);
}
