package com.fpoly.managebookings.api;

import com.fpoly.managebookings.models.OrderDetail;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.ResponseMessage;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.UpdateAnyRoomDetail;
import com.fpoly.managebookings.models.updateOrder.UpdateBookingStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://datphongkhachsan.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    // OrderRoomBorder
    @GET("orderRoomBooked/{bookingStatus}")
    Call<List<OrderRoomBooked>> getOrderRoom(@Path("bookingStatus") int bookingStatus);

    @POST("orderRoomBooked/create")
    Call<OrderRoomBooked> createOrder(@Body OrderRoomBooked orderRoomBooked);

    @POST("orderRoomBooked/delete/{id}")
    Call<ResponseMessage> deleteOrder(@Path("id") String id);

    @FormUrlEncoded
    @POST("orderRoomBooked/update/{id}")
    Call<UpdateBookingStatus> changeBookingStatus(@Path("id") String id, @Field("bookingStatus") int bookingStatus);

    //OrderDetail
    @GET("oderRoomBookingDetail/{idBooking}")
    Call<List<OrderDetail>> getOrderDetail(@Path("idBooking") String idBooking);

    @POST("oderRoomBookingDetail/create")
    Call<OrderDetail> createOrderDetail(@Body OrderDetail orderDetail);

    @POST("oderRoomBookingDetail/delete/{id}")
    Call<ResponseMessage> deleteOrderDetail(@Path("id") String id);

    // RoomDetail
    @GET("roomDetail/getAllByStatus/{roomStatus}")
    Call<List<RoomDetail>> getAllRoom(@Path("roomStatus") int roomStatus);

    @GET("roomDetail/idBooking/{idBooking}")
    Call<List<RoomDetail>> getAllRoomByIdBooking(@Path("idBooking") String idBooking);

    @GET("roomDetail/{id}")
    Call<RoomDetail> getRoomById(@Path("id") String id);

    @FormUrlEncoded
    @POST("roomDetail/updateAny/{idBooking}")
    Call<UpdateAnyRoomDetail> updateWhileRemoveOrder(@Path("idBooking") String idBooking, @Field("roomStatus") int roomStatus,@Field("idBooking") String updateIdBooking);


}
