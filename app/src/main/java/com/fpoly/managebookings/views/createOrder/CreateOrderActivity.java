package com.fpoly.managebookings.views.createOrder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.fpoly.managebookings.tool.DialogExit;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderConfirmedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderOccupiedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CreateOrderActivity extends AppCompatActivity implements CreateOrderInterface {
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditText edtFullname;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private LinearLayout btnBookingStart;
    private TextView tvTimeBookingStart, tv_error_fullname;
    private TextView tvDateBookingStart;
    private LinearLayout btnBookingEnd;
    private TextView tvTimeBookingEnd;
    private TextView tvDateBookingEnd;
    private Button btnCreateOrder;
    private Button btnCancelOrder;
    private CreateOrderPresenter mCreateOrderPresenter = new CreateOrderPresenter(this);
    private String dateStart ="", dateEnd ="";
    private Date now = Calendar.getInstance().getTime();
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        anhXa();
        initializeNavigationView();

//        tvTimeBookingStart.setText(now.getHours() + ":" + now.getMinutes());
//        tvDateBookingStart.setText(now.getDay() + "/" + (now.getMonth()+1) + "/" + now.getYear());
//
//        tvTimeBookingEnd.setText(now.getHours() + ":" + now.getMinutes());
//        tvDateBookingEnd.setText(now.getDay() + "/" + (now.getMonth()+1) + "/" + now.getYear());

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                OrderRoomBooked orderRoomBooked = new OrderRoomBooked(edtFullname.getText().toString(),
                        edtPhoneNumber.getText().toString(),
                        edtEmail.getText().toString(),
                        dateStart,
                        dateEnd,
                        0, 0, 0);
                try {
                    mCreateOrderPresenter.onClickCreate(orderRoomBooked, CreateOrderActivity.this);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("orderRoomBooked", orderRoomBooked.toString());
            }
        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnBookingStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CreateOrderActivity.this);
                bottomSheetDialog.setContentView(R.layout.date_picker_bottom_sheet);

                TimePicker timePicker = bottomSheetDialog.findViewById(R.id.time_picker);
                DatePicker datePicker = bottomSheetDialog.findViewById(R.id.date_picker);
                TextView tvDone = bottomSheetDialog.findViewById(R.id.tv_done);
                timePicker.setIs24HourView(true);
                datePicker.setMinDate(new Date().getTime());
                if (!dateStart.isEmpty()) {
                    try {
                        timePicker.setHour(Formater.formatToDateTime(dateStart).getHours());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                tvDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateStart = timePicker.getHour() + ":" + timePicker.getMinute() + " " + datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
                        tvTimeBookingStart.setText(timePicker.getHour() + ":" + timePicker.getMinute());
                        tvDateBookingStart.setText(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getYear());
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.show();
            }
        });

        btnBookingEnd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CreateOrderActivity.this);
                bottomSheetDialog.setContentView(R.layout.date_picker_bottom_sheet);

                TimePicker timePicker = bottomSheetDialog.findViewById(R.id.time_picker);
                DatePicker datePicker = bottomSheetDialog.findViewById(R.id.date_picker);
                TextView tvDone = bottomSheetDialog.findViewById(R.id.tv_done);
                timePicker.setIs24HourView(true);
                datePicker.setMinDate(new Date().getTime());
                if (!dateEnd.isEmpty()) {
                    try {
                        timePicker.setHour(Formater.formatToDateTime(dateEnd).getHours());
                        datePicker.setMinDate(Formater.formatToDateTime(dateStart).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                tvDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateEnd = timePicker.getHour() + ":" + timePicker.getMinute() + " " + datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
                        tvTimeBookingEnd.setText(timePicker.getHour() + ":" + timePicker.getMinute());
                        tvDateBookingEnd.setText(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1 )+ "/" + datePicker.getYear());
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();
            }
        });

        edtFullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 6) {
                    tv_error_fullname.setText(R.string.error_fullname);
                } else {
                    tv_error_fullname.setText("");
                }
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
        tv_error_fullname = findViewById(R.id.tv_error_fullname);
        drawerLayout = findViewById(R.id.drawer_layout_createOrder);
        navigationView = findViewById(R.id.nav_view_createOrder);
    }

    private void initializeNavigationView() {
        toolbar.setTitle(getString(R.string.orders_comleted));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_waiting:
                        startActivity(new Intent(CreateOrderActivity.this, ListOrderWaitingActivity.class));
                        break;
                    case R.id.confirm_order:
                        startActivity(new Intent(CreateOrderActivity.this, ListOrderConfirmedActivity.class));
                        break;
                    case R.id.occupied_order:
                        startActivity(new Intent(CreateOrderActivity.this, ListOrderOccupiedActivity.class));
                        break;
                    case R.id.orders_completed:
                        startActivity(new Intent(CreateOrderActivity.this, ListOrdersCompletedActivity.class));
                        break;
                    case R.id.list_rooms:
                        startActivity(new Intent(CreateOrderActivity.this, ListRoomEmptyActivity.class));
                        break;
                    case R.id.create_order:
                        startActivity(new Intent(CreateOrderActivity.this, CreateOrderActivity.class));
                        break;
                    case R.id.menu_exit:
                        DialogExit dialogExit = new DialogExit();
                        dialogExit.exit(CreateOrderActivity.this);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
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
        intent.putExtra("ORDERROOMBOOKED", orderRoomBooked);
        intent.putExtra("CONTEXT", "CreateOrderActivity");
        startActivity(intent);
    }
}