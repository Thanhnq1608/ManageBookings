package com.fpoly.managebookings.api.pictureOfRoom;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.picture.PictureOfRoom;
import com.fpoly.managebookings.models.picture.ResponGetPicture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiPictureRoom {
    private ResponGetPictureRoom mResponGetPictureRoom;

    public ApiPictureRoom(ResponGetPictureRoom mResponGetPictureRoom) {
        this.mResponGetPictureRoom = mResponGetPictureRoom;
    }

    public void getPictureRoom(int price){
        ApiService.apiService.getPictureRoom(price).enqueue(new Callback<ResponGetPicture>() {
            @Override
            public void onResponse(Call<ResponGetPicture> call, Response<ResponGetPicture> response) {
                if(response.isSuccessful()){
                    ResponGetPicture responGetPicture = response.body();
                    Log.e("Picture Success",""+response.code());
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
}
