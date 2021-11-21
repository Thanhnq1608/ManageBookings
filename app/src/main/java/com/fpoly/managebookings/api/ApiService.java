package com.fpoly.managebookings.api;

import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.ResponseMessage;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.updateOrder.UpdateBookingStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://datphongkhachsan.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("orderRoomBooked/{bookingStatus}")
    Call<List<OrderRoomBooked>> getOrderRoom(@Path("bookingStatus") int bookingStatus);

    @POST("orderRoomBooked/delete/{id}")
    Call<ResponseMessage> deleteOrder(@Path("id") String id);

    @FormUrlEncoded
    @POST("orderRoomBooked/update/{id}")
    Call<UpdateBookingStatus> changeBookingStatus(@Path("id") String id, @Field("bookingStatus") int bookingStatus);

    @GET("roomDetail/getAllByStatus/{roomStatus}")
    Call<List<RoomDetail>> getAllRoom(@Path("roomStatus") int roomStatus);

    @GET("roomDetail/idBooking/{idBooking}")
    Call<List<RoomDetail>> getAllRoomByIdBooking(@Path("idBooking") String idBooking);

}
