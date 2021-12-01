package com.fpoly.managebookings.views.listOrdersCompleted;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrdersAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.databinding.DatePickerBottomSheetBinding;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.tool.DialogExit;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.views.createOrder.CreateOrderActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderConfirmedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderOccupiedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class ListOrdersCompletedActivity extends AppCompatActivity implements ApiOrderBookedInterface, ListOrdersAdapter.ListOrderFilterInterface {
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private ListOrdersAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LoadingDialog loadingDialog;
    private TextView tvTotalPayment, tvTotalOrders;
    private EditText edt_search;
    private ImageView btn_filter;
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private String day, month, year;
    private String dateFilter;
    private ArrayList<OrderRoomBooked> orderRoomBookeds = new ArrayList<>();
    private ArrayList<OrderRoomBooked> orderRoomBookedFilters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders_completed);

        anhXa();

        //pull refresh
        setPullRefresh();

        initializeNavigationView();

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                dialogFilterOrder();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //get All Order Completed with boookingStatus = 3
        mApiOrderRoomBooked.getOrderByBookingStatus(3);

        //Loading Data
        loadingDialog = new LoadingDialog(ListOrdersCompletedActivity.this);
        loadingDialog.startLoadingDialog();
    }

    void dialogFilterOrder() {
        BottomSheetDialog dialog = new BottomSheetDialog(ListOrdersCompletedActivity.this);
        dialog.setContentView(R.layout.dialog_filter_statistical);
        dialog.setCancelable(false);

        RadioButton rdoDay, rdoMonth, rdoYear;
        NumberPicker dayPicker, monthPicker, yearPicker;
        TextView btnConfirm;
        ImageView btnCancel;

        rdoDay = (RadioButton) dialog.findViewById(R.id.rdo_day);
        rdoMonth = (RadioButton) dialog.findViewById(R.id.rdo_month);
        rdoYear = (RadioButton) dialog.findViewById(R.id.rdo_year);
        dayPicker = (NumberPicker) dialog.findViewById(R.id.day_picker);
        monthPicker = (NumberPicker) dialog.findViewById(R.id.month_picker);
        yearPicker = (NumberPicker) dialog.findViewById(R.id.year_picker);
        btnConfirm = dialog.findViewById(R.id.btn_confirm_filter);
        btnCancel = dialog.findViewById(R.id.btn_cancel_filter);

        Calendar cal = Calendar.getInstance();


        dayPicker.setMinValue(1);
        dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));
        dayPicker.setMaxValue(30);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (picker.getValue() == 2) {
                    dayPicker.setMaxValue(28);
                } else if (picker.getValue() == 4 || picker.getValue() == 6 || picker.getValue() == 9 || picker.getValue() == 11) {
                    dayPicker.setMaxValue(30);
                } else {
                    dayPicker.setMaxValue(31);
                }

            }
        });

        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2040);
        yearPicker.setValue(cal.get(Calendar.YEAR));

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (dayPicker.getValue() < 10) {
//                    day = "0" + dayPicker.getValue();
//                } else {
                    day = String.valueOf(dayPicker.getValue());
//                }

//                if (monthPicker.getValue() < 10) {
//                    month = "0" + monthPicker.getValue();
//                } else {
                    month = String.valueOf(monthPicker.getValue());
//                }

                year = "" + yearPicker.getValue();

                if (rdoDay.isChecked()) {
                    dateFilter = day + "/" + month + "/" + year;
                } else if (rdoMonth.isChecked()) {
                    dateFilter = month + "/" + year;
                } else if (rdoYear.isChecked()) {
                    dateFilter = year;
                }
                orderRoomBookedFilters.clear();
                for (int i = 0; i< orderRoomBookeds.size();i++){
                    if (orderRoomBookeds.get(i).getTimeBookingEnd().contains(dateFilter)){
                        orderRoomBookedFilters.add(orderRoomBookeds.get(i));
                    }
                }
                adapter = new ListOrdersAdapter(ListOrdersCompletedActivity.this, orderRoomBookedFilters, ListOrdersCompletedActivity.this);
                recyclerView.setAdapter(adapter);
                tvTotalOrders.setText(String.valueOf(orderRoomBookedFilters.size()));
                int totalPayment = 0;
                for (int i = 0; i < orderRoomBookedFilters.size(); i++) {
                    totalPayment += orderRoomBookedFilters.get(i).getTotalRoomRate();
                }
                tvTotalPayment.setText(Formater.getFormatMoney(totalPayment));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void anhXa() {
        recyclerView = findViewById(R.id.recListOrderCompleted);
        layout = findViewById(R.id.layoutListCompleted);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_complete);
        navigationView = findViewById(R.id.nav_view_complete);
        tvTotalOrders = findViewById(R.id.tv_total_orders);
        tvTotalPayment = findViewById(R.id.tv_total_payment);
        edt_search = findViewById(R.id.edt_search);
        btn_filter = findViewById(R.id.btn_filter);
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

    void searchOrderByPhone() {
        edt_search.setText("");
        if (edt_search != null) {
            edt_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
//                    dateFilter = "";
//                    adapter = new ListOrdersAdapter(ListOrdersCompletedActivity.this, orderRoomBookeds, ListOrdersCompletedActivity.this,dateFilter);
//                    recyclerView.setAdapter(adapter);
                    adapter.getFilter().filter(s);
                }
            });
        }
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
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListOrderWaitingActivity.class));
                        break;
                    case R.id.confirm_order:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListOrderConfirmedActivity.class));
                        break;
                    case R.id.occupied_order:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListOrderOccupiedActivity.class));
                        break;
                    case R.id.orders_completed:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListOrdersCompletedActivity.class));
                        break;
                    case R.id.list_rooms:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListRoomEmptyActivity.class));
                        break;
                    case R.id.create_order:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, CreateOrderActivity.class));
                        break;
                    case R.id.menu_exit:
                        DialogExit dialogExit = new DialogExit();
                        dialogExit.exit(ListOrdersCompletedActivity.this);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }


    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {
        if (!list.isEmpty()) {
            this.orderRoomBookeds.clear();
            this.orderRoomBookeds.addAll(list);
            btn_filter.setVisibility(View.VISIBLE);
            edt_search.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new ListOrdersAdapter(this, list, this);
            recyclerView.setAdapter(adapter);
            tvTotalOrders.setText(String.valueOf(list.size()));
            int totalPayment = 0;
            for (int i = 0; i < list.size(); i++) {
                totalPayment += list.get(i).getTotalRoomRate();
            }
            tvTotalPayment.setText(Formater.getFormatMoney(totalPayment));
            searchOrderByPhone();
        } else {
            btn_filter.setVisibility(View.INVISIBLE);
            edt_search.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.background_empty);
        }
    }

    @Override
    public void responseCreateOrder(OrderRoomBooked orderRoomBooked) {

    }

    @Override
    public void listOrderFilter(ArrayList<OrderRoomBooked> orderRoomBookeds) {
        if (orderRoomBookeds.isEmpty()){
            tvTotalOrders.setText(0);
            int totalPayment = 0;
            tvTotalPayment.setText(Formater.getFormatMoney(totalPayment));
        }else {
            tvTotalOrders.setText(String.valueOf(orderRoomBookeds.size()));
            int totalPayment = 0;
            for (int i = 0; i < orderRoomBookeds.size(); i++) {
                totalPayment += orderRoomBookeds.get(i).getTotalRoomRate();
            }
            tvTotalPayment.setText(Formater.getFormatMoney(totalPayment));
        }
    }
}