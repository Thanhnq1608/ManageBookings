package com.fpoly.managebookings.api.orderRoomBooked;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.ResponseMessage;
import com.fpoly.managebookings.models.updateOrder.UpdateBookingStatus;
import com.fpoly.managebookings.models.updateOrder.UpdateTotalRoomRate;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiOrderRoomBooked {
    private ApiOrderBookedInterface apiOrderBookedInterface;
    private ApiOrderBookingDetailInterface mApiOrderBookingDetailInterface;
    private ResponseGetOrderInterface mResponseGetOrderInterface;

    public ApiOrderRoomBooked() {
    }

    public ApiOrderRoomBooked(ResponseGetOrderInterface mResponseGetOrderInterface) {
        this.mResponseGetOrderInterface = mResponseGetOrderInterface;
    }

    public ApiOrderRoomBooked(ApiOrderBookingDetailInterface mApiOrderBookingDetailInterface) {
        this.mApiOrderBookingDetailInterface = mApiOrderBookingDetailInterface;
    }

    public ApiOrderRoomBooked(ApiOrderBookedInterface getOrderBookedInterface) {
        this.apiOrderBookedInterface = getOrderBookedInterface;
    }

    public void createOrder(OrderRoomBooked orderRoomBooked, Context context) {
        ApiService.apiService.createOrder(orderRoomBooked.getFullName(),
                orderRoomBooked.getPhone(),
                orderRoomBooked.getEmail(),
                orderRoomBooked.getTimeBookingStart(),
                orderRoomBooked.getTimeBookingEnd(),
                orderRoomBooked.getBookingStatus(),
                orderRoomBooked.getTotalRoomRate(),
                orderRoomBooked.getAdvanceDeposit()).enqueue(new Callback<OrderRoomBooked>() {
            @Override
            public void onResponse(Call<OrderRoomBooked> call, Response<OrderRoomBooked> response) {
                if (response.isSuccessful()) {
                    apiOrderBookedInterface.responseCreateOrder(response.body());
                    Log.e("orderRoomBooked", "" + response.body());
                } else if (response.code() == 400) {
                    Log.e("createOrder", response.message());
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e("Loi", "" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderRoomBooked> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
    }

    public void getOrderById(String id) {
        ApiService.apiService.getOrderRoomById(id).enqueue(new Callback<OrderRoomBooked>() {
            @Override
            public void onResponse(Call<OrderRoomBooked> call, Response<OrderRoomBooked> response) {
                if (response.isSuccessful()) {
                    mResponseGetOrderInterface.getOrderById(response.body());
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

    public void getOrderByBookingStatus(int bookingStatus) {
        ArrayList<OrderRoomBooked> list = new ArrayList<>();
        ApiService.apiService.getOrderRoom(bookingStatus).enqueue(new Callback<List<OrderRoomBooked>>() {
            @Override
            public void onResponse(Call<List<OrderRoomBooked>> call, Response<List<OrderRoomBooked>> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body());
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

    public void changeBookingStatus(String id, int bookingStatus) {
        ApiService.apiService.changeBookingStatus(id, bookingStatus).enqueue(new Callback<UpdateBookingStatus>() {
            @Override
            public void onResponse(Call<UpdateBookingStatus> call, Response<UpdateBookingStatus> response) {
                if (response.isSuccessful()) {
                    Log.e("ChangeBookingStatus", "" + response.body().getMessage());
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

    public void updateTotalRoomRate(String id, int totalRoomRate) {
        ApiService.apiService.updatePaymentWhileUpdateRoom(id, totalRoomRate).enqueue(new Callback<UpdateTotalRoomRate>() {
            @Override
            public void onResponse(Call<UpdateTotalRoomRate> call, Response<UpdateTotalRoomRate> response) {
                if (response.isSuccessful()) {
                    Log.e("UpdateTotalRoomRate", "" + response.body().getData());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UpdateTotalRoomRate> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
    }

    public void update(String id, JsonObject jsonObject) {
        ApiService.apiService.update(id, jsonObject).enqueue(new Callback<UpdateTotalRoomRate>() {
            @Override
            public void onResponse(Call<UpdateTotalRoomRate> call, Response<UpdateTotalRoomRate> response) {
                if (response.isSuccessful()) {
                    Log.e("UpdateTotalRoomRate", "" + response.body().getData());
                } else {
                    Log.e("Loi", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UpdateTotalRoomRate> call, Throwable t) {
                Log.e("Error:", "" + t.getMessage());
            }
        });
    }
}
