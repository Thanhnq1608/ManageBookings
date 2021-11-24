package com.fpoly.managebookings.api.orderRoomBooked;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.ResponseMessage;
import com.fpoly.managebookings.models.updateOrder.UpdateBookingStatus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiOrderRoomBooked {
    private ApiOrderBookedInterface apiOrderBookedInterface;
    private ApiOrderBookingDetailInterface mApiOrderBookingDetailInterface;

    public ApiOrderRoomBooked(ApiOrderBookingDetailInterface mApiOrderBookingDetailInterface) {
        this.mApiOrderBookingDetailInterface = mApiOrderBookingDetailInterface;
    }

    public ApiOrderRoomBooked(ApiOrderBookedInterface getOrderBookedInterface) {
        this.apiOrderBookedInterface = getOrderBookedInterface;
    }

    public void getOrderByBookingStatus(int bookingStatus) {
        ArrayList<OrderRoomBooked> list = new ArrayList<>();
        ApiService.apiService.getOrderRoom(bookingStatus).enqueue(new Callback<List<OrderRoomBooked>>() {
            @Override
            public void onResponse(Call<List<OrderRoomBooked>> call, Response<List<OrderRoomBooked>> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body()) ;
                            apiOrderBookedInterface.getOrderWaiting(list);
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<OrderRoomBooked>> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
    }

    public void deleteOrder(String id) {
        ApiService.apiService.deleteOrder(id).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    mApiOrderBookingDetailInterface.deleteOrder(response.body().getMessage());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
    }

    public void changeBookingStatus(String id,int bookingStatus) {
        ApiService.apiService.changeBookingStatus(id,bookingStatus).enqueue(new Callback<UpdateBookingStatus>() {
            @Override
            public void onResponse(Call<UpdateBookingStatus> call, Response<UpdateBookingStatus> response) {
                if (response.isSuccessful()) {
                    Log.e("ChangeBookingStatus",""+response.body().getMessage());
                    mApiOrderBookingDetailInterface.changeBookingStatus("Successful confirmation");
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }
            @Override
            public void onFailure(Call<UpdateBookingStatus> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
    }
}
