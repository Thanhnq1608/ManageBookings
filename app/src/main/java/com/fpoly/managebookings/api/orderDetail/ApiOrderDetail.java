package com.fpoly.managebookings.api.orderDetail;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.OrderDetail;
import com.fpoly.managebookings.models.RoomDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiOrderDetail {
    private ApiOrderDetailInterface mApiOrderDetailInterface;

    public ApiOrderDetail() {
    }

    public ApiOrderDetail(ApiOrderDetailInterface mApiOrderDetailInterface) {
        this.mApiOrderDetailInterface = mApiOrderDetailInterface;
    }

    public void getAllOrderDetail(String idBooking){
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        ApiService.apiService.getOrderDetail(idBooking).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                if(response.isSuccessful()){
                    orderDetails.addAll(response.body());
                    mApiOrderDetailInterface.getAllOrderDetail(orderDetails);
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

            }
        });
    }

    public void createOrderDetail(ArrayList<RoomDetail> list,String idBooking){
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < list.size();i++){
            ApiService.apiService.createOrderDetail(new OrderDetail(idBooking,list.get(i).getId())).enqueue(new Callback<OrderDetail>() {
                @Override
                public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                    if(response.isSuccessful()){
                        orderDetails.add(response.body());
                    }
                }
                BottomS
                @Override
                public void onFailure(Call<OrderDetail> call, Throwable t) {

                }
            });
        }
    }
}
