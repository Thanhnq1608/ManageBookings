package com.fpoly.managebookings.views.listOrderWaiting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderWaitingAdapter;
import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.api.orderRoomBooked.GetAllOrderRoomBookedWaiting;
import com.fpoly.managebookings.api.orderRoomBooked.GetOrderBookedInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOrderWaitingActivity extends AppCompatActivity implements GetOrderBookedInterface {
    private RecyclerView recView;
    private ListOrderWaitingAdapter adapter;
    private GetAllOrderRoomBookedWaiting getOrderWaiting = new GetAllOrderRoomBookedWaiting(this);
    private ArrayList<OrderRoomBooked> orderRoomBookeds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_waiting);

        getOrderWaiting.getListAllUser(0);
        recView = findViewById(R.id.recyclerHero);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {
        orderRoomBookeds.addAll(list);
        adapter = new ListOrderWaitingAdapter(ListOrderWaitingActivity.this,orderRoomBookeds);
        recView.setAdapter(adapter);
    }
}