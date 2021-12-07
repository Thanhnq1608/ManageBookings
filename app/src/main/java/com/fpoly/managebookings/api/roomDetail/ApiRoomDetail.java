package com.fpoly.managebookings.api.roomDetail;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.OrderDetail;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.ResponseMessage;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.UpdateAnyRoomDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRoomDetail {
    private ApiRoomDetailInterface mApiRoomDetailInterface;
    private GetAllRoomInterface mGetAllRoomInterface;

    public ApiRoomDetail() {
    }

    public ApiRoomDetail(ApiRoomDetailInterface mApiRoomDetailInterface, GetAllRoomInterface mGetAllRoomInterface) {
        this.mApiRoomDetailInterface = mApiRoomDetailInterface;
        this.mGetAllRoomInterface = mGetAllRoomInterface;
    }

    public void getAllRoom(){
        ArrayList<RoomDetail> list = new ArrayList<>();
        ApiService.apiService.getAllRoom().enqueue(new Callback<List<RoomDetail>>() {
            @Override
            public void onResponse(Call<List<RoomDetail>> call, Response<List<RoomDetail>> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body()) ;
                    mGetAllRoomInterface.getAllRoom(list);
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

    public void getRooMByStatus(int roomStatus){
        ArrayList<RoomDetail> list = new ArrayList<>();
        ApiService.apiService.getRoomByStatus(roomStatus).enqueue(new Callback<List<RoomDetail>>() {
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

    public void updateWhileRemoveOrCheck(String idBooking,int roomStatus,String updateIdBooking){
        ApiService.apiService.updateWhileRemoveOrder(idBooking,roomStatus,updateIdBooking).enqueue(new Callback<UpdateAnyRoomDetail>() {
            @Override
            public void onResponse(Call<UpdateAnyRoomDetail> call, Response<UpdateAnyRoomDetail> response) {
                if (response.isSuccessful()) {
                    Log.e("Status",""+response.body().getMessage());
                    mApiRoomDetailInterface.updateWhileRemoveOrder(response.body().getMessage());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UpdateAnyRoomDetail> call, Throwable t) {
                Log.e("ErrorRoom", "" + t.getMessage());
            }
        });
    }

    public void getRoomById(ArrayList<OrderDetail> listId){
        ArrayList<RoomDetail> roomDetails = new ArrayList<>();
        int i =0;
        for (i = 0; i < listId.size(); i++){
            int temp = i;
            ApiService.apiService.getRoomById(listId.get(i).getIdRoom()).enqueue(new Callback<RoomDetail>() {
                @Override
                public void onResponse(Call<RoomDetail> call, Response<RoomDetail> response) {
                    if (response.isSuccessful()) {
                        roomDetails.add(response.body());
                        Log.e("SizeId",""+roomDetails.size());
                        if (temp == (listId.size()-1)){
                            mApiRoomDetailInterface.getRoomById(roomDetails);
                        }
                    } else {
                        Log.e("Loi", "" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<RoomDetail> call, Throwable t) {
                    Log.e("ErrorRoom", "" + t.getMessage());
                }
            });
        }
    }
    public void removeRoomFromOrder(String id,int roomStatus,String updateIdBooking){
        ApiService.apiService.removeRoomFromOrder(id,roomStatus,updateIdBooking).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Log.e("Status",""+response.body().getMessage());
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
