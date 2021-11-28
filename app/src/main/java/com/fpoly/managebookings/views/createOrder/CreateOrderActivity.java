package com.fpoly.managebookings.views.createOrder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CreateOrderActivity extends AppCompatActivity implements CreateOrderInterface {
    private Toolbar toolbar;
    private EditText edtFullname;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private LinearLayout btnBookingStart;
    private TextView tvTimeBookingStart;
    private TextView tvDateBookingStart;
    private LinearLayout btnBookingEnd;
    private TextView tvTimeBookingEnd;
    private TextView tvDateBookingEnd;
    private Button btnCreateOrder;
    private Button btnCancelOrder;
    private CreateOrderPresenter mCreateOrderPresenter = new CreateOrderPresenter(this);
    private String dateStart, dateEnd;
    private Date now = Calendar.getInstance().getTime();
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        anhXa();
        setToolbar();

        dateStart = now.toString();
        dateEnd = now.toString();

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                OrderRoomBooked orderRoomBooked = new OrderRoomBooked(edtFullname.getText().toString().trim(),
                        edtPhoneNumber.getText().toString().trim(),
                        edtEmail.getText().toString().trim(),
                        dateStart,
                        dateEnd,
                        0, 0, 0);
                try {
                    mCreateOrderPresenter.onClickCreate(orderRoomBooked);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("orderRoomBooked",orderRoomBooked.toString());
            }
        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnBookingStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CreateOrderActivity.this);
                bottomSheetDialog.setContentView(R.layout.date_picker_bottom_sheet);

                TimePicker timePicker = bottomSheetDialog.findViewById(R.id.time_picker);
                DatePicker datePicker = bottomSheetDialog.findViewById(R.id.date_picker);
                TextView tvDone = bottomSheetDialog.findViewById(R.id.tv_done);

                tvDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateStart = timePicker.getHour() + ":" + timePicker.getMinute() + " " + datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
                        tvTimeBookingStart.setText(timePicker.getHour() + ":" + timePicker.getMinute());
                        tvDateBookingStart.setText(datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear());
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.show();
            }
        });

        btnBookingEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CreateOrderActivity.this);
                bottomSheetDialog.setContentView(R.layout.date_picker_bottom_sheet);

                TimePicker timePicker = bottomSheetDialog.findViewById(R.id.time_picker);
                DatePicker datePicker = bottomSheetDialog.findViewById(R.id.date_picker);
                TextView tvDone = bottomSheetDialog.findViewById(R.id.tv_done);

                tvDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateEnd = timePicker.getHour() + ":" + timePicker.getMinute() + " " + datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
                        tvTimeBookingEnd.setText(timePicker.getHour() + ":" + timePicker.getMinute());
                        tvDateBookingEnd.setText(datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear());
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();
            }
        });

    }

    void anhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtFullname = (EditText) findViewById(R.id.edt_fullname);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_phone_number);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        btnBookingStart = (LinearLayout) findViewById(R.id.btn_bookingStart);
        tvTimeBookingStart = (TextView) findViewById(R.id.tv_time_bookingStart);
        tvDateBookingStart = (TextView) findViewById(R.id.tv_date_bookingStart);
        btnBookingEnd = (LinearLayout) findViewById(R.id.btn_bookingEnd);
        tvTimeBookingEnd = (TextView) findViewById(R.id.tv_time_bookingEnd);
        tvDateBookingEnd = (TextView) findViewById(R.id.tv_date_bookingEnd);
        btnCreateOrder = (Button) findViewById(R.id.btn_create_order);
        btnCancelOrder = (Button) findViewById(R.id.btn_cancel_order);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.order_create));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void emptyData(String alert) {
        fixSizeForToast.fixSizeToast(alert);
    }

    @Override
    public void checkTimeBooking(String alert) {
        fixSizeForToast.fixSizeToast(alert);
    }

    @Override
    public void createSuccess(OrderRoomBooked orderRoomBooked) {
        fixSizeForToast.fixSizeToast("Create Order Success!");
        Intent intent = new Intent(this, ListRoomEmptyActivity.class);
        intent.putExtra("IDBOOKING", orderRoomBooked.get_id());
        startActivity(intent);
    }
}