package com.fpoly.managebookings.api;

import com.fpoly.managebookings.models.OrderDetail;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.ResponseMessage;
import com.fpoly.managebookings.models.RoomAmenities;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.UpdateAnyRoomDetail;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.models.login.ResponseGetUser;
import com.fpoly.managebookings.models.login.ResponseUpdateUser;
import com.fpoly.managebookings.models.login.ResponseLogin;
import com.fpoly.managebookings.models.picture.ResponGetPicture;
import com.fpoly.managebookings.models.picture.ResponseCreatePicture;
import com.fpoly.managebookings.models.updateOrder.UpdateBookingStatus;
import com.fpoly.managebookings.models.updateOrder.UpdateTotalRoomRate;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://datphongkhachsan.herokuapp.com/")
            .client(new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    // OrderRoomBorder
    @GET("orderRoomBooked/getOrderByBookingStatus/{bookingStatus}")
    Call<List<OrderRoomBooked>> getOrderRoom(@Path("bookingStatus") int bookingStatus);

    @GET("orderRoomBooked/{id}")
    Call<OrderRoomBooked> getOrderRoomById(@Path("id") String id);

    @FormUrlEncoded
    @POST("orderRoomBooked/create")
    Call<OrderRoomBooked> createOrder(@Field("fullName") String fullName,
                                      @Field("phone") String phone,
                                      @Field("email") String email,
                                      @Field("timeBookingStart") String timeBookingStart,
                                      @Field("timeBookingEnd") String timeBookingEnd,
                                      @Field("bookingStatus") int bookingStatus,
                                      @Field("totalRoomRate") int totalRoomRate,
                                      @Field("advanceDeposit") int advanceDeposit);

    @POST("orderRoomBooked/delete/{id}")
    Call<ResponseMessage> deleteOrder(@Path("id") String id);

    @FormUrlEncoded
    @POST("orderRoomBooked/update/{id}")
    Call<UpdateBookingStatus> changeBookingStatus(@Path("id") String id, @Field("bookingStatus") int bookingStatus);

    @FormUrlEncoded
    @POST("orderRoomBooked/update/{id}")
    Call<UpdateTotalRoomRate> updatePaymentWhileUpdateRoom(@Path("id") String id, @Field("totalRoomRate") int totalRoomRate);

    @POST("orderRoomBooked/update/{id}")
    Call<UpdateTotalRoomRate> update(@Path("id") String id, @Body JsonObject jsonObject);

    //OrderDetail
    @GET("oderRoomBookingDetail/{idBooking}")
    Call<List<OrderDetail>> getOrderDetail(@Path("idBooking") String idBooking);

    @POST("oderRoomBookingDetail/create")
    Call<OrderDetail> createOrderDetail(@Body OrderDetail orderDetail);

    @POST("oderRoomBookingDetail/delete/{id}")
    Call<ResponseMessage> deleteOrderDetail(@Path("id") String id);

    // RoomDetail
    @GET("roomDetail/all")
    Call<List<RoomDetail>> getAllRoom();

    @GET("roomDetail/getAllByStatus/{roomStatus}")
    Call<List<RoomDetail>> getRoomByStatus(@Path("roomStatus") int roomStatus);

    @GET("roomDetail/idBooking/{idBooking}")
    Call<List<RoomDetail>> getAllRoomByIdBooking(@Path("idBooking") String idBooking);

    @GET("roomDetail/{id}")
    Call<RoomDetail> getRoomById(@Path("id") String id);

    @POST("roomDetail/create")
    Call<RoomDetail> createNewRoom(@Body JsonObject jsonObject);

    @FormUrlEncoded
    @POST("roomDetail/updateAny/{idBooking}")
    Call<UpdateAnyRoomDetail> updateWhileRemoveOrder(@Path("idBooking") String idBooking, @Field("roomStatus") int roomStatus, @Field("idBooking") String updateIdBooking);

    @FormUrlEncoded
    @PUT("roomDetail/update/{id}")
    Call<ResponseMessage> removeRoomFromOrder(@Path("id") String id, @Field("roomStatus") int roomStatus, @Field("idBooking") String idBooking);

    @DELETE("roomDetail/delete/{id}")
    Call<ResponseMessage> removeRoom(@Path("id") String id);

    //User
    @FormUrlEncoded
    @POST("api/v1/auth/login")
    Call<ResponseLogin> login(@Field("phone")String phone,@Field("passWord")String password);

    @FormUrlEncoded
    @POST("api/v1/auth/ResetPassWord")
    Call<ResponseUpdateUser> forgetPassword(@Field("phone")String phone, @Field("password") String password);

    @GET("api/v1/auth/getPhone/{phone}")
    Call<ResponseGetUser> getUserByPhone(@Path("phone") String phone);

    @Multipart
    @PUT("api/v1/auth/uploadAvatar")
    Call<ResponseUpdateUser> uploadAvatar(@Header("Authorization") String token,
                                          @Part MultipartBody.Part file);

    @PUT("api/v1/auth/updateTokenId")
    Call<ResponseUpdateUser> updateTokenId(@Header("Authorization") String token, @Body JsonObject jsonObject);

    //Picture
    @GET("api/v1/pictureOfRoom/getPrice/{price}")
    Call<ResponGetPicture> getPictureRoom(@Path("price") int price);

    @Multipart
    @PUT("api/v1/pictureOfRoom/uploadPicture")
    Call<ResponseCreatePicture> uploadRoomPictures(@Query("price") int price,
                                                   @Part MultipartBody.Part[] file);

    //Room Amenities
    @GET("/roomAmenities/{idKindOfRoom}")
    Call<List<RoomAmenities>> getRoomAmenities(@Path("idKindOfRoom") int idKindOfRoom);
}
