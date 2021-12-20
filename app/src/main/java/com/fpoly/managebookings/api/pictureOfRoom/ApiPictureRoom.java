package com.fpoly.managebookings.api.pictureOfRoom;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.picture.PictureOfRoom;
import com.fpoly.managebookings.models.picture.ResponGetPicture;
import com.fpoly.managebookings.models.picture.ResponseCreatePicture;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiPictureRoom {
    private ResponGetPictureRoom mResponGetPictureRoom;

    public ApiPictureRoom() {
    }

    public ApiPictureRoom(ResponGetPictureRoom mResponGetPictureRoom) {
        this.mResponGetPictureRoom = mResponGetPictureRoom;
    }

    public void getPictureRoom(int price){
        ApiService.apiService.getPictureRoom(price).enqueue(new Callback<ResponGetPicture>() {
            @Override
            public void onResponse(Call<ResponGetPicture> call, Response<ResponGetPicture> response) {
                if(response.isSuccessful()){
                    ResponGetPicture responGetPicture = response.body();
                    mResponGetPictureRoom.responsePicture(responGetPicture.getData());
                }else {
                    Log.e("Picture Error",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponGetPicture> call, Throwable t) {
                Log.e("Picture Fail",""+t.getMessage());
            }
        });
    }

    public void getListPictureRooms(ArrayList<Integer> roomDetails){
        ArrayList<PictureOfRoom> list = new ArrayList<>();
        for (int i = 0; i < roomDetails.size(); i++) {
            int temp = i;
            ApiService.apiService.getPictureRoom(roomDetails.get(i).intValue()).enqueue(new Callback<ResponGetPicture>() {
                @Override
                public void onResponse(Call<ResponGetPicture> call, Response<ResponGetPicture> response) {
                    if(response.isSuccessful()){
                        ResponGetPicture responGetPicture = response.body();
                        list.add(response.body().getData());
                        if (temp == roomDetails.size() - 1){
                            mResponGetPictureRoom.responseListPicture(list);
                        }
                    }else {
                        Log.e("Picture Error",""+response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponGetPicture> call, Throwable t) {
                    Log.e("Picture Fail",""+t.getMessage());
                }
            });
        }
    }

    public void uploadRoomPicture(int price, MultipartBody.Part[] image){
            ApiService.apiService.uploadRoomPictures(price, image).enqueue(new Callback<ResponseCreatePicture>() {
                @Override
                public void onResponse(Call<ResponseCreatePicture> call, Response<ResponseCreatePicture> response) {
                    if(response.isSuccessful()){
                        PictureOfRoom responGetPicture = response.body().getData();
//                        if (temp == roomDetails.size() - 1){
//                            mResponGetPictureRoom.responseListPicture(list);
//                        }
                        Log.e("Picture Status",""+response.body().getMessage());
                    }else {
                        Log.e("Picture Error",""+response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseCreatePicture> call, Throwable t) {
                    Log.e("Picture Fail",""+t.getMessage());
                }
            });
    }
}
