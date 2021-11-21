package com.fpoly.managebookings.views.orderBookingDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.managebookings.MainActivity;
import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderDetailAdapter;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;

import java.util.ArrayList;

public class OrderBookingDetailActivity extends AppCompatActivity implements OrderBookingDetailInterface, ApiRoomDetailInterface {
    private TextView tvEmailUser;
    private TextView tvDateStart;
    private TextView tvDateEnd;
    private TextView tvFullnameUser;
    private TextView tvPhoneUser;
    private RecyclerView recyclerView;
    private Button btnConfirm, btnCancel;
    private TextView tvRoomCharge;
    private TextView tvTotal;
    private TextView tvVAT;
    private TextView tvAdvanceDeposit;
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this);
    private OrderBookingDetailPresenter mOrderBookingDetailPresenter = new OrderBookingDetailPresenter(this);
    private ListOrderDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_book_room_detail);
        Intent intent = getIntent();
        OrderRoomBooked itemOrderRoomBooked = (OrderRoomBooked) intent.getSerializableExtra("ORDERROOMBOOKED");
        mApiRoomDetail.getAllRoomByIdBooking(itemOrderRoomBooked.get_id());

        anhXa();
        //Create layout for RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        //Create Button back on ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Fill data from API to TextView
        tvFullnameUser.setText(itemOrderRoomBooked.getFullName());
        tvPhoneUser.setText(itemOrderRoomBooked.getPhone());
        tvDateStart.setText(Formater.formatToDateTime(itemOrderRoomBooked.getTimeBookingStart()));
        tvDateEnd.setText(Formater.formatToDateTime(itemOrderRoomBooked.getTimeBookingEnd()));
        tvRoomCharge.setText(Formater.getFormatMoney(itemOrderRoomBooked.getTotalRoomRate()));
        tvAdvanceDeposit.setText(Formater.getFormatMoney(itemOrderRoomBooked.getAdvanceDeposit()));
        tvVAT.setText(Formater.getFormatMoney((int) (itemOrderRoomBooked.getTotalRoomRate() * 0.05)));
        mOrderBookingDetailPresenter.getTotal(itemOrderRoomBooked.getTotalRoomRate(), itemOrderRoomBooked.getAdvanceDeposit());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderBookingDetailActivity.this);
                alertDialogBuilder.setTitle("Delete Order");
                alertDialogBuilder.setIcon(R.drawable.ic_delete);

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to delete this order?")
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mOrderBookingDetailPresenter.onClickCancel(itemOrderRoomBooked.get_id());
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderBookingDetailPresenter.onClickCofirm(itemOrderRoomBooked.get_id(),1);
            }
        });
    }

    private void anhXa() {
        tvEmailUser = findViewById(R.id.tvEmailUser);
        tvDateStart = findViewById(R.id.tvDateStart);
        tvDateEnd = findViewById(R.id.tvDateEnd);
        tvFullnameUser = findViewById(R.id.tvFullnameUser);
        tvPhoneUser = findViewById(R.id.tvPhoneUser);
        recyclerView = findViewById(R.id.recyclerView);
        btnConfirm = findViewById(R.id.btnConfirm);
        tvRoomCharge = findViewById(R.id.tvRoomCharge);
        tvTotal = findViewById(R.id.tvTotal);
        tvVAT = findViewById(R.id.tvVAT);
        tvAdvanceDeposit = findViewById(R.id.tvAdvanceDeposit);
        btnCancel = findViewById(R.id.btnCancelRoom);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getTotal(int total) {
        tvTotal.setText(Formater.getFormatMoney(total));
    }

    @Override
    public void onConfirmSuccess(String updateStatus) {
        Toast.makeText(this, ""+updateStatus, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ListOrderWaitingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCancelRoom(String cancelStatus) {
        Toast.makeText(this, "" + cancelStatus, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ListOrderWaitingActivity.class);
        startActivity(intent);
    }


    @Override
    public void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void getAllRoomByIdBooking(ArrayList<RoomDetail> roomDetails) {
        adapter = new ListOrderDetailAdapter(this,roomDetails);
        recyclerView.setAdapter(adapter);
    }
}