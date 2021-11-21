package com.fpoly.managebookings.views.listOrderWaiting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderWaitingAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;

public class ListOrderWaitingActivity extends AppCompatActivity implements ApiOrderBookedInterface {
    private RecyclerView recView;
    private ListOrderWaitingAdapter adapter;
    private ApiOrderRoomBooked getOrderWaiting = new ApiOrderRoomBooked(this);
    private ArrayList<OrderRoomBooked> orderRoomBookeds = new ArrayList<>();
    private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_waiting);
        getSupportActionBar().setTitle("Waiting List");

        getOrderWaiting.getOrderByBookingStatus(0);
        recView = findViewById(R.id.recyclerHero);
        layout = findViewById(R.id.layoutListWaiting);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {
        if (!list.isEmpty()){
            orderRoomBookeds.addAll(list);
            adapter = new ListOrderWaitingAdapter(ListOrderWaitingActivity.this,orderRoomBookeds);
            recView.setAdapter(adapter);
        }else {
            recView.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.background_empty);
        }
    }

    @Override
    public void getOrderCompleted(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderConfirmed(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderOccupied(ArrayList<OrderRoomBooked> list) {

    }
}