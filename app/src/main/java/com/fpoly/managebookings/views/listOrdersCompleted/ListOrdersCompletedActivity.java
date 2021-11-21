package com.fpoly.managebookings.views.listOrdersCompleted;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderWaitingAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;

public class ListOrdersCompletedActivity extends AppCompatActivity implements ApiOrderBookedInterface {
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private ListOrderWaitingAdapter adapter;
    private RecyclerView recyclerView;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders_completed);

        mApiOrderRoomBooked.getOrderByBookingStatus(3);
        recyclerView= findViewById(R.id.recListOrderCompleted);
        layout = findViewById(R.id.layoutListCompleted);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderCompleted(ArrayList<OrderRoomBooked> list) {
        if (!list.isEmpty()){
            adapter = new ListOrderWaitingAdapter(this,list);
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.background_empty);
        }
    }

    @Override
    public void getOrderConfirmed(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderOccupied(ArrayList<OrderRoomBooked> list) {

    }
}