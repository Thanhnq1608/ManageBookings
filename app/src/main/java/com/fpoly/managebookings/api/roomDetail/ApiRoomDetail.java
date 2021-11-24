package com.fpoly.managebookings.api.roomDetail;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.ResponseMessage;
import com.fpoly.managebookings.models.RoomDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRoomDetail {
    private ApiRoomDetailInterface mApiRoomDetailInterface;

    public ApiRoomDetail(ApiRoomDetailInterface mApiRoomDetailInterface) {
        this.mApiRoomDetailInterface = mApiRoomDetailInterface;
    }
    public void getAllRoom(int roomStatus){
        ArrayList<RoomDetail> list = new ArrayList<>();
        ApiService.apiService.getAllRoom(roomStatus).enqueue(new Callback<List<RoomDetail>>() {
            @Override
            public void onResponse(Call<List<RoomDetail>> call, Response<List<RoomDetail>> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body()) ;
                    mApiRoomDetailInterface.getAllRoomEmpty(list);
                    Log.e("SizeRoom===================", "" + list.size());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RoomDetail>> call, Throwable t) {
                Log.e("ErrorRoom", "" + t.getMessage());
            }
        });
    }

    public void getAllRoomByIdBooking(String idBooking){
        ArrayList<RoomDetail> list = new ArrayList<>();
        ApiService.apiService.getAllRoomByIdBooking(idBooking).enqueue(new Callback<List<RoomDetail>>() {
            @Override
            public void onResponse(Call<List<RoomDetail>> call, Response<List<RoomDetail>> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body());
                    mApiRoomDetailInterface.getAllRoomByIdBooking(list);
                    Log.e("SizeRoom===================", "" + response.body().toString());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RoomDetail>> call, Throwable t) {
                Log.e("ErrorRoom", "" + t.getMessage());
            }
        });
    }

    public void updateWhileRemoveOrder(String id){
        ApiService.apiService.updateWhileRemoveOrder(id,"",0).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    mApiRoomDetailInterface.updateWhileRemoveOrder(response.body().toString());
                    Log.e("Status",""+response.body().toString());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e("ErrorRoom", "" + t.getMessage());
            }
        });
    }
}
