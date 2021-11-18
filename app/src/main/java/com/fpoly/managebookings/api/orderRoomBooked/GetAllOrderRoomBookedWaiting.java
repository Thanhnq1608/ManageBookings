package com.fpoly.managebookings.api.orderRoomBooked;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllOrderRoomBookedWaiting {
    private GetOrderBookedInterface getOrderBookedInterface;

    public GetAllOrderRoomBookedWaiting(GetOrderBookedInterface getOrderBookedInterface) {
        this.getOrderBookedInterface = getOrderBookedInterface;
    }

    public void getListAllUser(int bookingStatus) {
        ArrayList<OrderRoomBooked> list = new ArrayList<>();
        ApiService.apiService.getOrderRoom(bookingStatus).enqueue(new Callback<OrderRoomBooked>() {
            @Override
            public void onResponse(Call<OrderRoomBooked> call, Response<OrderRoomBooked> response) {
                if (response.isSuccessful()) {
                    list.add(response.body()) ;
                    getOrderBookedInterface.getOrderWaiting(list);
                    Log.e("Size===================", "" + list.size());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }
            @Override
            public void onFailure(Call<OrderRoomBooked> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
    }
}
