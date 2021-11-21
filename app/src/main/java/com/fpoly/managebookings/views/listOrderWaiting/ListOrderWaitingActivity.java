package com.fpoly.managebookings.views.listOrderWaiting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderWaitingAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBookedWaiting;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;

public class ListOrderWaitingActivity extends AppCompatActivity implements ApiOrderBookedInterface {
    private RecyclerView recView;
    private ListOrderWaitingAdapter adapter;
    private ApiOrderRoomBookedWaiting getOrderWaiting = new ApiOrderRoomBookedWaiting(this);
    private ArrayList<OrderRoomBooked> orderRoomBookeds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_waiting);
        getSupportActionBar().setTitle("Waiting List");

        getOrderWaiting.getListAllOrder(0);
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