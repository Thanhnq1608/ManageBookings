package com.fpoly.managebookings.api.roomAmenities;

import android.util.Log;

import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.RoomAmenities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRoomAmenities {
    private GetRoomAmenitiesInterface mGetRoomAmenitiesInterface;

    public ApiRoomAmenities(GetRoomAmenitiesInterface mGetRoomAmenitiesInterface) {
        this.mGetRoomAmenitiesInterface = mGetRoomAmenitiesInterface;
    }

    public void getAmeniies(int price){
        ApiService.apiService.getRoomAmenities(price).enqueue(new Callback<List<RoomAmenities>>() {
            @Override
            public void onResponse(Call<List<RoomAmenities>> call, Response<List<RoomAmenities>> response) {
                if (response.isSuccessful()){
                    mGetRoomAmenitiesInterface.getAmenities(response.body().get(0));
                    Log.e("Amenities", "" + response.code());
                }else {
                    mGetRoomAmenitiesInterface.getAmenities(null);
                    Log.e("Amenities", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RoomAmenities>> call, Throwable t) {
                Log.e("Amenities", "" + t.getMessage());
            }
        });
    }
}
