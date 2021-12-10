package com.fpoly.managebookings.views.orderBookingDetail;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.managebookings.MainActivity;
import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderDetailAdapter;
import com.fpoly.managebookings.api.orderDetail.ApiOrderDetail;
import com.fpoly.managebookings.api.orderDetail.ApiOrderDetailInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.orderRoomBooked.ResponseGetOrderInterface;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.models.OrderDetail;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderConfirmedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderOccupiedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OrderBookingDetailActivity extends AppCompatActivity implements OrderBookingDetailInterface,
        ApiRoomDetailInterface,
        ApiOrderDetailInterface,
        ListOrderDetailAdapter.RoomSelectedInterface,
        ResponseGetOrderInterface {
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
    private Toolbar toolbar;
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this);
    private OrderBookingDetailPresenter mOrderBookingDetailPresenter = new OrderBookingDetailPresenter(this);
    private ListOrderDetailAdapter adapter;
    private ArrayList<RoomDetail> listRoomDetails = new ArrayList<>();
    private OrderRoomBooked itemOrderRoomBooked;
    private ApiOrderDetail mApiOrderDetail = new ApiOrderDetail(this);
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private ImageView btn_delete, btn_add;
    private LoadingDialog loadingDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_book_room_detail);
        Intent intent = getIntent();
        itemOrderRoomBooked = (OrderRoomBooked) intent.getSerializableExtra("ORDERROOMBOOKED");

        findViewsById();
        setToolbar();
        setPullRefresh();

        if (itemOrderRoomBooked.getBookingStatus() == 3){
            btn_add.setVisibility(View.INVISIBLE);
        }

        //Create layout for RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        Formater.setButtonWithBookingStatus(itemOrderRoomBooked.getBookingStatus(), btnConfirm, btnCancel);

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
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mApiRoomDetail.updateWhileRemoveOrCheck(itemOrderRoomBooked.get_id(), 0, null);
                                mOrderBookingDetailPresenter.onClickCancel(itemOrderRoomBooked.get_id());
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
                mOrderBookingDetailPresenter.onClickCofirm(itemOrderRoomBooked, listRoomDetails, OrderBookingDetailActivity.this);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OrderBookingDetailActivity.this, ListRoomEmptyActivity.class);
                intent1.putExtra("ORDERROOMBOOKED", itemOrderRoomBooked);
                intent1.putExtra("CONTEXT", "OrderBookingDetailActivity");
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_delete.setVisibility(View.INVISIBLE);
        mApiOrderRoomBooked.getOrderById(itemOrderRoomBooked.get_id());
        if (itemOrderRoomBooked.getBookingStatus() != 3) {
            mApiRoomDetail.getAllRoomByIdBooking(itemOrderRoomBooked.get_id());
        } else {
            mApiOrderDetail.getAllOrderDetail(itemOrderRoomBooked.get_id());

        }

        loadingDialog = new LoadingDialog(OrderBookingDetailActivity.this);
        loadingDialog.startLoadingDialog(2000);
    }


    private void findViewsById() {
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
        toolbar = findViewById(R.id.toolbar);
        btn_add = findViewById(R.id.btn_add);
        btn_delete = findViewById(R.id.btn_delete);
    }

    private void setToolbar() {
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText(getString(R.string.order_Detail));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    void setPullRefresh() {
        final SwipeRefreshLayout pullRefresh = findViewById(R.id.refresh);
        pullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onStart();
                pullRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
        fixSizeForToast.fixSizeToast(updateStatus);
    }

    @Override
    public void onCancelRoom(String cancelStatus) {
        fixSizeForToast.fixSizeToast(cancelStatus);
        onBackPressed();
    }


    @Override
    public void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void getRoomById(ArrayList<RoomDetail> roomDetails) {
        this.listRoomDetails.clear();
        this.listRoomDetails.addAll(roomDetails);
        adapter = new ListOrderDetailAdapter(this, listRoomDetails, this,itemOrderRoomBooked);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getAllRoomByIdBooking(ArrayList<RoomDetail> roomDetails) {
        this.listRoomDetails.clear();
        this.listRoomDetails.addAll(roomDetails);
        adapter = new ListOrderDetailAdapter(this, roomDetails, this,itemOrderRoomBooked);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateWhileRemoveOrder(String message) {
        fixSizeForToast.fixSizeToast(message);
    }

    @Override
    public void getAllOrderDetail(ArrayList<OrderDetail> orderDetails) {
        mApiRoomDetail.getRoomById(orderDetails);

    }

    @Override
    public void listIdRoomSelected(List<RoomDetail> roomIds) {
        if (roomIds.isEmpty()) {
            btn_delete.setVisibility(View.INVISIBLE);
        } else {
            btn_delete.setVisibility(View.VISIBLE);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderBookingDetailActivity.this);
                    alertDialogBuilder.setTitle("Delete Room");
                    alertDialogBuilder.setIcon(R.drawable.ic_delete);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Are you sure you want to delete this room?")
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    int total = 0;
                                    for (int i = 0; i < roomIds.size(); i++) {
                                        mApiRoomDetail.removeRoomFromOrder(roomIds.get(i).getId(), 0, "");
                                        total = total + roomIds.get(i).getRoomPrice();
                                    }
                                    total = itemOrderRoomBooked.getTotalRoomRate() - total;
                                    mApiOrderRoomBooked.updateTotalRoomRate(itemOrderRoomBooked.get_id(),total );
                                    onStart();
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getOrderById(OrderRoomBooked orderRoomBooked) {
        //Fill data from API to TextView
        this.itemOrderRoomBooked = orderRoomBooked;
        tvFullnameUser.setText(orderRoomBooked.getFullName());
        tvPhoneUser.setText(orderRoomBooked.getPhone());
        if (orderRoomBooked.getEmail() != null) {
            tvEmailUser.setText(orderRoomBooked.getEmail());
        }
        try {
            tvDateStart.setText(Formater.formatDateTimeToString(orderRoomBooked.getTimeBookingStart()));
            tvDateEnd.setText(Formater.formatDateTimeToString(orderRoomBooked.getTimeBookingEnd()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvRoomCharge.setText(Formater.getFormatMoney(orderRoomBooked.getTotalRoomRate()));
        tvAdvanceDeposit.setText(Formater.getFormatMoney(orderRoomBooked.getAdvanceDeposit()));
        tvVAT.setText(Formater.getFormatMoney((int) (orderRoomBooked.getTotalRoomRate() * 0.05)));
        mOrderBookingDetailPresenter.getTotal(orderRoomBooked.getTotalRoomRate(), orderRoomBooked.getAdvanceDeposit());
    }
}