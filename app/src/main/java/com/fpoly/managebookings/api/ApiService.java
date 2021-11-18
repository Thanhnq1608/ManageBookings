package com.fpoly.managebookings.api;

import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://datphongkhachsan.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("orderRoomBooked/{bookingStatus}")
    Call<OrderRoomBooked> getOrderRoom(@Path("bookingStatus") int bookingStatus);

}
